
package com.darian.volatiledemo;

/***
 * JVM 参数查看一下汇编指令，
 *
 *
 *
 *
 */
public class VolatileArraysDemo {

    private static volatile Object[] objects = new Object[10];

    public static void main(String[] args) {
        // 查看 汇编指令。
        objects[0] = 1;             // 这一行没有 lock 指令
        objects = new Object[2];    // 这一行有 lock 命令
    }
}