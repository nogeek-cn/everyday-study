package com.darian.registerAndSms;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.darian.registerAndSms.ThreadUtils.sleepSecond;

@RestController
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping("asyncRegister")
    public ResponseModule asyncRegister() {
        long start = System.currentTimeMillis();
        userService.asyncRegister();
        long end = System.currentTimeMillis();
        return new ResponseModule(end - start);
    }

    @GetMapping("syncRegister")
    public ResponseModule syncRegister() {
        long start = System.currentTimeMillis();
        userService.syncRegister();
        long end = System.currentTimeMillis();
        return new ResponseModule(end - start);
    }

    /**

     ###
     GET http://localhost:8080/asyncRegister

     {
     "cost": 1004
     }

     ###
     GET http://localhost:8080/syncRegister

     {
     "cost": 3021
     }
     **/

}

@Data
@AllArgsConstructor
class ResponseModule {
    private long cost;
}

@Service
class UserService {
    @Resource
    private SMSService smsService;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * 异步注册
     */
    public void asyncRegister() {
        register();
        executorService.submit(() -> smsService.sendMsg());
    }

    public void syncRegister() {
        register();
        smsService.sendMsg();
    }

    private void register() {
        sleepSecond(1);
    }
}

@Service
class SMSService {
    /**
     * 发送短信需要 2 秒
     */
    public void sendMsg() {
        sleepSecond(2);
    }
}

class ThreadUtils {
    public static void sleepSecond(long second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}