# 64. 最小路径和

**难度: 中等**

## 题目描述
给定一个包含非负整数的 `m x n` 网格 grid，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。

**说明：** 每次只能向下或者向右移动一步。

---

## 示例说明
### 示例 1：

![minpath](../../readFile/image/minpath.jpg)

输入：grid = [[1,3,1],[1,5,1],[4,2,1]]  
输出：7  
解释：因为路径 1→3→1→1→1 的总和最小。

### 示例 2：
输入：grid = [[1,2,3],[4,5,6]]  
输出：12

---

## 提示：
- m = grid.length
- n = grid[i].length
- 1 ≤ m, n ≤ 200
- 0 ≤ grid[i][j] ≤ 200

---

## 解题思路

### 核心思想
使用**动态规划**来解决最短路径问题。定义 `dp[i][j]` 表示从左上角到达位置 (i, j) 的最小路径和。

### 关键观察
- 由于只能向右或向下移动，到达 (i, j) 只能从上方 (i-1, j) 或左方 (i, j-1) 过来
- 状态转移方程：`dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]`
- 需要处理边界情况：第一行只能从左边来，第一列只能从上边来

### 算法步骤
1. 获取网格的行数 m 和列数 n
2. 创建 DP 数组 `dp[m][n]`
3. 初始化左上角：`dp[0][0] = grid[0][0]`
4. 初始化第一行：`dp[0][j] = dp[0][j-1] + grid[0][j]`
5. 初始化第一列：`dp[i][0] = dp[i-1][0] + grid[i][0]`
6. 遍历其余位置：`dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]`
7. 返回 `dp[m-1][n-1]`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def minPathSum(self, grid: List[List[int]]) -> int:
        if not grid or not grid[0]:
            return 0
        
        m, n = len(grid), len(grid[0])
        
        # 创建 DP 数组
        dp = [[0] * n for _ in range(m)]
        
        # 初始化左上角
        dp[0][0] = grid[0][0]
        
        # 初始化第一行
        for j in range(1, n):
            dp[0][j] = dp[0][j-1] + grid[0][j]
        
        # 初始化第一列
        for i in range(1, m):
            dp[i][0] = dp[i-1][0] + grid[i][0]
        
        # 填充 DP 表
        for i in range(1, m):
            for j in range(1, n):
                dp[i][j] = min(dp[i-1][j], dp[i][j-1]) + grid[i][j]
        
        return dp[m-1][n-1]
```

### Java 代码实现
```java
class Solution {
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        
        int m = grid.length;
        int n = grid[0].length;
        
        // 创建 DP 数组
        int[][] dp = new int[m][n];
        
        // 初始化左上角
        dp[0][0] = grid[0][0];
        
        // 初始化第一行
        for (int j = 1; j < n; j++) {
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }
        
        // 初始化第一列
        for (int i = 1; i < m; i++) {
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        
        // 填充 DP 表
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = Math.min(dp[i-1][j], dp[i][j-1]) + grid[i][j];
            }
        }
        
        return dp[m-1][n-1];
    }
}
```

### C 代码实现
```c
int minPathSum(int** grid, int gridSize, int* gridColSize) {
    if (gridSize == 0 || gridColSize[0] == 0) {
        return 0;
    }
    
    int m = gridSize;
    int n = gridColSize[0];
    
    // 创建 DP 数组
    int** dp = (int**)malloc(m * sizeof(int*));
    for (int i = 0; i < m; i++) {
        dp[i] = (int*)malloc(n * sizeof(int));
    }
    
    // 初始化左上角
    dp[0][0] = grid[0][0];
    
    // 初始化第一行
    for (int j = 1; j < n; j++) {
        dp[0][j] = dp[0][j-1] + grid[0][j];
    }
    
    // 初始化第一列
    for (int i = 1; i < m; i++) {
        dp[i][0] = dp[i-1][0] + grid[i][0];
    }
    
    // 填充 DP 表
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            int min = dp[i-1][j] < dp[i][j-1] ? dp[i-1][j] : dp[i][j-1];
            dp[i][j] = min + grid[i][j];
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
```

---