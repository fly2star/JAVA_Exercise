# 1758. 生成交替二进制字符串的最少操作数

**难度: 简单**

## 题目描述
给你一个仅由字符 '0' 和 '1' 组成的字符串 s。一步操作可以将 '0' 变成 '1'，或者将 '1' 变成 '0'。

交替字符串定义为：字符串中不存在相邻两个字符相同的情况。例如，字符串 "010" 是交替字符串，而字符串 "0100" 不是。

返回使 s 变成交替字符串所需的最少操作数。

---

## 示例说明
### 示例 1：
输入：s = "0100"  
输出：1  
解释：将最后一个字符变为 '1'，得到 "0101"，即符合交替字符串定义。

### 示例 2：
输入：s = "10"  
输出：0  
解释：s 已经是交替字符串。

### 示例 3：
输入：s = "1111"  
输出：2  
解释：需要 2 步操作得到 "1010"（将第2、4位改为0），或者 2 步操作得到 "0101"（将第1、3位改为1）。

---

## 提示：
- 1 <= s.length <= 10^4
- s[i] 是 '0' 或 '1'

---

## 解题思路

### 核心思想
对于一个长度为 n 的字符串，可能的交替字符串只有两种模式：
- 模式 A：以 '0' 开头，即 "010101..."（偶数位为0，奇数位为1）
- 模式 B：以 '1' 开头，即 "101010..."（偶数位为1，奇数位为0）

我们只需要计算将原字符串转换成这两种模式所需要的操作次数，取较小值即可。

### 关键观察
- 对于模式 A，位置 i 上的期望字符是：如果 i 是偶数，期望为 '0'；如果 i 是奇数，期望为 '1'
- 对于模式 B，期望字符与模式 A 相反
- 操作次数就是原字符串与目标模式不同的字符个数
- 两种模式的操作次数之和等于字符串长度（因为它们是互补的）

### 算法步骤
1. 初始化 count1 = 0 记录转换成模式 A 的操作次数
2. 遍历字符串的每个位置 i：
   - 期望字符 = '0' 如果 i 是偶数，否则 '1'
   - 如果 s[i] != 期望字符，count1++
3. 模式 B 的操作次数 count2 = n - count1
4. 返回 min(count1, count2)

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def minOperations(self, s: str) -> int:
        n = len(s)
        count1 = 0  # 转换成以 '0' 开头的交替字符串所需的操作数
        
        for i, ch in enumerate(s):
            # 模式 A：偶数位应为 '0'，奇数位应为 '1'
            expected = '0' if i % 2 == 0 else '1'
            if ch != expected:
                count1 += 1
        
        # 模式 B 的操作数 = n - count1（互补）
        return min(count1, n - count1)
```

### Java 代码实现
```java
class Solution {
    public int minOperations(String s) {
        int n = s.length();
        int count1 = 0; // 转换成以 '0' 开头的交替字符串所需的操作数
        
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // 模式 A：偶数位应为 '0'，奇数位应为 '1'
            char expected = (i % 2 == 0) ? '0' : '1';
            if (c != expected) {
                count1++;
            }
        }
        
        // 模式 B 的操作数 = n - count1
        return Math.min(count1, n - count1);
    }
}
```

### C 代码实现
```c
int minOperations(char* s) {
    int n = strlen(s);
    int count1 = 0; // 转换成以 '0' 开头的交替字符串所需的操作数
    
    for (int i = 0; i < n; i++) {
        // 模式 A：偶数位应为 '0'，奇数位应为 '1'
        char expected = (i % 2 == 0) ? '0' : '1';
        if (s[i] != expected) {
            count1++;
        }
    }
    
    // 模式 B 的操作数 = n - count1
    int count2 = n - count1;
    return count1 < count2 ? count1 : count2;
}
```

---