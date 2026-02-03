package LeetCode_Hot100;

/*
312. 戳气球

有 `n` 个气球，编号为 `0` 到 `n-1`，每个气球上都标有一个数字，这些数字存在数组 `nums` 中。

现在要求你戳破所有的气球。戳破第 `i` 个气球，你可以获得 `nums[i-1] * nums[i] * nums[i+1]` 枚硬币。
这里的 `i-1` 和 `i+1` 代表和 `i` 相邻的两个气球的序号。如果 `i-1` 或 `i+1` 超出了数组的边界，那么就当它是一个数字为 `1` 的气球。

求所能获得硬币的最大数量。
*/

public class question40 {
    public static void main(String[] args) {
        
    }

    /*
    关键之处: 不是考虑先戳哪个气球，而是考虑最后戳哪个气球。
    !!! 关键观察
    1. 如果考虑最后戳破气球 `k`，那么戳破它时，它的左右两边已经没有其他气球了，
        所以此时获得的硬币是：`nums[left] * nums[k] * nums[right]`
    2. 在戳破 `k` 之前，它左右两侧的气球是相互独立的，可以分别计算
    3. 定义 `dp[i][j]` 表示戳破区间 `(i, j)` 内所有气球能获得的最大硬币数
        （注意：这里 `i` 和 `j` 是开区间，表示边界不被戳破）
    4. 状态转移方程：`dp[i][j] = max(dp[i][j], dp[i][k] + dp[k][j] + nums[i] * nums[k] * nums[j])`，其中 `k` 是最后戳破的气球
    */

    // 方法 1 : 动态规划
    public static int maxCoins(int[] nums) {
        int n = nums.length;
        // 在数组两端添加值为 1 的虚拟气球
        int[] newNums = new int[n + 2];
        newNums[0] = 1;
        newNums[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            newNums[i + 1] = nums[i];
        }

        int m = n + 2;
        // dp[i][j] 表示戳破区间(i,j)内所有气球能获得的最大硬币数
        int[][] dp = new int[m][m];

        // 按区间长度从小到大计算
        for (int length = 2; length < m; length++) {               // 区间长度至少为 2
            for (int i = 0; i < m - length; i++) {       // 区间起点
                int j = i + length;                      // 区间终点

                // 枚举最后戳破的气球 k
                for (int k = i + 1; k < j; k++) {
                    // 状态转移
                    dp[i][j] = Math.max(dp[i][j], dp[i][k] + dp[k][j] + newNums[i] * newNums[k] * newNums[j]);
                }
            }
        }
        return dp[0][m - 1];
    }

    // 方法 2 : 记忆化搜索版本
    public static int maxCoins2(int[] nums) {
        int n = nums.length;
        // 在数组两端添加值为1的虚拟气球
        int [] newNums = new int[n + 2];
        newNums[0] = 1;
        newNums[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            newNums[i + 1] = nums[i];
        }

        int[][] memo = new int[n + 2][n + 2];
        return dfs(newNums, memo, 0, n + 1);
    }

    public static int dfs(int[] nums, int[][] memo, int left, int right){
        // 区间内没有气球
        if (left + 1 == right) {
            return 0;
        }

        if(memo[left][right] > 0) {
            return memo[left][right];
        }

        int res = 0;
        // 枚举最后戳破的气球
        for (int k = left + 1; k < right; k++) {
            // 最后戳破气球 k 的收益
            int coins = nums[left] * nums[k] * nums[right];
            // 左右两边的收益
            coins += dfs(nums, memo, left, k) + dfs(nums, memo, k, right);
            res = Math.max(res, coins);
        }

        memo[left][right] = res;
        return res;
    }
    
}
