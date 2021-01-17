package com.darian.join;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/15  上午2:29
 */
public class JoinDemo {
    private static int i = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> i = 30);
        // callable / feature
        thread.start();
        // thread 线程中的执行结果对 main 线程可见
        thread.join(); // happens-before 模型

        // 希望执行结果可见

        System.out.println(i);
    }
}
