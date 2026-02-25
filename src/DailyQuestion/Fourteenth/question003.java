package DailyQuestion.Fourteenth;

import java.util.Arrays;
import java.util.Comparator;

/*
1356. 根据数字二进制下 1 的数目排序

给你一个整数数组 `arr`。请你将数组中的元素按照其二进制表示中数字 1 的数目升序排列。
如果存在多个数字二进制中 1 的数目相同，则必须将它们按照数值大小升序排列。请你返回排序后的数组。
*/
public class question003 {
    public static void main(String[] args) {
        int[] arr = {0,1,2,3,4,5,6,7,8};
        int[] res = sortByBits(arr);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }

    public static int[] sortByBits(int[] arr) {
        // 将int[]转换为Integer[]以便使用Arrays.sort()的自定义比较器
        Integer[] nums = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nums[i] = arr[i]; 
        }

        // 匿名内部类的写法
        // Arrays.sort(nums, new Comparator<Integer>() {
        //     @Override
        //     public int compare(Integer a, Integer b){
        //         int countA = Integer.bitCount(a);
        //         int countB = Integer.bitCount(b);
        //         if (countA != countB) {
        //             return countA - countB;
        //         }
        //         return a - b;
        //     }
        // });

        Arrays.sort(nums, (a, b) -> {
            int countA = Integer.bitCount(a);
            int countB = Integer.bitCount(b);
            if (countA != countB) {
                return countA - countB;
            }
            return a - b;
        });

        // 转化为 int[]
        for (int i = 0; i < arr.length; i++) {
            arr[i] = nums[i];
        }

        return arr;
    }
}
