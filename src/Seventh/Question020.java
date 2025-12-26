package Seventh;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-26-16:18
 **/
public class Question020 {
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
