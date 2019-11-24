package com.darian.chaper4;


public class Demo01 {


    public static void main(String[] args) {
        int hour = 24;
        long mi = hour * 60 * 60 * 1000;
        long mic = hour * 60 * 60 * 1000 * 1000;
        int i = 0;
        long l  = 1;
        i = (int) l;
//       User u  = new User();
//       Object obj = u;
        System.out.println(mic/mi);

    }
}
