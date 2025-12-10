package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-13-10:07
 **/
/*
3228. 将 1 移动到末尾的最大操作次数
----------------------------------
给你一个 二进制字符串 s。
你可以对这个字符串执行 任意次 下述操作：
    选择字符串中的任一下标 i（ i + 1 < s.length ），该下标满足 s[i] == '1' 且 s[i + 1] == '0'。
    将字符 s[i] 向 右移 直到它到达字符串的末端或另一个 '1'。例如，对于 s = "010010"，如果我们选择 i = 1，结果字符串将会是 s = "000110"。
返回你能执行的 最大 操作次数。
----------------------------------
示例 1:
输入： s = "1001101"
输出： 4
解释：
可以执行以下操作：
    选择下标 i = 0。结果字符串为 s = "0011101"。
    选择下标 i = 4。结果字符串为 s = "0011011"。
    选择下标 i = 3。结果字符串为 s = "0010111"。
    选择下标 i = 2。结果字符串为 s = "0001111"。
实例 2:
输入： s = "00111"
输出： 0
----------------------------------
提示:
    1 <= s.length <= 105
    s[i] 为 '0' 或 '1'。
*/
public class Question02 {
    public static void main(String[] args) {
        String s = "1001101";
        System.out.println(maxOperations(s));
        String s1 = "00111";
        System.out.println(maxOperations(s1));
        String s2 = "011010";
        System.out.println(maxOperations2(s2));

    }

    public static int maxOperations(String s) {
        /**
         * 1. 从左到右遍历 s，同时用一个变量 count 维护遍历到的 1 的个数。
         * 2. 如果 s[i] 是 1，把 count 增加 1。
         * 3. 如果 s[i] 是 0 且 s[i−1] 是 1，意味着我们找到了一段道路，可以让 i 左边的每辆车都操作一次，把答案增加 count
         * 4. 遍历结束，返回答案。
         */
        char [] chars = s.toCharArray();
        int ans = 0;
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1') {
                count++;
            } else if (i > 0 && chars[i - 1] == '1') {
                ans += count;
            }
        }
        return ans;
    }

    public static int maxOperations2(String s) {
        int countOne = 0;
        int ans = 0;
        int i = 0;
        while (i < s.length()) {
            if (s.charAt(i) == '0') {
                while (i + 1 < s.length() && s.charAt(i + 1) == '0') {
                    i++;
                }
                ans += countOne;
            } else {
                countOne++;
            }
            i++;
        }
        return ans;

    }

}
/*
011010 010110 001110 001101 001011 000111
i=0 ct=0 ans=0
i=1 ct=1 ans=0
i=2 ct=2 ans=0
i=3 ct=2 ans=2
i=4 ct=3 ans=2
i=5 ct=3 ans=5

*/
/*
1001101
i=0 ct=1 ans=0
i=1 ct=1 ans=1
i=2 ct=1 ans=1
i=3 ct=2 ans=1
i=4 ct=3 ans=1
i=5 ct=3 ans=4
i=6 ct=4 ans=4
*/
