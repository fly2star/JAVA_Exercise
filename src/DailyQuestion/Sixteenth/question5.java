package DailyQuestion.Sixteenth;

/*
44. 通配符匹配

给你一个输入字符串（s）和一个字符模式（p），请你实现一个支持 `'?'` 和 `'*'` 匹配规则的通配符匹配：
- `'?'` 可以匹配任何单个字符。
- `'*'` 可以匹配任意字符序列（包括空字符序列）。

判定匹配成功的充要条件是：字符模式必须能够 **完全匹配** 输入字符串（而不是部分匹配）。

## 提示：
    -- 0 ≤ s.length, p.length ≤ 2000
    -- s 仅由小写英文字母组成
    -- p 仅由小写英文字母、'?' 或 '*' 组成
*/
public class question5 {
    public static void main(String[] args) {
        String s = "aa";
        String p = "*";

        question5 sl05 = new question5();
        System.out.println(sl05.isMatch(s, p));
    }

    /*
        1. 初始化 `dp[0][0] = True`，空模式匹配空串。
        2. 处理模式 p 开头的连续 `'*'`，它们可以匹配空串。
        3. 遍历 i 从 1 到 m，j 从 1 到 n：
        - 若 `p[j-1] == '*'`，则 `dp[i][j] = dp[i][j-1] or dp[i-1][j]`
        - 若 `p[j-1] == '?'` 或 `s[i-1] == p[j-1]`，则 `dp[i][j] = dp[i-1][j-1]`
        - 否则 `dp[i][j] = False`
        4. 返回 `dp[m][n]`
    */
    public boolean isMatch(String s, String p) {

        int m = s.length();
        int n = p.length();

        boolean[][] dp = new boolean[m + 1][n + 1];
        dp[0][0] = true;
        
        // `dp[i][0]=false` 非空字符串绝对无法匹配空模式 (默认就是 false)
        // `dp[0][j]` 空字符串去匹配模式串。只有当模式串全是由 * 组成时才能成功。遇到不是 * 的立刻停止。
        for (int j = 1; j <= n ; j++) {
            if (p.charAt(j-1) == '*') {
                dp[0][j] = dp[0][j-1];
            }
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n ; j++) {
                char sc = s.charAt(i-1);
                char pc = p.charAt(j-1);
                if (sc == pc || pc == '?') {
                    dp[i][j] = dp[i-1][j-1];
                } else if (pc == '*') {
                    // dp[i-1][j] 是 * 匹配了当前字符（*继续保留）
                    // dp[i][j-1] 是 * 匹配了 0 个字符（跳过该*）
                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                }
            }
        }

        return dp[m][n];
    }
}
