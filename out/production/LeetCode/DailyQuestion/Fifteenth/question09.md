# 2906. 构造乘积矩阵

**难度: 中等**

## 题目描述
给你一个下标从 0 开始、大小为 `n x m` 的二维整数矩阵 grid，定义一个下标从 0 开始、大小为 `n x m` 的二维矩阵 p。如果满足以下条件，则称 p 为 grid 的 **乘积矩阵**：

- 对于每个元素 `p[i][j]`，它的值等于除了 `grid[i][j]` 外所有元素的乘积。乘积对 12345 取余数。

返回 grid 的乘积矩阵。

---

## 示例说明
### 示例 1：
输入：grid = [[1,2],[3,4]]  
输出：[[24,12],[8,6]]  
解释：
- p[0][0] = grid[0][1] * grid[1][0] * grid[1][1] = 2 * 3 * 4 = 24
- p[0][1] = grid[0][0] * grid[1][0] * grid[1][1] = 1 * 3 * 4 = 12
- p[1][0] = grid[0][0] * grid[0][1] * grid[1][1] = 1 * 2 * 4 = 8
- p[1][1] = grid[0][0] * grid[0][1] * grid[1][0] = 1 * 2 * 3 = 6

### 示例 2：
输入：grid = [[12345],[2],[1]]  
输出：[[2],[0],[0]]  
解释：
- p[0][0] = grid[1][0] * grid[2][0] = 2 * 1 = 2
- p[1][0] = grid[0][0] * grid[2][0] = 12345 * 1 = 12345 % 12345 = 0
- p[2][0] = grid[0][0] * grid[1][0] = 12345 * 2 = 24690 % 12345 = 0

---

## 提示：
- 1 < n = grid.length < 10^5
- 1 < m = grid[i].length < 10^5
- 2 < n * m < 10^5
- 1 < grid[i][j] < 10^9

---

## 解题思路

### 核心思想
这个问题要求计算除自身外所有元素的乘积，类似于一维数组的"除自身以外数组的乘积"问题的二维扩展。由于矩阵元素数量最多为 10^5，我们可以使用**前缀积和后缀积**的方法来避免重复计算。

### 关键观察
- 对于一维数组，可以通过前缀积和后缀积计算除自身外的乘积
- 对于二维矩阵，我们可以将其展平为一维数组，然后应用相同的方法
- 由于取模的模数是 12345，不是质数，不能使用乘法逆元，所以必须使用前缀积和后缀积的方法

### 算法步骤
1. 获取矩阵的行数 `n` 和列数 `m`，计算总元素个数 `total = n * m`
2. 将二维矩阵展平为一维数组 `arr`（按行优先顺序）
3. 计算前缀积数组 `prefix`：
   - `prefix[0] = 1`
   - `prefix[i] = (prefix[i-1] * arr[i-1]) % MOD` 对于 i 从 1 到 total
4. 计算后缀积数组 `suffix`：
   - `suffix[total] = 1`
   - `suffix[i] = (suffix[i+1] * arr[i]) % MOD` 对于 i 从 total-1 到 0
5. 对于每个位置 i，`result[i] = (prefix[i] * suffix[i+1]) % MOD`
6. 将结果重新组织成 `n x m` 的二维矩阵返回

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def constructProductMatrix(self, grid: List[List[int]]) -> List[List[int]]:
        MOD = 12345
        n, m = len(grid), len(grid[0])
        total = n * m
        
        # 展平为一维数组
        arr = []
        for i in range(n):
            for j in range(m):
                arr.append(grid[i][j] % MOD)
        
        # 计算前缀积
        prefix = [1] * (total + 1)
        for i in range(1, total + 1):
            prefix[i] = (prefix[i - 1] * arr[i - 1]) % MOD
        
        # 计算后缀积
        suffix = [1] * (total + 2)
        for i in range(total - 1, -1, -1):
            suffix[i] = (suffix[i + 1] * arr[i]) % MOD
        
        # 计算每个位置的乘积
        res_flat = [0] * total
        for i in range(total):
            res_flat[i] = (prefix[i] * suffix[i + 1]) % MOD
        
        # 重塑为二维矩阵
        result = []
        idx = 0
        for i in range(n):
            row = []
            for j in range(m):
                row.append(res_flat[idx])
                idx += 1
            result.append(row)
        
        return result
```

### Java 代码实现
```java
class Solution {
    public int[][] constructProductMatrix(int[][] grid) {
        final int MOD = 12345;
        int n = grid.length;
        int m = grid[0].length;
        int total = n * m;
        
        // 展平为一维数组
        int[] arr = new int[total];
        int idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                arr[idx++] = grid[i][j] % MOD;
            }
        }
        
        // 计算前缀积
        int[] prefix = new int[total + 1];
        prefix[0] = 1;
        for (int i = 1; i <= total; i++) {
            prefix[i] = (prefix[i - 1] * arr[i - 1]) % MOD;
        }
        
        // 计算后缀积
        int[] suffix = new int[total + 2];
        suffix[total] = 1;
        for (int i = total - 1; i >= 0; i--) {
            suffix[i] = (suffix[i + 1] * arr[i]) % MOD;
        }
        
        // 计算每个位置的乘积
        int[] resFlat = new int[total];
        for (int i = 0; i < total; i++) {
            resFlat[i] = (int)((long)prefix[i] * suffix[i + 1] % MOD);
        }
        
        // 重塑为二维矩阵
        int[][] result = new int[n][m];
        idx = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                result[i][j] = resFlat[idx++];
            }
        }
        
        return result;
    }
}
```

### C 代码实现
```c
/**
 * Return an array of arrays of size *returnSize.
 * The sizes of the arrays are returned as *returnColumnSizes array.
 * Note: Both returned array and *columnSizes array must be malloced, assume caller calls free().
 */

int** constructProductMatrix(int** grid, int gridSize, int* gridColSize, int* returnSize, int** returnColumnSizes) {
    const int MOD = 12345;
    int n = gridSize;
    int m = gridColSize[0];
    int total = n * m;
    
    *returnSize = n;
    *returnColumnSizes = (int*)malloc(n * sizeof(int));
    for (int i = 0; i < n; i++) {
        (*returnColumnSizes)[i] = m;
    }
    
    // 展平为一维数组
    int* arr = (int*)malloc(total * sizeof(int));
    int idx = 0;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            arr[idx++] = grid[i][j] % MOD;
        }
    }
    
    // 计算前缀积
    int* prefix = (int*)malloc((total + 1) * sizeof(int));
    prefix[0] = 1;
    for (int i = 1; i <= total; i++) {
        prefix[i] = (prefix[i - 1] * arr[i - 1]) % MOD;
    }
    
    // 计算后缀积
    int* suffix = (int*)malloc((total + 2) * sizeof(int));
    suffix[total] = 1;
    for (int i = total - 1; i >= 0; i--) {
        suffix[i] = (suffix[i + 1] * arr[i]) % MOD;
    }
    
    // 计算每个位置的乘积
    int* resFlat = (int*)malloc(total * sizeof(int));
    for (int i = 0; i < total; i++) {
        resFlat[i] = (int)((long long)prefix[i] * suffix[i + 1] % MOD);
    }
    
    // 重塑为二维矩阵
    int** result = (int**)malloc(n * sizeof(int*));
    idx = 0;
    for (int i = 0; i < n; i++) {
        result[i] = (int*)malloc(m * sizeof(int));
        for (int j = 0; j < m; j++) {
            result[i][j] = resFlat[idx++];
        }
    }
    
    // 释放临时内存
    free(arr);
    free(prefix);
    free(suffix);
    free(resFlat);
    
    return result;
}
```

---