package _2023_05_07_interview_od_huawei;

import java.util.*;
import java.util.stream.Collectors;

/***
 * 优秀学员统计
 * 输入员工个数
 * 输入30 天的每天的打开人数，
 * 排序
 * 1. 打卡多的 > 打卡少的
 * 2. 同一天打开算同一时间打卡，打卡前一天的 > 打卡后一天的
 * 3. 员工需要小的 > 员工序号大的
 *
 * （牛客网-测试通过率，100%）
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/5/7  16:04
 */
public class DaKa {

    public static void main(String[] args) {

        TreeMap<Integer, Integer> treeMap = new TreeMap<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        List<Integer> dayDaKa = new ArrayList<>();

        for (int i = 0; i < 30; i++) {
            int yuangong = sc.nextInt();
            dayDaKa.add(yuangong);
        }

        TreeMap<Integer, Integer> orderMap = new TreeMap<>();

        for (int i = 0; i < 30; i++) {
            Integer dayCount = dayDaKa.get(i);
            int size = orderMap.size();
            size = size + 1;
            for (Integer j = 0; j < dayCount; j++) {
                int yuangong = sc.nextInt();
                Integer count = treeMap.getOrDefault(yuangong, 0);
                count = count + 1;
                treeMap.put(yuangong, count);

                if (!orderMap.containsKey(yuangong)) {
                    orderMap.put(yuangong, size);
                }
            }
        }

        String collect = treeMap.entrySet().stream().sorted(new Comparator<Map.Entry<Integer, Integer>>() {
                    @Override
                    public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                        // 打卡多的 > 打卡少的
                        int daKaCiShuCom = o1.getValue().compareTo(o2.getValue());
                        if (daKaCiShuCom != 0) {
                            return -daKaCiShuCom;
                        }

                        // 同一天打开算同一时间打卡，打卡前一天的 > 打卡后一天的
                        int daKaTimeCom = orderMap.get(o1.getKey()).compareTo(orderMap.get(o2.getKey()));
                        if (daKaTimeCom != 0) {
                            return daKaTimeCom;
                        }

                        // 员工需要小的 > 员工序号大的
                        return o1.getKey().compareTo(o2.getKey());
                    }
                })
//                .peek(entry -> {
//                    System.out.println(entry);
//                })
                .map(Map.Entry::getKey)
                .map(String::valueOf)
                .limit(5)
                .collect(Collectors.joining(" "));
        System.out.println(collect);
    }
}
