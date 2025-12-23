package Sixth;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-15-09:56
 **/
/*
2110. 股票平滑下跌阶段的数目
给你一个整数数组 prices ，表示一支股票的历史每日股价，其中 prices[i] 是这支股票第 i 天的价格。
一个 平滑下降的阶段 定义为：对于 连续一天或者多天 ，每日股价都比 前一日股价恰好少 1 ，这个阶段第一天的股价没有限制。
请你返回 平滑下降阶段 的数目
*/
public class Question01 {
    public static void main(String[] args) {
        int[] prices = {3, 2, 1, 4};
        int[] prices2 = {8, 6, 7, 7};
        System.out.println(getDescentPeriods( prices));
        System.out.println(getDescentPeriods2( prices2));
    }
    // 方法一: 统计数组中的最长递减光滑子数组的个数
    //          一个长度恒定的最长光滑子数组, 可以表示恒定个数个连续递减子数组
    public static long getDescentPeriods(int[] prices) {
        int n = prices.length;
        long res = 0;
        List<Integer> smoothArr = new ArrayList<>();
        int i = 0;
        while (i < n) {
            int subLen = 0;
            int j = i;
            while (j < n - 1 && prices[j] == prices[j + 1] + 1) {
                j++;
            }
            subLen = j - i + 1;
            smoothArr.add(subLen);
            i = j + 1;
        }
        for (int num : smoothArr) {
//            System.out.println(num);
            res += (long)num * (num + 1) / 2;
        }
        return res;

    }

    // 方法二: 在遍历 prices 的同时，统计当前这段连续递减的长度 dec，那么右端点为 i 的连续递减子数组个数就是 dec，加到答案中
    //              如果 prices[i] = prices[i - 1] - 1, 连续递减, dec 增加一
    //              否则没连续递减中断, dec 重置为 1
    public static long getDescentPeriods2(int[] prices) {
        long ans = 0;
        int des = 0;
        for (int i = 0; i < prices.length; i++) {
            if (i > 0 && prices[i] == prices[i - 1] - 1) {
                // 连续递减
                des++;
            } else {
                // 连续递减中断, 重置为 1
                des = 1;
            }
            // 右端点为 i 的连续递减子数组个数就是 des, 加到答案中
            ans += des;
        }

        return ans;
    }
}
