
package com.darian.redis;

import redis.clients.jedis.Jedis;

/**
 * <groupId>redis.clients</groupId>
 * <artifactId>jedis</artifactId>
 * <version>2.6.3</version>
 */
public class RedisUtils {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("redis.darian.com", 6379);
        jedis.auth("darian");
        jedis.set("username", "zhangsan");

        System.err.println("|-- jedis.set(\"username\", \"zhangsan\");");
        System.err.println("+-- jedis.get(\"username\")");

        System.err.println(jedis.get("username"));
        jedis.close();
    }
}