package _2024_03_27_国外;

import java.util.HashMap;
import java.util.Scanner;

/***
 *
 *
 * @author <a href="mailto:1934849492@qq.com">Darian1996</a>
 * @date 2024/3/27  10:39
 */
public class Main2 {
    public static String NonrepeatingCharacter(String str) {
        HashMap<Character, Integer> charCounts = new HashMap<>();

        // Count occurrences of each character
        for (char c : str.toCharArray()) {
            charCounts.put(c, charCounts.getOrDefault(c, 0) + 1);
        }

        // Find the first non-repeating character
        for (char c : str.toCharArray()) {
            if (charCounts.get(c) == 1) {
                return String.valueOf(c);
            }
        }

        return str;
    }

    public static void main (String[] args) {
        // keep this function call here
        Scanner s = new Scanner(System.in);
        System.out.print(NonrepeatingCharacter(s.nextLine()));
    }
}
