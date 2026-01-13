package LeetCode_Hot100;

import java.util.Arrays;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-12-21:27
 **/
public class question026 {
    public static void main(String[] args) {
        int[] coins = {186, 419, 83, 408};
        int amount = 6249;
        System.out.println(coinChange(coins, amount));
    }

    public static int coinChange(int[] coins, int amount) {
        if (amount == 0) return 0;

        int[] dp = new int[amount + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }

        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
