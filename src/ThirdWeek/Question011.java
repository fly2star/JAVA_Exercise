package ThirdWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-19-11:13
 **/
/*
516. 最长回文子序列
给你一个字符串 s ，找出其中最长的回文子序列，并返回该序列的长度。
子序列定义为：不改变剩余字符顺序的情况下，删除某些字符或者不删除任何字符形成的一个序列。
*/
public class Question011 {
    public static void main(String[] args) {
        String s = "bbbab";
        String s2 = "cbbd";
        System.out.println(longestPalindromeSubseq(s));
        System.out.println(longestPalindromeSubseq(s2));
    }
    // 单个字符肯定是回文子序列
    public static int longestPalindromeSubseq(String s) {
        int n = s.length();
        int[][] f = new int[n][n];
        for (int i = 0; i < n; i++) {
            f[i][i] = 1;
        }
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (s.charAt(i) == s.charAt(j)) {
                    f[i][j] = f[i + 1][j - 1] + 2;
                } else {
                    f[i][j] = Math.max(f[i + 1][j], f[i][j - 1]);
                }
            }
        }
        return f[0][n - 1];
    }

    public static int longestPalindromeSubseq2(String s) {
        int n = s.length();
        if (n == 0) return 0;
        int[] dp = new int[n];
        char[] arr = s.toCharArray();
        for (int i = n - 1; i >= 0; i--) {
            dp[i] = 1;
            int leftup = 0, temp;
            for (int j = i + 1; j < n; j++) {
                temp = dp[j];
                if (arr[i] == arr[j]) {
                    dp[j] = leftup + 2;
                } else {
                    dp[j] = Math.max(dp[j], dp[j-1]);
                }
                leftup = temp;
            }
        }
        return dp[n-1];
    }
}
