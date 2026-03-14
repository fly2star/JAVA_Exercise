package LeetCode_Hot100;

import java.util.Stack;

/*
85. 最大矩形

给定一个仅包含 0 和 1、大小为 rows x cols 的二维二进制矩阵，
找出只包含 1 的最大矩形，并返回其面积。

## 提示：
    -- rows == matrix.length
    -- cols == matrix[0].length
    -- 1 <= rows, cols <= 200
    -- matrix[i][j] 为 '0' 或 '1'
*/
public class question87 {
    public static void main(String[] args) {
        char[][] matrix = {{'1','0','1','0','0'},
                            {'1','0','1','1','1'},
                            {'1','1','1','1','1'},
                            {'1','0','0','1','0'}};
        
        question87 sl87 = new question87();
        System.out.println(sl87.maximalRectangle(matrix));
    }

    // 方法1: 单调栈
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[] heights = new int[cols];
        int maxArea = 0;

        for (int i = 0; i < rows; i++) {
            // 更新高度矩阵
            for (int j = 0; j < cols; j++) {
                if (matrix[i][j] == '1') {
                    heights[j] += 1;
                } else {
                    heights[j] = 0;
                }
            }

            // 计算当前行对应的最大矩形面积
            maxArea = Math.max(maxArea, largestRectsnglrArea(heights));
        }

        return maxArea;
    }

    private int largestRectsnglrArea(int[] heights) {
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
