package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;

/*
76. 最小覆盖子串

给定两个字符串 `s` 和 `t`，长度分别是 `m` 和 `n`，
返回 `s` 中的 **最短窗口子串**，使得该子串包含 `t` 中的每一个字符（包括重复字符）。
如果没有这样的子串，返回空字符串 `""`。

测试用例保证答案唯一。

## 提示：
    -- m = s.length
    -- n = t.length
    -- 1 ≤ m, n ≤ 10^5
    -- s 和 t 由英文字母组成
*/
public class question91 {
    public static void main(String[] args) {
        
    }

    // 方法1: 滑动窗口
    public String minWindow(String s, String t) {
        if (s == null || t == null || s.length() < t.length()) {
            return "";
        }

        // 统计 t 中每个字符的需求量
        Map<Character, Integer> need = new HashMap<>();
        for (char c : t.toCharArray()) {
            need.put(c, need.getOrDefault(c, 0) + 1);
        }

        // 滑动窗口
        Map<Character, Integer> window = new HashMap<>();
        int required = need.size();       // 还需要匹配的字符种类数
        int left = 0, right = 0;
        
        // 记录最小窗口的起始位置和长度
        int minLen = Integer.MAX_VALUE;
        int start = 0;

        while (right < s.length()) {
            // 将右指针字符加入窗口
            char c = s.charAt(right);
            if (need.containsKey(c)) {
                window.put(c, window.getOrDefault(c, 0) + 1);
                if (window.get(c).equals(need.get(c))) {
                    required--;
                }
            }
            
            // 当窗口满足条件时，尝试收缩左边界
            while (required == 0 && left <= right) {
                // 更新最小窗口
                if (right - left + 1 < minLen) {
                    minLen = right - left + 1;
                    start = left;
                }
                
                // 移动左指针，缩小窗口
                char leftChar = s.charAt(left);
                if (need.containsKey(leftChar)) {
                    window.put(leftChar, window.get(leftChar) - 1);
                    if (window.get(leftChar) < need.get(leftChar)) {
                        required++;
                    }
                }
                left++;
            }
            
            right++;
        }

        return minLen == Integer.MAX_VALUE ? "" : s.substring(start, start + minLen);

    }
}
