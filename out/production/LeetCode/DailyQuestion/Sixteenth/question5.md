# 44. 通配符匹配

**难度: 困难**

## 题目描述
给你一个输入字符串（s）和一个字符模式（p），请你实现一个支持 `'?'` 和 `'*'` 匹配规则的通配符匹配：
- `'?'` 可以匹配任何单个字符。
- `'*'` 可以匹配任意字符序列（包括空字符序列）。

判定匹配成功的充要条件是：字符模式必须能够 **完全匹配** 输入字符串（而不是部分匹配）。

---

## 示例说明
### 示例 1：
输入：s = "aa", p = "a"  
输出：false  
解释："a" 无法匹配 "aa" 整个字符串。

### 示例 2：
输入：s = "aa", p = "*"  
输出：true  
解释：'*' 可以匹配任意字符序列。

### 示例 3：
输入：s = "cb", p = "?a"  
输出：false  
解释：'?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。

---

## 提示：
- 0 ≤ s.length, p.length ≤ 2000
- s 仅由小写英文字母组成
- p 仅由小写英文字母、'?' 或 '*' 组成

---

## 解题思路

### 核心思想
使用**动态规划**或**贪心 + 双指针**。动态规划定义 `dp[i][j]` 表示 s 的前 i 个字符与 p 的前 j 个字符是否匹配。转移时处理 `'?'` 和 `'*'`。

### 关键观察
- `'?'` 匹配单个字符，状态转移：`dp[i][j] = dp[i-1][j-1]`
- `'*'` 可以匹配空序列或任意长度序列，转移：
  - 匹配空：`dp[i][j] = dp[i][j-1]`
  - 匹配一个字符：`dp[i][j] = dp[i-1][j]`（保持星号继续匹配后续）
- 优化：由于数据规模较大，可使用滚动数组降低空间复杂度。

### 算法步骤
1. 初始化 `dp[0][0] = True`，空模式匹配空串。
2. 处理模式 p 开头的连续 `'*'`，它们可以匹配空串。
3. 遍历 i 从 1 到 m，j 从 1 到 n：
   - 若 `p[j-1] == '*'`，则 `dp[i][j] = dp[i][j-1] or dp[i-1][j]`
   - 若 `p[j-1] == '?'` 或 `s[i-1] == p[j-1]`，则 `dp[i][j] = dp[i-1][j-1]`
   - 否则 `dp[i][j] = False`
4. 返回 `dp[m][n]`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def isMatch(self, s: str, p: str) -> bool:
        m, n = len(s), len(p)
        dp = [[False] * (n + 1) for _ in range(m + 1)]
        dp[0][0] = True
        # 处理模式开头的连续 '*'
        for j in range(1, n + 1):
            if p[j-1] == '*':
                dp[0][j] = dp[0][j-1]
        for i in range(1, m + 1):
            for j in range(1, n + 1):
                if p[j-1] == '*':
                    dp[i][j] = dp[i][j-1] or dp[i-1][j]
                elif p[j-1] == '?' or s[i-1] == p[j-1]:
                    dp[i][j] = dp[i-1][j-1]
        return dp[m][n]
```

### Java 代码实现
```java
class Solution {
    public boolean isMatch(String s, String p) {
        int m = s.length(), n = p.length();
        boolean[][] dp = new boolean[m+1][n+1];
        dp[0][0] = true;
        for (int j = 1; j <= n; j++) {
            if (p.charAt(j-1) == '*') dp[0][j] = dp[0][j-1];
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                char sc = s.charAt(i-1);
                char pc = p.charAt(j-1);
                if (pc == '*') {
                    dp[i][j] = dp[i][j-1] || dp[i-1][j];
                } else if (pc == '?' || sc == pc) {
                    dp[i][j] = dp[i-1][j-1];
                }
            }
        }
        return dp[m][n];
    }
}
```

### C 代码实现
```c
bool isMatch(char* s, char* p) {
    int m = strlen(s), n = strlen(p);
    bool dp[m+1][n+1];
    memset(dp, 0, sizeof(dp));
    dp[0][0] = true;
    for (int j = 1; j <= n; j++) {
        if (p[j-1] == '*') dp[0][j] = dp[0][j-1];
    }
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (p[j-1] == '*') {
                dp[i][j] = dp[i][j-1] || dp[i-1][j];
            } else if (p[j-1] == '?' || s[i-1] == p[j-1]) {
                dp[i][j] = dp[i-1][j-1];
            }
        }
    }
    return dp[m][n];
}
```

---