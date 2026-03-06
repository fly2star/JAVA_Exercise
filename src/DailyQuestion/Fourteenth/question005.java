package DailyQuestion.Fourteenth;

/*
1758. 生成交替二进制字符串的最少操作数

给你一个仅由字符 '0' 和 '1' 组成的字符串 s。一步操作可以将 '0' 变成 '1'，或者将 '1' 变成 '0'。
交替字符串定义为：字符串中不存在相邻两个字符相同的情况。例如，字符串 "010" 是交替字符串，而字符串 "0100" 不是。

返回使 s 变成交替字符串所需的最少操作数。

提示：
    -- 1 <= s.length <= 10^4
    -- s[i] 是 '0' 或 '1'
*/

public class question005 {
    public static void main(String[] args) {
        String s = "0100";
        System.out.println(minOperations(s));
    }
    // 方法1: 
    // 交替字符串共有两种模式, A:0101... ; B:1010...
    // 比较变换为两种模式所需要的步数
    public static int minOperations(String s) {
        int n = s.length();
        int count = 0;  // 转换成以 '0' 开头的交替字符串所需的操作数

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);
            // 以索引0开始, 偶数位为'0', 奇数位为'1'
            char expected = (i % 2 == 0) ? '0' : '1';
            if (c != expected) {
                count++;
            }
        }
        return Math.min(count, n - count);
    }
}
