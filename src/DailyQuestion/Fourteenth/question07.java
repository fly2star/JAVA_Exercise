package DailyQuestion.Fourteenth;

/*
1888. 使二进制字符串字符交替的最少反转次数

给你一个二进制字符串 `s`。你可以按任意顺序执行以下两种操作任意次：

    --类型 1：删除字符串 `s` 的第一个字符并将它 **添加** 到字符串结尾。
    --类型 2：选择字符串 `s` 中任意一个字符并将该字符 **反转**，也就是如果值为 `'0'`，则反转得到 `'1'`，反之亦然。

请你返回使 `s` 变成交替字符串的前提下，**类型 2 的最少** 操作次数。

我们称一个字符串是 **交替的**，需要满足任意相邻字符都不同。
    比方说，字符串 "010" 和 "1010" 都是交替的，但是字符串 "0100" 不是。

## 提示：
    -- 1 <= s.length <= 10^5
    -- s[i] 要么是 '0', 要么是 '1'。
*/
public class question07 {
    public static void main(String[] args) {
        String s = "111000";
        System.out.println(minFlips(s));
    }

    public static int minFlips(String s) {
        int n = s.length();
        String s2 = s + s;
        
        // 构建两种目标模式
        char[] target0 = new char[2 * n];
        char[] target1 = new char[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            target0[i] = (i % 2 == 0) ? '0' : '1';
            target1[i] = (i % 2 == 0) ? '1' : '0';
        }
        
        // 滑动窗口计算
        int ans = Integer.MAX_VALUE;
        int window0 = 0, window1 = 0;
        
        for (int i = 0; i < 2 * n; i++) {
            if (s2.charAt(i) != target0[i]) window0++;
            if (s2.charAt(i) != target1[i]) window1++;
            
            if (i >= n) {
                if (s2.charAt(i - n) != target0[i - n]) window0--;
                if (s2.charAt(i - n) != target1[i - n]) window1--;
            }
            
            if (i >= n - 1) {
                ans = Math.min(ans, Math.min(window0, window1));
            }
        }
        
        return ans;
    }
}
