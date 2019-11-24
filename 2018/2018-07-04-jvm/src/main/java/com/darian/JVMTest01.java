package com.darian;


import java.util.ArrayList;
import java.util.List;

public class JVMTest01 {
    byte[] byteArray = new byte[1 * 1024 * 1024];

    public static void main(String[] args) {
        List<JVMTest01> list = new ArrayList<>();
        int count = 0;
        try {
            while (true) {
                list.add(new JVMTest01());
                count++;
            }
        } catch (Exception e) {
            System.out.println("count = " + count);
            e.printStackTrace();
        }
    }
}
