package com.darian.memory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/22  18:51
 */
public class MemoryPoolManager {

    private final MemoryPool[] pools;

    public MemoryPoolManager() {
        this.pools = new MemoryPool[16];
    }

    public void createMemoryPool(int blockSize, int initialBlockCount, int alignment) {
        for (int i = 0; i < 16; i++) {
            MemoryPool pool = new MemoryPool(blockSize, initialBlockCount, alignment);
            pools[i] = pool;
        }
    }

    public MemoryPoolNode allocate(int index) {
        MemoryPool pool = pools[index];
        if (pool != null) {
            return pool.allocate();
        } else {
            throw new IllegalArgumentException("Memory pool with specified block size does not exist.");
        }
    }

    public MemoryPool getMemoryPool(int index) {
        MemoryPool pool = pools[index];
        if (pool != null) {
            return pool;
        } else {
            throw new IllegalArgumentException("Memory pool with specified block size does not exist.");
        }
    }

    public void deallocate(MemoryPoolNode node) {
        MemoryPool pool = node.getPool();
        pool.deallocate(node);
    }

    public static void main(String[] args) {
        MemoryPoolManager manager = new MemoryPoolManager();
        manager.createMemoryPool(4,4,4);
    }
}
