package DailyQuestion.Fourteenth;

/*
1404. 将二进制表示减到 1 的步骤数

给你一个以二进制形式表示的数字 `s`。请你返回按下述规则将其减少到 1 所需要的步骤数：
- 如果当前数字为偶数，则将其除以 2。
- 如果当前数字为奇数，则将其加上 1。

题目保证总是可以按上述规则将测试用例变为 1。

*/
public class question04 {
    public static void main(String[] args) {
        
    }

    public static int numSteps(String s) {
        int step = 0;
        int carry = 0;

        for (int i = s.length() - 1; i > 0; i--) {
            int digit = (s.charAt(i) - '0') + carry;
            if (digit == 1) {
                step += 2;      // 加1和除以2
                carry = 1;
            } else if (digit == 2) {
                step += 1;      // 只需除以2
                carry = 1;
            } else {
                step += 1;      // 只需除以2
                carry = 0;
            }
        }

        // 处理最高位
        return step + carry;
    }

}
