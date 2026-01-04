package DailyQuestion.Ninth;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-04-20:47
 **/
public class Question01 {
    public static void main(String[] args) {
        int [] nums = {21, 7, 4};
        System.out.println(sumFourDivisors(nums));
    }

    public static int sumFourDivisors(int[] nums) {
        int ans =  0;
        List<List <Integer>> list = new ArrayList<>();

        for (int num : nums) {
            List <Integer> divisor = new ArrayList<>();
            int sqrt  = (int) Math.sqrt(num);
            for (int i = 1; i <= sqrt; i++) {
                if (num % i == 0) {
                    divisor.add(i);
                    if (i != num / i) {
                        divisor.add(num / i);
                    }
                }
            }
             if ( divisor.size() == 4) {
                 list.add(divisor);
             }
        }

        for (List<Integer> tempList : list) {
            for (int num : tempList) {
                ans  += num;
            }

        }

        return ans;
    }
}
