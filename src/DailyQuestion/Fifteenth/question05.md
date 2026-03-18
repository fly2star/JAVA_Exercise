# 3070. 元素和小于等于 k 的子矩阵的数目

**难度: 中等**

## 题目描述
给你一个下标从 0 开始的整数矩阵 grid 和一个整数 k。

返回包含 grid 左上角元素、元素和小于或等于 k 的子矩阵的数目。

---

## 示例说明
### 示例 1：
输入：grid = [[7,6,3],[6,6,1]], k = 18  

![3070-example1](../../../readFile/image/example1.png)

输出：4  
解释：如上图所示，只有 4 个子矩阵满足：包含 grid 的左上角元素，并且元素和小于或等于 18。

### 示例 2：
输入：grid = [[7,2,9],[1,5,0],[2,6,6]], k = 20  

![3070-example21](../../../readFile/image/example21.png)

输出：6  
解释：如上图所示，只有 6 个子矩阵满足：包含 grid 的左上角元素，并且元素和小于或等于 20。

---

## 提示：
- m = grid.length
- n = grid[i].length
- 1 ≤ n, m ≤ 1000
- 0 ≤ grid[i][j] ≤ 1000
- 1 ≤ k ≤ 10^9

---

## 解题思路

### 核心思想
题目要求子矩阵必须包含左上角元素 `(0,0)`，这意味着所有符合条件的子矩阵都是从 `(0,0)` 开始的连续矩形区域。因此，我们可以使用**二维前缀和**来快速计算任意从 `(0,0)` 到 `(i,j)` 的子矩阵的和，然后统计所有和 ≤ k 的子矩阵个数。

### 关键观察
- 子矩阵的左上角固定为 `(0,0)`，右下角可以是任意 `(i,j)`，其中 0 ≤ i < m，0 ≤ j < n
- 因此总共有 m × n 个可能的子矩阵
- 使用二维前缀和可以在 O(1) 时间内计算任意子矩阵的和
- 当 m 和 n 都较大时，需要注意前缀和可能超过 int 范围，需要使用 long 类型

### 算法步骤
1. 构建二维前缀和数组 `prefix`，其中 `prefix[i][j]` 表示从 `(0,0)` 到 `(i-1,j-1)` 的子矩阵和
2. 遍历所有可能的右下角 `(i,j)`：
   - 计算从 `(0,0)` 到 `(i,j)` 的子矩阵和
   - 如果和 ≤ k，则计数加 1
3. 返回计数

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def countSubmatrices(self, grid: List[List[int]], k: int) -> int:
        m, n = len(grid), len(grid[0])
        
        # 构建二维前缀和
        prefix = [[0] * (n + 1) for _ in range(m + 1)]
        for i in range(m):
            for j in range(n):
                prefix[i + 1][j + 1] = prefix[i + 1][j] + prefix[i][j + 1] - prefix[i][j] + grid[i][j]
        
        # 统计符合条件的子矩阵
        count = 0
        for i in range(1, m + 1):
            for j in range(1, n + 1):
                if prefix[i][j] <= k:
                    count += 1
                else:
                    # 由于前缀和是非递减的，如果当前 (i,j) 已经 > k，那么同一行后面 j 更大的也会 > k
                    # 但注意不同行之间没有这个性质，因为增加行可能增加和也可能减少（不会减少）
                    # 实际上 grid 元素都是非负的，所以前缀和是单调递增的
                    # 因此可以提前跳出内层循环
                    break
        
        return count
```

### Java 代码实现
```java
class Solution {
    public int countSubmatrices(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        
        // 构建二维前缀和
        long[][] prefix = new long[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefix[i + 1][j + 1] = prefix[i + 1][j] + prefix[i][j + 1] - prefix[i][j] + grid[i][j];
            }
        }
        
        // 统计符合条件的子矩阵
        int count = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (prefix[i][j] <= k) {
                    count++;
                } else {
                    // 由于 grid 元素非负，前缀和单调递增，所以可以提前跳出内层循环
                    break;
                }
            }
        }
        
        return count;
    }
}
```

### C 代码实现
```c
int countSubmatrices(int** grid, int gridSize, int* gridColSize, int k) {
    int m = gridSize;
    int n = gridColSize[0];
    
    // 构建二维前缀和
    long long** prefix = (long long**)malloc((m + 1) * sizeof(long long*));
    for (int i = 0; i <= m; i++) {
        prefix[i] = (long long*)calloc((n + 1), sizeof(long long));
    }
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            prefix[i + 1][j + 1] = prefix[i + 1][j] + prefix[i][j + 1] - prefix[i][j] + grid[i][j];
        }
    }
    
    // 统计符合条件的子矩阵
    int count = 0;
    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (prefix[i][j] <= k) {
                count++;
            } else {
                // 由于 grid 元素非负，前缀和单调递增，所以可以提前跳出内层循环
                break;
            }
        }
    }
    
    // 释放内存
    for (int i = 0; i <= m; i++) {
        free(prefix[i]);
    }
    free(prefix);
    
    return count;
}
```

---