package com.darian.chaper5;

public class Demo02 {


    public  static int i = 1;

    static {
        System.out.println("staic init block is called");
         i= 2;
    }

    public static void main(String[] args) {
        System.out.println(Sub.B);
    }

    static class Sub extends Demo02 {
        public static int B = i;
    }
}
