# 1727. 重新排列后的最大子矩阵

**难度: 中等**

## 题目描述
给你一个二进制矩阵 `matrix`，它的大小为 `m x n`，你可以将 `matrix` 中的 **列** 按任意顺序重新排列。

请你返回最优方案下将 `matrix` 重新排列后，全是 1 的最大子矩阵面积。

---

## 示例说明
### 示例 1：
输入：matrix = [[0,0,1],[1,1,1],[1,0,1]]  

![pic_1727_1](../../../readFile/image/screenshot-2020-12-30-at-40536-pm.png)

输出：4  
解释：你可以按照上图方式重新排列矩阵的每一列。最大的全 1 子矩阵是上图中加粗的部分，面积为 4。

### 示例 2：
输入：matrix = [[1,0,1,0,1]]  

![pic_1727_2](../../../readFile/image/screenshot-2020-12-30-at-40852-pm.png)

输出：3  
解释：你可以按照上图方式重新排列矩阵的每一列。最大的全 1 子矩阵是上图中加粗的部分，面积为 3。

### 示例 3：
输入：matrix = [[1,1,0],[1,0,1]]  
输出：2  
解释：由于你只能整列整列重新排布，所以没有比面积为 2 更大的全 1 子矩形。

### 示例 4：
输入：matrix = [[0,0],[0,0]]  
输出：0  
解释：由于矩阵中没有 1，没有任何全 1 的子矩阵，所以面积为 0。

---

## 提示：
- m = matrix.length
- n = matrix[i].length
- 1 ≤ m * n ≤ 10^5
- matrix[i][j] 要么是 0，要么是 1。

---

## 解题思路

### 核心思想
这个问题可以转化为：对于每一行，计算以该行为底边的柱状图高度，然后对列进行排序，使得能够形成面积最大的矩形。由于可以重新排列列，我们可以通过贪心策略来最大化面积。

### 关键观察
- 对于每一行，我们可以计算每个位置向上连续 1 的个数（即柱状图的高度）
- 由于可以重新排列列，我们可以将高度数组按降序排序
- 对于排序后的高度数组，第 i 个位置可以形成的矩形高度为 `heights[i]`，宽度为 `i+1`（因为前面 i 列的高度都 ≥ heights[i]）
- 取所有可能矩形的最大值

### 算法步骤
1. 构建高度矩阵：对于每个位置 (i, j)，计算从该位置向上连续 1 的个数
   - 如果 matrix[i][j] == 1，则 height[i][j] = height[i-1][j] + 1
   - 否则 height[i][j] = 0
2. 对于每一行，将高度数组排序（降序）
3. 对于排序后的高度数组，计算最大矩形面积：`max(heights[j] * (j + 1))`
4. 取所有行中的最大值作为答案

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def largestSubmatrix(self, matrix: List[List[int]]) -> int:
        m, n = len(matrix), len(matrix[0])
        ans = 0
        
        # 计算每个位置向上连续1的高度
        for i in range(m):
            for j in range(n):
                if i > 0 and matrix[i][j] == 1:
                    matrix[i][j] += matrix[i-1][j]
            
            # 对当前行的高度进行排序
            row = sorted(matrix[i], reverse=True)
            
            # 计算以当前行为底边的最大矩形面积
            for j in range(n):
                ans = max(ans, row[j] * (j + 1))
        
        return ans
```

### Java 代码实现
```java
class Solution {
    public int largestSubmatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int ans = 0;
        
        for (int i = 0; i < m; i++) {
            // 计算每个位置向上连续1的高度
            for (int j = 0; j < n; j++) {
                if (i > 0 && matrix[i][j] == 1) {
                    matrix[i][j] += matrix[i-1][j];
                }
            }
            
            // 对当前行的高度进行排序
            int[] row = matrix[i].clone();
            Arrays.sort(row);
            
            // 计算以当前行为底边的最大矩形面积
            for (int j = 0; j < n; j++) {
                ans = Math.max(ans, row[j] * (n - j));
            }
        }
        
        return ans;
    }
}
```

### C 代码实现
```c
int cmp(const void* a, const void* b) {
    return *(int*)b - *(int*)a;  // 降序排序
}

int largestSubmatrix(int** matrix, int matrixSize, int* matrixColSize) {
    int m = matrixSize;
    int n = matrixColSize[0];
    int ans = 0;
    
    for (int i = 0; i < m; i++) {
        // 计算每个位置向上连续1的高度
        for (int j = 0; j < n; j++) {
            if (i > 0 && matrix[i][j] == 1) {
                matrix[i][j] += matrix[i-1][j];
            }
        }
        
        // 对当前行的高度进行排序
        int* row = (int*)malloc(n * sizeof(int));
        memcpy(row, matrix[i], n * sizeof(int));
        qsort(row, n, sizeof(int), cmp);
        
        // 计算以当前行为底边的最大矩形面积
        for (int j = 0; j < n; j++) {
            int area = row[j] * (j + 1);
            if (area > ans) {
                ans = area;
            }
        }
        
        free(row);
    }
    
    return ans;
}
```

---