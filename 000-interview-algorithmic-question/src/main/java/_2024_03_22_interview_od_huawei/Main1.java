package _2024_03_22_interview_od_huawei;


import java.util.*;

// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main1 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        int nextInt = in.nextInt();

        List<String> strList = new ArrayList<>();
        for (int i = 0; i < nextInt; i++) {
            String str = in.next();
            strList.add(str);
        }

        int ceng = in.nextInt();

        String findStr = in.next();

        long count = strList.stream()
                .map(str -> {
                    return str.split("/");
                })
                .filter(strs -> {
                    return strs.length > ceng;
                })
                .filter(strs -> {
                    return Objects.equals(strs[ceng], findStr);
                })
//                .peek(System.out::println)
                .count();

        System.out.println(count);
    }
}
