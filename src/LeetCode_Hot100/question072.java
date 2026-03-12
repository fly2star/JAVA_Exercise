package LeetCode_Hot100;

/*
5. 最长回文子串

给你一个字符串 `s`，找到 `s` 中最长的 **回文子串**。

## 提示：
    -- 1 ≤ s.length ≤ 1000
    -- s 仅由数字和英文字母组成

*/
public class question072 {
    public static void main(String[] args) {
        String s = "babad";
        question072 sl72 = new question072();
        System.out.println(sl72.longestPalindrome(s));
    }

    public String longestPalindrome(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        int start = 0, end = 0;

        for (int i = 0; i < s.length(); i++) {
            // 情况 1：中心是一个字符（奇数长度），如 "aba"
            int len1 = expandAroundCenter(s, i, i);
            // 情况 2：中心是两个字符之间（偶数长度），如 "abba"
            int len2 = expandAroundCenter(s, i, i + 1);

            // 取两者的最大值
            int maxLen = Math.max(len1, len2);

            // 如果找到了更长的回文，更新起始和结束位置
            if (maxLen > end - start) {
                // 通过长度算出起始点
                // 偶数长度(i, i+1)之间, 循环是遍历到 i 的
                // 所以 start 是更接近遍历的 i
                start = i - (maxLen - 1) / 2;
                end = i + maxLen / 2;
            }
        }
        return s.substring(start, end + 1);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        // 向两边扩展，直到不满足回文条件
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回回文串的长度
        // 注意：循环跳出时, left和right已经多走了一步,
        //       所以长度是 (right - 1) - (left + 1) + 1 = right - left - 1
        return right - left - 1;
    }
}
