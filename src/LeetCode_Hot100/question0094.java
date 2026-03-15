package LeetCode_Hot100;

/*
70. 爬楼梯

假设你正在爬楼梯。需要 `n` 阶你才能到达楼顶。

每次你可以爬 `1` 或 `2` 个台阶。你有多少种不同的方法可以爬到楼顶呢？

## 提示：
    -- 1 ≤ n ≤ 45
*/
public class question0094 {
    public static void main(String[] args) {
        
        question0094 sl94 = new question0094();
        System.out.println(sl94.climbStairs(3));
    }

    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];

    }
    
}
