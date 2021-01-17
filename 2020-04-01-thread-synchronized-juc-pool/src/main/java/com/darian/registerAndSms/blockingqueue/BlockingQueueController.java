package com.darian.registerAndSms.blockingqueue;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午5:44
 */
@RestController
@RequestMapping("/blockingQueue/")
public class BlockingQueueController {


    @Resource
    private QueueUserService queueUserService;

    @GetMapping("/register")
    public void register() {
        String name = String.valueOf(new Random().nextInt(1000));
        queueUserService.doRegister(name);
    }

}

@Service
class QueueUserService {

    @Resource
    private SendMsgService sendMsgService;

    public void doRegister(String name) {
        System.out.println(String.format("[%s]:UserService#doRegister:%s",
                Thread.currentThread().getName(), name));
        sendMsgService.sendMsg(name);
    }
}

@Service
class SendMsgService extends Thread {

    public void sendMsg(String name) {
        try {
            queue.put(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static volatile BlockingQueue<String> queue = new LinkedBlockingDeque<>();

    static ExecutorService executorService = Executors.newFixedThreadPool(1);

    {
        init();
    }

    public void init() {
        executorService.submit(() -> {
            while (true) {
                try {
                    String name = queue.take();
                    doSendMsg(name);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void doSendMsg(String name) {
        System.out.println(String.format("[%s]:SendMsgService#sendMsg:%s",
                Thread.currentThread().getName(), name));
    }
}