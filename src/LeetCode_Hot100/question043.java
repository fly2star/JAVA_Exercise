package LeetCode_Hot100;

import java.util.Arrays;

/*
300. 最长递增子序列

给你一个整数数组 `nums`，找到其中最长严格递增子序列的长度。
*/
public class question043 {
    public static void main(String[] args) {
        int[] nums = {0,1,0,3,2,3};
        System.out.println(lengthOfLIS(nums));
    }

    // 方法 1 : 动态规划
    public static int lengthOfLIS (int[] nums) {
        int n = nums.length;

        // dp[i] 表示以 num[i] 为结尾的递增子序列的长度
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        
        int maxLen = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if(nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    // 方法 2 : 贪心 + 二分
    public static int lengthOfLIS2 (int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        // tail[i] 表示长度为 i+1 的递增子序列的最小末尾元素
        int[] tail = new int[n]; 
        int len = 0;

        for (int num : nums) {
            // 如果当前元素大于 tail 的最后一个元素，直接添加到末尾
            if (len == 0 || num > tail[len - 1]) {
                tail[len] = num;
                len++;
            } else {
                // 二分查找第一个大于等于 num 的位置
                int left = 0, right = len - 1;
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (tail[mid] < num) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                tail[left] = num;
            }
        }

        return len;
    }

}
