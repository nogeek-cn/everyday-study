package com.darian.threadlocal;

/***
 *
 *
 * @author <a href="1934849492@qq.com">Darian</a> 
 * @date 2020/1/30  20:47
 */
public class Println {
    public static void println(Object... objects) {
        StringBuffer sb = new StringBuffer();
        sb.append("threadId: [ " + Thread.currentThread().getId() + " ] ");
        sb.append("threadName: [ " + Thread.currentThread().getName() + " ] ");

        for (int i = 0; i < objects.length; i++) {
            sb.append("\n   - " + objects[i]);
        }

        System.out.println(sb.toString());
    }
}
