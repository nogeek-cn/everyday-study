package com.darian.checkLogStatus;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.apache.xml.internal.serialize.OutputFormat.Defaults;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.AsyncContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/***
 * http://localhost:8080/login/checkNewLoginStatus
 * http://localhost:8080/login/loginInto
 * http://localhost:8080/login/checkCurrentLoginStatus
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/17  上午2:35
 */
@RestController
@RequestMapping("/login")
public class CheckLoginStatusController {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Resource
    private ConfigurableApplicationContext applicationContext;

    /**
     * 为了测试方便，这里全局 用户Id
     */
    public static String currentUUIdString;

    @Resource
    private LongPollingCheckLoginStatusService longPollingCheckLoginStatusService;

    //@GetMapping("/checkCurrentLoginStatus")
    //public void checkCurrentLoginStatus(HttpServletRequest request) {
    //    LOGGER.info(String.format("用户[%s]检查状态。。。", currentUUIdString));
    //
    //    longPollingCheckLoginStatusService.addLongPollingClient(request, currentUUIdString);
    //    return;
    //}

    /**
     * 扫第三方码以后，检查登录状态
     *
     * @param request
     */
    @GetMapping("/checkNewLoginStatus")
    public void checkNewLoginStatus(HttpServletRequest request) {
        String uuidString = UUID.randomUUID().toString().replace("-", "");
        currentUUIdString = uuidString;
        LOGGER.info(String.format("用户[%s]检查状态。。。", currentUUIdString));
        longPollingCheckLoginStatusService.addLongPollingClient(request, currentUUIdString);
        return;
    }

    /**
     * 第三方登录回调接口
     *
     * @return
     */
    @GetMapping("/loginInto")
    public ResponseBody loginInto() {
        LOGGER.info(String.format("用户[%s]登陆成功。。。", currentUUIdString));

        String changeStatus = "第三方通知本服务：登录成功";

        LoginStatusChangeEvent loginStatusChangeEvent = new LoginStatusChangeEvent(this);
        loginStatusChangeEvent.setCacheKey(currentUUIdString);
        loginStatusChangeEvent.setChangeStatus(changeStatus);
        LoginStatusRepository.USER_ID_STATUS_MYSQL_DATABASE_MAP.put(currentUUIdString, changeStatus);
        applicationContext.publishEvent(loginStatusChangeEvent);
        return new ResponseBody("success", "登陆成功");
    }
}

@Service
class LongPollingCheckLoginStatusService implements ApplicationListener<LoginStatusChangeEvent> {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1, new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("com.darian.live.longPolling");
            return t;
        }
    });

    final LinkedBlockingQueue<ClientLongPolling> clientLongPollingQueue = new LinkedBlockingQueue();

    @Override
    public void onApplicationEvent(LoginStatusChangeEvent event) {
        LOGGER.info(String.format("[listener][LoginStatusChangeEvent][%s]", event.getCacheKey()));
        scheduledExecutorService.submit(new LoginStatusChangeTask(event.getCacheKey(), event.getChangeStatus()));
    }

    public void addLongPollingClient(HttpServletRequest request, String userId) {
        LOGGER.info(String.format("[addLongPollingClient][%s]", userId));
        String longPollingTime = request.getHeader("Long-Polling-Time");
        if (longPollingTime == null || longPollingTime.length() == 0) {
            longPollingTime = "10000"; // 10S
        }
        long timeOut = Long.valueOf(longPollingTime);
        AsyncContext asyncContext = request.startAsync();
        asyncContext.setTimeout(0L);
        ClientLongPolling clientLongPolling = new ClientLongPolling(timeOut, userId, asyncContext);
        scheduledExecutorService.execute(clientLongPolling);
    }

    @RequiredArgsConstructor
    class ClientLongPolling implements Runnable {
        // hold 住请求，延迟返回
        final long timeOutTime;

        final String userId;

        final AsyncContext asyncContext;

        ScheduledFuture<?> asyncTimeoutFeature;

        @Override
        public void run() {
            asyncTimeoutFeature = scheduledExecutorService.schedule(() -> {
                try {
                    boolean remove = clientLongPollingQueue.remove(ClientLongPolling.this);

                    // false 说明 长时间未，需要返回
                    if (remove) {
                        sendResponse(checkUserInfoFromCheck(userId));
                    } else {
                        LOGGER.info(String.format("[状态已经返回了][%s]", userId));
                    }
                } catch (Exception e) {
                    LOGGER.error("[long polling schedule[run] error:", e);
                }
            }, timeOutTime, TimeUnit.MILLISECONDS);// 延后执行
            // 将当前线程长轮询请求添加到队列
            clientLongPollingQueue.add(this);
        }

        public void sendResponse(String userStatus) {
            generateResponse(new ResponseBody("SUCCESS", userStatus));
        }

        void generateResponse(ResponseBody responseBody) {
            LOGGER.info(String.format("[generateResponse][%s]", userId));
            ServletResponse response = asyncContext.getResponse();
            try {
                response.setCharacterEncoding(Defaults.Encoding);
                response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
                PrintWriter writer = response.getWriter();
                ObjectMapper objectMapper = new ObjectMapper();
                String responseBodyString = objectMapper.writeValueAsString(responseBody);
                writer.write(responseBodyString);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                asyncContext.complete();
            }
        }

    }

    private String checkUserInfoFromCheck(String userId) {
        String status = LoginStatusRepository.USER_ID_STATUS_MYSQL_DATABASE_MAP.get(userId);
        if (status == null) {
            return "10秒内，用户没有登陆行为";
        }
        return status;
    }

    @RequiredArgsConstructor
    class LoginStatusChangeTask implements Runnable {

        private Logger LOGGER = LoggerFactory.getLogger(getClass());

        private final String userId;

        private final String changeStatus;

        @Override
        public void run() {
            LOGGER.info(String.format("[LoginStatusChangeTask][%s][run.........]", userId));
            LoginStatusRepository.USER_ID_STATUS_MYSQL_DATABASE_MAP.put(userId, changeStatus);
            // 阻塞队列删除掉
            clientLongPollingQueue.removeIf(clientLongPolling -> {
                if (clientLongPolling.userId.equals(userId)) {
                    clientLongPolling.sendResponse(changeStatus);
                    clientLongPolling.asyncTimeoutFeature.cancel(false);
                    return true;
                }
                return false;
            });
        }

    }

}

@Getter
@Setter
class LoginStatusChangeEvent extends ApplicationEvent {

    private String cacheKey;

    private String changeStatus;

    public LoginStatusChangeEvent(Object source) {
        super(source);
    }

}

class LoginStatusRepository {
    public static Map<String, String> USER_ID_STATUS_MYSQL_DATABASE_MAP = new HashMap();

}

@AllArgsConstructor
@Data
class ResponseBody {

    private String msg;

    private Object dataBody;
}

