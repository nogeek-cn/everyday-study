package com.darian.od;

import java.util.Scanner;

/***
 * 牛客网给的例子，多行输入，多个输入
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/5/8  09:57
 */
public class ExampleOd {


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        while (in.hasNextInt()) {// 注意，如果输入是多个测试用例，请通过while循环处理多个测试用例
            int a = in.nextInt();
            int b = in.nextInt();
            System.out.println(a + b);
        }
    }

    // public static void main(String[] args) {
    //        Scanner sc = new Scanner(System.in);
    //        int n = sc.nextInt();
    //        int ans = 0, x;
    //        for(int i = 0; i < n; i++){
    //            for(int j = 0; j < n; j++){
    //                x = sc.nextInt();
    //                ans += x;
    //            }
    //        }
    //        System.out.println(ans);
    //    }
}
