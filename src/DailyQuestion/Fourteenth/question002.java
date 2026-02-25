package DailyQuestion.Fourteenth;


/*
693. 交替位二进制数

给定一个正整数，检查它的二进制表示是否总是 0、1 交替出现：换句话说，就是二进制表示中相邻两位的数字不相同。
*/
public class question002 {
    public static void main(String[] args) {
        int m = 5;
        System.out.println(hasAlternatingBits(m));
    }

    // 方法1: 异或测试
    // 交替位二进制数的特点是相邻位不同
    // 可以通过将原数右移一位后与原数进行异或操作：如果原数是交替位二进制数，那么异或结果的所有位应该都是 1
    // 然后检查异或结果加 1 后与原结果进行与运算是否为零
    public static boolean hasAlternatingBits(int n) {
        int temp = n ^ (n >> 1);
        return (temp & (temp + 1)) == 0;
    }
}
