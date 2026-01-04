package DailyQuestion.FourthWeek;

import java.util.Arrays;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-27-15:29
 **/
/*
3381. 长度可被 K 整除的子数组的最大元素和
给你一个整数数组 nums 和一个整数 k 。
返回 nums 中一个 非空子数组 的 最大 和，要求该子数组的长度可以 被 k 整除。
*/
public class Question03 {
    public static void main(String[] args) {
        int[] num = {-1,-2,-3,-4,-5};
        System.out.println(maxSubarraySum(num, 4));
    }

    public static long maxSubarraySum(int[] nums, int k) {
        int n = nums.length;
        long prefixSum = 0;
        long maxSum = Long.MIN_VALUE;
        long[] kSum = new long[k];
        Arrays.fill(kSum, Long.MAX_VALUE / 2);
        kSum[k - 1] = 0;
        for (int i = 0; i < n; i++) {
            prefixSum += nums[i];
            maxSum = Math.max(maxSum, prefixSum - kSum[i % k]);
            kSum[i % k] = Math.min(kSum[i % k], prefixSum);
        }
        return maxSum;
    }
}
