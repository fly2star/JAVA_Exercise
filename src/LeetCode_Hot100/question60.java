package LeetCode_Hot100;

import java.util.Stack;

/*
32. 最长有效括号

给你一个只包含 `'('` 和 `')'` 的字符串，找出最长有效（格式正确且连续）括号子串的长度。

左右括号匹配，即每个左括号都有对应的右括号将其闭合的字符串是格式正确的，比如 `"((()))"`。

## 提示：
    -- 0 ≤ s.length ≤ 3 * 10^4
    -- s[i] 为 '(' 或 ')'
*/
public class question60 {
    public static void main(String[] args) {
        String s = ")()())";
        System.out.println(longestValidParentheses2(s));
    }

    // 方法1: 单调栈
    public static int longestValidParentheses(String s){
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxLen = 0;

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (!stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        return maxLen;
    }

    // 方法2: 动态规划
    /*
    1. 定义长度
        dp[i] 表示以索引 i 结尾的 最长有效括号子串的长度
    2. 状态转移逻辑
        - 情况 A: s[i-1] == '(' （形如...()）
        这意味着刚才凑成了一对
        dp[i] = dp[i-2] + 2
        - 情况 B: s[i-1] == ')' （形如...))）
        这意味着如果 i 是有效的, 它必须和前面某个更早的 '(' 匹配。
        我们要找的位置是 'i - dp[i-1] - 1' 。
        如果那个位置加号是 '(', 那么:
        'dp[i] = dp[i-1] + 2 + dp [i - dp[i-1] - 2]'
        (这里的 '+2' 是当前的匹配, 'dp[i-1]' 是内部的有效长度, 
        后面的 'dp[...]' 是由于这次匹配而接上的更前面的有效长度)
    */
    public static int longestValidParentheses2(String s) {
        int n = s.length();
        if (n < 2) {
            return 0;
        }

        int[] dp = new int[n];
        int maxLen = 0;

        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // 刚刚凑成了一对括号
                    dp[i] = (i >=2 ? dp[i-2] : 0) + 2;
                } else if (i - dp[i-1] > 0 && s.charAt(i - dp[i-1] - 1) == '(') {
                    // dp[i-1] 表示紧邻 i 左边那对已经凑好的“有效括号串”的长度
                    // 那么这个“有效括号串”的起点是 'i - dp[i-1]'
                    // 找到当前 s.charAt(i) （也就是那个 ')' ）找一个匹配 '('。
                    dp[i] = dp[i-1] + 2 + (i - dp[i-1] >= 2 ? dp[i - dp[i-1] - 2] : 0);
                    // 'dp[i] = dp[i-1] + 2 + (更前面的长度)'
                    // 'dp[i-1]' : 是被当前这一对括号包裹在里面的有效长度
                    // '2' : 就是 'i' 位置的 ')' 和它刚找到的那个 'i - dp[i-1] - 1' 位置的 '('，这一对儿贡献的长度
                    // 'dp[i - dp[i - 1] - 2]' : 因为 'i - dp[i-1] - 1' 位置的 '(' 的括号闭合后, 可能会连接上之前的”有效字符串“
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }
        return maxLen;
    }
}
