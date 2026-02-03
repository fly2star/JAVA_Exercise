package LeetCode_Hot100;

/*
338. 比特位计数

给你一个整数 n，对于 0 ≤ i ≤ n 中的每个 i，计算其二进制表示中 1 的个数，返回一个长度为 n + 1 的数组 ans 作为答案。
*/
public class question0037 {
    public static void main(String[] args) {
        int n = 2;
        int[] res = countBits2(n);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }

    // 方法 1 : 直接使用 java 内部的函数
    public static int[] countBits(int n) {
        int[] res = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            res[i] = Integer.bitCount(i);
        }
        return res;
    }


    // 方法 2 : Brian Kernighan 算法
    public static int[] countBits2(int n) {
        int[] res = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            int countOne = 0;
        int temp = i; // 关键：用临时变量来操作
        while (temp != 0) {
            // 每次执行都会干掉最右边的那个 1
            temp &= (temp - 1);
            countOne++;
        }
        res[i] = countOne;
        }
        return res;
    }

    
    
}
