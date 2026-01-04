package DailyQuestion.SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-14-19:06
 **/
/*
62. 不同路径
一个机器人位于一个 m x n 网格的左上角 （起始点在下图中标记为 “Start” ）。
机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish” ）。
问总共有多少条不同的路径？
*/
public class Question017 {
    public static void main(String[] args) {
        System.out.println(uniquePaths(3,7));
        System.out.println(uniquePaths2(3,7));
    }

    public static int uniquePaths(int m, int n) {
        return (int)comb(m + n-2, m-1);

    }

    public static long comb(int n, int k) {
        k = Math.min(n-k, k);
        long res = 1;
        for (int i = 1; i <= k; i++) {
            res = res * ( n- i + 1) / i ;
        }
        return res;
    }

    public static int uniquePaths2(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }
}
