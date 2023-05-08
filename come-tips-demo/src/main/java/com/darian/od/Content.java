package com.darian.od;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/***
 * 知识图谱新词挖掘 1
 *
 * 找出 内容文本中，包含 词语里边 的字符串的乱序的 个数
 *
 * abab -> ab  -> 3
 * abcqwerty -> abc -> 1
 *
 * （牛客网-测试通过率，100%）
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2023/5/7  18:06
 */
public class Content {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String content = sc.next();
        String word = sc.next();

        Map<Character, Integer> charMap = charMap(word);

        Integer count = 0;

        for (int i = 0; i <= content.length() - word.length(); i++) {
            String spilt = content.substring(i, i + word.length());

            Map<Character, Integer> spiltMap = charMap(spilt);

            if (charMap.equals(spiltMap)) {
                count ++;
            }
        }
        System.out.println(count);
    }

    private static Map<Character, Integer> charMap (String word) {
        Map<Character, Integer> charMap = new HashMap<>();
        for (char c : word.toCharArray()) {
            Integer orDefault = charMap.getOrDefault(c, 0);
            charMap.put(c, orDefault + 1);
        }
        return charMap;
    }
}
