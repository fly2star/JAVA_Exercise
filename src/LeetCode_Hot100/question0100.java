package LeetCode_Hot100;

/*
53. 最大子数组和

给你一个整数数组 `nums`，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

子数组是数组中的一个连续部分。

## 提示：
    -- 1 ≤ nums.length ≤ 10^5
    -- -10^4 ≤ nums[i] ≤ 10^4
*/
public class question0100 {
    public static void main(String[] args) {
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};

        question0100 sl100 = new question0100();
        System.out.println(sl100.maxSubArray(nums));
    }

    // 方法1: 动态规划
    public int maxSubArray(int[] nums) {
        int currSum = nums[0];
        int maxSum = nums[0];

        for (int i = 0; i < nums.length; i++) {
            // 如果之前的和为负，就从当前元素重新开始; 否则累加
            currSum = Math.max(nums[i], currSum + nums[i]);
            // 更新最大和
            maxSum = Math.max(maxSum, currSum);
        }
        return maxSum;
    }


}
