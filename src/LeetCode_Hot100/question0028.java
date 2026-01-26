package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-26-20:14
 **/
public class question0028 {
    public static void main(String[] args) {
        int x = 1, y = 4;
        System.out.println(hammingDistance(x, y));
    }

    // 方法 1: 异或后运算 1 的个数
    public static int hammingDistance(int x, int y) {
        int xor = x ^ y;
        return Integer.bitCount(xor);
    }

    // 方法 2: 逐位异或，然后计算 1 的个数 (用到了 布莱恩·克尼汉 算法  Brian Kernighan's Algorithm)
    public static int hammingDistance2(int x, int y) {
        int xor = x ^ y;
        int count = 0;
        while (xor != 0) {
            count++;
            xor = xor & (xor - 1);
        }
        return count;
    }
}
