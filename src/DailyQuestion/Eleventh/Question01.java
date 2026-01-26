package DailyQuestion.Eleventh;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-19-14:58
 **/
public class Question01 {
    public static void main(String[] args) {

    }

    public static int f1292(int[][] mat, int threshold) {
        int m = mat.length;
        int n = mat[0].length;
        // 1. 构建二维前缀和矩阵 (多出一行一列方便边界处理)
        int[][] prefix = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                prefix[i][j] = mat[i-1][j-1] + prefix[i-1][j]
                        + prefix[i][j-1] - prefix[i-1][j-1];
            }
        }

        int maxSide = 0;
        // 2. 遍历矩阵，尝试扩大边长
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                // 核心技巧：只尝试比当前 maxSide 大 1 的边长
                int k = maxSide + 1;
                // 确保不越界
                if (i >= k && j >= k) {
                    // O(1) 获取正方形区域和
                    int currentSum = prefix[i][j] - prefix[i-k][j]
                            - prefix[i][j-k] + prefix[i-k][j-k];

                    if (currentSum <= threshold) {
                        maxSide = k; // 成功找到更大的，更新 maxSide
                    }
                }
            }
        }
        return maxSide;
    }
}
