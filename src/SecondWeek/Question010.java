package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-14-15:02
 **/
/*
2536. 子矩阵元素加 1
给你一个正整数 n ，表示最初有一个 n x n 、下标从 0 开始的整数矩阵 mat ，矩阵中填满了 0 。
另给你一个二维整数数组 query 。针对每个查询 query[i] = [row1i, col1i, row2i, col2i] ，请你执行下述操作：
    找出 左上角 为 (row1i, col1i) 且 右下角 为 (row2i, col2i) 的子矩阵，将子矩阵中的 每个元素 加 1 。
    也就是给所有满足 row1i <= x <= row2i 和 col1i <= y <= col2i 的 mat[x][y] 加 1 。
返回执行完所有操作后得到的矩阵 mat

*/
public class Question010 {
    public static void main(String[] args) {
        int[][] ans = rangeAddQueries(3, new int[][]{{1,1,2,2},{0,0,1,1}});
        for (int[] an : ans) {
            for (int i : an) {
                System.out.print(i + " ");
            }
            System.out.println();
        }

    }

    public static int[][] rangeAddQueries(int n, int[][] queries) {
        int[][] ans = new int[n][n];
        for (int[] query : queries) {
            int row1 = query[0];
            int col1 = query[1];
            int row2 = query[2];
            int col2 = query[3];
            for (int i = row1; i <= row2; i++) {
                for (int j = col1; j <= col2; j++) {
                    ans[i][j]++;
                }
            }
        }
        return ans;

    }

    public static int[][] rangeAddQueries2(int n, int[][] queries) {
        int[][] diff = new int[n + 1][n + 1];
        for (int[] q : queries) {
            int row1 = q[0], col1 = q[1], row2 = q[2], col2 = q[3];
            diff[row1][col1] += 1;
            diff[row2 + 1][col1] -= 1;
            diff[row1][col2 + 1] -= 1;
            diff[row2 + 1][col2 + 1] += 1;
        }

        int[][] mat = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int x1 = (i == 0) ? 0 : mat[i - 1][j];
                int x2 = (j == 0) ? 0 : mat[i][j - 1];
                int x3 = (i == 0 || j == 0) ? 0 : mat[i - 1][j - 1];
                mat[i][j] = diff[i][j] + x1 + x2 - x3;
            }
        }
        return mat;
    }

    public int[][] rangeAddQueries3(int n, int[][] queries) {
        // 二维差分
        int[][] diff = new int[n + 2][n + 2];
        for (int[] q : queries) {
            int r1 = q[0], c1 = q[1], r2 = q[2], c2 = q[3];
            diff[r1 + 1][c1 + 1]++;
            diff[r1 + 1][c2 + 2]--;
            diff[r2 + 2][c1 + 1]--;
            diff[r2 + 2][c2 + 2]++;
        }

        // 原地计算 diff 的二维前缀和，然后填入答案
        int[][] ans = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                diff[i + 1][j + 1] += diff[i + 1][j] + diff[i][j + 1] - diff[i][j];
                ans[i][j] = diff[i + 1][j + 1];
            }
        }
        return ans;
    }
}
