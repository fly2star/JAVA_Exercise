package DailyQuestion.Fourteenth;

/*
1784. 检查二进制字符串字段

给你一个二进制字符串 `s`，该字符串 **不含前导零**。
如果 `s` 包含 **零个或一个由连续的 '1' 组成的字段**，返回 `true`。否则，返回 `false`。

提示：
    -- 1 ≤ s.length ≤ 100
    -= s[i] 为 '0' 或 '1'
    -- s[0] 为 '1'（字符串不含前导零）

*/
public class question006 {
    public static void main(String[] args) {
        String s = "1101";
        System.out.println(checkOnesSegment(s));
    }
    // 方法1:
    // 思路: 因为字符串不含前导零, 所以第一个字符必为一。也即是说至少含有一个连续的的'1'.
    public static boolean checkOnesSegment(String s) {
        // 找到第一个0的位置
        int zeroIndex = s.indexOf('0');

        // 没有找到, 说明全为 1
        if (zeroIndex == -1) {
            return true;
        }

        // 检查第一个 0 后是否还有 1 , 若存在, 就说明至少有两个连续的 1 , 返回 false .
        return s.indexOf('1', zeroIndex) == -1;

    }
    
}
