package com.darian.utils;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2022/11/5  17:32
 */
public class PrintUtils {

    public static void printThread(String printString) {
        System.out.printf("[ThreadName][%s][:][%s]\n", Thread.currentThread().getName(), printString);
    }
}
