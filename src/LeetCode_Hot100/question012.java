package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-03-21:09
 **/
/*
198. 打家劫舍
你是一个专业的小偷，计划偷窃沿街的房屋。每间房内都藏有一定的现金，影响你偷窃的唯一制约因素就是相邻的房屋装有相互连通的防盗系统，
如果两间相邻的房屋在同一晚上被小偷闯入，系统会自动报警。

给定一个代表每个房屋存放金额的非负整数数组，计算你 不触动警报装置的情况下 ，一夜之内能够偷窃到的最高金额。
*/
public class question012 {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 1};
        System.out.println(rob(nums));
    }

    public static int rob(int[] nums){
        int n = nums.length;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = nums[0];
        // 对于 第 i 间房屋，偷或者不偷
        // 偷第 i 间房屋，就不能偷第 i-1 间房屋，总金额为前 i-2 间房屋偷到的总和加上当前的第 i 间房屋
        // 即 dp[i]`= dp[i-2] + nums[i]
        // 不偷第 i 间房屋，总金额为前 i-1 间房屋偷到的总和
        // 即 dp[i]`= dp[i-1]
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        return dp[n];
    }
}
