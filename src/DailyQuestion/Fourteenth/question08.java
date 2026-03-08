package DailyQuestion.Fourteenth;

/*
1980. 找出不同的二进制字符串

给你一个字符串数组 `nums`，该数组由 n 个互不相同的二进制字符串组成，且每个字符串长度都是 n。
请你找出并返回一个长度为 n 且没有出现在 nums 中的二进制字符串。如果存在多种答案，只需返回任意一个即可。

## 提示：
    -- n = nums.length
    -- 1 ≤ n ≤ 16
    -- nums[i].length == n
    -- nums[i] 为 '0' 或 '1'
    -- nums 中的所有字符串互不相同
*/
public class question08 {
    public static void main(String[] args) {
        String[] nums = {"00", "10"};

        System.out.println(findDifferentBinaryString(nums));
    }

    // 方法1: 构造一个字符串, 使它的第`i`位与`nums[i]`的第`i`位不相同
    //      注意题目: 该数组由 n 个互不相同的二进制字符串组成, 且每个字符串长度都是 n
    public static String findDifferentBinaryString(String[] nums) {
        int n = nums.length;
        char[] res = new char[n];

        for (int i = 0; i < n; i++) {
            char c = nums[i].charAt(i);
            if (c == '0') {
                res[i] = '1';
            } else {
                res[i] = '0';
            }
        }


        return new String(res);
    }
}
