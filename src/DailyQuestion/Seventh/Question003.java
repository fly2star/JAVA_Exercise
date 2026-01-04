package DailyQuestion.Seventh;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-28-20:49
 **/
/*
1351. 统计有序矩阵中的负数
给你一个 m * n 的矩阵 grid，矩阵中的元素无论是按行还是按列，都以非严格递减顺序排列。 请你统计并返回 grid 中 负数 的数目。
*/
public class Question003 {
    public static void main(String[] args) {
        int[][] grid = {{4, 3, 2, -1}, {3, 2, 1, -1}, {1, 1, -1, -2}, {-1, -1, -2, -3}};
        System.out.println(countNegatives(grid));
    }

    public static int countNegatives(int[][]  grid) {
        int m = grid.length;
        int n = grid[0].length;
        int ans = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int count = 0;
                if (grid[i][j] < 0) {
                    count = n - j;
                    ans += count;
                    break;
                }
            }
        }
        return ans;
    }
}
