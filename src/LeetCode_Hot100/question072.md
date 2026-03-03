# 5. 最长回文子串

**难度: 中等**

## 题目描述
给你一个字符串 `s`，找到 `s` 中最长的 **回文子串**。

---

## 示例说明
### 示例 1：
输入：s = "babad"  
输出："bab"  
解释："aba" 同样是符合题意的答案。

### 示例 2：
输入：s = "cbbd"  
输出："bb"

---

## 提示：
- 1 ≤ s.length ≤ 1000
- s 仅由数字和英文字母组成

---

## 解题思路

### 核心思想
回文串的特点是中心对称，因此可以从中心向两边扩展来寻找回文串。这种方法称为**中心扩展法**，时间复杂度 O(n²)，空间复杂度 O(1)。

### 关键观察
- 回文串的长度可能是奇数或偶数
- 奇数长度回文的中心是一个字符
- 偶数长度回文的中心是两个字符之间
- 遍历每个可能的中心，向两边扩展直到不能形成回文为止

### 算法步骤
1. 初始化结果字符串 `result = ""`
2. 遍历字符串的每个位置 `i`：
   - 以 `i` 为中心进行奇数长度回文扩展（中心是一个字符）
   - 以 `i` 和 `i+1` 为中心进行偶数长度回文扩展（中心是两个字符之间）
   - 对每次扩展得到的回文，如果长度大于当前结果长度，则更新结果
3. 定义辅助函数 `expandAroundCenter(left, right)`：
   - 当 `left >= 0` 且 `right < n` 且 `s[left] == s[right]` 时，向两边扩展
   - 返回回文的长度
4. 返回结果字符串

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def longestPalindrome(self, s: str) -> str:
        if not s or len(s) < 1:
            return ""
        
        start, end = 0, 0
        
        for i in range(len(s)):
            # 奇数长度回文（中心是一个字符）
            len1 = self.expandAroundCenter(s, i, i)
            # 偶数长度回文（中心是两个字符之间）
            len2 = self.expandAroundCenter(s, i, i + 1)
            # 取较长的回文长度
            max_len = max(len1, len2)
            
            if max_len > end - start:
                start = i - (max_len - 1) // 2
                end = i + max_len // 2
        
        return s[start:end + 1]
    
    def expandAroundCenter(self, s: str, left: int, right: int) -> int:
        while left >= 0 and right < len(s) and s[left] == s[right]:
            left -= 1
            right += 1
        # 返回回文长度
        return right - left - 1
```

### Java 代码实现
```java
class Solution {
    public String longestPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return "";
        }
        
        int start = 0, end = 0;
        
        for (int i = 0; i < s.length(); i++) {
            // 奇数长度回文（中心是一个字符）
            int len1 = expandAroundCenter(s, i, i);
            // 偶数长度回文（中心是两个字符之间）
            int len2 = expandAroundCenter(s, i, i + 1);
            // 取较长的回文长度
            int maxLen = Math.max(len1, len2);
            
            if (maxLen > end - start) {
                start = i - (maxLen - 1) / 2;
                end = i + maxLen / 2;
            }
        }
        
        return s.substring(start, end + 1);
    }
    
    private int expandAroundCenter(String s, int left, int right) {
        while (left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)) {
            left--;
            right++;
        }
        // 返回回文长度
        return right - left - 1;
    }
}
```

### C 代码实现
```c
int expandAroundCenter(char* s, int left, int right) {
    int len = strlen(s);
    while (left >= 0 && right < len && s[left] == s[right]) {
        left--;
        right++;
    }
    // 返回回文长度
    return right - left - 1;
}

char* longestPalindrome(char* s) {
    int len = strlen(s);
    if (len < 1) return "";
    
    int start = 0, end = 0;
    
    for (int i = 0; i < len; i++) {
        // 奇数长度回文（中心是一个字符）
        int len1 = expandAroundCenter(s, i, i);
        // 偶数长度回文（中心是两个字符之间）
        int len2 = expandAroundCenter(s, i, i + 1);
        // 取较长的回文长度
        int maxLen = len1 > len2 ? len1 : len2;
        
        if (maxLen > end - start) {
            start = i - (maxLen - 1) / 2;
            end = i + maxLen / 2;
        }
    }
    
    // 提取结果子串
    char* result = (char*)malloc((end - start + 2) * sizeof(char));
    int j = 0;
    for (int i = start; i <= end; i++) {
        result[j++] = s[i];
    }
    result[j] = '\0';
    
    return result;
}
```

---