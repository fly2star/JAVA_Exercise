# 240. 搜索二维矩阵 II

**难度: 中等**

## 题目描述
编写一个高效的算法来搜索 `m × n` 矩阵 `matrix` 中的一个目标值 `target`。该矩阵具有以下特性：

- 每行的元素从左到右升序排列。
- 每列的元素从上到下升序排列。

---

## 示例说明
### 示例 1：

矩阵:

![searchgrid2](../../readFile/image/searchgrid2.jpg)

target = 5
输出: true
解释: 数字 5 存在于矩阵中


---

### 示例 2：

矩阵（同上）

![searchgrid](../../readFile/image/searchgrid.jpg)

target = 20
输出: false
解释: 数字 20 不在矩阵中


---

### 示例 3：
```
矩阵: [[1, 3, 5]]
target = 3
输出: true
```

---

### 示例 4：
```
矩阵: [[1], [3], [5]]
target = 5
输出: true
```

---

## 提示：
- m = matrix.length
- n = matrix[i].length
- 1 ≤ n, m ≤ 300
- -10⁹ ≤ matrix[i][j] ≤ 10⁹
- 每行的所有元素从左到右升序排列
- 每列的所有元素从上到下升序排列
- -10⁹ ≤ target ≤ 10⁹

---

## 解题思路

### 核心思想
利用矩阵的特性（行列有序），从右上角或左下角开始搜索，每次排除一行或一列。

### 关键观察
1. 矩阵的每一行从左到右递增
2. 矩阵的每一列从上到下递增
3. 从右上角开始搜索：
   - 如果当前元素等于 target，找到目标
   - 如果当前元素大于 target，由于列是递增的，当前列下方的元素都更大，可以排除当前列（向左移动）
   - 如果当前元素小于 target，由于行是递增的，当前行左边的元素都更小，可以排除当前行（向下移动）
4. 从左下角开始搜索同理

### 算法步骤（从右上角开始）
1. 初始化行索引 `row = 0`，列索引 `col = n-1`
2. 当 `row < m` 且 `col >= 0` 时循环：
   - 如果 `matrix[row][col] == target`，返回 `true`
   - 如果 `matrix[row][col] > target`，`col--`（向左移动，排除当前列）
   - 如果 `matrix[row][col] < target`，`row++`（向下移动，排除当前行）
3. 如果循环结束未找到，返回 `false`

### 算法步骤（从左下角开始）
1. 初始化行索引 `row = m-1`，列索引 `col = 0`
2. 当 `row >= 0` 且 `col < n` 时循环：
   - 如果 `matrix[row][col] == target`，返回 `true`
   - 如果 `matrix[row][col] > target`，`row--`（向上移动，排除当前行）
   - 如果 `matrix[row][col] < target`，`col++`（向右移动，排除当前列）
3. 如果循环结束未找到，返回 `false`

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List

class Solution:
    def searchMatrix(self, matrix: List[List[int]], target: int) -> bool:
        """方法1：从右上角开始搜索"""
        if not matrix or not matrix[0]:
            return False
        
        m, n = len(matrix), len(matrix[0])
        row, col = 0, n - 1  # 从右上角开始
        
        while row < m and col >= 0:
            if matrix[row][col] == target:
                return True
            elif matrix[row][col] > target:
                # 当前元素太大，向左移动（排除当前列）
                col -= 1
            else:
                # 当前元素太小，向下移动（排除当前行）
                row += 1
        
        return False
    
    def searchMatrix_left_bottom(self, matrix: List[List[int]], target: int) -> bool:
        """方法2：从左下角开始搜索"""
        if not matrix or not matrix[0]:
            return False
        
        m, n = len(matrix), len(matrix[0])
        row, col = m - 1, 0  # 从左下角开始
        
        while row >= 0 and col < n:
            if matrix[row][col] == target:
                return True
            elif matrix[row][col] > target:
                # 当前元素太大，向上移动（排除当前行）
                row -= 1
            else:
                # 当前元素太小，向右移动（排除当前列）
                col += 1
        
        return False
    
    def searchMatrix_binary_search(self, matrix: List[List[int]], target: int) -> bool:
        """方法3：逐行二分查找（适用于矩阵较小的情况）"""
        if not matrix or not matrix[0]:
            return False
        
        for row in matrix:
            # 对每一行进行二分查找
            left, right = 0, len(row) - 1
            while left <= right:
                mid = (left + right) // 2
                if row[mid] == target:
                    return True
                elif row[mid] < target:
                    left = mid + 1
                else:
                    right = mid - 1
        
        return False
```

---

### Java 代码实现

```java
class Solution {
    // 方法1：从右上角开始搜索
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int row = 0, col = n - 1;  // 从右上角开始
        
        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                // 当前元素太大，向左移动（排除当前列）
                col--;
            } else {
                // 当前元素太小，向下移动（排除当前行）
                row++;
            }
        }
        
        return false;
    }
    
    // 方法2：从左下角开始搜索
    public boolean searchMatrixLeftBottom(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int row = m - 1, col = 0;  // 从左下角开始
        
        while (row >= 0 && col < n) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                // 当前元素太大，向上移动（排除当前行）
                row--;
            } else {
                // 当前元素太小，向右移动（排除当前列）
                col++;
            }
        }
        
        return false;
    }
    
    // 方法3：逐行二分查找
    public boolean searchMatrixBinarySearch(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        for (int[] row : matrix) {
            if (binarySearch(row, target)) {
                return true;
            }
        }
        
        return false;
    }
    
    private boolean binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>

