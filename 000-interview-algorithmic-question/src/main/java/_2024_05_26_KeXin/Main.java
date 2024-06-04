package _2024_05_26_KeXin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/5/26  上午12:15
 */
public class Main {

    public static void main(String[] args) {
        System.out.println(nextExceed(new LinkedList<Integer>(Arrays.asList(5,3,1,2,4))));
    }

    private static List<Integer> nextExceed(List<Integer> integerList) {
        List<Integer> result = new LinkedList<>();

        LinkedList<Integer> memoList = new LinkedList<>();
        for (int i = 0; i < integerList.size(); i++) {
            result.add(-1);

            while (!memoList.isEmpty()
                    && integerList.get(memoList.getFirst()) < integerList.get(i)) {
                result.set(memoList.peekFirst(), i - memoList.pollFirst());
            }

            memoList.push(i);
        }
        return result;
    }
}
