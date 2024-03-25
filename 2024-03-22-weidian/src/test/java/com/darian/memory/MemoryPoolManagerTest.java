package com.darian.memory;

import org.junit.Test;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/22  18:53
 */

public class MemoryPoolManagerTest {


    @Test
    public void test() {
        // 创建内存池管理器
        MemoryPoolManager manager = new MemoryPoolManager();

        // 创建内存池，块大小为1024字节，初始块数量为3，对齐方式为4字节
        manager.createMemoryPool(1024, 3, 4);

        // 多线程并发测试
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                // 分配内存
                MemoryPoolNode node = manager.allocate(1024);

                if (node != null) {
                    // 输出当前线程成功分配内存的信息
                    System.out.println("Thread " + Thread.currentThread().getId() + " allocated memory");
                    // 模拟一些操作
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // 释放内存
                    manager.deallocate(node);
                    // 输出当前线程成功释放内存的信息
                    System.out.println("Thread " + Thread.currentThread().getId() + " deallocated memory");
                } else {
                    // 输出当前线程分配内存失败的信息
                    System.out.println("Thread " + Thread.currentThread().getId() + " failed to allocate memory");
                }
            });
            threads[i].start();
        }

        // 等待所有线程执行完成
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
