package com.darian.threadlocal;

import java.util.concurrent.atomic.AtomicInteger;

/***
 *
 *
 * @author <a href="1934849492@qq.com">Darian</a> 
 * @date 2020/1/31  15:53
 */
public class HashIncrementDemo {
    private static final int HASH_INCREMENT = 0x61c88647;
    private static AtomicInteger atomicInteger = new AtomicInteger();

    public static void main(String[] args) {
        magicHash(16);
        magicHash(32);
    }

    private static void magicHash(int size) {
        int hashCode = 0;
        for (int i = 0; i < size; i++) {
            hashCode = atomicInteger.getAndAdd(HASH_INCREMENT);
            System.out.print((hashCode & (size - 1)) + "\t");
        }
        System.out.println("\n");
        // 7	14	5	12	3	10	1	8	15	6	13	4	11	2	9	0
        //
        //7	14	21	28	3	10	17	24	31	6	13	20	27	2	9	16	23	30	5	12	19	26	1	8	15	22	29	4	11	18	25	0
    }
}
