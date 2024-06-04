package _2024_04_04_interview_od;


//

// 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串 s ，判断字符串是否有效。
//
//有效字符串需满足：
//
//左括号必须用相同类型的右括号闭合。
//左括号必须以正确的顺序闭合。
//每个右括号都有一个对应的相同类型的左括号。
//
//示例 1：
//输入：s = "()"
//输出：true
//
//示例 2：
//输入：s = "()[]{}"
//输出：true
//
//示例 3：
//输入：s = "(]"
//输出：false
//
//提示：
//1 <= s.length <= 104
//s 仅由括号 '()[]{}' 组成

import java.util.Stack;

// '[{}]'
public class Main {

    public static void main(String[] args) {
        System.out.println(isTrue("[{}]"));
        System.out.println(isTrue("()"));
        System.out.println(isTrue("()[]{}"));
        System.out.println(isTrue("(]"));
    }

    public static boolean isTrue(String s) {
        char[] charArray = s.toCharArray();
        Stack<Character> characterStack = new Stack<>();
        for (char right : charArray) {
            if (characterStack.size() > 0) {
                Character left = characterStack.peek();
                if ((left == '(' && right == ')')
                        || (left == '{' && right == '}')
                        || (left == '[' && right == ']')) {
                    characterStack.pop();
                } else {
                    characterStack.add(right);
                }
            } else {
                characterStack.add(right);
            }
        }
        return characterStack.size() == 0;
    }


}
