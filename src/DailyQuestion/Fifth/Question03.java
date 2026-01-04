package DailyQuestion.Fifth;

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
        // dp[i][j] 表示 s1[0:i] 和 s2[0:j] 相同的最小ASCII删除和
        // s1[0:i]表示s1长度为 i 的前缀, s2[0:j]表示s2长度为 j 的前缀
        // !!! 注意: s[0:k]表示s长度为 k 的前缀, 即从 s[0]到s[k-1], 而s[k]表示字符串的第 k+1 个字符
        int[][] dp = new int[m + 1][n + 1];
        // 边界情况
        //      1.当 i=j=0 时, s1[0:i]和s2[0:j]都为空, dp[i][j] = 0
        //      2.j=0 的情况: s2 为空串, 删除 s1 中所有的字符串, dp[i][0]=dp[i−1][0]+s1[i-1]
        //      3.i=0 的情况: s1 为空串, 删除 s2 中所有的字符串, dp[0][j]=dp[0][j-1]+s1[j-1]
        for (int i = 1; i <= m; i++) {
            dp[i][0] = dp[i - 1][0] + s1.codePointAt(i - 1);
        }

        for (int j = 1; j <= n; j++) {
            dp[0][j] = dp[0][j - 1] + s2.codePointAt(j - 1);
        }
        // 状态转移
        // i>0, j>0 的情况
        // 1. 如果s1[i-1]=s2[j-1]时, 将这两个相同的字符称为公共字符, 考虑使得s1[0:i-1]和s2[0:j-1]的相同的最小ASCII删除和,
        //    最小ASCII删除和不变, dp[i][j]=dp[i−1][j-1]
        // 2. 如果s1[i-1]!=s2[j-1]时, 考虑两种情况
        //    2.1. 删除 s1[i-1] 后, 使 s1[0:i-1] 和 s2[0:j] 的ASCII删除和相同, dp[i][j]=dp[i-1][j]+s1[i-1]
        //    2.2. 删除 s2[j-1] 后, 使 s1[0:i] 和 s2[0:j-1] 的ASCII删除和相同, dp[i][j]=dp[i][j-1]+s2[j-1]
        //    比较两种情况, 取最小值 dp[i][j]=min(dp[i-1][j]+s1[i-1], dp[i][j-1]+s2[j-1])
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
