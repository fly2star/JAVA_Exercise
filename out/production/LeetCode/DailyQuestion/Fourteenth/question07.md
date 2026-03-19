# 1888. 使二进制字符串字符交替的最少反转次数

**难度: 中等**

## 题目描述
给你一个二进制字符串 `s`。你可以按任意顺序执行以下两种操作任意次：

- **类型 1**：删除字符串 `s` 的第一个字符并将它 **添加** 到字符串结尾。
- **类型 2**：选择字符串 `s` 中任意一个字符并将该字符 **反转**，也就是如果值为 `'0'`，则反转得到 `'1'`，反之亦然。

请你返回使 `s` 变成交替字符串的前提下，**类型 2 的最少** 操作次数。

我们称一个字符串是 **交替的**，需要满足任意相邻字符都不同。

---

## 示例说明
### 示例 1：
输入：s = "111000"  
输出：2  
解释：执行第一种操作两次，得到 s = "100011"。然后对第三个和第六个字符执行第二种操作，得到 s = "101010"。

### 示例 2：
输入：s = "010"  
输出：0  
解释：字符串已经是交替的。

### 示例 3：
输入：s = "1110"  
输出：1  
解释：对第二个字符执行第二种操作，得到 s = "1010"。

---

## 提示：
- 1 <= s.length <= 10^5
- s[i] 要么是 '0', 要么是 '1'。

---

## 解题思路

### 核心思想
这是一个**滑动窗口**问题。类型 1 操作相当于对字符串进行循环移位，类型 2 操作是翻转字符。我们需要找到所有可能的循环移位中，变成交替字符串所需的最小翻转次数。

### 关键观察
- 交替字符串只有两种模式：
  - 模式 A：以 '0' 开头，即 "010101..."
  - 模式 B：以 '1' 开头，即 "101010..."
- 类型 1 操作相当于将原字符串复制一份拼接在后面，然后取长度为 n 的滑动窗口
- 对于每个窗口，计算将其变成两种模式所需的最小翻转次数
- 可以利用前缀和优化计算过程

### 算法步骤
1. 将字符串 s 复制一份拼接成 `s + s`，长度为 2n
2. 构建两种目标模式字符串（长度为 2n）：
   - pattern0：以 '0' 开头的交替模式
   - pattern1：以 '1' 开头的交替模式
3. 计算原字符串与两种模式的差异数组（diff0[i] 和 diff1[i]）
4. 使用滑动窗口计算每个长度为 n 的窗口的差异和
5. 取所有窗口的最小值作为答案

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def minFlips(self, s: str) -> int:
        n = len(s)
        # 将字符串复制一份
        s2 = s + s
        
        # 构建两种目标模式
        target0 = ['0', '1'] * n
        target1 = ['1', '0'] * n
        
        # 计算差异数组
        diff0 = [0] * (2 * n)
        diff1 = [0] * (2 * n)
        
        for i in range(2 * n):
            diff0[i] = 1 if s2[i] != target0[i % n] else 0
            diff1[i] = 1 if s2[i] != target1[i % n] else 0
        
        # 滑动窗口计算
        ans = float('inf')
        window0 = window1 = 0
        
        for i in range(2 * n):
            window0 += diff0[i]
            window1 += diff1[i]
            
            if i >= n:
                window0 -= diff0[i - n]
                window1 -= diff1[i - n]
            
            if i >= n - 1:
                ans = min(ans, window0, window1)
        
        return ans
```

### Java 代码实现
```java
class Solution {
    public int minFlips(String s) {
        int n = s.length();
        String s2 = s + s;
        
        // 构建两种目标模式
        char[] target0 = new char[2 * n];
        char[] target1 = new char[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            target0[i] = (i % 2 == 0) ? '0' : '1';
            target1[i] = (i % 2 == 0) ? '1' : '0';
        }
        
        // 滑动窗口计算
        int ans = Integer.MAX_VALUE;
        int window0 = 0, window1 = 0;
        
        for (int i = 0; i < 2 * n; i++) {
            if (s2.charAt(i) != target0[i]) window0++;
            if (s2.charAt(i) != target1[i]) window1++;
            
            if (i >= n) {
                if (s2.charAt(i - n) != target0[i - n]) window0--;
                if (s2.charAt(i - n) != target1[i - n]) window1--;
            }
            
            if (i >= n - 1) {
                ans = Math.min(ans, Math.min(window0, window1));
            }
        }
        
        return ans;
    }
}
```

### C 代码实现
```c
int minFlips(char* s) {
    int n = strlen(s);
    int len = 2 * n;
    
    // 复制字符串
    char* s2 = (char*)malloc((len + 1) * sizeof(char));
    strcpy(s2, s);
    strcat(s2, s);
    
    int ans = n; // 初始化为最大值
    int window0 = 0, window1 = 0;
    
    for (int i = 0; i < len; i++) {
        // 检查与两种模式的差异
        char expected0 = (i % 2 == 0) ? '0' : '1';
        char expected1 = (i % 2 == 0) ? '1' : '0';
        
        if (s2[i] != expected0) window0++;
        if (s2[i] != expected1) window1++;
        
        if (i >= n) {
            int prev = i - n;
            char prevExpected0 = (prev % 2 == 0) ? '0' : '1';
            char prevExpected1 = (prev % 2 == 0) ? '1' : '0';
            
            if (s2[prev] != prevExpected0) window0--;
            if (s2[prev] != prevExpected1) window1--;
        }
        
        if (i >= n - 1) {
            int min = window0 < window1 ? window0 : window1;
            if (min < ans) ans = min;
        }
    }
    
    free(s2);
    return ans;
}
```

---