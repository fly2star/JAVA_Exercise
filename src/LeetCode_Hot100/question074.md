# 3. 无重复字符的最长子串

**难度: 中等**

## 题目描述
给定一个字符串 `s`，请你找出其中不含有重复字符的 **最长子串** 的长度。

---

## 示例说明
### 示例 1：
输入：s = "abcabcbb"  
输出：3  
解释：因为无重复字符的最长子串是 "abc"，所以其长度为 3。

### 示例 2：
输入：s = "bbbbb"  
输出：1  
解释：因为无重复字符的最长子串是 "b"，所以其长度为 1。

### 示例 3：
输入：s = "pwwkew"  
输出：3  
解释：因为无重复字符的最长子串是 "wke"，所以其长度为 3。请注意，你的答案必须是 **子串** 的长度，"pwke" 是一个子序列，不是子串。

---

## 提示：
- 0 ≤ s.length ≤ 10^4
- s 由英文字母、数字、符号和空格组成

---

## 解题思路

### 核心思想
使用**滑动窗口**技术，维护一个窗口，保证窗口内的字符都是不重复的。当遇到重复字符时，移动窗口的左边界到重复字符的下一个位置。

### 关键观察
- 子串要求连续，因此滑动窗口是合适的
- 需要快速判断字符是否在窗口中出现过，可以使用哈希表记录字符最后出现的位置
- 窗口的左边界在遇到重复字符时需要更新为 max(当前左边界, 重复字符上次出现位置的下一个位置)

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

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def lengthOfLongestSubstring(self, s: str) -> int:
        # 哈希表记录每个字符最后出现的位置
        char_index = {}
        left = 0
        max_len = 0
        
        for right, char in enumerate(s):
            # 如果当前字符已经在窗口中，更新左指针
            if char in char_index and char_index[char] >= left:
                left = char_index[char] + 1
            
            # 更新字符位置
            char_index[char] = right
            
            # 计算当前窗口长度并更新最大值
            current_len = right - left + 1
            max_len = max(max_len, current_len)
        
        return max_len
```

### Java 代码实现
```java
class Solution {
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
}
```

### C 代码实现
```c
int lengthOfLongestSubstring(char* s) {
    // 使用数组模拟哈希表（ASCII 128个字符）
    int charIndex[128];
    for (int i = 0; i < 128; i++) {
        charIndex[i] = -1;
    }
    
    int left = 0;
    int maxLen = 0;
    int len = strlen(s);
    
    for (int right = 0; right < len; right++) {
        char c = s[right];
        
        // 如果当前字符已经在窗口中，更新左指针
        if (charIndex[c] >= left) {
            left = charIndex[c] + 1;
        }
        
        // 更新字符位置
        charIndex[c] = right;
        
        // 计算当前窗口长度并更新最大值
        int currentLen = right - left + 1;
        if (currentLen > maxLen) {
            maxLen = currentLen;
        }
    }
    
    return maxLen;
}
```

---