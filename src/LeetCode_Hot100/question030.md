# 438. 找到字符串中所有字母异位词

**难度：中等**

## 题目描述

给定两个字符串 `s` 和 `p`，找到 `s` 中所有 `p` 的 **异位词** 的子串，返回这些子串的起始索引。不考虑答案输出的顺序。

> 异位词：两个字符串包含相同的字符，且每个字符出现次数相同（即字母重排后相等）。

---

## 示例说明

**示例 1：**  
输入：`s = "cbaebabacd"`, `p = "abc"`  
输出：`[0, 6]`  
解释：
- `s[0:3] = "cba"` 是 `"abc"` 的异位词 → 起始索引 0
- `s[6:9] = "bac"` 是 `"abc"` 的异位词 → 起始索引 6

**示例 2：**  
输入：`s = "abab"`, `p = "ab"`  
输出：`[0, 1, 2]`  
解释：
- `s[0:2] = "ab"` → 异位词
- `s[1:3] = "ba"` → 异位词
- `s[2:4] = "ab"` → 异位词

---

## 解题思路

### 核心思想：
- 使用 **滑动窗口 + 哈希表计数**。
- 窗口大小为 `len(p)`，在 `s` 上滑动。
- 维护当前窗口内字符频次，与 `p` 的字符频次比较。

### 步骤：
1. 构建 `p` 的字符频次哈希表。
2. 在 `s` 上维护一个长度为 `len(p)` 的滑动窗口。
3. 每次移动窗口时，更新当前窗口的字符频次。
4. 若当前窗口频次与 `p` 相同，则记录起始索引。

---

## 复杂度分析

- **时间复杂度**：O(n)，其中 n 是 `s` 的长度。每个字符最多被访问两次。
- **空间复杂度**：O(1)，因为字符集是固定的（26 小写字母）。

---

## 参考代码（Python、Java、C）

### Python 实现
```python
def findAnagrams(s, p):
    if len(p) > len(s):
        return []
    
    # 构建 p 的字符频次
    p_count = [0] * 26
    for char in p:
        p_count[ord(char) - ord('a')] += 1
    
    # 滑动窗口
    s_count = [0] * 26
    result = []
    left = 0
    
    for right in range(len(s)):
        # 添加右端字符
        s_count[ord(s[right]) - ord('a')] += 1
        
        # 窗口长度达到 p 的长度
        if right - left + 1 == len(p):
            # 检查是否为异位词
            if s_count == p_count:
                result.append(left)
            
            # 移除左端字符
            s_count[ord(s[left]) - ord('a')] -= 1
            left += 1
    
    return result
```

### Java 实现
```Java
import java.util.*;

public List<Integer> findAnagrams(String s, String p) {
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
```

### C 实现
```C
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int* findAnagrams(char* s, char* p, int* returnSize) {
    int sLen = strlen(s);
    int pLen = strlen(p);
    
    if (pLen > sLen) {
        *returnSize = 0;
        return NULL;
    }
    
    // 构建 p 的字符频次
    int pCount[26] = {0};
    for (int i = 0; i < pLen; i++) {
        pCount[p[i] - 'a']++;
    }
    
    // 滑动窗口
    int sCount[26] = {0};
    int* result = (int*)malloc(sLen * sizeof(int));
    int idx = 0;
    int left = 0;
    
    for (int right = 0; right < sLen; right++) {
        // 添加右端字符
        sCount[s[right] - 'a']++;
        
        // 窗口长度达到 p 的长度
        if (right - left + 1 == pLen) {
            // 检查是否为异位词
            int match = 1;
            for (int i = 0; i < 26; i++) {
                if (sCount[i] != pCount[i]) {
                    match = 0;
                    break;
                }
            }
            if (match) {
                result[idx++] = left;
            }
            
            // 移除左端字符
            sCount[s[left] - 'a']--;
            left++;
        }
    }
    
    *returnSize = idx;
    return result;
}
```