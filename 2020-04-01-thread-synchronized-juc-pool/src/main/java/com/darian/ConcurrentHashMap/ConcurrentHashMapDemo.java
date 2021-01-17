package com.darian.ConcurrentHashMap;

import java.util.concurrent.ConcurrentHashMap;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian</a>
 * @date 2021/1/17  下午2:03
 */
public class ConcurrentHashMapDemo {
    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        concurrentHashMap.put("key_1", "value_1");
        concurrentHashMap.put("key_2", "value_2");
    }
}
