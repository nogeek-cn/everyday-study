package com.darian.memory;


import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// 内存池类
public class MemoryPool {
    // 内存缓冲区
    private volatile byte[] buffer;
    // 内存池头节点
    private final MemoryPoolNode header;
    // 块大小
    private final int blockSize;
    // 块数量
    private int blockCount;
    // 内存对齐方式
    private final int alignment;
    // 用于扩容的锁对象，保证线程安全
    private final Lock resizeLock;

    // 构造函数，初始化内存池
    public MemoryPool(int blockSize, int initialBlockCount, int alignment) {
        this.blockSize = blockSize;
        this.blockCount = initialBlockCount;
        this.alignment = alignment;
        this.buffer = new byte[blockSize * initialBlockCount];
        this.header = new MemoryPoolNode(this);
        initializePool();
        this.resizeLock = new ReentrantLock();
    }

    // 对齐内存缓冲区
    private void alignBuffer() {
        int alignedSize = (blockSize * blockCount + alignment - 1) / alignment * alignment;
        if (alignedSize != buffer.length) {
            byte[] newBuffer = new byte[alignedSize];
            System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
            buffer = newBuffer;
        }
    }

    // 初始化内存池节点
    private void initializePool() {
        MemoryPoolNode current = header;
        for (int i = 0; i < blockCount; i++) {
            MemoryPoolNode node = new MemoryPoolNode(this);
            node.setNext(current.getNext());
            current.setNext(node);
            current = node;
        }
    }

    // 获取内存缓冲区
    public byte[] getBuffer() {
        return buffer;
    }

    // 分配内存
    public MemoryPoolNode allocate() {
        MemoryPoolNode current = header.getNext();
        while (current != null) {
            if (!current.isUsed()) {
                Lock lock = current.getLock();
                // 获取节点锁
                lock.lock();
                try {
                    if (!current.isUsed()) {
                        // 设置节点为已使用状态
                        current.setUsed(true);
                        return current;
                    }
                } finally {
                    // 释放节点锁
                    lock.unlock();
                }
            }
            current = current.getNext();
        }
        // 内存池已满，尝试扩容
        if (resizePool()) {
            // 重新分配内存
            return allocate();
        } else {
            // 分配失败
            return null;
        }
    }

    // 释放内存
    public void deallocate(MemoryPoolNode node) {
        Lock lock = node.getLock();
        // 获取节点锁
        lock.lock();
        try {
            if (node.isUsed() && node.isBelong()) {
                // 设置节点为未使用状态
                node.setUsed(false);
                // 合并空闲块
                mergeFreeBlocks();
            } else {
                throw new IllegalArgumentException("Invalid memory block for deallocation");
            }
        } finally {
            // 释放节点锁
            lock.unlock();
        }
    }

    // 合并空闲块
    private void mergeFreeBlocks() {
        MemoryPoolNode current = header;
        while (current != null && current.getNext() != null) {
            MemoryPoolNode next = current.getNext();
            Lock currentLock = current.getLock();
            Lock nextLock = next.getLock();
            currentLock.lock(); // 获取当前节点锁
            nextLock.lock(); // 获取下一个节点锁
            try {
                // 检查当前节点和下一个节点是否都是空闲状态
                if (!current.isUsed() && !next.isUsed()) {
                    // 合并当前节点和下一个节点的下一个节点
                    current.setNext(next.getNext());
                    // 将下一个节点移动到链表末尾
                    appendToEnd(next);
                } else {
                    // 如果当前节点和下一个节点不都是空闲状态，继续检查下一个节点
                    current = next;
                }
            } finally {
                currentLock.unlock(); // 释放当前节点锁
                nextLock.unlock(); // 释放下一个节点锁
            }
        }
    }

    // 将空节点移动到链表末尾
    private void appendToEnd(MemoryPoolNode node) {
        MemoryPoolNode current = header;
        while (current.getNext() != null) {
            current = current.getNext();
        }
        current.setNext(node);
        node.setNext(null);
    }


    // 扩容内存池
    private boolean resizePool() {
        // 获取扩容锁
        resizeLock.lock();
        try {
            // 块数量翻倍
            int newBlockCount = blockCount * 2;
            byte[] newBuffer = new byte[blockSize * newBlockCount];
            if (newBuffer.length > buffer.length) {
                System.arraycopy(buffer, 0, newBuffer, 0, buffer.length);
                // 更新内存缓冲区
                buffer = newBuffer;
                // 更新块数量
                blockCount = newBlockCount;
                // 对齐内存缓冲区
                alignBuffer();
                // 重新初始化内存池
                initializePool();
                // 成功扩容内存池
                return true;
            } else {
                // 扩容失败（内存不足）
                return false;
            }
        } finally {
            // 释放扩容锁
            resizeLock.unlock();
        }
    }

    public int getBlockSize() {
        return blockSize;
    }
}
