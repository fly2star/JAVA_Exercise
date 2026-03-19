# 1784. 检查二进制字符串字段

**难度: 简单**

## 题目描述
给你一个二进制字符串 `s`，该字符串 **不含前导零**。

如果 `s` 包含 **零个或一个由连续的 '1' 组成的字段**，返回 `true`。否则，返回 `false`。

---

## 示例说明
### 示例 1：
输入：s = "1001"  
输出：false  
解释：由连续若干个 '1' 组成的字段数量为 2（第一个字段是开头的 "1"，第二个字段是末尾的 "1"），返回 false

### 示例 2：
输入：s = "110"  
输出：true  
解释：只有一个由连续的 '1' 组成的字段 "11"，返回 true

### 示例 3：
输入：s = "1"  
输出：true

---

## 提示：
- 1 ≤ s.length ≤ 100
- s[i] 为 '0' 或 '1'
- s[0] 为 '1'（字符串不含前导零）

---

## 解题思路

### 核心思想
题目要求判断字符串中是否只包含 **零个或一个由连续的 '1' 组成的字段**。由于字符串以 '1' 开头，所以至少有一个连续的 '1' 字段。问题转化为：在第一个连续的 '1' 字段结束后，是否还会出现 '1'。

### 关键观察
- 字符串以 '1' 开头，所以至少有一个 '1' 字段
- 我们只需要检查在遇到第一个 '0' 之后，是否还会出现 '1'
- 如果在第一个 '0' 之后又出现 '1'，说明有多个连续的 '1' 字段，返回 false

### 算法步骤
1. 遍历字符串，找到第一个 '0' 出现的位置
2. 如果字符串中没有 '0'（全是 '1'），返回 true
3. 从第一个 '0' 之后继续遍历，如果遇到 '1'，返回 false
4. 如果遍历完都没有再遇到 '1'，返回 true

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def checkOnesSegment(self, s: str) -> bool:
        # 找到第一个 '0' 的位置
        zero_index = s.find('0')
        
        # 如果没有 '0'，说明全是 '1'，符合条件
        if zero_index == -1:
            return True
        
        # 检查第一个 '0' 之后是否还有 '1'
        return '1' not in s[zero_index:]
```

### Java 代码实现
```java
class Solution {
    public boolean checkOnesSegment(String s) {
        // 找到第一个 '0' 的位置
        int zeroIndex = s.indexOf('0');
        
        // 如果没有 '0'，说明全是 '1'，符合条件
        if (zeroIndex == -1) {
            return true;
        }
        
        // 检查第一个 '0' 之后是否还有 '1'
        return s.indexOf('1', zeroIndex) == -1;
    }
}
```

### C 代码实现
```c
#include <stdbool.h>
#include <string.h>

bool checkOnesSegment(char* s) {
    int len = strlen(s);
    
    // 找到第一个 '0' 的位置
    int zeroIndex = -1;
    for (int i = 0; i < len; i++) {
        if (s[i] == '0') {
            zeroIndex = i;
            break;
        }
    }
    
    // 如果没有 '0'，说明全是 '1'，符合条件
    if (zeroIndex == -1) {
        return true;
    }
    
    // 检查第一个 '0' 之后是否还有 '1'
    for (int i = zeroIndex + 1; i < len; i++) {
        if (s[i] == '1') {
            return false;
        }
    }
    
    return true;
}
```

---