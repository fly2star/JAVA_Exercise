package LeetCode_Hot100;

/*
62. 不同路径

一个机器人位于一个 `m x n` 网格的左上角（起始点在下图中标记为 “Start”）。

机器人每次只能向下或者向右移动一步。机器人试图达到网格的右下角（在下图中标记为 “Finish”）。

问总共有多少条不同的路径？

## 提示：
    -- 1 ≤ m, n ≤ 100
    -- 题目数据保证答案小于等于 2 * 10^9
*/
public class question097 {
    public static void main(String[] args) {
        
        question097 sl97 = new question097();
        System.out.println(sl97.uniquePaths(3, 7));
    }

    // 方法1: 动态规划
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];

        for (int i = 0; i < n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    // 方法2: 数学方法-排列组合
    //          需要移动 (m-1) 次向下和 (n-1) 次向右，总共 (m+n-2) 步，
    //          问题转化为在 (m+n-2) 步中选择 (m-1) 步向下的组合数
    // 计算组合数 C(m+n-2, m-1)
    public int uniquePaths2(int m, int n) {
        long res = 1;
        int total = m + n - 2;
        int k  = Math.min(m - 1, n - 1);

        for (int i = 1; i <= k; i++) {
            res = res * (total - k -i) / i;
        }

        return (int)res;
    }
}
