package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-13-20:10
 **/
/*
263. 丑数
丑数 就是只包含质因数 2、3 和 5 的 正 整数。
给你一个整数 n ，请你判断 n 是否为 丑数 。如果是，返回 true ；否则，返回 false 。
* */
public class Question004 {
    public static void main(String[] args) {
        int n = 30;
        System.out.println(isUgly(n));
    }

    public static boolean isUgly(int n) {
        // 当 n>0 时，若 n 是丑数，则 n 可以写成 n=2^a*3^b*5^c,
        // 其中 a,b,c 都是非负整数。特别地，当 a,b,c 都是 0 时，n=1。
        if (n <= 0) return false;
        while (n % 2 == 0) {
            n /= 2;
        }
        while (n % 3 == 0) {
            n /= 3;
        }
        while (n % 5 == 0) {
            n /= 5;
        }
        return n == 1;
    }
}
