package Fifth;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-08-09:55
 **/
/*
1925. 统计平方和三元组的数目
一个 平方和三元组 (a,b,c) 指的是满足 a2 + b2 = c2 的 整数 三元组 a，b 和 c 。
给你一个整数 n ，请你返回满足 1 <= a, b, c <= n 的 平方和三元组 的数目。
*/
public class Question001 {
    public static void main(String[] args) {
        int n = 50;
        System.out.println(countTriples(n));
        System.out.println(countTriples2(n));
    }

    public static int countTriples(int n) {
        int ans = 0;
        for (int a = 1; a <= n ; a++) {
            for (int b = a + 1; b <= n; b++) {
                int sum = a * a + b * b;
                int c = (int) Math.sqrt(sum);
                if (c * c == sum && c <= n) {
                    ans+=2;
                }
            }
        }
        return ans;
    }

    public static int countTriples2(int n) {
        int ans = 0;
       for (int u =3; u * u < n * 2; u += 2) {
           for (int v = 1; v < u && v * v + u * u <= n * 2; v += 2) {
               if (gcd(u, v) == 1) {
                   ans += n * 2 / (u * u + v * v);
               }
           }
       }
        return ans * 2;
    }

    public static int gcd(int a, int b) {
        while (a != 0) {
            int temp = a;
            a = b % a;
            b = temp;
        }
        return b;
    }
}
