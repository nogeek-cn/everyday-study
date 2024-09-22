package dandiaozhan;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/***
 *
 *
 * @author <a href="mailto:">notOnlyGeek</a>
 * @date 2024/6/16  下午10:13
 */
public class Leet_496 {

    public static void main(String[] args) {


    }

    static class Solution {

        public static int[] dailyTemperatures(int[] temperatures) {

            Map<Integer, Integer> smallIndexNextIndexMap = new HashMap<>();
            LinkedList<Integer> linkedList = new LinkedList<>();
            for (int index = 0; index < temperatures.length; index++) {
                // 第一个数，的值，比当前数小的，取出来了。剩下的是比当前数大的集合。   小｜大
                while (!linkedList.isEmpty() && temperatures[index] > temperatures[linkedList.peekFirst()] ) {
                    Integer leftIndex = linkedList.pollFirst();
                    smallIndexNextIndexMap.put(leftIndex, index);
                }
                linkedList.addFirst(index);
            }

            int[] result = new int[temperatures.length];
            for (int index = 0; index < temperatures.length; index++) {
                Integer nextIndex = smallIndexNextIndexMap.get(index);
                if (nextIndex == null) {
                    result[index] = 0;
                } else {
                    result[index] = nextIndex - index;
                }
            }
            return result;
        }

    }
}

