package LeetCode_Hot100;

/*
309. 买卖股票的最佳时机含冷冻期

给定一个整数数组 prices，其中第 prices[i] 表示第 i 天的股票价格。

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）：
- 卖出股票后，你无法在第二天买入股票（即冷冻期为 1 天）。
- 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。
*/
public class question041 {
    public static void main(String[] args) {
        int prices[] = {1, 2, 3, 0, 2};
        System.out.println(maxProfit(prices));
    }

    // 方法 1 : 动态规划
    public static int maxProfit(int[] prices) {
        int n = prices.length;
        if (prices == null || n < 2) {
            return 0;
        }

        int[] hold = new int[n];    // 持有股票
        int[] cold = new int[n];    // 不持有, 处于冷却期
        int[] free = new int[n];    // 不持有, 不处于冷却期

        // 初始化
        hold[0] = -prices[0];   // 第一天买入
        cold[0] = 0;            // 第一天不可能买入
        free[0] = 0;            // 第一天不持有

        for (int i = 1; i < n; i++) {
            // 状态转移
            hold[i] = Math.max(hold[i - 1], free[i - 1] - prices[i]);
            cold[i] = hold[i - 1] + prices[i];
            free[i] = Math.max(free[i - 1], cold[i - 1]);
        }

        // 最后一天不能持有股票
        return Math.max(cold[n - 1], free[n - 1]);

    }
}
