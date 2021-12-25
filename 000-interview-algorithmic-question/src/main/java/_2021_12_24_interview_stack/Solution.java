package _2021_12_24_interview_stack;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.joining;

/***
 *
 * @author darian1996
 */
public class Solution {

    // Complete the braces function below.
    static List<String> braces(List<String> values) {
        List<String> resultString = new ArrayList<>();
        if (values == null) {
            return resultString;
        }

        for (String value : values) {
            char[] chars = value.toCharArray();
            // 用栈
            Stack<Character> stack = new Stack();
            for (char aChar : chars) {
                if (stack.size() == 0) {
                    stack.add(aChar);
                } else {
                    Character peek = stack.peek();
                    if ((Objects.equals(peek, new Character('{'))
                            && Objects.equals(aChar, new Character('}')))
                            || (Objects.equals(peek, new Character('['))
                            && Objects.equals(aChar, new Character(']')))
                            || (Objects.equals(peek, new Character('('))
                            && Objects.equals(aChar, new Character(')')))
                    ) {
                        stack.pop();
                    } else {
                        stack.add(aChar);
                    }
                }
            }
            if (stack.size() == 0) {
                resultString.add("YES");
            } else {
                resultString.add("NO");
            }
        }
        return resultString;
    }

    public static void main(String[] args) throws IOException {
        List<String> stringList = new ArrayList<>();

        stringList.add("[]]");
        stringList.add("[]");
        stringList.add("[]{}");
        stringList.add("[]{}");
        stringList.add("[[]{}]");
        stringList.add("[[]{}]]");

        List<String> braceList = braces(stringList);
        System.out.println(braceList);


//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
//
//        int valuesCount = Integer.parseInt(bufferedReader.readLine().trim());
//
//        List<String> values = new ArrayList<>();
//
//        IntStream.range(0, valuesCount).forEach(i -> {
//            try {
//                values.add(bufferedReader.readLine());
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        });
//
//        List<String> res = braces(values);
//
//        bufferedWriter.write(
//                res.stream()
//                        .collect(joining("\n"))
//                        + "\n"
//        );
//
//        bufferedReader.close();
//        bufferedWriter.close();
    }
}
