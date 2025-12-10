package ThirdWeek;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-18-20:14
 **/
/*
139. 单词拆分
给你一个字符串 s 和一个字符串列表 wordDict 作为字典。如果可以利用字典中出现的一个或多个单词拼接出 s 则返回 true。
注意：不要求字典中出现的单词全部都使用，并且字典中的单词可以重复使用。
*/
public class Question09 {
    public static void main(String[] args) {

        String s = "leetcode";
        List<String> wordDict = List.of("leet", "code");
        System.out.println(wordBreak(s, wordDict));
    }
    public static boolean wordBreak(String s, List<String> wordDict) {
        int n = s.length();
        boolean[] dp = new boolean[n + 1];
        dp[0] = true;
        for (int i = 1; i <= n; i++) {
            for (String word : wordDict) {
                int len = word.length();
                if (i >= len && s.substring(i - len, i).equals(word) && dp[i - len]) {
                    dp[i] = true;
                    break;
                }
            }

        }
        return dp[n];
    }
    public static boolean wordBreak2(String s, List<String> wordDict) {
        Set<String> wordDictSet = new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];

    }
}
