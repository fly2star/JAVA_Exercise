package DailyQuestion.Sixteenth;

import java.util.Scanner;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-04-24-16:04
 **/
/*
一个正整数，代表米小游需要消耗的最少 mp。

    - 第一行输入两个正整数 `n` 和 `x`，分别代表深渊法师的数量和“高天之歌”的技能 mp 消耗。
    - 第二行输入 `n` 个正整数 `a_i`，分别代表每个深渊法师的护盾值。
    - 第三行输入一个长度为 `n` 的字符串，第 `i` 个字符为：
        - `'F'` 代表该深渊法师是火系，
        - `'I'` 代表冰系，
        - `'W'` 代表水系。

*/
public class mihoyo2023B1 {

    public static void main(String[] args) {
        
        // minMpcost 的测试 >>>
        // int[] shields = new int[]{6, 8 ,2 ,3};
        // String elements = "FFWI";
        // mihoyo2023B1 m1 = new mihoyo2023B1();
        // System.out.println(m1.minMpCost(4, 5, shields, elements));
        // <<<


        // ACM 模式 >>> 
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        long x = scanner.nextLong();    // 可能会很大
        long[] a = new long[n];
        long totalCost = 0;     // 不使用技能的基础总消耗
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextLong();
            totalCost += a[i];

        }

        String s = scanner.next();

        // 动态规划求最大节省值
        // dp[i] 表示前 i 个法师通过合理使用技能，所能节省的最大 mp 数
        // prev2 代表 dp[i-2], prev1 代表 dp[i-1]
        long prev2 = 0;
        long prev1 = 0;


        // 状态转移方程:
        //  对于第 i 个法师:
        //      1. 不与前一个法师配对: 那么节省的 mp 不变, 即 dp[i] = dp[i-1]
        //      2. 与前一个法师配对 (前提是属性不同): 那么节省的 mp 为 前 i-2 个法师节省的 mp + 当前这对法师节省的 mp .
        //                                          即 dp[i] = dp[i-2] + (a[i] + a[i-1] - x)
        for (int i = 1; i < n; i++) {
            // 默认不匹配
            long current = prev1;

            // 如果相邻法师属性不同, 尝试配对
            if (s.charAt(i) != s.charAt(i - 1)) {
                // 计算通过配对能省下多少 mp
                long save = a[i] + a[i - 1]  - x;

                // 只有配对能省 mp 时才考虑转移
                if (save > 0) {
                    current = Math.max(current, prev1 + save);
                }
            }

            // 状态滚动
            prev2 = prev1;
            prev1 = current;
        }

        // 最终结果 = 原始总消耗 - 最大能节省的消耗
        System.out.println(totalCost - prev1);
        scanner.close();
    }

    /**
     * 
     * @param nums     深渊法师的数量
     * @param mp       使用“高天之歌”技能的 mp 消耗
     * @param shields  每个深渊法师的护盾值数组
     * @param elements 每个深渊法师的元素属性字符串（如 "FFWI"）
     * @return 最少需要的 mp 总消耗
    **/
    public int minMpCost(int nums, int mp, int[] shields, String elements) {

        // 边界防御条件
        if (nums <= 0 || shields == null || shields.length == 0 || elements == null) {
            return 0;
        }

        // 计算不使用任何技能时的基础总消耗
        int totalCost = 0;
        for (int i = 0; i < nums; i++) {
            totalCost += shields[i];
        }

        // 动态规划求最大节省的 mp 值
        int prev2 = 0;     // 相当于 dp[i-2]
        int prev1 = 0;     // 相当于 dp[i-1]

        for (int i = 1; i < nums; i++) {
            int current = prev1;   // 默认当前法师不与前一个配对

            // 判断相邻法师属性是否不同
            if (elements.charAt(i) != elements.charAt(i-1)) {
                // 计算配对能节省的 mp
                int save = shields[i] + shields[i - 1] - mp;

                // 只有当配对确实能省下 mp 时，才进行状态转移
                if (save > 0) {
                    current = Math.max(current, prev2 + save);
                }
            }

            // 状态滚动更新
            prev2 = prev1;
            prev1 = current;
        }
        return totalCost - prev1;
    }
}
