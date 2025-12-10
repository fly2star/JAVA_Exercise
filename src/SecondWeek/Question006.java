package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-13-20:37
 **/
/*
867. 转置矩阵
给你一个二维整数数组 matrix， 返回 matrix 的 转置矩阵 。
矩阵的 转置 是指将矩阵的主对角线翻转，交换矩阵的行索引与列索引。
*/
public class Question006 {
    public static void main(String[] args) {
        int[][] matrix = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] res = transpose(matrix);
        for (int i = 0; i < res.length; i++) {
            for (int j = 0; j < res[0].length; j++) {
                System.out.print(res[i][j] + " ");
            }
            System.out.println();
        }

    }
    public static int[][] transpose(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] res = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][i] = matrix[i][j];
            }
        }
        return res;
    }
}
