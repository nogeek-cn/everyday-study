package com.darian.threadlocal;


import java.util.concurrent.TimeUnit;

/***
 *
 *
 * @author <a href="1934849492@qq.com">Darian</a> 
 * @date 2020/1/30  20:45
 */
public class Example {
    private static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        // 每一个线程拿到的值是不确定的。
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                num += 5;
                Println.println("num = " + num);
            }, "thread-(" + i + ")").start();
        }

        TimeUnit.SECONDS.sleep(1);
    }

    // thredId: [ 18 ] thredName: [ thread-(4) ]
    //   - num = 25
    //thredId: [ 15 ] thredName: [ thread-(1) ]
    //   - num = 10
    //thredId: [ 17 ] thredName: [ thread-(3) ]
    //   - num = 20
    //thredId: [ 14 ] thredName: [ thread-(0) ]
    //   - num = 5
    //thredId: [ 16 ] thredName: [ thread-(2) ]
    //   - num = 15
}
