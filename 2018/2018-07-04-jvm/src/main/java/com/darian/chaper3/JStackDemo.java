package com.darian.chaper3;

import java.util.Map;

public class JStackDemo {

    public static void main(String[] args) {
        Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
        for (Map.Entry<Thread,StackTraceElement[]> entry : allStackTraces.entrySet()){
            Thread t = entry.getKey();
            StackTraceElement[] value = entry.getValue();
            System.out.println("Thread name is " + t.getName());
            for (StackTraceElement element : value){
                System.err.println("\t" + element.toString());
            }
        }
    }

}
