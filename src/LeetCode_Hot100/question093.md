# 72. 编辑距离

**难度: 困难**

## 题目描述
给你两个单词 `word1` 和 `word2`，请返回将 `word1` 转换成 `word2` 所使用的最少操作数。

你可以对一个单词进行如下三种操作：
- 插入一个字符
- 删除一个字符
- 替换一个字符

---

## 示例说明
### 示例 1：
输入：word1 = "horse", word2 = "ros"  
输出：3  
解释：
- horse -> rorse (将 'h' 替换为 'r')
- rorse -> rose (删除 'r')
- rose -> ros (删除 'e')

### 示例 2：
输入：word1 = "intention", word2 = "execution"  
输出：5  
解释：
- intention -> intention (删除 't')
- intention -> enention (将 'i' 替换为 'e')
- enention -> exention (将 'n' 替换为 'x')
- exention -> exection (将 'n' 替换为 'c')
- exection -> execution (插入 'u')

---

## 提示：
- 0 ≤ word1.length, word2.length ≤ 500
- word1 和 word2 由小写英文字母组成

---

## 解题思路

### 核心思想
使用**动态规划**来解决编辑距离问题。定义 `dp[i][j]` 表示将 word1 的前 i 个字符转换为 word2 的前 j 个字符所需要的最少操作数。

### 关键观察
- 三种操作对应三种状态转移：
  - 插入：`dp[i][j] = dp[i][j-1] + 1`
  - 删除：`dp[i][j] = dp[i-1][j] + 1`
  - 替换：`dp[i][j] = dp[i-1][j-1] + 1`
- 如果当前字符相同，则不需要操作：`dp[i][j] = dp[i-1][j-1]`
- 最终答案是 `dp[m][n]`

### 算法步骤
1. 创建 DP 数组 `dp[m+1][n+1]`，其中 m 和 n 分别是两个字符串的长度
2. 初始化边界条件：
   - `dp[0][j] = j`：将空字符串转换为 word2 的前 j 个字符需要 j 次插入
   - `dp[i][0] = i`：将 word1 的前 i 个字符转换为空字符串需要 i 次删除
3. 遍历所有 i 从 1 到 m，j 从 1 到 n：
   - 如果 `word1[i-1] == word2[j-1]`，则 `dp[i][j] = dp[i-1][j-1]`
   - 否则，`dp[i][j] = min(dp[i-1][j], dp[i][j-1], dp[i-1][j-1]) + 1`
4. 返回 `dp[m][n]`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def minDistance(self, word1: str, word2: str) -> int:
        m, n = len(word1), len(word2)
        
        # 创建 DP 数组
        dp = [[0] * (n + 1) for _ in range(m + 1)]
        
        # 初始化边界条件
        for i in range(m + 1):
            dp[i][0] = i
        for j in range(n + 1):
            dp[0][j] = j
        
        # 填充 DP 表
        for i in range(1, m + 1):
            for j in range(1, n + 1):
                if word1[i - 1] == word2[j - 1]:
                    dp[i][j] = dp[i - 1][j - 1]
                else:
                    dp[i][j] = min(
                        dp[i - 1][j],      # 删除
                        dp[i][j - 1],      # 插入
                        dp[i - 1][j - 1]   # 替换
                    ) + 1
        
        return dp[m][n]
```

### Java 代码实现
```java
class Solution {
    public int minDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();
        
        // 创建 DP 数组
        int[][] dp = new int[m + 1][n + 1];
        
        // 初始化边界条件
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }
        
        // 填充 DP 表
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j], dp[i][j - 1]),
                        dp[i - 1][j - 1]
                    ) + 1;
                }
            }
        }
        
        return dp[m][n];
    }
}
```

### C 代码实现
```c
#include <string.h>
#include <stdio.h>

int min(int a, int b) {
    return a < b ? a : b;
}

int minDistance(char* word1, char* word2) {
    int m = strlen(word1);
    int n = strlen(word2);
    
    // 创建 DP 数组
    int dp[m + 1][n + 1];
    
    // 初始化边界条件
    for (int i = 0; i <= m; i++) {
        dp[i][0] = i;
    }
    for (int j = 0; j <= n; j++) {
        dp[0][j] = j;
    }
    
    // 填充 DP 表
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (word1[i - 1] == word2[j - 1]) {
                dp[i][j] = dp[i - 1][j - 1];
            } else {
                dp[i][j] = min(
                    min(dp[i - 1][j], dp[i][j - 1]),
                    dp[i - 1][j - 1]
                ) + 1;
            }
        }
    }
    
    return dp[m][n];
}
```

---