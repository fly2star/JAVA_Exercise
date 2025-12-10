package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-14-17:05
 **/
/*
1137. 第 N 个泰波那契数
T0 = 0, T1 = 1, T2 = 1, 且在 n >= 0 的条件下 Tn+3 = Tn + Tn+1 + Tn+2
给你整数 n，请返回第 n 个泰波那契数 Tn 的值。
*/
public class Question0013 {
    public static void main(String[] args) {
        System.out.println(tribonacci(25));
    }

    public static int tribonacci(int n) {
        if (n == 0) return 0;
        if (n == 1 || n == 2) return 1;
        int p = 0, q = 1, r = 1, s = 0;
        for (int i = 3; i <= n; i++) {
            s = p + q + r;
            p = q;
            q = r;
            r = s;
        }
        return s;
    }
}
