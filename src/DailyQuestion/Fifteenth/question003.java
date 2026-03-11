package DailyQuestion.Fifteenth;

/*
1009. 十进制整数的反码

每个非负整数 `N` 都有其二进制表示。例如，5 可以被表示为二进制 "101"，11 可以用二进制 "1011" 表示，依此类推。
注意，除 `N = 0` 外，任何二进制表示中都不含前导零。

二进制的反码表示是将每个 1 改为 0 且每个 0 变为 1。
例如，二进制数 "101" 的二进制反码为 "010"。

给你一个十进制数 `N`，请你返回其二进制表示的反码所对应的十进制整数。


## 提示：
    -- 0 ≤ N < 10^9
*/
public class question003 {
    public static void main(String[] args) {
        question003 bc = new question003();
        System.out.println(bc.bitwiseComplement(5));
    }

    public int bitwiseComplement(int N) {
        if (N == 0) {
            return 1;
        }

        // 计算与 N 相同位数的全 1 掩码
        int mask = 0;
        int temp = N;
        while (temp > 0) {
            // 左移末尾补0, 然后按位或 1
            // 就是得到全1的掩码 
            mask = (mask << 1) | 1;
            // 右移赋值, 砍掉最后一位数字. 相当于除以2
            // 用来遍历二进制的每一位
            temp >>= 1;
        }

        return N ^ mask;
    }
}
