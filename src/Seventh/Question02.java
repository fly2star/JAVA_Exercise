package Seventh;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-26-16:18
 **/
/*
2483. 商店的最少代价
给你一个顾客访问商店的日志，用一个下标从 0 开始且只包含字符 'N' 和 'Y' 的字符串 customers 表示：
    如果第 i 个字符是 'Y' ，它表示第 i 小时有顾客到达。
    如果第 i 个字符是 'N' ，它表示第 i 小时没有顾客到达。
如果商店在第 j 小时关门（0 <= j <= n），代价按如下方式计算：
    在开门期间，如果某一个小时没有顾客到达，代价增加 1 。
    在关门期间，如果某一个小时有顾客到达，代价增加 1 。
请你返回在确保代价 最小 的前提下，商店的 最早 关门时间。
注意，商店在第 j 小时关门表示在第 j 小时以及之后商店处于关门状态。
*/
public class Question02 {
    public static void main(String[] args) {
        String customers = "YYYY";
        System.out.println(bestClosingTime(customers));
    }

    // 方法1:
    public static int bestClosingTime(String customers) {
        int n = customers.length();
        int[] pre = new int[n + 1];
        // 统计在 customer[i]以及之前 Y 的个数
        for (int i = 0; i < n; i++) {
            pre[i + 1] = pre[i] + (customers.charAt(i) == 'Y' ? 1 : 0);
        }
        int min = Integer.MAX_VALUE;
        int ans = -1;
        for (int i = 0; i <= n; i++) {
            int temp = (i - pre[i]) + (pre[n] - pre[i]);
            System.out.println(temp);
            if (temp < min) {
                min = temp;
                ans = i;
            }
        }
        return ans;
    }


    // 方法2: 前缀后缀分解
    public static int bestClosingTime2(String customers) {
        char[] s = customers.toCharArray();
        int penalty = 0;
        for (char c : s) {
            if (c == 'Y') {
                penalty++;
            }
        }

        int minPenalty = penalty;
        int ans = 0; // [0,n-1] 是第二段
        for (int i = 0; i < s.length; i++) {
            penalty += s[i] == 'N' ? 1 : -1;
            if (penalty < minPenalty) {
                minPenalty = penalty;
                ans = i + 1; // [0,i] 是第一段，[i+1,n-1] 是第二段
            }
        }
        return ans;
    }

    // 方法3: 对方法2进行优化, 一次遍历
    public static int bestClosingTime3(String customers) {
        int penalty = 0;
        int minPenalty = 0;
        int ans = 0;
        for (int i = 0; i < customers.length(); i++) {
            penalty += customers.charAt(i) == 'N' ? 1 : -1;
            if (penalty < minPenalty) {
                minPenalty = penalty;
                ans = i + 1;
            }
        }
        return ans;
    }

}
