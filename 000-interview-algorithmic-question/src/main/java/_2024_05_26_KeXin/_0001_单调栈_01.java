package _2024_05_26_KeXin;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/5/26  上午1:14
 */
public class _0001_单调栈_01 {
    // 5,3,1,2,4
    // [-1, 3, 1, 1, -1]
    public static void main(String[] args) {
        System.out.println(
                findFirstMax(Arrays.asList(5, 3, 1, 2, 4))
        );
    }

    private static List<Integer> findFirstMax(List<Integer> integerList) {
        List<Integer> stepList = new LinkedList<>();

        List<Integer> cacheLeftSmallList = new LinkedList<>();

        for (int currentIndex = 0; currentIndex < integerList.size(); currentIndex++) {
            stepList.add(-1);

            while (cacheLeftSmallList.size() > 0
                    && integerList.get(currentIndex) > integerList.get(cacheLeftSmallList.get(0))) {
                Integer leftSmallIndex = cacheLeftSmallList.remove(0);
                stepList.set(leftSmallIndex, currentIndex - leftSmallIndex);
            }
            cacheLeftSmallList.add(0, currentIndex);
        }

        return stepList;
    }
}
