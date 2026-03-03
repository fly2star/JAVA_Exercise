# 10. 正则表达式匹配

**难度: 困难**

## 题目描述
给你一个字符串 `s` 和一个字符规律 `p`，请你来实现一个支持 `'.'` 和 `'*'` 的正则表达式匹配。

- `'.'` 匹配任意单个字符
- `'*'` 匹配零个或多个前面的那个元素

返回一个布尔值，表示匹配是否覆盖整个输入字符串（而非部分）。

---

## 示例说明
### 示例 1：
输入：s = "aa", p = "a"  
输出：false  
解释："a" 无法匹配 "aa" 整个字符串。

### 示例 2：
输入：s = "aa", p = "a*"  
输出：true  
解释：因为 '*' 代表可以匹配零个或多个前面的那个元素，在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。

### 示例 3：
输入：s = "ab", p = ".*"  
输出：true  
解释：".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。

---

## 提示：
- 1 ≤ s.length ≤ 20
- 1 ≤ p.length ≤ 20
- s 只包含从 a-z 的小写字母
- p 只包含从 a-z 的小写字母，以及字符 '.' 和 '*'
- 保证每次出现字符 '*' 时，前面都匹配到有效的字符

---

## 解题思路

### 核心思想
使用**动态规划**来解决正则表达式匹配问题。定义 `dp[i][j]` 表示字符串 s 的前 i 个字符与模式 p 的前 j 个字符是否匹配。

### 关键观察
- 模式中的普通字符必须精确匹配
- '.' 可以匹配任意单个字符
- '*' 可以匹配零个或多个前面的字符，这是最复杂的部分
- 需要处理 '*' 的两种情况：
  - 匹配零次：忽略模式和前面的字符
  - 匹配多次：当前字符匹配成功，继续匹配字符串的下一个字符

### 算法步骤
1. 创建二维 DP 数组 `dp[m+1][n+1]`，其中 m 和 n 分别是 s 和 p 的长度
2. 初始化 `dp[0][0] = true`（空字符串匹配空模式）
3. 处理模式中可能出现 '*' 的情况，初始化第一行
4. 遍历所有状态：
   - 如果当前字符匹配（相同字符或 '.'）：
     - `dp[i][j] = dp[i-1][j-1]`
   - 如果当前模式字符是 '*'：
     - 匹配零次：`dp[i][j] = dp[i][j-2]`
     - 匹配多次：如果 s[i-1] 与 p[j-2] 匹配，则 `dp[i][j] = dp[i][j] or dp[i-1][j]`
5. 返回 `dp[m][n]`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def isMatch(self, s: str, p: str) -> bool:
        m, n = len(s), len(p)
        
        # dp[i][j] 表示 s 的前 i 个字符和 p 的前 j 个字符是否匹配
        dp = [[False] * (n + 1) for _ in range(m + 1)]
        
        # 空字符串和空模式匹配
        dp[0][0] = True
        
        # 初始化第一行（处理 a*, a*b*, a*b*c* 等模式）
        for j in range(2, n + 1):
            if p[j - 1] == '*':
                dp[0][j] = dp[0][j - 2]
        
        # 填充 DP 表
        for i in range(1, m + 1):
            for j in range(1, n + 1):
                # 如果当前字符匹配（相同字符或 '.'）
                if p[j - 1] == '.' or p[j - 1] == s[i - 1]:
                    dp[i][j] = dp[i - 1][j - 1]
                # 如果当前模式字符是 '*'
                elif p[j - 1] == '*':
                    # 匹配零次：忽略模式和前面的字符
                    dp[i][j] = dp[i][j - 2]
                    # 匹配多次：如果前一个模式字符与当前字符匹配
                    if p[j - 2] == '.' or p[j - 2] == s[i - 1]:
                        dp[i][j] = dp[i][j] or dp[i - 1][j]
        
        return dp[m][n]
```

### Java 代码实现
```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        
        // dp[i][j] 表示 s 的前 i 个字符和 p 的前 j 个字符是否匹配
        boolean[][] dp = new boolean[m + 1][n + 1];
        
        // 空字符串和空模式匹配
        dp[0][0] = true;
        
        // 初始化第一行（处理 a*, a*b*, a*b*c* 等模式）
        for (int j = 2; j <= n; j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = dp[0][j - 2];
            }
        }
        
        // 填充 DP 表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i - 1);
                char pc = p.charAt(j - 1);
                
                // 如果当前字符匹配（相同字符或 '.'）
                if (pc == '.' || pc == sc) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                // 如果当前模式字符是 '*'
                else if (pc == '*') {
                    // 匹配零次：忽略模式和前面的字符
                    dp[i][j] = dp[i][j - 2];
                    
                    // 匹配多次：如果前一个模式字符与当前字符匹配
                    char prev = p.charAt(j - 2);
                    if (prev == '.' || prev == sc) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                }
            }
        }
        
        return dp[m][n];
    }
}
```

### C 代码实现
```c
#include <stdbool.h>
#include <string.h>

bool isMatch(char* s, char* p) {
    int m = strlen(s);
    int n = strlen(p);
    
    // dp[i][j] 表示 s 的前 i 个字符和 p 的前 j 个字符是否匹配
    bool dp[m + 1][n + 1];
    memset(dp, 0, sizeof(dp));
    
    // 空字符串和空模式匹配
    dp[0][0] = true;
    
    // 初始化第一行（处理 a*, a*b*, a*b*c* 等模式）
    for (int j = 2; j <= n; j++) {
        if (p[j - 1] == '*') {
            dp[0][j] = dp[0][j - 2];
        }
    }
    
    // 填充 DP 表
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            char sc = s[i - 1];
            char pc = p[j - 1];
            
            // 如果当前字符匹配（相同字符或 '.'）
            if (pc == '.' || pc == sc) {
                dp[i][j] = dp[i - 1][j - 1];
            }
            // 如果当前模式字符是 '*'
            else if (pc == '*') {
                // 匹配零次：忽略模式和前面的字符
                dp[i][j] = dp[i][j - 2];
                
                // 匹配多次：如果前一个模式字符与当前字符匹配
                char prev = p[j - 2];
                if (prev == '.' || prev == sc) {
                    dp[i][j] = dp[i][j] || dp[i - 1][j];
                }
            }
        }
    }
    
    return dp[m][n];
}
```

---