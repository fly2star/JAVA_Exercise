package DailyQuestion.Fifteenth;

import java.util.Arrays;

public class question2 {

    long[][][] memo;
    int MOD = 1_000_000_007;
    int limit;
    public static void main(String[] args) {
        question2 q2 = new question2();
        System.out.println(q2.numberOfStableArrays(3, 3, 2));
        
    }

    public int numberOfStableArrays(int zero, int one, int limit) {
        this.limit = limit;
        // memo[z][o][last]
        memo = new long[zero + 1][one + 1][2];
        for (long[][] mat : memo) {
            for (long[] row : mat) Arrays.fill(row, -1);
        }

        // 结果是：最后一位放 0 的方案数 + 最后一位放 1 的方案数
        long res = (dfs(zero, one, 0) + dfs(zero, one, 1)) % MOD;
        return (int) res;
    }

    private long dfs(int z, int o, int last) {
        // 基础边界情况
        if (z == 0) return (last == 1 && o <= limit) ? 1 : 0;
        if (o == 0) return (last == 0 && z <= limit) ? 1 : 0;
        
        if (memo[z][o][last] != -1) return memo[z][o][last];

        long res = 0;
        if (last == 0) {
            // 当前想放 0，它等于：(上一步放 0 的总数) + (上一步放 1 的总数) - (非法项)
            // 这里的上一步状态是 (z-1, o)
            res = (dfs(z - 1, o, 0) + dfs(z - 1, o, 1)) % MOD;
            if (z > limit) {
                // 非法项：刚才连放了 limit+1 个 0，剩下的状态必须是以 1 结尾
                res = (res - dfs(z - limit - 1, o, 1) + MOD) % MOD;
            }
        } else {
            // 当前想放 1，它等于：(上一步放 0 的总数) + (上一步放 1 的总数) - (非法项)
            // 这里的上一步状态是 (z, o-1)
            res = (dfs(z, o - 1, 0) + dfs(z, o - 1, 1)) % MOD;
            if (o > limit) {
                // 非法项：刚才连放了 limit+1 个 1，剩下的状态必须是以 0 结尾
                res = (res - dfs(z, o - limit - 1, 0) + MOD) % MOD;
            }
        }

        return memo[z][o][last] = res;
    }

    
}
