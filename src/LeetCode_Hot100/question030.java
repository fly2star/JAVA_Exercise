package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-26-21:24
 **/
/*
438. 找到字符串中所有字母异位词

给定两个字符串 `s` 和 `p`，找到 `s` 中所有 `p` 的 **异位词** 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。

异位词：两个字符串包含相同的字符，且每个字符出现次数相同（即字母重排后相等）。

*/
public class question030 {
    public static void main(String[] args) {
        String s = "cbaebabacd", p = "abc";
        String s1 = "abab", p1 = "ab";
        System.out.println(findAnagrams(s, p));
        System.out.println(findAnagrams2(s1, p1));
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();

        // 创建一个窗口，记录窗口内字母出现的次数
        // 异位词，字母出现的次数相同
        int n = s.length(), m = p.length();
        int[] pCount = new int[26];
        for (int i = 0; i < m; i++) {
            pCount[p.charAt(i) - 'a']++;
        }

        // 通过一个滑动窗口来遍历字符串s, 比较窗口技术是否相同
        for (int i = 0; i <= n - m; i++) {
            int[] sCount = new int[26];
            for (int j = i; j < i + m; j++) {
                sCount[s.charAt(j) - 'a']++;
            }
            if (Arrays.equals(pCount, sCount)) {
                res.add(i);
            }
        }

        return res;
    }


    // 方法 2: 更高级的滑动窗口
    public static List<Integer> findAnagrams2(String s, String p) {
        if (p.length() > s.length()) {
            return new ArrayList<>();
        }

        // 构建 p 的字符频次
        int[] pCount = new int[26];
        for (char c : p.toCharArray()) {
            pCount[c - 'a']++;
        }

        // 滑动窗口
        int[] sCount = new int[26];
        List<Integer> result = new ArrayList<>();
        int left = 0;

        for (int right = 0; right < s.length(); right++) {
            // 添加右端字符
            sCount[s.charAt(right) - 'a']++;

            // 窗口长度达到 p 的长度
            if (right - left + 1 == p.length()) {
                // 检查是否为异位词
                if (Arrays.equals(sCount, pCount)) {
                    result.add(left);
                }

                // 移除左端字符
                sCount[s.charAt(left) - 'a']--;
                left++;
            }
        }

        return result;
    }
}
