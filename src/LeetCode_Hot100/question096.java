package LeetCode_Hot100;

/*
64. 最小路径和

给定一个包含非负整数的 `m x n` 网格 grid，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。

**说明：** 每次只能向下或者向右移动一步。

## 提示：
    -- m = grid.length
    -- n = grid[i].length
    -- 1 ≤ m, n ≤ 200
    -- 0 ≤ grid[i][j] ≤ 200
*/
public class question096 {
    
    public static void main(String[] args) {
        int[][] grid = new int[][]{{1,3,1}, {1,5,1}, {4,2,1}};

        question096 sl96 = new question096();
        System.out.println(sl96.minPathSum(grid));
    }

    public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];

        dp[0][0] = grid[0][0];
        for (int i = 1; i < n; i++) {
            dp[0][i] = dp[0][i - 1] + grid[0][i];
        }
        for (int j = 1; j < m; j++) {
            dp[j][0] = dp[j - 1][0] + grid[j][0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        return dp[m - 1][n - 1];
    }
}
