package Sixth;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-15-09:56
 **/
public class Question01 {
    public static void main(String[] args) {
        int[] prices = {3, 2, 1, 4};
        int[] prices2 = {8, 6, 7, 7};
        System.out.println(getDescentPeriods( prices));
        System.out.println(getDescentPeriods( prices2));
    }

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
            System.out.println(num);
            res += (long)num * (num + 1) / 2;
        }
        return res;

    }

    public static long getDescentPeriods2(int[] prices) {
        long ans = 0;
        int des = 0;
        for (int i = 0; i < prices.length; i++) {
            if (i > 0 && prices[i] == prices[i - 1] - 1) {
                des++;
            } else {
                des = 1;
            }
            ans += des;
        }

        return ans;
    }
}
