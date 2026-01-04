package DailyQuestion.SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-14-15:17
 **/
/*
70. 爬楼梯
假设你正在爬楼梯。需要 n 阶你才能到达楼顶。
每次你可以爬 1 或 2 个台阶。你有多少种不同的方法可以爬到楼顶呢？
*/
public class Question0011 {
    public static void main(String[] args) {
        System.out.println(climbStairs(3));
    }

    // 动态规划
    // 我们用 f(x) 表示爬到第 x 级台阶的方案数，考虑最后一步可能跨了一级台阶，也可能跨了两级台阶，所以我们可以列出如下式子：
    // f(x)=f(x−1)+f(x−2)
    public static int climbStairs (int n) {
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

    public static int climbStairs2 (int n) {
        int p = 0, q = 0, r = 1;
        for (int i = 1; i <= n; ++i) {
            p = q;
            q = r;
            r = p + q;
        }
        return r;

    }


}
