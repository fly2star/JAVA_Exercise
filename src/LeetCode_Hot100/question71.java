package LeetCode_Hot100;

/*
10. 正则表达式匹配

给你一个字符串 `s` 和一个字符规律 `p`，请你来实现一个支持 `'.'` 和 `'*'` 的正则表达式匹配。
- `'.'` 匹配任意单个字符
- `'*'` 匹配零个或多个前面的那个元素

返回一个布尔值，表示匹配是否覆盖整个输入字符串（而非部分）。

## 提示：
    -- 1 ≤ s.length ≤ 20
    -- 1 ≤ p.length ≤ 20
    -- s 只包含从 a-z 的小写字母
    -- p 只包含从 a-z 的小写字母，以及字符 '.' 和 '*'
    -- 保证每次出现字符 '*' 时，前面都匹配到有效的字符
*/
public class question71 {
    public static void main(String[] args) {
        
    }

    // 方法1: 动态规划
    public boolean isMatch(String s, String p) {
        int m = s.length();
        int n = p.length();

        // dp[i][j] 表示 s 的前 i 个字符和 p 的前 j 个字符是否匹配
        boolean[][] dp = new boolean[m + 1][n + 1];

        // 空字符串和空模式匹配
        dp[0][0] = true;

        // 初始化第一行 (处理 a*, a*b*, a*b*c* 等模式)
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }

        // 填充 DP 表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // dp 的 0 表示空, 而 s 和 p 的 0 表示第一个字符
                // 二者之间相差一个 
                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);

                // 如果当前字符匹配 (相同字符或 '.')
                if (pc == '.' || pc == sc) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // 如果当前模式字符是 '*'
                else if (pc == '*') {
                    // 匹配零次：忽略模式和前面的字符
                    dp[i][j] = dp[i][j - 2];

                    // 匹配多次：如果前一个模式字符与当前字符匹配
                    // for 循环从前向后遍历, 每次只需要考虑前一个字符
                    char prev = p.charAt(j-2);
                    if (prev == '.' || prev == sc) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }

            }
        }

        return dp[m][n];

    }
}
