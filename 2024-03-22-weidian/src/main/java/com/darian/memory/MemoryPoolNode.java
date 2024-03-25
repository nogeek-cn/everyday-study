package com.darian.memory;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 内存池节点类
public class MemoryPoolNode {
    private final MemoryPool pool; // 所属内存池
    private MemoryPoolNode next; // 下一个节点
    private boolean used; // 节点是否被使用
    private boolean belong; // 是否属于内存池
    private final Lock lock; // 锁对象，用于保护节点的并发访问

    // 构造函数，初始化节点
    public MemoryPoolNode(MemoryPool pool) {
        this.pool = pool;
        this.next = null;
        this.used = false;
        this.belong = true;
        this.lock = new ReentrantLock(); // 初始化锁对象
    }

    public MemoryPool getPool() {
        return pool;
    }

    public MemoryPoolNode getNext() {
        return next;
    }

    public void setNext(MemoryPoolNode next) {
        this.next = next;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isBelong() {
        return belong;
    }

    public void setBelong(boolean belong) {
        this.belong = belong;
    }

    public Lock getLock() {
        return lock;
    }
}
