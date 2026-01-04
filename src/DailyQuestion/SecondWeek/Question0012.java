package DailyQuestion.SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-14-16:52
 **/
/*
509. 斐波那契数
斐波那契数 （通常用 F(n) 表示）形成的序列称为 斐波那契数列 。该数列由 0 和 1 开始，后面的每一项数字都是前面两项数字的和。也就是：
    F(0) = 0，F(1) = 1
    F(n) = F(n - 1) + F(n - 2)，其中 n > 1
给定 n ，请计算 F(n) 。
*/
public class Question0012 {
    public static void main(String[] args) {
        System.out.println(fib(3));
    }

    public static int fib(int n) {
        int a = 0, b = 0, c = 1;
        if (n < 2) {
            return a;
        }
        for (int i = 2; i <= n; i++) {
            a = b;
            b = c;
            c = a + b;
        }
        return c;
    }
}
