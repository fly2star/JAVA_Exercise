# 1594. 矩阵的最大非负积

**难度: 中等**

## 题目描述
给你一个大小为 `m x n` 的矩阵 `grid`。最初，你位于左上角 `(0, 0)`，每一步，你可以在矩阵中向右或向下移动。

在从左上角 `(0, 0)` 开始到右下角 `(m-1, n-1)` 结束的所有路径中，找出具有 **最大非负积** 的路径。路径的权是路径访问的单元格中所有整数的乘积。

返回 **最大非负积** 对 `10^9 + 7` 取余的结果。如果最大积为负数，则返回 `-1`。

注意，取余是在得到最大积之后执行的。

---

## 示例说明
### 示例 1：
输入：grid = [[-1,-2,-3],[-2,-3,-3],[-3,-3,-2]]  

![1594-product1](../../../readFile/image/product1.jpg)

输出：-1  
解释：从 (0,0) 到 (2,2) 的路径中无法得到非负积，所以返回 -1。

### 示例 2：
输入：grid = [[1,-2,1],[1,-2,1],[3,-4,1]]  

![1594-product2](../../../readFile/image/product2.jpg)

输出：8  
解释：最大非负积对应的路径如图所示 (1 * 1 * -2 * -4 * 1 = 8)。

### 示例 3：
输入：grid = [[1,3],[0,-4]]

![1594-product3](../../../readFile/image/product3.jpg)

输出：0  
解释：最大非负积对应的路径如图所示 (1 * 0 * -4 = 0)。

---

## 提示：
- m = grid.length
- n = grid[0].length
- 1 ≤ m, n ≤ 15
- -4 ≤ grid[i][j] ≤ 4

---

## 解题思路

### 核心思想
这是一个**动态规划**问题。由于路径中可能出现负数，乘积可能为正或负。为了得到最大非负积，我们需要同时维护到达每个位置的最大值和最小值，因为负数乘负数可能得到更大的正数。

### 关键观察
- 当遇到负数时，最大值和最小值的角色会互换
- 对于每个位置 `(i, j)`，只能从上方 `(i-1, j)` 或左方 `(i, j-1)` 到达
- 我们需要同时记录到达每个位置的最大乘积和最小乘积
- 最终答案是右下角的最大乘积（如果为负数，返回 -1）

### 算法步骤
1. 创建两个二维数组 `maxVal` 和 `minVal`，分别记录到达每个位置的最大乘积和最小乘积
2. 初始化 `maxVal[0][0] = minVal[0][0] = grid[0][0]`
3. 初始化第一行：只能从左方到达
    - `maxVal[0][j] = maxVal[0][j-1] * grid[0][j]`
    - `minVal[0][j] = minVal[0][j-1] * grid[0][j]`
4. 初始化第一列：只能从上方到达
    - `maxVal[i][0] = maxVal[i-1][0] * grid[i][0]`
    - `minVal[i][0] = minVal[i-1][0] * grid[i][0]`
5. 遍历其余位置 `(i, j)`：
    - 从上方和左方分别获取最大值和最小值
    - 计算从上方到达的乘积：`upMax = maxVal[i-1][j] * grid[i][j]`，`upMin = minVal[i-1][j] * grid[i][j]`
    - 计算从左方到达的乘积：`leftMax = maxVal[i][j-1] * grid[i][j]`，`leftMin = minVal[i][j-1] * grid[i][j]`
    - `maxVal[i][j] = max(upMax, upMin, leftMax, leftMin)`
    - `minVal[i][j] = min(upMax, upMin, leftMax, leftMin)`
