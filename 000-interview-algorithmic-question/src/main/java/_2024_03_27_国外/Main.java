package _2024_03_27_国外;


import java.util.*;
import java.io.*;

class Main {

    public static String MinWindowSubstring(String[] strArr) {

//        String[] split = strArr[0].split("\"");
//         code goes here
//        String N = split[1];
//        String K = split[3];
        String N = strArr[0];
        String K = strArr[1];

        HashMap<Character, Integer> targetCharCounts = new HashMap<>();
        for (char c : K.toCharArray()) {
            targetCharCounts.put(c, targetCharCounts.getOrDefault(c, 0) + 1);
        }

        int left = 0;
        int minLen = Integer.MAX_VALUE;
        int minLeft = 0;
        int count = 0;
        HashMap<Character, Integer> windowCounts = new HashMap<>();

        for (int right = 0; right < N.length(); right++) {
            char currentChar = N.charAt(right);

            if (targetCharCounts.containsKey(currentChar)) {
                windowCounts.put(currentChar, windowCounts.getOrDefault(currentChar, 0) + 1);
                if (windowCounts.get(currentChar).intValue() <= targetCharCounts.get(currentChar).intValue()) {
                    count++;
                }
            }

            while (count == K.length()) {
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    minLeft = left;
                }

                char leftChar = N.charAt(left);
                if (targetCharCounts.containsKey(leftChar)) {
                    windowCounts.put(leftChar, windowCounts.get(leftChar) - 1);
                    if (windowCounts.get(leftChar).intValue() < targetCharCounts.get(leftChar).intValue()) {
                        count--;
                    }
                }
                left++;
            }
        }

        return minLen == Integer.MAX_VALUE ? "" : N.substring(minLeft, minLeft + minLen);
    }

    public static void main (String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
        System.out.print(MinWindowSubstring(new String[]{s.nextLine(), s.nextLine()}));
    }

}