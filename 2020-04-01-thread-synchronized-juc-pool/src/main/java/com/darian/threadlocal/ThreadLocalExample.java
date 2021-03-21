package com.darian.threadlocal;

/***
 *
 *
 * @author <a href="1934849492@qq.com">Darian</a> 
 * @date 2020/1/30  21:44
 */
public class ThreadLocalExample {
    static ThreadLocal<Integer> numLocal = ThreadLocal.withInitial(() -> Integer.valueOf(0));
    static ThreadLocal<String> strLocal = ThreadLocal.withInitial(() -> "hello");

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                // 获得 ThreadLocal 中初始化的值
                Integer integer = numLocal.get();
                integer += 5;
                // 设置修改以后得值。
                numLocal.set(integer);
                strLocal.set(strLocal.get() + " world");
                Println.println(integer + strLocal.get());
            }, "thread - [" + (i + 1) + "]").start();
        }
    }

    //thredId: [ 17 ] thredName: [ thread - [4] ]。
    //   - 5
    //thredId: [ 15 ] thredName: [ thread - [2] ]
    //   - 5
    //thredId: [ 14 ] thredName: [ thread - [1] ]
    //   - 5
    //thredId: [ 18 ] thredName: [ thread - [5] ]
    //   - 5
    //thredId: [ 16 ] thredName: [ thread - [3] ]
    //   - 5
}
