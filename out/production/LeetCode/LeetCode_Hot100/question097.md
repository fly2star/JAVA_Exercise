# 62. 不同路径

**难度: 中等**

## 题目描述
一个机器人位于一个 `m x n` 网格的左上角（起始点在下图中标记为 “Start”）。

机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。

问总共有多少条不同的路径？

---

## 示例说明
### 示例 1：

![1697422740-adxmsI-image](../../readFile/image/1697422740-adxmsI-image.png)

输入：m = 3, n = 7  
输出：28

### 示例 2：
输入：m = 3, n = 2  
输出：3  
解释：从左上角开始，总共有 3 条路径可以到达右下角：
1. 向右 → 向下 → 向下
2. 向下 → 向下 → 向右
3. 向下 → 向右 → 向下

### 示例 3：
输入：m = 7, n = 3  
输出：28

---

## 提示：
- 1 ≤ m, n ≤ 100
- 题目数据保证答案小于等于 2 * 10^9

---

## 解题思路

### 核心思想
这是一个经典的**动态规划**问题，也可以看作**组合数学**问题。从左上角到右下角，总共需要移动 (m-1) 次向下和 (n-1) 次向右，总共 (m+n-2) 步，问题转化为在 (m+n-2) 步中选择 (m-1) 步向下的组合数。

### 关键观察
- 动态规划方法：`dp[i][j]` 表示到达位置 (i, j) 的不同路径数
- 状态转移方程：`dp[i][j] = dp[i-1][j] + dp[i][j-1]`
- 边界条件：第一行和第一列都只有 1 条路径
- 组合数学方法：答案 = C(m+n-2, m-1) = C(m+n-2, n-1)

### 算法步骤

#### 方法一：动态规划
1. 创建 DP 数组 `dp[m][n]`
2. 初始化第一行和第一列为 1
3. 遍历填充：`dp[i][j] = dp[i-1][j] + dp[i][j-1]`
4. 返回 `dp[m-1][n-1]`

#### 方法二：组合数学
1. 计算组合数 C(m+n-2, m-1)
2. 注意使用 long 类型避免溢出

---

## 代码参考(python, java, c)

### Python 代码实现
```python
# 方法一：动态规划
class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        # 创建 DP 数组
        dp = [[1] * n for _ in range(m)]
        
        # 填充 DP 表
        for i in range(1, m):
            for j in range(1, n):
                dp[i][j] = dp[i-1][j] + dp[i][j-1]
        
        return dp[m-1][n-1]

# 方法二：组合数学
class Solution:
    def uniquePaths(self, m: int, n: int) -> int:
        # 计算 C(m+n-2, m-1)
        total = m + n - 2
        k = min(m - 1, n - 1)
        
        result = 1
        for i in range(1, k + 1):
            result = result * (total - k + i) // i
        
        return result
```

### Java 代码实现
```java
// 方法一：动态规划
class Solution {
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        
        // 初始化第一行和第一列
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        
        // 填充 DP 表
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        
        return dp[m-1][n-1];
    }
}

// 方法二：组合数学
class Solution {
    public int uniquePaths(int m, int n) {
        long result = 1;
        int total = m + n - 2;
        int k = Math.min(m - 1, n - 1);
        
        for (int i = 1; i <= k; i++) {
            result = result * (total - k + i) / i;
        }
        
        return (int) result;
    }
}
```

### C 代码实现
```c
// 方法一：动态规划
int uniquePaths(int m, int n) {
    // 创建 DP 数组
    int** dp = (int**)malloc(m * sizeof(int*));
    for (int i = 0; i < m; i++) {
        dp[i] = (int*)malloc(n * sizeof(int));
    }
    
    // 初始化第一行和第一列
    for (int i = 0; i < m; i++) {
        dp[i][0] = 1;
    }
    for (int j = 0; j < n; j++) {
        dp[0][j] = 1;
    }
    
    // 填充 DP 表
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = dp[i-1][j] + dp[i][j-1];
        }
    }
    
    int result = dp[m-1][n-1];
    
    // 释放内存
    for (int i = 0; i < m; i++) {
        free(dp[i]);
    }
    free(dp);
    
    return result;
}

// 方法二：组合数学
int uniquePaths(int m, int n) {
    long result = 1;
    int total = m + n - 2;
    int k = (m - 1 < n - 1) ? m - 1 : n - 1;
    
    for (int i = 1; i <= k; i++) {
        result = result * (total - k + i) / i;
    }
    
    return (int) result;
}
```

---