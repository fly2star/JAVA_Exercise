package LeetCode_Hot100;

import java.util.Arrays;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-13-21:51
 **/
/*
494. 目标和

给你一个非负整数数组 `nums` 和一个整数 `target`。

向数组中的每个整数前添加 `'+'` 或 `'-'`，然后串联起所有整数，可以构造一个表达式：
    - 例如，`nums = [2, 1]`，可以在 `2` 之前添加 `'+'`，在 `1` 之前添加 `'-'`，然后串联起来得到表达式 `"2-1"`。

返回可以通过上述方法构造的、运算结果等于 `target` 的不同 **表达式** 的数目。
*/
public class question027 {
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 1, 1};
        int target = 3;
        System.out.println(findTargetSumWays(nums, target));
    }

    public static int findTargetSumWays(int[] nums, int target) {
        int totalSum = Arrays.stream(nums).sum();

        // 检查 (totalSum + target) 是否非负且为偶数
        if (Math.abs(target) > totalSum || (totalSum + target) % 2 != 0) {
            return 0;
        }

        int P = (totalSum + target) / 2;
        int[] dp = new int[P + 1];
        // 和为 0 有一种方案: 什么都不选
        dp[0] = 1;
        // 假设用 'cont(i, j)' 表示 i 个数字凑出和为 j 的方案数.
        // 对于第 i 个数字 num , 有两种选择.
        //      1. 不选, 方案数="用前 i-1 个数字凑出 j 的方案数"
        //      2. 选, 方案数="用前 i-1 个数字凑出 j-num 的方案数"
        // 所以 count(i, j) = count(i-1, j) + count(i-1, j-num)
        for (int num : nums) {
            // 为什么要逆序 ???
            // 这样计算 dp[j] 时, 用到的 dp[j-num] 还是上一层留下的旧值. 保证每个数字只被考虑了一次 .
            // 如果使用正序, 计算 dp[j] 时, 用到的 dp[j-num] 已经是当前层更新过的值. 这相当于一个数字可以拿无数次 .
            for (int j = P; j >= num; j--) {
                dp[j] += dp[j-num];
            }
        }
        return dp[P];

    }
}
