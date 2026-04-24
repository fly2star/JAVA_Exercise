# 3567. 子矩阵的最小绝对差

**难度: 中等**

## 题目描述
给你一个 `m x n` 的整数矩阵 `grid` 和一个整数 `k`。

对于矩阵 `grid` 中的每个连续的 `k x k` 子矩阵，计算其中任意两个不同值之间的 **最小绝对差**。

返回一个大小为 `(m - k + 1) x (n - k + 1)` 的二维数组 `ans`，其中 `ans[i][j]` 表示以 `grid` 中坐标 `(i, j)` 为左上角的子矩阵的最小绝对差。

**注意**：如果子矩阵中的所有元素都相同，则答案为 0。

---

## 示例说明
### 示例 1：
输入：grid = [[1,8],[3,-2]], k = 2  
输出：[[2]]  
解释：
- 只有一个可能的 k x k 子矩阵：[[1,8],[3,-2]]
- 子矩阵中的不同值为 [1,8,3,-2]
- 子矩阵中的最小绝对差为 |1-3| = 2

### 示例 2：
输入：grid = [[3,-1]], k = 1  
输出：[[0,0]]  
解释：每个 k x k 子矩阵中只有一个不同的元素，因此答案为 0。

### 示例 3：
输入：grid = [[1,-2,3],[2,3,5]], k = 2  
输出：[[1,2]]  
解释：
- 以 (0,0) 为起点的子矩阵：[[1,-2],[2,3]]，不同值为 [1,-2,2,3]，最小绝对差为 |1-2| = 1
- 以 (0,1) 为起点的子矩阵：[[-2,3],[3,5]]，不同值为 [-2,3,5]，最小绝对差为 |3-5| = 2

---

## 提示：
- 1 ≤ m = grid.length ≤ 30
- 1 ≤ n = grid[i].length ≤ 30
- -10^5 ≤ grid[i][j] ≤ 10^5
- 1 ≤ k ≤ min(m, n)

---

## 解题思路

### 核心思想
对于每个 k x k 子矩阵，我们需要找到其中任意两个不同值之间的最小绝对差。由于矩阵大小有限（最多 30x30），我们可以采用**滑动窗口**的方式遍历所有子矩阵，并对每个子矩阵收集所有元素，排序后计算相邻元素的最小差值。

### 关键观察
- 子矩阵总数为 `(m-k+1) * (n-k+1)`，最多约 900 个（30x30）
- 每个子矩阵有 k² 个元素，最多 900 个
- 直接对每个子矩阵的元素进行排序并计算最小差值，时间复杂度可以接受

### 算法步骤
1. 计算结果矩阵的行数 `rows = m - k + 1` 和列数 `cols = n - k + 1`
2. 创建结果矩阵 `ans`，大小为 `rows x cols`
3. 遍历每个可能的左上角位置 `(i, j)`：
   - 创建一个列表 `values`，收集子矩阵中所有元素
   - 对 `values` 进行排序
   - 初始化最小差值为 `Integer.MAX_VALUE`
   - 遍历排序后的列表，计算相邻元素的差值，更新最小值
   - 如果最小差值仍为 `Integer.MAX_VALUE`（说明所有元素相同），设为 0
   - 将结果存入 `ans[i][j]`
4. 返回结果矩阵

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def minDifference(self, grid: List[List[int]], k: int) -> List[List[int]]:
        m, n = len(grid), len(grid[0])
        rows, cols = m - k + 1, n - k + 1
        ans = [[0] * cols for _ in range(rows)]
        
        for i in range(rows):
            for j in range(cols):
                # 收集子矩阵中的所有元素
                values = []
                for x in range(i, i + k):
                    for y in range(j, j + k):
                        values.append(grid[x][y])
                
                # 排序并计算最小绝对差
                values.sort()
                min_diff = float('inf')
                for idx in range(1, len(values)):
                    diff = abs(values[idx] - values[idx - 1])
                    min_diff = min(min_diff, diff)
                
                # 如果所有元素相同，min_diff 仍为 inf，设为 0
                ans[i][j] = min_diff if min_diff != float('inf') else 0
        
        return ans
```

### Java 代码实现
```java
class Solution {
    public int[][] minDifference(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int rows = m - k + 1;
        int cols = n - k + 1;
        int[][] ans = new int[rows][cols];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 收集子矩阵中的所有元素
                List<Integer> values = new ArrayList<>();
                for (int x = i; x < i + k; x++) {
                    for (int y = j; y < j + k; y++) {
                        values.add(grid[x][y]);
                    }
                }
                
                // 排序并计算最小绝对差
                Collections.sort(values);
                int minDiff = Integer.MAX_VALUE;
                for (int idx = 1; idx < values.size(); idx++) {
                    int diff = Math.abs(values.get(idx) - values.get(idx - 1));
                    minDiff = Math.min(minDiff, diff);
                }
                
                // 如果所有元素相同，minDiff 仍为 MAX_VALUE，设为 0
                ans[i][j] = (minDiff == Integer.MAX_VALUE) ? 0 : minDiff;
            }
        }
        
        return ans;
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

int cmp(const void* a, const void* b) {
    return *(int*)a - *(int*)b;
}

int** minDifference(int** grid, int gridSize, int* gridColSize, int k, int* returnSize, int** returnColumnSizes) {
    int m = gridSize;
    int n = gridColSize[0];
    int rows = m - k + 1;
    int cols = n - k + 1;
    
    *returnSize = rows;
    *returnColumnSizes = (int*)malloc(rows * sizeof(int));
    for (int i = 0; i < rows; i++) {
        (*returnColumnSizes)[i] = cols;
    }
    
    int** ans = (int**)malloc(rows * sizeof(int*));
    for (int i = 0; i < rows; i++) {
        ans[i] = (int*)malloc(cols * sizeof(int));
    }
    
    int size = k * k;
    int* values = (int*)malloc(size * sizeof(int));
    
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++) {
            // 收集子矩阵中的所有元素
            int index = 0;
            for (int x = i; x < i + k; x++) {
                for (int y = j; y < j + k; y++) {
                    values[index++] = grid[x][y];
                }
            }
            
            // 排序并计算最小绝对差
            qsort(values, size, sizeof(int), cmp);
            int minDiff = INT_MAX;
            for (int idx = 1; idx < size; idx++) {
                int diff = abs(values[idx] - values[idx - 1]);
                if (diff < minDiff) {
                    minDiff = diff;
                }
            }
            
            // 如果所有元素相同，minDiff 仍为 INT_MAX，设为 0
            ans[i][j] = (minDiff == INT_MAX) ? 0 : minDiff;
        }
    }
    
    free(values);
    return ans;
}
```

---