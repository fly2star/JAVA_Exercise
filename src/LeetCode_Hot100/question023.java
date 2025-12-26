package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-24-20:03
 **/
/*
647. 回文子串
给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。

回文字符串 是正着读和倒过来读一样的字符串。

子字符串 是字符串中的由连续字符组成的一个序列。
*/
public class question023 {
    public static void main(String[] args) {
        String s = "abc";
        System.out.println(countSubstrings(s));
    }

    // 方法1: 暴力解法
    public static int countSubstrings(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = i; j < s.length(); j++) {
                if (isPalindrome(s.substring(i, j + 1))) {
                    count++;
                }
            }
        }
        return count;
    }

    /// 辅助方法: 判断 s 是否是回文串
    public static boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        while (left < right) {
            if (s.charAt(left) != s.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    // 方法2: 中心扩展法
    /**
     * 思路理解: 首先寻找回文中心, 奇数的回文中心是一个字符, 偶数的回文中心是两个字符</br>
     *          那么对于一个长度为 n 的字符串，共有 2n-1 个回文中心(一个字符的 n个, 两个字符的 n-1 个)</br>
     * 对于任意长度为 n 的字符串, 回文中心可以 left=i/2 向下取整, right=left + (i mod 2),</br>
     * 当 left==right 时，回文串的长度是奇数</br>
     * */
    public static int countSubstrings2(String s) {
        int n = s.length();
        int ans = 0;
        for (int i = 0; i < 2 * n - 1; i++) {
            // left和right指针和中心点的关系是？
            // 首先是left，有一个很明显的2倍关系的存在，其次是right，可能和left指向同一个（偶数时），也可能往后移动一个（奇数）
            // 大致的关系出来了，可以选择带两个特殊例子进去看看是否满足。
            int left = i / 2;
            int right = left + i % 2;
            // 回文中心向外扩展
            while (left >= 0 && right < n && s.charAt(left) == s.charAt(right)) {
                ans++;
                left--;
                right++;
            }
        }
        return ans;
    }

    // 方法3: 动态规划
    /**
     * 状态: dp[i][j] 表示 s[i:j] 是否是回文串 </br>
     * 状态转移方程: dp[i][j] = s[i] == s[j] && dp[i + 1][j - 1] 时候为 true, 否则为 false</br>
     * 状态转移房产的思路:</br>
     *                1. 当只有一个字符串时, 必定为回文子串, dp[i][j] = true </br>
     *                2. 当两个字符串长度为 2 时, 如果两个字符相等, 也是一个回文子串, dp[i][j] = true </br>
     *                3. 当两个字符串的长度大于 2 时, 比如 ababa 这个字符记作串 1，把两边的 a 去掉，也就是 bab 记作串 2，</br>
     *                   可以看出只要串2是一个回文串，那么左右各多了一个 a 的串 1 必定也是回文串。 </br>
     *                   所以当 s[i]==s[j] 时，自然要看 dp[i+1][j-1] 是不是一个回文串</br>
     * */
    public static int countSubstrings3(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];
        int ans = 0;

        for (int j = 0; j < n; j++) {
            for (int i = 0; i < j; i++) {
                if (s.charAt(i) == s.charAt(j) && (j - i < 2 || dp[i + 1][j - 1])) {
                    dp[i][j] = true;
                    ans++;
                }
            }
        }
        return ans;
    }

    // 方法4: Manacher 算法
    public static int countSubstrings4(String s) {
        int n = s.length();
        StringBuffer t = new StringBuffer("$#");
        for (int i = 0; i < n; ++i) {
            t.append(s.charAt(i));
            t.append('#');
        }
        n = t.length();
        t.append('!');

        int[] f = new int[n];
        int iMax = 0, rMax = 0, ans = 0;
        for (int i = 1; i < n; ++i) {
            // 初始化 f[i]
            f[i] = i <= rMax ? Math.min(rMax - i + 1, f[2 * iMax - i]) : 1;
            // 中心拓展
            while (t.charAt(i + f[i]) == t.charAt(i - f[i])) {
                ++f[i];
            }
            // 动态维护 iMax 和 rMax
            if (i + f[i] - 1 > rMax) {
                iMax = i;
                rMax = i + f[i] - 1;
            }
            // 统计答案, 当前贡献为 (f[i] - 1) / 2 上取整
            ans += f[i] / 2;
        }

        return ans;
    }

}
