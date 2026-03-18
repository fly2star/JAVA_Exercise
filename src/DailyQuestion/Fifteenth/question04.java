package DailyQuestion.Fifteenth;

import java.util.Arrays;

/*
1727. 重新排列后的最大子矩阵

给你一个二进制矩阵 `matrix`，它的大小为 `m x n`，你可以将 `matrix` 中的 **列** 按任意顺序重新排列。

请你返回最优方案下将 `matrix` 重新排列后，全是 1 的最大子矩阵面积。

## 提示：
-- m = matrix.length
-- n = matrix[i].length
-- 1 ≤ m * n ≤ 10^5
-- matrix[i][j] 要么是 0，要么是 1。
*/
public class question04 {
    public static void main(String[] args) {
        int[][] matrix = new int[][]{{0,0,1}, {1,1,1}, {1,0,1}};

        question04 sl04 = new question04();
        System.out.println(sl04.largestSubmatrix(matrix));
    }

    public int largestSubmatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int ans = 0;

        for (int i = 0; i < m; i++) {
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
