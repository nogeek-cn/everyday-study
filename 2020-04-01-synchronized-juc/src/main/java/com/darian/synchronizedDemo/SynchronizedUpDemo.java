package com.darian.synchronizedDemo;

import org.openjdk.jol.info.ClassLayout;

/**
 * 演示锁升级的过程
 */
public class SynchronizedUpDemo {
    public static void main(String[] args) throws InterruptedException {
        new SynchronizedUpThread(1).start();
        Thread.sleep(8000);
        new SynchronizedUpThread(2).start();
        new SynchronizedUpThread(3).start();

        /**
         * 轻量级锁：000
         * 重量级锁：010
         */

        //[SynchronizedUpThread-1]抢占到锁：
        //java.lang.Object object internals:
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //      0     4        (object header)                           f0 f2 ff 61 (11110000 11110010 11111111 01100001) (1644163824)
        //      4     4        (object header)                           72 00 00 00 (01110010 00000000 00000000 00000000) (114)
        //      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
        //     12     4        (loss due to the next object alignment)
        //Instance size: 16 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        //
        //[SynchronizedUpThread-2]抢占到锁：
        //java.lang.Object object internals:
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //      0     4        (object header)                           ea c4 ca a5 (11101010 11000100 11001010 10100101) (-1513437974)
        //      4     4        (object header)                           30 02 00 00 (00110000 00000010 00000000 00000000) (560)
        //      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
        //     12     4        (loss due to the next object alignment)
        //Instance size: 16 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total
        //
        //[SynchronizedUpThread-3]抢占到锁：
        //java.lang.Object object internals:
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //      0     4        (object header)                           ea c4 ca a5 (11101010 11000100 11001010 10100101) (-1513437974)
        //      4     4        (object header)                           30 02 00 00 (00110000 00000010 00000000 00000000) (560)
        //      8     4        (object header)                           e5 01 00 f8 (11100101 00000001 00000000 11111000) (-134217243)
        //     12     4        (loss due to the next object alignment)
        //Instance size: 16 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total



    }
}

class SynchronizedUpThread extends Thread {

    public static Object lockObject = new Object();

    public SynchronizedUpThread(int i) {
        setName("SynchronizedUpThread-" + i);
    }

    @Override
    public void run() {
        synchronized (lockObject) {
            System.out.println(String.format("[%s]抢占到锁：",
                    Thread.currentThread().getName()));
            System.out.println(ClassLayout.parseInstance(lockObject).toPrintable());
        }
    }
}