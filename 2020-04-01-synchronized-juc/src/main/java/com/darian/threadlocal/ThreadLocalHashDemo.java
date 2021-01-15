package com.darian.threadlocal;

public class ThreadLocalHashDemo {

    private static final int HASH_INCREMENT = 0x61c88647;

    private static final int MY_HASH_INCREMENT = 5;

    public static void main(String[] args) {
        magic(16);
        magic(32);
        System.out.println();
        System.out.println("HASH_INCREMENT:" + HASH_INCREMENT);
        System.out.println("HASH_INCREMENT % 16: " + (HASH_INCREMENT % 16));
        System.out.println("HASH_INCREMENT / 16:" + (HASH_INCREMENT / 16));

        System.out.println();
        myMagic(16);
        myMagic(32);
    }

    private static void magic(int size) {
        int hashCode = 0;

        for (int i = 0; i < size; i++) {
            hashCode = i * HASH_INCREMENT + HASH_INCREMENT;

            System.out.print(" " + (hashCode & (size - 1)));
        }

        System.out.println();
    }

    private static void myMagic(int size) {
        int hashCode = 0;

        for (int i = 0; i < size; i++) {
            hashCode = i * MY_HASH_INCREMENT + MY_HASH_INCREMENT;

            System.out.print(" " + (hashCode & (size - 1)));
        }

        System.out.println();
    }
}