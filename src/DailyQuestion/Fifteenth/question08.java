package DailyQuestion.Fifteenth;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-03-23-20:47
 **/

/*
1594. 矩阵的最大非负积

给你一个大小为 `m x n` 的矩阵 `grid`。最初，你位于左上角 `(0, 0)`，每一步，你可以在矩阵中向右或向下移动。

在从左上角 `(0, 0)` 开始到右下角 `(m-1, n-1)` 结束的所有路径中，找出具有 **最大非负积** 的路径。路径的权是路径访问的单元格中所有整数的乘积。

返回 **最大非负积** 对 `10^9 + 7` 取余的结果。如果最大积为负数，则返回 `-1`。

注意，取余是在得到最大积之后执行的。


## 提示：
    -- m = grid.length
    -- n = grid[0].length
    -- 1 ≤ m, n ≤ 15
    -- -4 ≤ grid[i][j] ≤ 4
*/
public class question08 {

    public static void main(String[] args) {
        int[][] grid = new int[][]{{1, -2, 1}, {1, -2, 1}, {3, -4, 1}};

        question08 sl08 = new question08();
        System.out.println(sl08.maxProductPath(grid));
    }

    // 方法1: 动态规划, 同时维护最大值和最小值
    public int maxProductPath(int[][] grid) {
        final int MOD = 1_000_000_007;
        int m = grid.length;  
        int n = grid[0].length;

        long[][] maxVal = new long[m][n];
        long[][] minVal = new long[m][n];

        // 起点
        maxVal[0][0] = grid[0][0];
        minVal[0][0] = grid[0][0];

        // 初始化第一行
        for (int j = 1; j < n; j++) {
            maxVal[0][j] = maxVal[0][j-1] * grid[0][j];
            minVal[0][j] = minVal[0][j-1] * grid[0][j];
        }

        // 初始化第一列
        for (int i = 1; i < m; i++) {
            maxVal[i][0] = maxVal[i-1][0] * grid[i][0];
            minVal[i][0] = minVal[i-1][0] * grid[i][0];
        }

        // 填充剩余位置
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                long val = grid[i][j];
                long[] candidates = {
                    maxVal[i-1][j] * val,
                    minVal[i-1][j] * val,
                    maxVal[i][j-1] * val,
                    minVal[i][j-1] * val
                };

                long max = Long.MIN_VALUE;
                long min = Long.MAX_VALUE;

                for (long cand : candidates) {
                    max = Math.max(max, cand);
                    min = Math.min(min, cand);
                }
                maxVal[i][j] = max;
                minVal[i][j] = min;

            }
        }

        long res = maxVal[m-1][n-1];
        if (res < 0) {
            return -1;
        }
        return (int)(res % MOD);
    }

}
