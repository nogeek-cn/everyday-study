package com.darian.semaphore;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.Semaphore;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/16  下午10:31
 */
public class SemaphoreCarDemo {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 20; i++) {
            new Car(i, semaphore).start();
        }

    }
}

class Car extends Thread {

    private final Semaphore semaphore;

    public Car(int i, Semaphore semaphore) {
        this.semaphore = semaphore;

        setName("Car-" + String.format("%03d", i));

    }

    @Override
    public void run() {
        System.out.println(String.format("[%s]\t[%s]\t等待车位.....",
                TimeUtils.getNowString(), Thread.currentThread().getName()));

        try {
            // 获得令牌，没有获得令牌就阻塞，拿到了就可以往下执行
            semaphore.acquire();
            System.out.println(String.format("[%s]\t[%s]\t抢到车位.....",
                    TimeUtils.getNowString(), Thread.currentThread().getName()));
            // 在车位上等待 2000 ms
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println(String.format("[%s]\t[%s]\t离开车位.....",
                    TimeUtils.getNowString(), Thread.currentThread().getName()));
            semaphore.release();
        }
    }
}

class TimeUtils {
    public static String getNowString() {
//        LocalDateTime.now();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SS").format(LocalDateTime.now());
    }
}
