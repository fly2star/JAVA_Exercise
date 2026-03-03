# 84. 柱状图中最大的矩形

**难度: 困难**

## 题目描述
给定 `n` 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1。

求在该柱状图中，能够勾勒出来的矩形的最大面积。

---

## 示例说明
### 示例 1：

![histogram](../../readFile/image/histogram.jpg)

输入：heights = [2,1,5,6,2,3]  
输出：10  
解释：最大的矩形面积为 10，由高度为 5 和 6 的柱子构成（宽度为 2，高度为 5）。

### 示例 2：

![histogram-1](../../readFile/image/histogram-1.jpg)

输入：heights = [2,4]  
输出：4

---

## 提示：
- 1 ≤ heights.length ≤ 10^5
- 0 ≤ heights[i] ≤ 10^4

---

## 解题思路

### 核心思想
使用**单调栈**来找到每个柱子左右两边第一个比它矮的柱子，从而确定以该柱子高度为矩形高度的最大宽度。这种方法可以在 O(n) 时间内解决问题。

### 关键观察
- 对于每个柱子，以其高度为矩形高度时，矩形的宽度可以扩展到左右两边第一个比它矮的柱子之间
- 使用单调递增栈（栈底到栈顶递增），可以在遍历过程中快速找到左右边界
- 在数组末尾添加一个高度为 0 的哨兵，可以确保最后所有柱子都能被处理

### 算法步骤
1. 在 heights 数组末尾添加一个 0，作为哨兵
2. 初始化栈 stack 和最大面积 maxArea = 0
3. 遍历每个柱子的索引 i 和高度 h：
   - 当栈不为空且当前高度小于栈顶高度时：
     - 弹出栈顶索引，得到高度 height = heights[top]
     - 计算宽度：如果栈为空，宽度为 i；否则宽度为 i - stack[-1] - 1
     - 计算面积并更新 maxArea
   - 将当前索引入栈
4. 返回 maxArea

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def largestRectangleArea(self, heights: List[int]) -> int:
        # 添加哨兵，确保最后能清空栈
        heights.append(0)
        stack = []
        max_area = 0
        
        for i, h in enumerate(heights):
            # 当当前高度小于栈顶高度时，说明找到了右边界
            while stack and heights[stack[-1]] > h:
                height = heights[stack.pop()]
                # 计算宽度：如果栈为空，宽度为 i；否则宽度为 i - stack[-1] - 1
                width = i if not stack else i - stack[-1] - 1
                max_area = max(max_area, height * width)
            stack.append(i)
        
        # 移除哨兵
        heights.pop()
        return max_area
```

### Java 代码实现
```java
class Solution {
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        // 创建一个新数组，末尾加一个 0 作为哨兵
        int[] newHeights = new int[n + 1];
        System.arraycopy(heights, 0, newHeights, 0, n);
        newHeights[n] = 0;
        
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        
        for (int i = 0; i <= n; i++) {
            // 当当前高度小于栈顶高度时，说明找到了右边界
            while (!stack.isEmpty() && newHeights[stack.peek()] > newHeights[i]) {
                int height = newHeights[stack.pop()];
                // 计算宽度：如果栈为空，宽度为 i；否则宽度为 i - stack.peek() - 1
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
int largestRectangleArea(int* heights, int heightsSize) {
    // 创建新数组，末尾加一个 0 作为哨兵
    int* newHeights = (int*)malloc((heightsSize + 1) * sizeof(int));
    for (int i = 0; i < heightsSize; i++) {
        newHeights[i] = heights[i];
    }
    newHeights[heightsSize] = 0;
    
    int* stack = (int*)malloc((heightsSize + 1) * sizeof(int));
    int top = -1;
    int maxArea = 0;
    
    for (int i = 0; i <= heightsSize; i++) {
        // 当当前高度小于栈顶高度时，说明找到了右边界
        while (top >= 0 && newHeights[stack[top]] > newHeights[i]) {
            int height = newHeights[stack[top--]];
            // 计算宽度：如果栈为空，宽度为 i；否则宽度为 i - stack[top] - 1
            int width = (top == -1) ? i : i - stack[top] - 1;
            int area = height * width;
            if (area > maxArea) {
                maxArea = area;
            }
        }
        stack[++top] = i;
    }
    
    free(stack);
    free(newHeights);
    return maxArea;
}
```

---