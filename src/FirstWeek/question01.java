package FirstWeek;

import java.util.*;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-04-19:33
 **/
/*
 给你一个由 n 个整数组成的数组 nums，以及两个整数 k 和 x。

 数组的 x-sum 计算按照以下步骤进行：

    统计数组中所有元素的出现次数。
    仅保留出现次数最多的前 x 个元素的每次出现。如果两个元素的出现次数相同，则数值 较大 的元素被认为出现次数更多。
    计算结果数组的和。
 注意，如果数组中的不同元素少于 x 个，则其 x-sum 是数组的元素总和。

 返回一个长度为 n - k + 1 的整数数组 answer，其中 answer[i] 是 子数组 nums[i..i + k - 1] 的 x-sum。

 子数组 是数组内的一个连续 非空 的元素序列。
 */

public class question01 {
    public static void main(String[] args) {
        int[] num = {1,1,2,2,3,4,2,3};
        int k = 6;
        int x = 2;

        int[] num1 = {3,8,7,8,7,5};
        int k1 = 2;
        int x1 = 2;

        System.out.println(Arrays.toString(getXSum(num, k, x)));
        System.out.println(Arrays.toString(getXSum(num1, k1, x1)));

    }

    public static int[] getXSum(int[] nums, int k, int x) {
        int n = nums.length;
        int[] answer = new int[n - k + 1];
        for (int i = 0; i < n - k + 1; i++) {
            Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
            for (int j = 0; j < k; j++) {
                int num = nums[i + j];
                counts.put(num, counts.getOrDefault(num, 0) + 1);
            }
            List<int[]> numsCounts = new ArrayList<int[]>();
            Set<Map.Entry<Integer, Integer>> entries = counts.entrySet();
            for (Map.Entry<Integer, Integer> entry : entries) {
                numsCounts.add(new int[]{entry.getKey(), entry.getValue()});
            }
            Collections.sort(numsCounts, (a, b) -> a[1] != b[1] ? b[1] - a[1] : b[0] - a[0]);
            int sum = 0;
            int maxCount = Math.min(x, numsCounts.size());
            for (int j = 0; j < maxCount; j++) {
                int[] numCount = numsCounts.get(j);
                sum += numCount[0] * numCount[1];
            }
            answer[i] = sum;
        }
        return answer;

    }


}
