package DailyQuestion.Fifteenth;

/*
3129. 找出所有稳定的二进制数组 I

给你 3 个正整数 `zero`、`one` 和 `limit`。

一个 **二进制数组** `arr` 如果满足以下条件，那么我们称它是 **稳定的**：
- 0 在 `arr` 中出现次数 **恰好为** `zero`。
- 1 在 `arr` 中出现次数 **恰好为** `one`。
- `arr` 中每个长度超过 `limit` 的子数组 **都同时包含** 0 和 1。

请你返回 **稳定** 二进制数组的总数目。

由于答案可能很大，将它对 \(10^9 + 7\) 取余后返回。

## 提示：
    -- 1 ≤ zero, one, limit ≤ 200
*/
public class question01 {
    public static void main(String[] args) {
        System.out.println(numberOfStableArrays(3, 3, 2));
    }


    public static int numberOfStableArrays(int zero, int one, int limit) {
        int MOD = 1_000_000_007;
        // dp[i][j][0] 表示 i个0, j个1, 且最后一个是0
        // dp[i][j][1] 表示 i个0, j个1, 且最后一个是1
        long[][][] dp = new long[zero + 1][one + 1][2];

        // 初始化：只放0或只放1的情况
        for (int i = 1; i <= Math.min(zero, limit); i++) {
            dp[i][0][0] = 1;
        }
        for (int j = 1; j <= Math.min(one, limit); j++) {
            dp[0][j][1] = 1;
        }

        for (int i = 1; i <= zero; i++) {
            for (int j = 1; j <= one; j++) {
                // 填 dp[i][j][0]：最后一段是连续的 k 个 0，前一个是 1
                for (int k = 1; k <= Math.min(i, limit); k++) {
                    dp[i][j][0] = (dp[i][j][0] + dp[i - k][j][1]) % MOD;
                }
                // 填 dp[i][j][0]：最后一段是连续的 k 个 0，前一个是 1
                for (int k = 1; k <= Math.min(j, limit); k++) {
                    dp[i][j][1] = (dp[i][j][1] + dp[i][j - k][0]) % MOD;
                }
            }
        }
        return (int)((dp[zero][one][0] + dp[zero][one][1]) % MOD);
    }

}
