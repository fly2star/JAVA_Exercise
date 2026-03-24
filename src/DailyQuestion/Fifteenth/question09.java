package DailyQuestion.Fifteenth;

/*
2906. 构造乘积矩阵

给你一个下标从 0 开始、大小为 `n x m` 的二维整数矩阵 grid，定义一个下标从 0 开始、大小为 `n x m` 的二维矩阵 
p。如果满足以下条件，则称 p 为 grid 的 **乘积矩阵**：

    - 对于每个元素 `p[i][j]`，它的值等于除了 `grid[i][j]` 外所有元素的乘积。乘积对 12345 取余数。

返回 grid 的乘积矩阵。


## 提示：
    -- 1 < n = grid.length < 10^5
    -- 1 < m = grid[i].length < 10^5
    -- 2 < n * m < 10^5
    -- 1 < grid[i][j] < 10^9
*/
public class question09 {

    public static void main(String[] args) {
        int[][] grid = new int[][]{{1, 2}, {3, 4}};

        question09 sl09 = new question09();
        int[][] res = sl09.constructProductMatrix(grid);

        for (int[] arr : res) {
            for (int i : arr) {
                System.out.print(i + "\t");
            }
            System.out.println();
        }
    }

    // 方法1: 类似于前缀后缀和
    public int[][] constructProductMatrix(int[][] grid) {
        final int MOD = 12345;
        int m = grid.length;
        int n = grid[0].length;
        int total = m * n;

        // 展平为一维数组
        int[] arr = new int[total];
        int idx = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                arr[idx++] = grid[i][j] % MOD;
            }
        }

        // 计算前缀和
        // 代表 arr 中下标从 0 到 i-1 的所有元素的乘积
        int[] prefix = new int[total + 1];
        prefix[0] = 1;
        for (int i = 1; i <= total; i++) {
            prefix[i] = (prefix[i - 1] * arr[i -1 ]) % MOD;
        }

        // 计算后缀和
        // 代表 arr 中下标从 i 到 total-1 的所有元素的乘积
        int[] suffix = new int[total + 2];
        suffix[total] = 1;
        for (int i = total - 1; i >= 0; i--) {
            suffix[i] = (suffix[i + 1] * arr[i]) % MOD;
        }

        // 计算每个位置的乘积
        // 当前位置的结果 = 前缀积[i] * 后缀积[i+1]
        int[] resFlat = new int[total];
        for (int i = 0; i < total; i++) {
            resFlat[i] = (int)((long)prefix[i] * suffix[i + 1] % MOD);
        }

        // 重塑为二维矩阵
        int[][] result = new int[m][n];
        idx = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[i][j] = resFlat[idx++];
            }
        }

        return result;
    }
    
}
