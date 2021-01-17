package com.darian.checkLogStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

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

    @Resource
    private ConfigurableApplicationContext applicationContext;

    public static String currentUUIdString;

    @GetMapping("/checkCurrentLoginStatus")
    public Callable<ResponseBody> checkCurrentLoginStatus() {
        System.out.println(String.format("[%s]用户[%s]检查状态。。。",
                Thread.currentThread().getName(), currentUUIdString));
        CheckLoginStatusCallable checkLoginStatusCallable = new CheckLoginStatusCallable(currentUUIdString);
        applicationContext.addApplicationListener(checkLoginStatusCallable);
        return checkLoginStatusCallable;
    }

    @GetMapping("/checkNewLoginStatus")
    public Callable<ResponseBody> checkNewLoginStatus() {
        String uuidString = UUID.randomUUID().toString().replace("-", "");
        currentUUIdString = uuidString;
        System.out.println(String.format("[%s]用户[%s]检查状态。。。",
                Thread.currentThread().getName(), currentUUIdString));
        CheckLoginStatusCallable checkLoginStatusCallable = new CheckLoginStatusCallable(uuidString);
        // 这些 listener 不会移除掉
        applicationContext.addApplicationListener(checkLoginStatusCallable);
        return checkLoginStatusCallable;
    }

    @GetMapping("/loginInto")
    public ResponseBody loginInto() {
        System.out.println(String.format("[%s]用户[%s]登陆成功。。。",
                Thread.currentThread().getName(), currentUUIdString));

        LoginStatusChangeEvent loginStatusChangeEvent = new LoginStatusChangeEvent(this);
        loginStatusChangeEvent.setUuidString(currentUUIdString);
        loginStatusChangeEvent.setUserStatus("loginInto");
        applicationContext.publishEvent(loginStatusChangeEvent);
        return new ResponseBody("success", "登陆成功");
    }
}

@Getter
@Setter
class LoginStatusChangeEvent extends ApplicationEvent {

    private String uuidString;

    private String userStatus;

    public LoginStatusChangeEvent(Object source) {
        super(source);
    }

}

class LoginStatusRepository {
    public static Map<String, String> userId_status_map = new HashMap();

    public static Map<String, Long> userId_status_last_modify_mill_map = new HashMap<>();

}

class CheckLoginStatusCallable
        implements Callable<ResponseBody>, ApplicationListener<LoginStatusChangeEvent> {
    private final String userId;

    private final Lock lock;

    private final Condition condition;


    CheckLoginStatusCallable(String userId) {
        this.userId = userId;
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }


    @Override
    public ResponseBody call() throws Exception {
        String currentUserId = this.userId;
        lock.lock();
        try {
            Long last_modify_long = LoginStatusRepository.userId_status_last_modify_mill_map.get(currentUserId);
            if (last_modify_long == null || (System.currentTimeMillis() - last_modify_long) > 10000) {
                // 代表状态过期
                LoginStatusRepository.userId_status_last_modify_mill_map.remove(currentUserId);
                LoginStatusRepository.userId_status_map.remove(currentUserId);
                System.out.println(String.format("[%s]:开始检查用户[%s]状态.....",
                        Thread.currentThread().getName(), currentUserId));
                condition.await(10, TimeUnit.SECONDS);
                String userStatus = LoginStatusRepository.userId_status_map.get(currentUserId);
                if (userStatus == null || userStatus.length() == 0) {
                    userStatus = "10秒内，用户没有登陆行为";
                }
                System.out.println(String.format("[%s]:检查用户[%s]状态结果：[%s].....",
                        Thread.currentThread().getName(), currentUserId, userStatus));
                return new ResponseBody("success", userStatus);
            } else {
                return new ResponseBody("success", LoginStatusRepository.userId_status_map.get(currentUserId));
            }
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onApplicationEvent(LoginStatusChangeEvent event) {
        String currentUserId = this.userId;
        if (currentUserId.equals(event.getUuidString())) {
            lock.lock();
            LoginStatusRepository.userId_status_map.put(currentUserId, event.getUserStatus());
            LoginStatusRepository.userId_status_last_modify_mill_map.put(currentUserId, System.currentTimeMillis());
            condition.signal();
            lock.unlock();
        }
    }
}

@AllArgsConstructor
@Data
class ResponseBody {
    private String msg;

    private Object dataBody;
}

