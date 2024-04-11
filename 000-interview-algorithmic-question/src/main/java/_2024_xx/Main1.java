package _2024_xx;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// 给定一个循环数组nums（nums[nums.length - 1] 的下一个元素是 nums[0]），返回nums中每个元素的下一个更大元素 。
//
//数字 x 的 下一个更大的元素 是按数组遍历顺序，这个数字之后的第一个比它更大的数，这意味着你应该循环地搜索它的下一个更大的数。如果不存在，则输出 -1 。
//
//示例 1:
//输入: nums = [1,2,1]
//输出: [2,-1,2]
//解释: 第一个 1 的下一个更大的数是 2；
//数字 2 找不到下一个更大的数；
//第二个 1 的下一个最大的数需要循环搜索，结果也是 2。
//示例 2:
//输入: nums = [1,2,3,4,3]
//输出: [2,3,4,-1,4]
// 单调栈
public class Main1 {


    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 3);
        Integer[] array = (Integer[]) list.toArray();

        List<Integer> indexList = getIndexList(array);
        System.out.println(indexList);
    }

    public static List<Integer> getIndexList(Integer[] integers) {
        List<Integer> indexList = new ArrayList<>();
        for (int i = 0; i < integers.length; i++) {
            int value =  integers[i];
            int firstBig = -1;


            int j = i + 1;
            if (i == integers.length -1) {
                j = 0;
            }
            for (; j < integers.length; j++) {
                if (j == (integers.length -1)) {
                    j = 0;
                }
                if (j == i) {
                    break;
                }

                if (value < integers[j]) {
                    firstBig = integers[j];
                    break;
                }

            }
            indexList.add(firstBig);
        }
        return indexList;
    }


}
