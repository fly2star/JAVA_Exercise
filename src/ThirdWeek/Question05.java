package ThirdWeek;

import java.util.Arrays;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-17-20:49
 **/
/*
931. 下降路径最小和
给你一个 n x n 的 方形 整数数组 matrix ，请你找出并返回通过 matrix 的下降路径 的 最小和 。

下降路径 可以从第一行中的任何元素开始，并从每一行中选择一个元素。
在下一行选择的元素和当前行所选元素最多相隔一列（即位于正下方或者沿对角线向左或者向右的第一个元素）。
具体来说，位置 (row, col) 的下一个元素应当是 (row + 1, col - 1)、(row + 1, col) 或者 (row + 1, col + 1) 。

*/
public class Question05 {
    public static void main(String[] args) {
        int[][] matrix = {{2,1,3},{6,5,4},{7,8,9}};
        int[][] matrix2 = {{-84,-36,2}, {87,-79,10}, {42,10,63}};
        System.out.println(minFallingPathSum(matrix));
        System.out.println(minFallingPathSum(matrix2));
        System.out.println(minFallingPathSum2(matrix));

    }
    public static int minFallingPathSum(int[][] matrix) {
        int n = matrix.length;

        int[][] dp = new int[n][n];
        System.arraycopy(matrix[0], 0, dp[0], 0, n);
        for (int i = 1; i < n; i++) {
            dp[i][0] = Math.min(dp[i - 1][0], dp[i-1][1]) + matrix[i][0];
            for (int j = 1; j < n-1; j++) {
                dp[i][j] = Math.min(Math.min(dp[i - 1][j-1], dp[i - 1][j + 1]) ,dp[i-1][j]) + matrix[i][j];
            }
            dp[i][n-1] = Math.min(dp[i - 1][n-2], dp[i-1][n-1]) + matrix[i][n-1];
        }
//        for (int i = 0; i < n; i++){
//            for (int j = 0; j < n; j++) {
//                System.out.print(dp[i][j] + " ");
//            }
//            System.out.println();
//        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            if (dp[n-1][i] < res) {
                res = dp[n-1][i];
            }
        }
        return res;
    }

    public static int minFallingPathSum2(int[][] matrix) {
        int n = matrix.length;
        int[][] dp = new int[n][n];
        System.arraycopy(matrix[0], 0, dp[0], 0, n);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int mn = dp[i - 1][j];
                if (j > 0) {
                    mn = Math.min(mn, dp[i - 1][j - 1]);
                }
                if (j < n - 1) {
                    mn = Math.min(mn, dp[i - 1][j + 1]);
                }
                dp[i][j] = mn + matrix[i][j];
            }
        }
        return Arrays.stream(dp[n - 1]).min().getAsInt();

    }

    public static int minFallingPathSum3(int[][] matrix) {
        int n = matrix.length;
        // 在两边补0
        var f = new int[n][n + 2];
        System.arraycopy(matrix[0], 0, f[0], 1, n);
        for (int r = 1; r < n; r++) {
            f[r - 1][0] = f[r - 1][n + 1] = Integer.MAX_VALUE;
            for (int c = 0; c < n; c++)
                f[r][c + 1] = Math.min(Math.min(f[r - 1][c], f[r - 1][c + 1]), f[r - 1][c + 2]) + matrix[r][c];
        }
        int ans = Integer.MAX_VALUE;
        for (int c = 1; c <= n; c++) {
            ans = Math.min(ans, f[n - 1][c]);
        }
        return ans;

    }

    // 空间优化，每计算f[r][-],只需要用到上一排的f[r-1][-], 不会用到 < r-1 的状态
    // 因此可以像 0-1 背包 那样，反复利用同一个长为 n+2 的一维数组。
    public static int minFallingPathSum4(int[][] matrix) {
        int n = matrix.length;
        var f = new int[n + 2];
        f[0] = f[n + 1] = Integer.MAX_VALUE;
        System.arraycopy(matrix[0], 0, f, 1, n);
        for (int r = 1; r < n; r++) {
            int pre = f[0];
            for (int c = 0; c < n; c++) {
                int tmp = pre;
                pre = f[c + 1];
                f[c + 1] = Math.min(tmp, Math.min(f[c + 1], f[c + 2])) + matrix[r][c];
            }
        }
        int ans = Integer.MAX_VALUE;
        for (int c = 1; c <= n; c++) {
            ans = Math.min(ans, f[c]);
        }
        return ans;
    }
}



// 会超时的递归代码
class Solution {
    private int[][] matrix;

    public int minFallingPathSum(int[][] matrix) {
        this.matrix = matrix;
        int n = matrix.length;
        int ans = Integer.MAX_VALUE;
        for (int c = 0; c < n; c++) {
            ans = Math.min(ans, dfs(n - 1, c));
        }
        return ans;
    }

    private int dfs(int r, int c) {
        if (c < 0 || c >= matrix.length) return Integer.MAX_VALUE; // 出界
        if (r == 0) return matrix[0][c]; // 到达第一行
        return Math.min(Math.min(dfs(r - 1, c - 1), dfs(r - 1, c)), dfs(r - 1, c + 1)) + matrix[r][c];
    }
}

// 递归 + 记录返回值 = 记忆化搜索
class Solution2 {
    private int[][] matrix, memo;

    public int minFallingPathSum(int[][] matrix) {
        this.matrix = matrix;
        int n = matrix.length;
        memo = new int[n][n];
        for (int i = 0; i < n; i++)
            Arrays.fill(memo[i], Integer.MIN_VALUE);

        int ans = Integer.MAX_VALUE;
        for (int c = 0; c < n; c++) {
            ans = Math.min(ans, dfs(n - 1, c));
        }
        return ans;
    }

    private int dfs(int r, int c) {
        if (c < 0 || c >= matrix.length) return Integer.MAX_VALUE; // 出界
        if (r == 0) return matrix[0][c]; // 到达第一行
        if (memo[r][c] != Integer.MIN_VALUE) return memo[r][c]; // 之前算过了
        return memo[r][c] = Math.min(Math.min(
                dfs(r - 1, c - 1), dfs(r - 1, c)), dfs(r - 1, c + 1)) + matrix[r][c];
    }
}


