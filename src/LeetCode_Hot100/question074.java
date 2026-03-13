package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;

/*
3. 无重复字符的最长子串

给定一个字符串 `s`，请你找出其中不含有重复字符的 **最长子串** 的长度。

## 提示：
-- 0 ≤ s.length ≤ 10^4
-- s 由英文字母、数字、符号和空格组成
*/
public class question074 {
    public static void main(String[] args) {
        String s = "abcabcbb";
        question074 sl74 = new question074();

        System.out.println(sl74.lengthOfLongestSubstring(s));
        System.out.println(sl74.lengthOfLongestSubstring2(s));
    }

    // 方法1: 滑动窗口
    /*
    ### 算法步骤
    1. 初始化一个哈希表 `char_index`，记录每个字符最近一次出现的位置
    2. 初始化左指针 `left = 0`，最大长度 `max_len = 0`
    3. 遍历字符串，右指针 `right` 从 0 到 n-1：
        - 如果当前字符 `s[right]` 在哈希表中，说明出现了重复
            - 更新左指针 `left = max(left, char_index[s[right]] + 1)`
        - 更新当前字符的位置：`char_index[s[right]] = right`
        - 计算当前窗口长度 `current_len = right - left + 1`
        - 更新 `max_len = max(max_len, current_len)`
    4. 返回 `max_len`
    */
    public int lengthOfLongestSubstring(String s) {
        // 哈希表记录每个字符最后出现的位置
        Map<Character, Integer> charIndex = new HashMap<>();
        int left = 0;
        int maxLen = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            // 如果当前字符已经在窗口中，更新左指针
            if (charIndex.containsKey(c) && charIndex.get(c) >= left) {
                left = charIndex.get(c) + 1;
            }

            // 更新字符位置
            charIndex.put(c, right);

            // 计算当前窗口长度并更新最大值
            int currentLen = right - left + 1;
            maxLen = Math.max(maxLen, currentLen);
        }

        return maxLen;
    }


    public int lengthOfLongestSubstring2(String s) {
        if (s.length() == 0) return 0;
    
        // Key: 字符, Value: 字符最后出现的位置的下一个索引
        Map<Character, Integer> map = new HashMap<>();
        int maxLen = 0;
        int left = 0; // 窗口左边界

        for (int right = 0; right < s.length(); right++) {
            char cur = s.charAt(right);
            
            // 如果当前字符在表里，说明之前见过
            if (map.containsKey(cur)) {
                // 【关键点】左边界跳跃，但不能往回跳
                // 比如 "abba"，处理到第二个 'a' 时，left 已经在索引 2 了，
                // 不能再被第一个 'a' 的位置扯回到索引 1。
                left = Math.max(left, map.get(cur));
            }
            
            // 计算当前窗口长度：右 - 左 + 1
            maxLen = Math.max(maxLen, right - left + 1);
            
            // 更新/存入当前字符的位置：存入索引 + 1，方便下次直接跳到该位
            map.put(cur, right + 1);
        }
        
        return maxLen;
    }
}
