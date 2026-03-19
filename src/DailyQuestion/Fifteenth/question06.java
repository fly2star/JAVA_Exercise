package DailyQuestion.Fifteenth;

public class question06 {
    public static void main(String[] args) {
        char[][] grid =new char[][]{{'X', 'Y', '.'}, {'Y', '.', '.'}};

        question06 sl06 = new question06();
        System.out.println(sl06.numberOfSubmatrices(grid));
    }

    // 方法1: 二维前缀和, 两个前缀和数组
    public int numberOfSubmatrices(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        // 构建二维前缀和
        // `prefixX[i][j]` 表示从 `(0,0)` 到 `(i-1,j-1)` 的 `'X'` 数量
        // `prefixY[i][j]` 表示从 `(0,0)` 到 `(i-1,j-1)` 的 `'Y'` 数量
        int[][] prefixX = new int[m + 1][n + 1];
        int[][] prefixY = new int[m + 1][n + 1];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                prefixX[i + 1][j + 1] = prefixX[i + 1][j] + prefixX[i][j + 1] - prefixX[i][j];
                prefixY[i + 1][j + 1] = prefixY[i + 1][j] + prefixY[i][j + 1] - prefixY[i][j];
                
                if (grid[i][j] == 'X') {
                    prefixX[i + 1][j + 1]++;
                } else if (grid[i][j] == 'Y') {
                    prefixY[i + 1][j + 1]++;
                }
            }
        }

        // 统计符合条件的子矩阵
        int count = 0;
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                int xCount = prefixX[i][j];
                int yCount = prefixY[i][j];

                if (xCount == yCount && xCount > 0) {
                    count++;
                }
            }
        }
        return count;
    }
}
