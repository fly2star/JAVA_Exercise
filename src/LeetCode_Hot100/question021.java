package LeetCode_Hot100;

import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-23-21:33
 **/
/*
139. 单词拆分
给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
*/
public class question021 {
    public static void main(String[] args) {
        String s = "leetcode";
        List<String> wordDict = List.of("leet", "code");
        System.out.println(wordBreak(s, wordDict));
    }

    public static boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        // boolean 默认初始化为 false
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (String word : wordDict) {
                int len = word.length();
                // substring(start, end) 是左闭右开
                if (i >= len && s.substring(i - len, i).equals(word) && dp[i - len]) {
                    // s[0:i] 的长度要大于 word 的长度
                    // s[i-len:i] == word, 那就说明 s[0:i] 可以被 word 匹配
                    // dp[i-len] = true; 说明 s[0:i-len] 可以被 wordDict 中的单词匹配
                    dp[i] = true;   // s[0:i] 的字符可以中的单词匹配
                    break;
                }
            }

        }
        return dp[n];
    }


}
