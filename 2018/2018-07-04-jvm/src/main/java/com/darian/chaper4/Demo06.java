package com.darian.chaper4;


import java.util.ArrayList;
import java.util.List;

public class Demo06 {

   static List<byte[]> array = new ArrayList<>();
    public static void main(String[] args) {

        byte[] b1 = new byte[2 * 1024* 1024];
        byte[] b2 = new byte[2 * 1024* 1024];
        byte[] b3 = new byte[2 * 1024* 1024];
        byte[] b4 = new byte[4 * 1024* 1024];
        array.add(b1);
        array.add(b2);
        array.add(b3);
        array.add(b4);
    }

}
