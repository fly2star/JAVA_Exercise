package DailyQuestion.SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-16-14:33
 **/
/*
1513. 仅含 1 的子串数
给你一个二进制字符串 s（仅由 '0' 和 '1' 组成的字符串）。
返回所有字符都为 1 的子字符串的数目。
由于答案可能很大，请你将它对 10^9 + 7 取模后返回。
*/
public class Question019 {
    public static void main(String[] args) {
        String s = "0110111";
        System.out.println(numSub(s));
        System.out.println(numSub3(s));
    }

    public static int numSub(String s) {
        final int MODULO = 1000000007;
        int n = s.length();
        long res = 0;
        int[] post0 = new int[n + 1];
        post0[0] = -1;
        int size = 1;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '0') {
                post0[size++] = i;
            } else {
                res = (res + i - post0[size-1])%MODULO;
            }
        }


        return (int)res;
    }

    public static int numSub2(String s) {
        final int MODULO = 1000000007;
        long total = 0;
        int length = s.length();
        long consecutive = 0;
        for (int i = 0; i < length; i++) {
            char c = s.charAt(i);
            if (c == '0') {
                total += consecutive * (consecutive + 1) / 2;
                total %= MODULO;
                consecutive = 0;
            } else {
                consecutive++;
            }
        }
        total += consecutive * (consecutive + 1) / 2;
        total %= MODULO;
        return (int) total;

    }

    public static int numSub3(String s) {
        int ans = 0;
        int cnt = 0;
        int M = 1_000_000_007; // 或 (int)(7 + 1e9)

        for (char c : s.toCharArray()) {
            if (c == '1') {
                cnt++;
                ans = (ans + cnt) % M;
            } else {
                cnt = 0;
            }
        }

        return ans;

    }

}
