package com.darian.chaper5;

public class Demo03 {

    static class  Hello {
        static {
            System.out.println(Thread.currentThread().getName() + " init...");
            try {
                Thread.sleep(3000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start...");
                 Hello dt = new Hello();
                System.out.println(Thread.currentThread().getName() + " end...");
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + " start...");
                Hello dt = new Hello();
                System.out.println(Thread.currentThread().getName() + " end...");
            }
        }).start();
    }
}
