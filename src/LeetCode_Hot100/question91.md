# 76. 最小覆盖子串

**难度: 困难**

## 题目描述
给定两个字符串 `s` 和 `t`，长度分别是 `m` 和 `n`，返回 `s` 中的 **最短窗口子串**，使得该子串包含 `t` 中的每一个字符（包括重复字符）。如果没有这样的子串，返回空字符串 `""`。

测试用例保证答案唯一。

---

## 示例说明
### 示例 1：
输入：s = "ADOBECODEBANC", t = "ABC"  
输出："BANC"  
解释：最小覆盖子串 "BANC" 包含来自字符串 t 的 'A'、'B' 和 'C'。

### 示例 2：
输入：s = "a", t = "a"  
输出："a"

### 示例 3：
输入：s = "a", t = "aa"  
输出：""  
解释：t 中两个字符 'a' 均应包含在 s 的子串中，因此没有符合条件的子字符串，返回空字符串。

---

## 提示：
- m = s.length
- n = t.length
- 1 ≤ m, n ≤ 10^5
- s 和 t 由英文字母组成

---

## 解题思路

### 核心思想
使用**滑动窗口**技术，维护一个窗口，保证窗口内包含 t 中的所有字符。通过移动右指针扩大窗口，当窗口满足条件时，尝试移动左指针缩小窗口以找到最短的满足条件的子串。

### 关键观察
- 需要统计 t 中每个字符的出现次数
- 需要统计窗口内每个字符的出现次数
- 用变量 `required` 记录还需要匹配的字符种类数
- 当 `required == 0` 时，窗口满足条件，可以尝试收缩左边界

### 算法步骤
1. 创建两个哈希表（或数组）`need` 和 `window`，分别记录 t 中字符的需求量和当前窗口中字符的数量
2. 初始化左右指针 `left = 0`, `right = 0`，以及 `required` 为 t 中不同字符的数量
3. 初始化结果变量 `minLen = INF` 和结果起始位置 `start = 0`
4. 当 `right < len(s)` 时：
   - 将右指针指向的字符加入窗口，更新 `window` 和 `required`
   - 当 `required == 0` 时（窗口已包含所有所需字符）：
     - 尝试更新最小长度
     - 移动左指针缩小窗口，并更新 `window` 和 `required`
   - 右指针右移
5. 如果 `minLen` 仍是 INF，返回空字符串，否则返回 `s[start:start+minLen]`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def minWindow(self, s: str, t: str) -> str:
        if not s or not t or len(s) < len(t):
            return ""
        
        # 统计 t 中每个字符的需求量
        need = {}
        for c in t:
            need[c] = need.get(c, 0) + 1
        
        # 滑动窗口
        window = {}
        required = len(need)  # 还需要匹配的字符种类数
        left = right = 0
        
        # 记录最小窗口的起始位置和长度
        min_len = float('inf')
        start = 0
        
        while right < len(s):
            # 将右指针字符加入窗口
            c = s[right]
            if c in need:
                window[c] = window.get(c, 0) + 1
                if window[c] == need[c]:
                    required -= 1
            
            # 当窗口满足条件时，尝试收缩左边界
            while required == 0 and left <= right:
                # 更新最小窗口
                if right - left + 1 < min_len:
                    min_len = right - left + 1
                    start = left
                
                # 移动左指针，缩小窗口
                c = s[left]
                if c in need:
                    window[c] -= 1
                    if window[c] < need[c]:
                        required += 1
                left += 1
            
            right += 1
        
        return s[start:start + min_len] if min_len != float('inf') else ""
```

### Java 代码实现
```java
class Solution {
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
        int required = need.size();  // 还需要匹配的字符种类数
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
```

### C 代码实现
```c
char* minWindow(char* s, char* t) {
    int sLen = strlen(s);
    int tLen = strlen(t);
    
    if (sLen < tLen) {
        return "";
    }
    
    // 使用数组模拟哈希表（ASCII 128个字符）
    int need[128] = {0};
    int window[128] = {0};
    
    // 统计 t 中每个字符的需求量
    int required = 0;
    for (int i = 0; i < tLen; i++) {
        if (need[t[i]] == 0) {
            required++;
        }
        need[t[i]]++;
    }
    
    int left = 0, right = 0;
    int minLen = sLen + 1;
    int start = 0;
    int matched = 0;
    
    while (right < sLen) {
        // 将右指针字符加入窗口
        char c = s[right];
        window[c]++;
        
        if (need[c] > 0 && window[c] == need[c]) {
            matched++;
        }
        
        // 当窗口满足条件时，尝试收缩左边界
        while (matched == required && left <= right) {
            // 更新最小窗口
            if (right - left + 1 < minLen) {
                minLen = right - left + 1;
                start = left;
            }
            
            // 移动左指针，缩小窗口
            char leftChar = s[left];
            window[leftChar]--;
            if (need[leftChar] > 0 && window[leftChar] < need[leftChar]) {
                matched--;
            }
            left++;
        }
        
        right++;
    }
    
    if (minLen > sLen) {
        return "";
    }
    
    char* result = (char*)malloc((minLen + 1) * sizeof(char));
    strncpy(result, s + start, minLen);
    result[minLen] = '\0';
    return result;
}
```

---