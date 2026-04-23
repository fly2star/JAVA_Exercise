package DailyQuestion.Sixteenth;

import java.util.Arrays;

/*
45. 跳跃游戏 II
给定一个长度为 `n` 的 0 索引整数数组 `nums`。初始位置在下标 0。

每个元素 `nums[i]` 表示从索引 `i` 向后跳转的最大长度。换句话说，如果你在索引 `i` 处，你可以跳到任意 `(i + j)` 处：
- 0 <= j <= nums[i]  且
- i + j < n 

返回到达 `n - 1` 的最小跳跃次数。测试用例保证可以到达 `n - 1`。


## 提示：
    -- 1 ≤ nums.length ≤ 10^4
    -- 0 ≤ nums[i] ≤ 1000
    -- 题目保证可以到达 n-1
*/
public class question06 {
    
    public static void main(String[] args) {
        
    }

    // 方法1 : 贪心算法
    public int jump(int[] nums) {
        if (nums.length <= 1) {
            return 0;
        }

        int jumps = 0;
        int currentEnd = 0;
        int farthest = 0;

        for (int i = 0; i < nums.length - 1; i++) {
            // 在当前区间内, 不断刷新我们能到达的最远距离
            farthest = Math.max(farthest, i + nums[i]);

            // 如果走到了当前跳跃能到达的边界
            if (i == currentEnd) {
                jumps++;                    // 被迫消耗一次跳跃次数
                currentEnd = farthest;      // 更新下一次跳跃的边界

                // 剪枝小优化: 如果边界已经覆盖了终点, 可以直接退出
                if (currentEnd >= nums.length - 1) {
                    break;
                }
            }
        }
        return jumps;
    }


    // 方法2: 动态规划
    public int jump2(int[] nums) {
        int n = nums.length;
        // 从索引 0 跳到当前位置 i 所需要的最少跳跃次数
        int[] dp = new int[n];

        // 初始化一个很大的数
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;      // 起点最少跳 0 次

        // 遍历每一个要到达的目的地 i
        for (int i = 1; i < n; i++) {
            // 回头看前面的所有起点 j
            for (int j = 0; j < i; j++) {
                // 如果从 j 能够一步跳到 i
                if (j + nums[j] >= i) {
                    // 更新到达 i 的最小步数
                    dp[i] = Math.min(dp[i], dp[j] + 1);
                }
            }
        }

        return dp[n - 1];
    }

}
