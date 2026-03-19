# 48. 旋转图像

**难度: 中等**

## 题目描述
给定一个 $ n \times n $ 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。

---

## 示例说明
### 示例 1：
![mat1](../../readFile/image/mat1.jpg)

输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]  
输出：[[7,4,1],[8,5,2],[9,6,3]]

### 示例 2：
![mat2](../../readFile/image/mat2.jpg)

输入：matrix = [[5,1,9,11],[2,4,8,10],[13,3,6,7],[15,14,12,16]]  
输出：[[15,13,2,5],[14,3,4,1],[12,6,8,9],[16,7,10,11]]

---

## 提示：
- n = matrix.length = matrix[i].length
- 1 ≤ n ≤ 20
- -1000 ≤ matrix[i][j] ≤ 1000

---

## 解题思路

### 核心思想
顺时针旋转90度可以通过两个步骤完成：**先转置矩阵，然后反转每一行**。这种方法不需要额外的矩阵空间，完全在原矩阵上操作。

### 关键观察
- 顺时针旋转90度 = 转置 + 每行反转
- 逆时针旋转90度 = 转置 + 每列反转
- 旋转操作实际上是四个对应位置的元素交换

### 算法步骤

#### 方法一：转置 + 反转
1. 将矩阵转置（`matrix[i][j]` 与 `matrix[j][i]` 交换）
2. 对转置后的矩阵的每一行进行反转

#### 方法二：直接旋转（四元素交换）
1. 将矩阵分为四个矩形区域
2. 对于每一层（从外到内），进行四个位置的循环交换
3. 每个元素 `(i,j)` 旋转后的新位置是 `(j, n-1-i)`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def rotate(self, matrix: List[List[int]]) -> None:
        """
        Do not return anything, modify matrix in-place instead.
        """
        n = len(matrix)
        
        # 方法一：转置 + 反转
        # 转置
        for i in range(n):
            for j in range(i, n):
                matrix[i][j], matrix[j][i] = matrix[j][i], matrix[i][j]
        
        # 反转每一行
        for i in range(n):
            matrix[i].reverse()

# 方法二：直接旋转（四元素交换）
class Solution:
    def rotate(self, matrix: List[List[int]]) -> None:
        n = len(matrix)
        
        for i in range(n // 2):
            for j in range((n + 1) // 2):
                # 保存左上角元素
                temp = matrix[i][j]
                # 左上角 = 左下角
                matrix[i][j] = matrix[n - 1 - j][i]
                # 左下角 = 右下角
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j]
                # 右下角 = 右上角
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i]
                # 右上角 = 左上角（保存的temp）
                matrix[j][n - 1 - i] = temp
```

### Java 代码实现
```java
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        
        // 方法一：转置 + 反转
        // 转置矩阵
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }
        
        // 反转每一行
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 - j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }
}

// 方法二：直接旋转（四元素交换）
class Solution {
    public void rotate(int[][] matrix) {
        int n = matrix.length;
        
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - i; j++) {
                // 保存左上角元素
                int temp = matrix[i][j];
                // 左上角 = 左下角
                matrix[i][j] = matrix[n - 1 - j][i];
                // 左下角 = 右下角
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                // 右下角 = 右上角
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                // 右上角 = 左上角（保存的temp）
                matrix[j][n - 1 - i] = temp;
            }
        }
    }
}
```

### C 代码实现
```c
void rotate(int** matrix, int matrixSize, int* matrixColSize) {
    int n = matrixSize;
    
    // 方法一：转置 + 反转
    // 转置矩阵
    for (int i = 0; i < n; i++) {
        for (int j = i; j < n; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[j][i];
            matrix[j][i] = temp;
        }
    }
    
    // 反转每一行
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n / 2; j++) {
            int temp = matrix[i][j];
            matrix[i][j] = matrix[i][n - 1 - j];
            matrix[i][n - 1 - j] = temp;
        }
    }
}

// 方法二：直接旋转（四元素交换）
void rotate(int** matrix, int matrixSize, int* matrixColSize) {
    int n = matrixSize;
    
    for (int i = 0; i < n / 2; i++) {
        for (int j = i; j < n - 1 - i; j++) {
            // 保存左上角元素
            int temp = matrix[i][j];
            // 左上角 = 左下角
            matrix[i][j] = matrix[n - 1 - j][i];
            // 左下角 = 右下角
            matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
            // 右下角 = 右上角
            matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
            // 右上角 = 左上角（保存的temp）
            matrix[j][n - 1 - i] = temp;
        }
    }
}
```

---