// 方法1：从右上角开始搜索
bool searchMatrix(int** matrix, int matrixSize, int* matrixColSize, int target) {
    if (matrixSize == 0 || matrixColSize[0] == 0) {
        return false;
    }
    
    int m = matrixSize;
    int n = matrixColSize[0];
    int row = 0, col = n - 1;  // 从右上角开始
    
    while (row < m && col >= 0) {
        if (matrix[row][col] == target) {
            return true;
        } else if (matrix[row][col] > target) {
            // 当前元素太大，向左移动（排除当前列）
            col--;
        } else {
            // 当前元素太小，向下移动（排除当前行）
            row++;
        }
    }
    
    return false;
}

// 方法2：从左下角开始搜索
bool searchMatrixLeftBottom(int** matrix, int matrixSize, int* matrixColSize, int target) {
    if (matrixSize == 0 || matrixColSize[0] == 0) {
        return false;
    }
    
    int m = matrixSize;
    int n = matrixColSize[0];
    int row = m - 1, col = 0;  // 从左下角开始
    
    while (row >= 0 && col < n) {
        if (matrix[row][col] == target) {
            return true;
        } else if (matrix[row][col] > target) {
            // 当前元素太大，向上移动（排除当前行）
            row--;
        } else {
            // 当前元素太小，向右移动（排除当前列）
            col++;
        }
    }
    
    return false;
}

// 方法3：逐行二分查找
bool binarySearch(int* arr, int size, int target) {
    int left = 0, right = size - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (arr[mid] == target) {
            return true;
        } else if (arr[mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return false;
}

bool searchMatrixBinarySearch(int** matrix, int matrixSize, int* matrixColSize, int target) {
    if (matrixSize == 0 || matrixColSize[0] == 0) {
        return false;
    }
    
    int n = matrixColSize[0];
    for (int i = 0; i < matrixSize; i++) {
        if (binarySearch(matrix[i], n, target)) {
            return true;
        }
    }
    
    return false;
}

// 测试代码
int main() {
    // 创建测试矩阵
    int matrixData[5][5] = {
        {1,   4,  7, 11, 15},
        {2,   5,  8, 12, 19},
        {3,   6,  9, 16, 22},
        {10, 13, 14, 17, 24},
        {18, 21, 23, 26, 30}
    };
    
    // 创建矩阵指针
    int* matrix[5];
    for (int i = 0; i < 5; i++) {
        matrix[i] = matrixData[i];
    }
    
    int matrixSize = 5;
    int matrixColSize[5] = {5, 5, 5, 5, 5};
    
    // 测试示例1：查找存在的元素
    int target1 = 5;
    bool result1 = searchMatrix(matrix, matrixSize, matrixColSize, target1);
    printf("测试1 (从右上角搜索):\n");
    printf("查找目标: %d\n", target1);
    printf("结果: %s (期望: true)\n\n", result1 ? "true" : "false");
    
    // 测试示例2：查找不存在的元素
    int target2 = 20;
    bool result2 = searchMatrixLeftBottom(matrix, matrixSize, matrixColSize, target2);
    printf("测试2 (从左下角搜索):\n");
    printf("查找目标: %d\n", target2);
    printf("结果: %s (期望: false)\n\n", result2 ? "true" : "false");
    
    // 测试示例3：查找最小元素
    int target3 = 1;
    bool result3 = searchMatrixBinarySearch(matrix, matrixSize, matrixColSize, target3);
    printf("测试3 (二分查找):\n");
    printf("查找目标: %d\n", target3);
    printf("结果: %s (期望: true)\n\n", result3 ? "true" : "false");
    
    // 测试示例4：查找最大元素
    int target4 = 30;
    bool result4 = searchMatrix(matrix, matrixSize, matrixColSize, target4);
    printf("测试4 (从右上角搜索):\n");
    printf("查找目标: %d\n", target4);
    printf("结果: %s (期望: true)\n\n", result4 ? "true" : "false");
    
    // 测试空矩阵
    int** emptyMatrix = NULL;
    int emptyMatrixSize = 0;
    int* emptyMatrixColSize = NULL;
    bool result5 = searchMatrix(emptyMatrix, emptyMatrixSize, emptyMatrixColSize, 1);
    printf("测试5 (空矩阵):\n");
    printf("结果: %s (期望: false)\n", result5 ? "true" : "false");
    
    return 0;
}
```

---

### 复杂度分析
| 方法 | 时间复杂度 | 空间复杂度 | 特点 |
|------|-----------|-----------|------|
| 从右上角/左下角搜索 | O(m + n) | O(1) | 最优解，利用矩阵特性 |
| 逐行二分查找 | O(m log n) | O(1) | 简单直观，但效率较低 |

### 算法详解

#### 1. 从右上角搜索（推荐）
- **起点选择**：右上角 (0, n-1)
- **移动规则**：
  - 如果当前值 > target：向左移动（列减1）
  - 如果当前值 < target：向下移动（行加1）
- **原理**：利用矩阵行列有序的特性，每次可以排除一行或一列
- **时间复杂度**：最多移动 m + n 次，所以 O(m + n)

#### 2. 从左下角搜索
- **起点选择**：左下角 (m-1, 0)
- **移动规则**：
  - 如果当前值 > target：向上移动（行减1）
  - 如果当前值 < target：向右移动（列加1）
- **原理**：与从右上角搜索对称

#### 3. 逐行二分查找
- **思路**：对每一行进行二分查找
- **优点**：实现简单，易于理解
- **缺点**：没有充分利用列的有序性
- **适用场景**：矩阵较小或行数较少时
