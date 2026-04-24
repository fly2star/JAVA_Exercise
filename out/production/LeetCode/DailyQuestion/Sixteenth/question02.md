# 73. 矩阵置零

**难度: 中等**

## 题目描述
给定一个 `m x n` 的矩阵，如果一个元素为 0，则将其所在行和列的所有元素都设为 0。请使用 **原地** 算法。

---

## 示例说明
### 示例 1：
输入：matrix = [[1,1,1],[1,0,1],[1,1,1]]

![73-mat1](../../../readFile/image/73-mat1.jpg)

输出：[[1,0,1],[0,0,0],[1,0,1]]

### 示例 2：
输入：matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]

![73-mat2](../../../readFile/image/73-mat2.jpg)

输出：[[0,0,0,0],[0,4,5,0],[0,3,1,0]]

---

## 提示：
- m = matrix.length
- n = matrix[0].length
- 1 ≤ m, n ≤ 200
- -2^31 ≤ matrix[i][j] ≤ 2^31 - 1

---

## 解题思路

### 核心思想
使用矩阵的第一行和第一列作为标记数组，记录某行或某列是否需要置零。同时额外用两个变量记录第一行和第一列本身是否原本包含 0。

### 关键观察
- 直接遍历矩阵，如果遇到 0，则将该行最左边和该列最上边的元素置为 0（作为标记）
- 最后根据标记将对应行和列置零
- 需要单独处理第一行和第一列，避免标记被提前覆盖

### 算法步骤
1. 获取矩阵行数 m 和列数 n
2. 初始化 `firstRowHasZero = False`，`firstColHasZero = False`
3. 遍历第一列，如果遇到 0，则 `firstColHasZero = True`
4. 遍历第一行，如果遇到 0，则 `firstRowHasZero = True`
5. 遍历其余元素（从 (1,1) 开始），如果 `matrix[i][j] == 0`，则设置 `matrix[i][0] = 0` 和 `matrix[0][j] = 0`
6. 根据第一行的标记（除了第一个元素），将对应列置零
7. 根据第一列的标记（除了第一个元素），将对应行置零
8. 如果 `firstRowHasZero`，将第一行全部置零
9. 如果 `firstColHasZero`，将第一列全部置零

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def setZeroes(self, matrix: List[List[int]]) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        if not matrix or not matrix[0]:
            return
        
        m, n = len(matrix), len(matrix[0])
        first_row_has_zero = any(matrix[0][j] == 0 for j in range(n))
        first_col_has_zero = any(matrix[i][0] == 0 for i in range(m))
        
        # 使用第一行和第一列作为标记
        for i in range(1, m):
            for j in range(1, n):
                if matrix[i][j] == 0:
                    matrix[i][0] = 0
                    matrix[0][j] = 0
        
        # 根据标记置零
        for i in range(1, m):
            if matrix[i][0] == 0:
                for j in range(1, n):
                    matrix[i][j] = 0
        
        for j in range(1, n):
            if matrix[0][j] == 0:
                for i in range(1, m):
                    matrix[i][j] = 0
        
        # 处理第一行和第一列
        if first_row_has_zero:
            for j in range(n):
                matrix[0][j] = 0
        
        if first_col_has_zero:
            for i in range(m):
                matrix[i][0] = 0
```

### Java 代码实现
```java
class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean firstRowZero = false;
        boolean firstColZero = false;
        
        // 检查第一列是否有0
        for (int i = 0; i < m; i++) {
            if (matrix[i][0] == 0) {
                firstColZero = true;
                break;
            }
        }
        // 检查第一行是否有0
        for (int j = 0; j < n; j++) {
            if (matrix[0][j] == 0) {
                firstRowZero = true;
                break;
            }
        }
        
        // 标记
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        
        // 置零
        for (int i = 1; i < m; i++) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < n; j++) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int j = 1; j < n; j++) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < m; i++) {
                    matrix[i][j] = 0;
                }
            }
        }
        
        // 处理第一行和第一列
        if (firstRowZero) {
            for (int j = 0; j < n; j++) {
                matrix[0][j] = 0;
            }
        }
        if (firstColZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }
}
```

### C 代码实现
```c
void setZeroes(int** matrix, int matrixSize, int* matrixColSize) {
    int m = matrixSize;
    int n = matrixColSize[0];
    int firstRowZero = 0, firstColZero = 0;
    
    // 检查第一列
    for (int i = 0; i < m; i++) {
        if (matrix[i][0] == 0) {
            firstColZero = 1;
            break;
        }
    }
    // 检查第一行
    for (int j = 0; j < n; j++) {
        if (matrix[0][j] == 0) {
            firstRowZero = 1;
            break;
        }
    }
    
    // 标记
    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            if (matrix[i][j] == 0) {
                matrix[i][0] = 0;
                matrix[0][j] = 0;
            }
        }
    }
    
    // 置零
    for (int i = 1; i < m; i++) {
        if (matrix[i][0] == 0) {
            for (int j = 1; j < n; j++) {
                matrix[i][j] = 0;
            }
        }
    }
    for (int j = 1; j < n; j++) {
        if (matrix[0][j] == 0) {
            for (int i = 1; i < m; i++) {
                matrix[i][j] = 0;
            }
        }
    }
    
    // 处理第一行和第一列
    if (firstRowZero) {
        for (int j = 0; j < n; j++) {
            matrix[0][j] = 0;
        }
    }
    if (firstColZero) {
        for (int i = 0; i < m; i++) {
            matrix[i][0] = 0;
        }
    }
}
```

---