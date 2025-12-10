package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-09-21:33
 **/
/*
152. 乘积最大子数组
给你一个整数数组 nums ，请你找出数组中乘积最大的非空连续 子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
测试用例的答案是一个 32-位 整数。
请注意，一个只包含一个元素的数组的乘积是这个元素的值。
*/
public class question016 {
    public static void main(String[] args) {
        int[] nums = {2, 3, -2, 4};
        System.out.println(maxProduct(nums));
    }

    public static int maxProduct(int[] nums) {
        int n = nums.length;
        if (n == 0) {
            return 0;
        }
        // dpMax[i] 表示以 nums[i] 结尾的子数组的最大乘积
        // dpMin[i] 表示以 nums[i] 结尾的子数组的最小乘积
        int[] dpMax = new int[n];
        int[] dpMin = new int[n];

        // 初始化: 所有长度为 1 的子数组乘积都是它本身
        dpMax[0] = nums[0];
        dpMin[0] = nums[0];
        // 全局最大乘积
        int maxProduct = nums[0];

        for (int i = 1; i < n; i++) {
            // 关键逻辑: 每个位置右三种可能
            //          1. 从当前元素重新开始（比如遇到0后）
            //          2. 前一个最大乘积 * 当前元素
            //          3. 前一个最小乘积 * 当前元素（负负得正的情况）
            int candidate1 = dpMax[i - 1] * nums[i];    // 延续之前的情况
            int candidate2 = dpMin[i - 1] * nums[i];    // 负负得正的情况
            int currentNum = nums[i];                   // 独立成子数组

            // 三者中取最大和最小和
            dpMax[i] = Math.max(Math.max(candidate1, candidate2), currentNum);
            dpMin[i] = Math.min(Math.min(candidate1, candidate2), currentNum);

            // 更新全局最大乘积
            maxProduct = Math.max(maxProduct, dpMax[i]);
        }

        return maxProduct;

    }
}
