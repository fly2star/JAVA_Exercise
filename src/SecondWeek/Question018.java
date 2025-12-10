package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-15-16:04
 **/
/*
3234. 统计 1 显著的字符串的数量
给你一个二进制字符串 s。
请你统计并返回其中 1 显著 的 子字符串 的数量。
如果字符串中 1 的数量 大于或等于 0 的数量的 平方，则认为该字符串是一个 1 显著 的字符串 。
*/
public class Question018 {
    public static void main(String[] args) {
        String s = "00011";
        System.out.println(numberOfSubstrings(s));
    }

    public static int numberOfSubstrings(String s) {
        int n = s.length();
        int[] pre = new int[n + 1];
        pre[0] = -1;
        for (int i = 0; i < n; i++) {
            if (i == 0 || (i > 0 && s.charAt(i - 1) == '0')) {
                pre[i + 1] = i;
            } else {
                pre[i + 1] = pre[i];
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            int cnt0 = s.charAt(i - 1) == '0' ? 1 : 0;
            int j = i;
            while (j > 0 && cnt0 * cnt0 <= n) {
                int cnt1 = (i - pre[j]) - cnt0;
                if (cnt0 * cnt0 <= cnt1) {
                    res += Math.min(j - pre[j], cnt1 - cnt0 * cnt0 + 1);
                }
                j = pre[j];
                cnt0++;
            }
        }
        return res;

    }

    public static int numberOfSubstrings2(String s) {
        int n = s.length();
        int[] pos0 = new int[n + 1]; // 0 的下标
        pos0[0] = -1; // 加个 -1 哨兵，方便处理 cnt0 达到最大时的计数
        int size = 1;
        int total1 = 0; // [0,r] 中的 1 的个数
        int ans = 0;

        for (int r = 0; r < n; r++) {
            if (s.charAt(r) == '0') {
                pos0[size++] = r; // 记录 0 的下标
            } else {
                total1++;
                ans += r - pos0[size - 1]; // 单独计算不含 0 的子串个数
            }

            // 倒着遍历 pos0，那么 cnt0 = size - i
            for (int i = size - 1; i > 0 && (size - i) * (size - i) <= total1; i--) {
                int p = pos0[i - 1];
                int q = pos0[i];
                int cnt0 = size - i;
                int cnt1 = r - q + 1 - cnt0; // [q,r] 中的 1 的个数 = [q,r] 的长度 - cnt0
                ans += Math.max(q - Math.max(cnt0 * cnt0 - cnt1, 0) - p, 0);
                // 如果 cnt0^2 > cnt1,那么还需要包含至少 cnt0^2 - cnt1 个1(p和q之前全为1),
                // 所以子串左端点的最大值为 q−(cnt0^2-cnt1) ,最小值为 p+1,
                // 一共 q−(cnt0^2 −cnt1)−p 个合法左端点。
                // 如果个数是负数，则没有合法左端点,所以与0进行比较。
            }
        }

        return ans;

    }
}
