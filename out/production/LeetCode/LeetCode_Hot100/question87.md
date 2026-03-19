# 85. 最大矩形

**难度: 困难**

## 题目描述
给定一个仅包含 0 和 1、大小为 rows x cols 的二维二进制矩阵，找出只包含 1 的最大矩形，并返回其面积。

---

## 示例说明
### 示例 1：

![1722912576-boIxpm-image](../../readFile/image/1722912576-boIxpm-image.png)

输入：matrix = [["1","0","1","0","0"],["1","0","1","1","1"],["1","1","1","1","1"],["1","0","0","1","0"]]  
输出：6  
解释：最大矩形面积为 6，如上图所示。

### 示例 2：
输入：matrix = [["0"]]  
输出：0

### 示例 3：
输入：matrix = [["1"]]  
输出：1

---

## 提示：
- rows == matrix.length
- cols == matrix[0].length
- 1 <= rows, cols <= 200
- matrix[i][j] 为 '0' 或 '1'

---

## 解题思路

### 核心思想
这个问题可以转化为**柱状图中最大矩形**问题的扩展。我们可以将矩阵的每一行看作一个底，然后计算以该行为底时，向上连续 1 的高度，得到一个高度数组，然后利用 84 题的方法求解该行的最大矩形面积。遍历所有行，取最大值。

### 关键观察
- 对于每一行，我们可以计算从该行向上连续 1 的高度
- 高度数组 heights[j] 表示当前行第 j 列向上连续 1 的个数
- 对于每一行得到的高度数组，可以应用 84 题的单调栈解法求最大矩形面积
- 遍历所有行，更新全局最大面积

### 算法步骤
1. 初始化 heights 数组，长度为 cols，全部为 0
2. 初始化最大面积 maxArea = 0
3. 遍历每一行 i：
   - 更新 heights[j]：
     - 如果 matrix[i][j] == '1'，则 heights[j] += 1
     - 否则 heights[j] = 0
   - 调用函数 largestRectangleArea(heights) 计算当前行对应的最大矩形面积
   - 更新 maxArea = max(maxArea, currentArea)
4. 返回 maxArea

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def maximalRectangle(self, matrix: List[List[str]]) -> int:
        if not matrix or not matrix[0]:
            return 0
        
        rows, cols = len(matrix), len(matrix[0])
        heights = [0] * cols
        max_area = 0
        
        for i in range(rows):
            # 更新高度数组
            for j in range(cols):
                if matrix[i][j] == '1':
                    heights[j] += 1
                else:
                    heights[j] = 0
            
            # 计算当前行对应的最大矩形面积
            max_area = max(max_area, self.largestRectangleArea(heights))
        
        return max_area
    
    def largestRectangleArea(self, heights: List[int]) -> int:
        stack = []
        max_area = 0
        heights.append(0)  # 添加哨兵，确保最后能清空栈
        
        for i, h in enumerate(heights):
            while stack and heights[stack[-1]] > h:
                height = heights[stack.pop()]
                width = i if not stack else i - stack[-1] - 1
                max_area = max(max_area, height * width)
            stack.append(i)
        
        heights.pop()  # 移除哨兵
        return max_area
```

### Java 代码实现
```java
class Solution {
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;
        
        for (int i = 0; i < rows; i++) {
            // 更新高度数组
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    heights[j] += 1;
                } else {
                    heights[j] = 0;
                }
            }
            
            // 计算当前行对应的最大矩形面积
            maxArea = Math.max(maxArea, largestRectangleArea(heights));
        }
        
        return maxArea;
    }
    
    private int largestRectangleArea(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.length;
        
        for (int i = 0; i <= n; i++) {
            int h = (i == n) ? 0 : heights[i];
            
            while (!stack.isEmpty() && heights[stack.peek()] > h) {
                int height = heights[stack.pop()];
                int width = stack.isEmpty() ? i : i - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }
            
            stack.push(i);
        }
        
        return maxArea;
    }
}
```

### C 代码实现
```c
int largestRectangleArea(int* heights, int n) {
    int* stack = (int*)malloc((n + 1) * sizeof(int));
    int top = -1;
    int maxArea = 0;
    
    for (int i = 0; i <= n; i++) {
        int h = (i == n) ? 0 : heights[i];
        
        while (top >= 0 && heights[stack[top]] > h) {
            int height = heights[stack[top--]];
            int width = (top == -1) ? i : i - stack[top] - 1;
            int area = height * width;
            if (area > maxArea) {
                maxArea = area;
            }
        }
        
        stack[++top] = i;
    }
    
    free(stack);
    return maxArea;
}

int maximalRectangle(char** matrix, int matrixSize, int* matrixColSize) {
    if (matrixSize == 0 || matrixColSize[0] == 0) {
        return 0;
    }
    
    int rows = matrixSize;
    int cols = matrixColSize[0];
    int* heights = (int*)calloc(cols, sizeof(int));
    int maxArea = 0;
    
    for (int i = 0; i < rows; i++) {
        // 更新高度数组
        for (int j = 0; j < cols; j++) {
            if (matrix[i][j] == '1') {
                heights[j] += 1;
            } else {
                heights[j] = 0;
            }
        }
        
        // 计算当前行对应的最大矩形面积
        int area = largestRectangleArea(heights, cols);
        if (area > maxArea) {
            maxArea = area;
        }
    }
    
    free(heights);
    return maxArea;
}
```

---