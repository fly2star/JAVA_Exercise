package DailyQuestion.FourthWeek;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-24-18:47
 **/
/*
1018. 可被 5 整除的二进制前缀
给定一个二进制数组 nums ( 索引从0开始 )。
我们将xi 定义为其二进制表示形式为子数组 nums[0..i] (从最高有效位到最低有效位)。
    例如，如果 nums =[1,0,1] ，那么 x0 = 1, x1 = 2, 和 x2 = 5。
返回布尔值列表 answer，只有当 xi 可以被 5 整除时，答案 answer[i] 为 true，否则为 false。
*/
public class Question001 {
    public static void main(String[] args) {
        int[] nums = {0, 1, 1};
        System.out.println(prefixesDivBy5(nums));
        System.out.println(prefixesDivBy5_2(nums));

    }

    public static List<Boolean> prefixesDivBy5(int[] nums) {
        List<Boolean> ans = new ArrayList<>();
        BigInteger Decimal = new BigInteger("0");
        for (int num : nums) {
            if (num  == 0) {
                Decimal = Decimal.multiply(BigInteger.valueOf(2));
            } else {
                Decimal = Decimal.multiply(BigInteger.valueOf(2)).add(BigInteger.valueOf(num));
            }
            ans.add(Decimal.remainder(BigInteger.valueOf(5)).equals(BigInteger.ZERO));
        }
        return ans;
    }

    public static List<Boolean> prefixesDivBy5_2(int[] nums) {
        List<Boolean> ans = new ArrayList<>();
        int Decimal = 0;
        for (int num : nums) {
            Decimal = (Decimal * 2 + num) % 5;
            ans.add(Decimal == 0);
        }
        return ans;
    }
}
