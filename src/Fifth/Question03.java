package Fifth;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-10-21:24
 **/
/*
712. 两个字符串的最小ASCII删除和
给定两个字符串s1 和 s2，返回 使两个字符串相等所需删除字符的 ASCII 值的最小和 。
*/
public class Question03 {
    public static void main(String[] args) {
        String s1 = "sea";
        String s2 = "eat";
        System.out.println(minimumDeleteSum(s1, s2));
    }

    // 方法1:动态规划
    public static int minimumDeleteSum(String s1, String s2) {
        int m = s1.length(), n = s2.length();
        // dp[i][j] 表示 s1[0:i] 和 s2[0:j] 的最小ASCII删除和
        int[][] dp = new int[m + 1][n + 1];
        // 边界情况
        // 当 i=j=0 时, s1[0:i]和s2[0:j]都为空, dp[i][j] = 0
        // j=0 的情况: s2 为空串, 删除 s1 中所有的字符串
        // i=0 的情况: s1 为空串, 删除 s2 中所有的字符串
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + s1.codePointAt(i - 1);
        }

        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] + s2.codePointAt(j - 1);
        }
        // i>0, j>0 的情况
        for (int i = 1; i <= m; i++) {
            int code1 = s1.codePointAt(i - 1);
            for (int j = 1; j <= n; j++) {
                int code2 = s2.codePointAt(j - 1);
                // 如果两个字符相等, 最小ASCII删除和不变
                if (code1 == code2) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // 如果两个字符不相等, 比较两种情况
                    // 1. 使s1[0:i-1]
                    dp[i][j] = Math.min(dp[i - 1][j] + code1, dp[i][j - 1] + code2);
                }
            }
        }
        return dp[m][n];
    }
}
