package com.darian.volatiledemo;

import java.io.File;

/**
 * 用 hsdis-amd64 工具来进行排查
 *
 * -server -Xcomp -XX:+UnlockDiagnosticVMOptions
 * -XX:+PrintAssembly
 * -XX:CompileCommand=compileonly,`*APP.*`
 *
 * <p>例子：</p>
 * -server -Xcomp -XX:+UnlockDiagnosticVMOptions
 * -XX:+PrintAssembly
 * -XX:CompileCommand=compileonly,`VolatileDemo.*`
 */
public class VolatileDemo {
    public static void main(String[] args) throws InterruptedException {
        // 线程停止不了
        StopFalse.stop();

        //StopTrue.stop();
    }
}

/**
 * 用 volatile 显示可见性
 */
class StopTrue {

    public static volatile boolean stop = false;

    public static void stop() throws InterruptedException {

        Thread thread = new Thread(() -> {
            int i = 0;
            while (!stop) {
                //System.out.println(i);
                i++;

            }
            System.out.println("rs:" + i);
        });
        thread.start();

        Thread.sleep(1_000);
        stop = true;
    }

}

/**
 * 错误的停止实例
 */
class StopFalse {

    public static Boolean stop = false;

    public static void stop() throws InterruptedException {

        Thread thread = new Thread(() -> {
            int i = 0;
            while (!stop) {
                //System.out.println(i);
                i++;

                // 这个 IO 操作会导致同步
                //new File("txt.txt");

                // synchronized 的释放会强制写操作同步到主内存
                //synchronized (StopFalse.class) { }

                //Thread.yield();

                //try {
                //    Thread.sleep(0);
                //} catch (InterruptedException e) {
                //    e.printStackTrace();
                //}
            }
            System.out.println("rs:" + i);
        });
        thread.start();

        Thread.sleep(1_000);
        stop = true;
    }

}