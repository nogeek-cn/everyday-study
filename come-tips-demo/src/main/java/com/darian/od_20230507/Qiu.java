package com.darian.od_20230507;

import java.util.*;
import java.util.stream.Collectors;

/***
 * 取出尽量少的球（时间复杂度不够小）
 * 就是 取出来球，使得 剩下的球的 sum <= 输入的和
 *
 * （牛客网-测试通过率，50%）
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/5/7  16:50
 */
public class Qiu {


    /**
     * 一开始用的 一个一个递归查找，后来觉得应该用二分法才能快速的寻找结果。。。。
     *
     * @param args
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int sumLimit = sc.nextInt();

        int listSize = sc.nextInt();
        List<Integer> integerList = new ArrayList<>();

        // 数量， 下标
        TreeMap<Integer, List<Integer>> orderMap = new TreeMap<Integer, List<Integer>>
                (Comparator.reverseOrder());


        for (int i = 0; i < listSize; i++) {
            int itemCount = sc.nextInt();
            integerList.add(itemCount);

            List<Integer> indexList = orderMap.getOrDefault(itemCount, new LinkedList<>());
            indexList.add(i);
            orderMap.put(itemCount, indexList);
        }

//        System.out.println(orderMap);


        List<Integer> outList = new ArrayList<>();
        // 填充球
        int qiuSum = integerList.stream().mapToInt(Integer::valueOf).sum();
        if (qiuSum >= sumLimit) {
            for (int i = 0; i < listSize; i++) {
                outList.add(0);
            }
        }

        if (qiuSum > sumLimit) {
            // 每次不需要再计算了，直接计算差值
            int chaZhi = qiuSum - sumLimit;
            int count = chaZhi / integerList.size();

            Map.Entry<Integer, List<Integer>> integerListEntry = orderMap.firstEntry();
            Integer key = integerListEntry.getKey();
            List<Integer> indexList = integerListEntry.getValue();

            int maxKey = key - count;

            while (count > 0) {
                for (Integer index : indexList) {
                    Integer outValue = outList.get(index);
                    outList.set(index, outValue + count);

                    qiuSum = qiuSum - count;
                }

                orderMap.remove(key);
                key = key - count;
                List<Integer> valueList = orderMap.getOrDefault(key, new LinkedList<>());
                valueList.addAll(indexList);
                orderMap.put(key, valueList);

                integerListEntry = orderMap.firstEntry();
                key = integerListEntry.getKey();
                indexList = integerListEntry.getValue();
                count = key - maxKey;
            }

        }

        while (qiuSum > sumLimit) {
            // 从最高的一个一个一个取出来
            // 取出来第一个 entry
            Map.Entry<Integer, List<Integer>> integerListEntry = orderMap.firstEntry();
            Integer key = integerListEntry.getKey();
            List<Integer> indexList = integerListEntry.getValue();

            for (Integer index : indexList) {
                Integer outValue = outList.get(index);
                outList.set(index, outValue + 1);

                // 球的和要一个一个减下去
                qiuSum--;
            }

            // 删除这个 key
            orderMap.remove(key);

            // 把这个 list 放在下一个 list 里边
            key = key - 1;
            List<Integer> nextValue = orderMap.getOrDefault(key, new LinkedList<>());
            nextValue.addAll(indexList);
            orderMap.put(key, nextValue);
//            System.out.println(qiuSum);
        }


        String collect = outList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",", "[", "]"));
        System.out.println(collect);


    }
}
