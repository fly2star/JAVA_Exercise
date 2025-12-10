package LeetCode_Hot100;

import java.util.Arrays;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-05-19:02
 **/
/*
238. 除自身以外数组的乘积
给你一个整数数组 nums，返回 数组 answer ，其中 answer[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积 。
题目数据 保证 数组 nums之中任意元素的全部前缀元素和后缀的乘积都在  32 位 整数范围内。
请 不要使用除法，且在 O(n) 时间复杂度内完成此题。
*/
public class question014 {
    public static void main(String[] args) {
        int[] nums = {1,2,3,4};
        System.out.println(Arrays.toString(productExceptSelf(nums)));
    }

    public static int[] productExceptSelf(int[] nums) {
        int n = nums.length;
        int pre=1, suf=1;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            ans[i] = pre;
            pre *= nums[i];
        }
        for (int i = n - 1; i >= 0; i--) {
            ans[i] *= suf;
            suf *= nums[i];
        }
        return ans;
    }

}