6. 如果 `maxVal[m-1][n-1] < 0`，返回 -1；否则返回 `maxVal[m-1][n-1] % MOD`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def maxProductPath(self, grid: List[List[int]]) -> int:
        MOD = 10**9 + 7
        m, n = len(grid), len(grid[0])
        
        # 初始化最大值和最小值矩阵
        maxVal = [[0] * n for _ in range(m)]
        minVal = [[0] * n for _ in range(m)]
        
        # 起点
        maxVal[0][0] = minVal[0][0] = grid[0][0]
        
        # 初始化第一行
        for j in range(1, n):
            maxVal[0][j] = maxVal[0][j-1] * grid[0][j]
            minVal[0][j] = minVal[0][j-1] * grid[0][j]
        
        # 初始化第一列
        for i in range(1, m):
            maxVal[i][0] = maxVal[i-1][0] * grid[i][0]
            minVal[i][0] = minVal[i-1][0] * grid[i][0]
        
        # 填充剩余位置
        for i in range(1, m):
            for j in range(1, n):
                val = grid[i][j]
                # 从上方和左方获取可能的值
                candidates = [
                    maxVal[i-1][j] * val,
                    minVal[i-1][j] * val,
                    maxVal[i][j-1] * val,
                    minVal[i][j-1] * val
                ]
                maxVal[i][j] = max(candidates)
                minVal[i][j] = min(candidates)
        
        # 返回结果
        result = maxVal[m-1][n-1]
        if result < 0:
            return -1
        return result % MOD
```

### Java 代码实现
```java
class Solution {
    public int maxProductPath(int[][] grid) {
        final int MOD = 1_000_000_007;
        int m = grid.length;
        int n = grid[0].length;
        
        long[][] maxVal = new long[m][n];
        long[][] minVal = new long[m][n];
        
        // 起点
        maxVal[0][0] = minVal[0][0] = grid[0][0];
        
        // 初始化第一行
        for (int j = 1; j < n; j++) {
            maxVal[0][j] = maxVal[0][j-1] * grid[0][j];
            minVal[0][j] = minVal[0][j-1] * grid[0][j];
        }
        
        // 初始化第一列
        for (int i = 1; i < m; i++) {
            maxVal[i][0] = maxVal[i-1][0] * grid[i][0];
            minVal[i][0] = minVal[i-1][0] * grid[i][0];
        }
        
        // 填充剩余位置
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                long val = grid[i][j];
                long[] candidates = {
                    maxVal[i-1][j] * val,
                    minVal[i-1][j] * val,
                    maxVal[i][j-1] * val,
                    minVal[i][j-1] * val
                };
                
                long max = Long.MIN_VALUE;
                long min = Long.MAX_VALUE;
                for (long cand : candidates) {
                    max = Math.max(max, cand);
                    min = Math.min(min, cand);
                }
                maxVal[i][j] = max;
                minVal[i][j] = min;
            }
        }
        
        long result = maxVal[m-1][n-1];
        if (result < 0) {
            return -1;
        }
        return (int)(result % MOD);
    }
}
```

### C 代码实现
```c
#define MOD 1000000007

int maxProductPath(int** grid, int gridSize, int* gridColSize) {
    int m = gridSize;
    int n = gridColSize[0];
    
    long long** maxVal = (long long**)malloc(m * sizeof(long long*));
    long long** minVal = (long long**)malloc(m * sizeof(long long*));
    for (int i = 0; i < m; i++) {
        maxVal[i] = (long long*)malloc(n * sizeof(long long));
        minVal[i] = (long long*)malloc(n * sizeof(long long));
    }
    
    // 起点
    maxVal[0][0] = minVal[0][0] = grid[0][0];
    
    // 初始化第一行
    for (int j = 1; j < n; j++) {
        maxVal[0][j] = maxVal[0][j-1] * grid[0][j];
        minVal[0][j] = minVal[0][j-1] * grid[0][j];
    }
    
    // 初始化第一列
    for (int i = 1; i < m; i++) {
        maxVal[i][0] = maxVal[i-1][0] * grid[i][0];
        minVal[i][0] = minVal[i-1][0] * grid[i][0];
    }
    
    // 填充剩余位置
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            long long val = grid[i][j];
            long long candidates[4] = {
                maxVal[i-1][j] * val,
                minVal[i-1][j] * val,
                maxVal[i][j-1] * val,
                minVal[i][j-1] * val
            };
            
            long long max = candidates[0];
            long long min = candidates[0];
            for (int t = 1; t < 4; t++) {
                if (candidates[t] > max) max = candidates[t];
                if (candidates[t] < min) min = candidates[t];
            }
            maxVal[i][j] = max;
            minVal[i][j] = min;
        }
    }
    
    long long result = maxVal[m-1][n-1];
    
    // 释放内存
    for (int i = 0; i < m; i++) {
        free(maxVal[i]);
        free(minVal[i]);
    }
    free(maxVal);
    free(minVal);
    
    if (result < 0) {
        return -1;
    }
    return (int)(result % MOD);
}
```

---