package com.darian.threadlocal;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/15  上午2:38
 */
public class ThreadLocalDemo {
    static ThreadLocal<Integer> local = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 0;
        }
    };

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                Integer integer = local.get();
                integer += 5;
                local.set(integer);
                System.out.printf("[%s]:%s\n", Thread.currentThread().getName(), integer);
            }).start();
        }
    }
}
