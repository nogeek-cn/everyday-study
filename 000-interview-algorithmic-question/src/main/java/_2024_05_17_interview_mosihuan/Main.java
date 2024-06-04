package _2024_05_17_interview_mosihuan;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/***
 * 题目描述：10 个学生转成圈圈，依次报数，报三的去掉，然后只剩下一个人，看那个人编号是几。
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/5/17  下午9:46
 */
public class Main {

    public static void main(String[] args) {
        testList(1);
        testList(2);
        testList(3);
        testList(10);
    }

    public static void testList(Integer count) {

        List<Integer> objects = new ArrayQueue<>(count);
        for (int i = 0; i < count; i++) {
            objects.add(i + 1);
        }
        System.out.println("\n--------------------------------\n");
        System.out.println("处理：" + objects);
        System.out.println("test(" + count + ") :" + test(objects));
    }

    public static Integer test(List<Integer> varList) {
        int k = 0;

        while (true) {
            if (k == 0) {
                k = 1;
            } else if (k == 1) {
                k = 2;
            } else if (k == 2) {
                k = 3;
            } else if (k == 3) {
                k = 1;
            }

            Integer first = varList.remove(0);
            if (k == 3) {

            } else {
                varList.add(first);
            }
            if (varList.size() ==1) {
                break;
            }
        }


        System.out.println();
        return varList.get(0);
    }
}
