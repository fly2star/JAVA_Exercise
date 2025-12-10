package ThirdWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-18-09:58
 **/
/*
717. 1 比特与 2 比特字符
有两种特殊字符：
    第一种字符可以用一比特 0 表示
    第二种字符可以用两比特（10 或 11）表示
给你一个以 0 结尾的二进制数组 bits ，如果最后一个字符必须是一个一比特字符，则返回 true 。
*/
public class Question007 {
    public static void main(String[] args) {
        int[] bits = {1, 0, 0};
        int[] bits2 = {1, 1, 1, 0};
        System.out.println(isOneBitCharacter(bits));
        System.out.println(isOneBitCharacter(bits2));
    }

    public static boolean isOneBitCharacter(int[] bits) {
        int n = bits.length;
        int i = 0;
        while (i < n - 1) {
            if (bits[i] == 1) {
                i += 2;
            } else {
                i++;
            }
        }
        return i == n - 1;
    }
}
