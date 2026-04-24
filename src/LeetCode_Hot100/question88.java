package LeetCode_Hot100;

import java.util.Stack;

/*
84. 柱状图中最大的矩形

给定 `n` 个非负整数，用来表示柱状图中各个柱子的高度。每个柱子彼此相邻，且宽度为 1。

求在该柱状图中，能够勾勒出来的矩形的最大面积。

## 提示：
    -- 1 ≤ heights.length ≤ 10^5
    -- 0 ≤ heights[i] ≤ 10^4
*/
public class question88 {
    public static void main(String[] args) {
        int[] heights = new int[]{2,1,5,6,2,3};
        
        question88 sl88 = new question88();
        System.out.println(sl88.largestRectangleArea(heights));
    }

    // 方法1: 单调栈
    public int largestRectangleArea(int[] heights) {
        int n = heights.length;
        // 创建一个新数组, 末尾添加一个 0 作为哨兵
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


    // 单调栈, 首尾哨兵
    public int largestRectangleArea2(int[] heights) {
        int n = heights.length;
        // 构建带有首尾哨兵的新数组
        int[] newHeights = new int[n + 2];
        // 首尾默认为 0，把原数组拷贝到中间
        System.arraycopy(heights, 0, newHeights, 1, n);

        int maxArea = 0;
        Stack<Integer> stack = new Stack<>();

        // 遍历新数组
        for (int i = 0; i < newHeights.length; i++) {
            // 当遇到比栈顶元素矮的柱子时，触发计算
            while (!stack.isEmpty() && newHeights[i] < newHeights[stack.peek()]) {
                // 此时，stack.peek() 就是我们要计算的那根柱子 (作为高度)
                int midIndex = stack.pop();
                int h = newHeights[midIndex];

                // 弹出后, 新的栈顶就是左边界. 当前的 i 就是右边界.
                int leftIndex = stack.peek();
                int rightIndex = i;

                // 宽度 = 右边界 - 左边界 - 1
                int w = rightIndex - leftIndex - 1;

                // 更新最大面积
                maxArea = Math.max(maxArea, h * w);
                
            }

            // 无论如何, 当前元素的索引进栈
            stack.push(i);
        }

        return maxArea;
    }

}
