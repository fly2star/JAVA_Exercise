package DailyQuestion.Fifteenth;

/*
3546. 等和矩阵分割Ⅰ

给你一个由正整数组成的 `m x n` 矩阵 `grid`。你的任务是判断是否可以通过一条水平或一条垂直分割线将矩阵分割成两部分，使得：

    - 分割后形成的每个部分都是 **非空的**。
    - 两个部分中所有元素的和 **相等**。

如果存在这样的分割，返回 `true`；否则，返回 `false`。


## 提示：
    -- 1 < m = grid.length ≤ 10^5
    -- 1 < n = grid[i].length ≤ 10^5
    -- 2 < m * n ≤ 10^5
    -- 1 < grid[i][j] ≤ 10^5
*/
public class question10 {
    
    public static void main(String[] args) {
        int[][] grid = new int[][]{{1,3}, {2,4}};

        question10 sl10 = new question10();
        System.out.println(sl10.canPartitionGrid(grid));
    }

    // 方法1: 前缀和思想
    public boolean canPartitionGrid(int[][] grid) {

        int m = grid.length;
        int n = grid[0].length;

        // 计算每一行的和
        long[] rowSum = new long[m];
        // 计算每一列的和
        long[] colSum = new long[n];
        long total = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowSum[i] += grid[i][j];
                colSum[j] += grid[i][j];
                total += grid[i][j];
            }
        }

        // 如果总和是奇数, 不可能平分
        if (total % 2 != 0) {
            return false;
        }

        long target = total / 2;

        // 检查水平分割
        long prefix = 0;
        for (int i = 0; i < m - 1; i++) {
            prefix += rowSum[i];
            if (prefix == target) {
                return true;
            }
        }

        // 检查垂直分割
        prefix = 0;
        for (int j = 0; j < n - 1; j++) {
            prefix += colSum[j];
            if (prefix == target) {
                return true;
            }
        }

        return false;
    }

}
