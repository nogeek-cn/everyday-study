package _2024_03_22_interview_od_huawei;

import java.util.*;
import java.util.stream.Collectors;


// 注意类名必须为 Main, 不要有任何 package xxx 信息
public class Main2 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        // 注意 hasNext 和 hasNextLine 的区别
        List<Integer> integerList = new ArrayList<>();

        String s = in.nextLine();
//        System.out.println(s);
        integerList = Arrays.stream(s.split(" "))
                .map(str -> str.trim())
                .filter(str -> !"".equals(str))
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        if (integerList.size() > 8) {
            System.out.println(-1);
            return;
        }

        while (integerList.size() < 8) {
            integerList = integerList
                    .stream()
                    .sorted(Comparator.comparingInt(a -> a))
                    .collect(Collectors.toList());
            Integer last = integerList.get(integerList.size() - 1);
            integerList.remove(last);
            integerList.add(last / 2);
            integerList.add(last - last / 2);
//            System.out.println(integerList);
        }
        integerList = integerList
                .stream()
                .sorted(Comparator.comparingInt(a -> a))
                .collect(Collectors.toList());

        System.out.println(integerList.get(7));

    }
}
