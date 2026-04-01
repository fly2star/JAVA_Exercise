# 3546. 等和矩阵分割Ⅰ

**难度: 中等**

## 题目描述
给你一个由正整数组成的 `m x n` 矩阵 `grid`。你的任务是判断是否可以通过一条水平或一条垂直分割线将矩阵分割成两部分，使得：

- 分割后形成的每个部分都是 **非空的**。
- 两个部分中所有元素的和 **相等**。

如果存在这样的分割，返回 `true`；否则，返回 `false`。

---

## 示例说明
### 示例 1：
输入：grid = [[1,4],[2,3]]  
输出：true  
解释：在第 0 行和第 1 行之间进行水平分割，得到两个非空部分，每部分的元素之和为 5。因此，答案是 true。

### 示例 2：
输入：grid = [[1,3],[2,4]]  
输出：false  
解释：无论是水平分割还是垂直分割，都无法使两个非空部分的元素之和相等。因此，答案是 false。

---

## 提示：
- 1 < m = grid.length ≤ 10^5
- 1 < n = grid[i].length ≤ 10^5
- 2 < m * n ≤ 10^5
- 1 < grid[i][j] ≤ 10^5

---

## 解题思路

### 核心思想
判断是否存在水平或垂直分割线，使得分割后两部分的元素和相等。可以先计算整个矩阵的总和 `total`，如果 `total` 是奇数，则不可能等分。然后分别检查水平方向和垂直方向是否存在分割点。

### 关键观察
- 水平分割：对于每一行，计算累积行和 `rowSum`，如果存在某个前缀行的和等于 `total / 2`，则找到水平分割
- 垂直分割：对于每一列，计算累积列和 `colSum`，如果存在某个前缀列的和等于 `total / 2`，则找到垂直分割
- 由于 `m * n ≤ 10^5`，可以直接计算行前缀和和列前缀和

### 算法步骤
1. 计算整个矩阵的总和 `total`
2. 如果 `total % 2 != 0`，直接返回 false
3. 计算每一行的和，存入数组 `rowSum`
4. 计算每一列的和，存入数组 `colSum`
5. 检查水平分割：
   - 初始化 `prefix = 0`
   - 遍历前 `m-1` 行，累加 `prefix += rowSum[i]`
   - 如果 `prefix == total / 2`，返回 true
6. 检查垂直分割：
   - 初始化 `prefix = 0`
   - 遍历前 `n-1` 列，累加 `prefix += colSum[j]`
   - 如果 `prefix == total / 2`，返回 true
7. 如果都没有找到，返回 false

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def canSplit(self, grid: List[List[int]]) -> bool:
        m, n = len(grid), len(grid[0])
        
        # 计算每一行的和
        row_sum = [sum(row) for row in grid]
        
        # 计算每一列的和
        col_sum = [0] * n
        for i in range(m):
            for j in range(n):
                col_sum[j] += grid[i][j]
        
        total = sum(row_sum)
        
        # 如果总和是奇数，不可能平分
        if total % 2 != 0:
            return False
        
        target = total // 2
        
        # 检查水平分割
        prefix = 0
        for i in range(m - 1):
            prefix += row_sum[i]
            if prefix == target:
                return True
        
        # 检查垂直分割
        prefix = 0
        for j in range(n - 1):
            prefix += col_sum[j]
            if prefix == target:
                return True
        
        return False
```

### Java 代码实现
```java
class Solution {
    public boolean canSplit(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // 计算每一行的和
        long[] rowSum = new long[m];
        // 计算每一列的和
        long[] colSum = new long[n];
        long total = 0;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowSum[i] += grid[i][j];
                colSum[j] += grid[i][j];
                total += grid[i][j];
            }
        }
        
        // 如果总和是奇数，不可能平分
        if (total % 2 != 0) {
            return false;
        }
        
        long target = total / 2;
        
        // 检查水平分割
        long prefix = 0;
        for (int i = 0; i < m - 1; i++) {
            prefix += rowSum[i];
            if (prefix == target) {
                return true;
            }
        }
        
        // 检查垂直分割
        prefix = 0;
        for (int j = 0; j < n - 1; j++) {
            prefix += colSum[j];
            if (prefix == target) {
                return true;
            }
        }
        
        return false;
    }
}
```

### C 代码实现
```c
bool canSplit(int** grid, int gridSize, int* gridColSize) {
    int m = gridSize;
    int n = gridColSize[0];
    
    // 计算每一行的和
    long long* rowSum = (long long*)calloc(m, sizeof(long long));
    // 计算每一列的和
    long long* colSum = (long long*)calloc(n, sizeof(long long));
    long long total = 0;
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            rowSum[i] += grid[i][j];
            colSum[j] += grid[i][j];
            total += grid[i][j];
        }
    }
    
    // 如果总和是奇数，不可能平分
    if (total % 2 != 0) {
        free(rowSum);
        free(colSum);
        return false;
    }
    
    long long target = total / 2;
    
    // 检查水平分割
    long long prefix = 0;
    for (int i = 0; i < m - 1; i++) {
        prefix += rowSum[i];
        if (prefix == target) {
            free(rowSum);
            free(colSum);
            return true;
        }
    }
    
    // 检查垂直分割
    prefix = 0;
    for (int j = 0; j < n - 1; j++) {
        prefix += colSum[j];
        if (prefix == target) {
            free(rowSum);
            free(colSum);
            return true;
        }
    }
    
    free(rowSum);
    free(colSum);
    return false;
}
```

---