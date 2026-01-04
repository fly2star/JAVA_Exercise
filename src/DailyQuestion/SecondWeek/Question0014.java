package DailyQuestion.SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-14-17:25
 **/
public class Question0014 {
    public static void main(String[] args) {
        int[] cost = {10, 15, 20};
        System.out.println(minCostClimbingStairs(cost));
        int[] cost2 = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};
        System.out.println(minCostClimbingStairs2(cost2));
    }
    public static int minCostClimbingStairs(int[] cost) {
        int n = cost.length;
        int[] dp = new int[n];
        dp[0] = cost[0];
        dp[1] = cost[1];
        for (int i = 2; i < n; i++) {
            dp[i] = Math.min(dp[i - 1], dp[i - 2]) + cost[i];
        }
        return Math.min(dp[n - 2], dp[n - 1]);
    }
    public static int minCostClimbingStairs2(int[] cost) {
        int prev2 = cost[0]; // dp[i-2]
        int prev1 = cost[1]; // dp[i-1]

        for (int i = 2; i < cost.length; i++) {
            int current = Math.min(prev1, prev2) + cost[i];
            prev2 = prev1;
            prev1 = current;
        }
        return Math.min(prev1, prev2);
    }
}
