package DailyQuestion.Eighth;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-30-09:57
 **/
/*
840. 矩阵中的幻方

3 x 3 的幻方是一个填充有 从 1 到 9  的不同数字的 3 x 3 矩阵，其中每行，每列以及两条对角线上的各数之和都相等。
给定一个由整数组成的row x col 的 grid，其中有多少个 3 × 3 的 “幻方” 子矩阵？

注意：虽然幻方只能包含 1 到 9 的数字，但 grid 可以包含最多15的数字。
*/
public class Question02 {
    public static void main(String[] args) {
        int [][] grid =  {{4,3,8,4},{9,5,1,9},{2,7,6,2}};
        int [][] grid1  =  {{5,5,5},{5,5,5},{5,5,5}};
        System.out.println(numMagicSquaresInside(grid));
        System.out.println(numMagicSquaresInside(grid1));
    }

    // 方法1 : 暴力解法
    public static int numMagicSquaresInside(int[][] grid) {
         int row = grid.length;
         int col = grid[0].length;
         if ( row < 3 || col < 3)  return 0;
         int count = 0;
         for (int i = 0; i < row - 2; i++) {
             for ( int j = 0; j < col - 2; j++) {
                 if (isMagic(grid, i, j)) count++;
             }
         }
          return count;
    }

    private static boolean isMagic(int[][] grid, int r, int c) {
        // 1. 快速剪枝：3x3 幻方中心必须是 5
        if (grid[r + 1][c + 1] != 5) return false;

        // 2. 检查数字是否在 1-9 之间且不重复
        // 使用 boolean 数组记录 1-9 的出现情况
        boolean[] seen = new boolean[10];
        for (int i = r; i < r + 3; i++) {
            for (int j = c; j < c + 3; j++) {
                int val = grid[i][j];
                // 排除 0, 10-15 以及重复数字
                if (val < 1 || val > 9 || seen[val]) {
                    return false;
                }
                seen[val] = true;
            }
        }

        // 3. 校验行、列、对角线和是否全等于 15
        // 因为数字是 1-9 且不重复，只要满足以下几条线，其他线会自动成立
        return (grid[r][c] + grid[r][c+1] + grid[r][c+2] == 15 && // 行 1
                grid[r+2][c] + grid[r+2][c+1] + grid[r+2][c+2] == 15 && // 行 3
                grid[r][c] + grid[r+1][c] + grid[r+2][c] == 15 && // 列 1
                grid[r][c+2] + grid[r+1][c+2] + grid[r+2][c+2] == 15 && // 列 3
                grid[r][c] + grid[r+1][c+1] + grid[r+2][c+2] == 15 && // 对角线 1
                grid[r][c+2] + grid[r+1][c+1] + grid[r+2][c] == 15);   // 对角线 2
    }
}
