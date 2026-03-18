package DailyQuestion.Fifteenth;

/*
3070. 元素和小于等于 k 的子矩阵的数目

给你一个下标从 0 开始的整数矩阵 grid 和一个整数 k。

返回包含 grid 左上角元素、元素和小于或等于 k 的子矩阵的数目。

## 提示：
    -- m = grid.length
    -- n = grid[i].length
    -- 1 ≤ n, m ≤ 1000
    -- 0 ≤ grid[i][j] ≤ 1000
    -- 1 ≤ k ≤ 10^9
*/
public class question05 {
    public static void main(String[] args) {
        int[][] grid = new int[][]{{7, 2, 9}, {1, 5, 0}, {2, 6, 6}};
        int[][] grid1 = new int[][]{{7, 6, 3}, {6, 6, 1}};

        question05 sl05 = new question05();
        System.out.println(sl05.countSubmatrices(grid, 20));
        System.out.println(sl05.countSubmatrices(grid1, 18));
    }

    public int countSubmatrices(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;

        // 构建二维前缀和
        // `prefix[i][j]` 表示从 `(0,0)` 到 `(i-1,j-1)` 的子矩阵和
        long[][] prefix = new long[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefix[i + 1][j + 1] = prefix[i + 1][j] + prefix[i][j + 1] - prefix[i][j] + grid[i][j];
            }
        }

        // 统计
        int count = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (prefix[i][j] <= k) {
                    count++;
                } else {
                    // 由于 grid 元素非负，前缀和单调递增，所以可以提前跳出内层循环
                    break;
                }
            }
        }

        return count;
    }
    
    
}
