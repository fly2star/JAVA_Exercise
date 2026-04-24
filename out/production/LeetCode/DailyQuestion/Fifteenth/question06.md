# 3212. 统计 X 和 Y 频数相等的子矩阵数量

**难度: 中等**

## 题目描述
给你一个二维字符矩阵 `grid`，其中 `grid[i][j]` 可能是 `'X'`、`'Y'` 或 `'.'`，返回满足以下条件的子矩阵数量：
- 包含 `grid[0][0]`
- `'X'` 和 `'Y'` 的频数相等
- 至少包含一个 `'X'`

---

## 示例说明
### 示例 1：

![3212-examplems](../../../readFile/image/examplems.png)

输入：grid = [["X","Y","."],["Y",".","."]]  
输出：3  
解释：满足条件的子矩阵有 3 个。

### 示例 2：
输入：grid = [["X","X"],["X","Y"]]  
输出：0  
解释：不存在满足 `'X'` 和 `'Y'` 频数相等的子矩阵。

### 示例 3：
输入：grid = [[".",".","."],[".",".","."]]  
输出：0  
解释：不存在满足至少包含一个 `'X'` 的子矩阵。

---

## 提示：
- m = grid.length
- n = grid[i].length
- 1 ≤ m, n ≤ 1000
- grid[i][j] 是 `'X'`、`'Y'` 或 `'.'`

---

## 解题思路

### 核心思想
题目要求子矩阵必须包含左上角元素 `(0,0)`，这意味着所有符合条件的子矩阵都是从 `(0,0)` 开始的连续矩形区域。我们可以使用**二维前缀和**来统计每个子矩阵中 `'X'` 和 `'Y'` 的数量，然后检查是否满足条件。

### 关键观察
- 子矩阵的左上角固定为 `(0,0)`，右下角可以是任意 `(i,j)`，其中 0 ≤ i < m，0 ≤ j < n
- 需要分别统计 `'X'` 和 `'Y'` 的数量，可以使用两个二维前缀和数组
- 条件要求：countX == countY 且 countX > 0

### 算法步骤
1. 构建两个二维前缀和数组 `prefixX` 和 `prefixY`：
   - `prefixX[i][j]` 表示从 `(0,0)` 到 `(i-1,j-1)` 的 `'X'` 数量
   - `prefixY[i][j]` 表示从 `(0,0)` 到 `(i-1,j-1)` 的 `'Y'` 数量
2. 遍历所有可能的右下角 `(i,j)`：
   - 计算从 `(0,0)` 到 `(i,j)` 的 `'X'` 数量 = `prefixX[i+1][j+1]`
   - 计算从 `(0,0)` 到 `(i,j)` 的 `'Y'` 数量 = `prefixY[i+1][j+1]`
   - 如果两者相等且大于 0，则计数加 1
3. 返回计数

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def numberOfSubmatrices(self, grid: List[List[str]]) -> int:
        m, n = len(grid), len(grid[0])
        
        # 构建二维前缀和
        prefixX = [[0] * (n + 1) for _ in range(m + 1)]
        prefixY = [[0] * (n + 1) for _ in range(m + 1)]
        
        for i in range(m):
            for j in range(n):
                prefixX[i + 1][j + 1] = prefixX[i + 1][j] + prefixX[i][j + 1] - prefixX[i][j]
                prefixY[i + 1][j + 1] = prefixY[i + 1][j] + prefixY[i][j + 1] - prefixY[i][j]
                
                if grid[i][j] == 'X':
                    prefixX[i + 1][j + 1] += 1
                elif grid[i][j] == 'Y':
                    prefixY[i + 1][j + 1] += 1
        
        # 统计符合条件的子矩阵
        count = 0
        for i in range(1, m + 1):
            for j in range(1, n + 1):
                x_count = prefixX[i][j]
                y_count = prefixY[i][j]
                if x_count == y_count and x_count > 0:
                    count += 1
        
        return count
```

### Java 代码实现
```java
class Solution {
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        
        // 构建二维前缀和
        int[][] prefixX = new int[m + 1][n + 1];
        int[][] prefixY = new int[m + 1][n + 1];
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefixX[i + 1][j + 1] = prefixX[i + 1][j] + prefixX[i][j + 1] - prefixX[i][j];
                prefixY[i + 1][j + 1] = prefixY[i + 1][j] + prefixY[i][j + 1] - prefixY[i][j];
                
                if (grid[i][j] == 'X') {
                    prefixX[i + 1][j + 1]++;
                } else if (grid[i][j] == 'Y') {
                    prefixY[i + 1][j + 1]++;
                }
            }
        }
        
        // 统计符合条件的子矩阵
        int count = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int xCount = prefixX[i][j];
                int yCount = prefixY[i][j];
                if (xCount == yCount && xCount > 0) {
                    count++;
                }
            }
        }
        
        return count;
    }
}
```

### C 代码实现
```c
int numberOfSubmatrices(char** grid, int gridSize, int* gridColSize) {
    int m = gridSize;
    int n = gridColSize[0];
    
    // 构建二维前缀和
    int** prefixX = (int**)malloc((m + 1) * sizeof(int*));
    int** prefixY = (int**)malloc((m + 1) * sizeof(int*));
    for (int i = 0; i <= m; i++) {
        prefixX[i] = (int*)calloc((n + 1), sizeof(int));
        prefixY[i] = (int*)calloc((n + 1), sizeof(int));
    }
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            prefixX[i + 1][j + 1] = prefixX[i + 1][j] + prefixX[i][j + 1] - prefixX[i][j];
            prefixY[i + 1][j + 1] = prefixY[i + 1][j] + prefixY[i][j + 1] - prefixY[i][j];
            
            if (grid[i][j] == 'X') {
                prefixX[i + 1][j + 1]++;
            } else if (grid[i][j] == 'Y') {
                prefixY[i + 1][j + 1]++;
            }
        }
    }
    
    // 统计符合条件的子矩阵
    int count = 0;
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (prefixX[i][j] == prefixY[i][j] && prefixX[i][j] > 0) {
                count++;
            }
        }
    }
    
    // 释放内存
    for (int i = 0; i <= m; i++) {
        free(prefixX[i]);
        free(prefixY[i]);
    }
    free(prefixX);
    free(prefixY);
    
    return count;
}
```

---