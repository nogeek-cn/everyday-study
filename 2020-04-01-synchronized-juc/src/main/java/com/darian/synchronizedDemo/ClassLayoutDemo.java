package com.darian.synchronizedDemo;

import org.openjdk.jol.info.ClassLayout;

/**
 * 偏向锁 -> 轻量级锁 -> 重量级锁
 *
 * 打开偏向锁：-XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *
 * 偏向锁 默认关闭
 * 计算一次 hashCode() 就没有偏向锁了
 */
public class ClassLayoutDemo {
    public static void main(String[] args) {

        ClassLayoutDemo classLayoutDemo = new ClassLayoutDemo();
        synchronized (classLayoutDemo) {
            System.out.println(String.format("[%s][%s]lock()...",
                    System.currentTimeMillis(),
                    Thread.currentThread().getName()
                    )
            );
            System.out.println(ClassLayout.parseInstance(classLayoutDemo).toPrintable());

        }

        // VM 参数： -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0


        //com.darian.synchronizedDemo.ClassLayoutDemo object internals:
        // OFFSET  SIZE   TYPE DESCRIPTION                               VALUE
        //      0     4        (object header)                           05 40 71 2d (00000101 01000000 01110001 00101101) (762396677)
        //      4     4        (object header)                           9e 01 00 00 (10011110 00000001 00000000 00000000) (414)
        //      8     4        (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
        //     12     4        (loss due to the next object alignment)
        //Instance size: 16 bytes
        //Space losses: 0 bytes internal + 4 bytes external = 4 bytes total

        /**
         * 解释：
         *OFFSET  SIZE   TYPE DESCRIPTION                               VALUE               101：偏向锁
         *       0     4        (object header)                           05 40 71 2d (00000101 01000000 01110001 00101101) (762396677)
         *       4     4        (object header)                           9e 01 00 00 (10011110 00000001 00000000 00000000) (414)
         *       8     4        (object header)                           05 c1 00 f8 (00000101 11000001 00000000 11111000) (-134168315)
         *      12     4        (loss due to the next object alignment)
         *
         */
    }
}
