package com.darian;

public class StackTest {
    public static void main(String[] args) {
        add(1, 2);
        test02();
    }

    private static void test02() {
        test02();
    }

    public static void test03() {
    }

    private static void add(int i, int i1) {
        int result = i + i1;
    }
}
