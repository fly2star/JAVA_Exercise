package DailyQuestion.Eleventh;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-22-20:43
 **/
public class Question003 {
    public static void main(String[] args) {
        int[] nums = {5, 2, 3, 1};
        System.out.println(minimumPairRemoval(nums));
    }

    public static int minimumPairRemoval(int[] nums) {

        int ans = 0;
        // 先变为 List
        List<Integer> list = new ArrayList<>();
        for (int num : nums) {
            list.add(num);
        }

        while (true) {
            // 检查是否非递减
            boolean nonDecFlag = true;
            for (int i = 0; i < list.size() -1 ; i++) {
                if (list.get(i) > list.get(i + 1)) {
                    nonDecFlag = false;
                    break;
                }
            }

            if (nonDecFlag) {
                return ans;
            }

            // 找到最小的相邻和
            int minSum = Integer.MAX_VALUE;
            int minSumIndex = -1;
            for (int i = 0; i < list.size() -1 ; i++) {
                int sum = list.get(i) + list.get(i + 1);
                if (sum < minSum) {
                    minSum = sum;
                    minSumIndex = i;
                }
            }

            // 替换元素 2换1
            list.remove(minSumIndex);
            list.remove(minSumIndex);   // 删除 minSumIndex 后, minSumIndex 后的元素都前移一位
            list.add(minSumIndex, minSum);
            ans++;
        }

    }
}
