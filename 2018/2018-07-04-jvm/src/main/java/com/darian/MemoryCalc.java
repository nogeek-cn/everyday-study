package com.darian;

/**
 * 内存计算
 * <br>Darian
 */
public class MemoryCalc {
    public static void main(String[] args) {
        long maxMemory = Runtime.getRuntime().maxMemory();// 返回 java 虚拟机试图使用得最大内存量。
        long totalMemory = Runtime.getRuntime().totalMemory(); //  返回 Java 虚拟机中得内存总量
        System.out.println("MAX-MEMORY = " + maxMemory + "（字节）" + (maxMemory / (double) 1024 / 1024) + "M");
        System.out.println("TOTAL-MEMORY = " + totalMemory + "（字节）" + (totalMemory / (double) 1024 / 1024) + "M");
    }
}
