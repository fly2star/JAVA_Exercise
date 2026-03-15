package LeetCode_Hot100;

/*
72. 编辑距离

给你两个单词 `word1` 和 `word2`，请返回将 `word1` 转换成 `word2` 所使用的最少操作数。

你可以对一个单词进行如下三种操作：
- 插入一个字符
- 删除一个字符
- 替换一个字符

## 提示：
    -- 0 ≤ word1.length, word2.length ≤ 500
    -- word1 和 word2 由小写英文字母组成
*/
public class question093 {
    public static void main(String[] args) {
        String word1 = "horse";
        String word2 = "ros";

        question093 sl93 = new question093();
        System.out.println(sl93.minDistance(word1, word2));
    }

    // 方法1: 动态规划
    /**
     * 当 word1[i] == word2[j]，dp[i][j] = dp[i-1][j-1]:
     * 
     * 当 word1[i] != word2[j]，dp[i][j] = min(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]) + 1
     * 
     * 其中，dp[i-1][j-1] 表示替换操作，dp[i-1][j] 表示删除操作，dp[i][j-1] 表示插入操作。
     * 
    */
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        // 创建 DP 数组
        // `dp[i][j]` 表示将 word1 的前 i 个字符转换为 word2 的前 j 个字符所需要的最少操作数。
        int[][] dp = new int[m + 1][n + 1];

        // 初始化边界条件
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // 填充 DP 表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // dp[0][] 表示空串, dp[i][] 表示第 i 个字符
                // string.charAt(i-1) 从零开始表示第 i 个字符
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j], dp[i][j - 1]), 
                        dp[i - 1][j - 1]
                    ) + 1;
                }
            }
        }
        return dp[m][n];
    }
}
