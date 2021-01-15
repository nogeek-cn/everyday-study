package com.darian.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLockDemo {

}

class ReadWriteLockMap {
    static Map<String, Object> cacheMap = new HashMap<>();

    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    static Lock readLock = rwl.readLock();

    static Lock writeLock = rwl.writeLock();

    public static Object get(String key) {
        readLock.lock(); // 读锁
        try {
            return cacheMap.get(key);
        } finally {
            readLock.unlock(); // 释放读锁
        }
    }

    public static Object write(String key, Object value) {
        writeLock.lock(); // 获得了写锁，
        try {
            return cacheMap.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}