package com.darian.volatiledemo;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/14  下午11:42
 */
public class DoubleCheckLockDemo {

    // 不加 volatile 会导致不完整的对象问题
    public static volatile DoubleCheckLockDemo instance;

    private DoubleCheckLockDemo() {
    }

    public static DoubleCheckLockDemo getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    // 三个指令，重排序，我这个对象还没有完全实例化，其他线程看到的已经不为空了，导致返回了不完整的对象
                    instance = new DoubleCheckLockDemo();
                }
            }
        }
        return instance;
    }
}
