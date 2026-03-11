package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;

/*
560. 和为 K 的子数组

给你一个整数数组 `nums` 和一个整数 `k`，请你统计并返回该数组中和为 `k` 的子数组的个数。

子数组是数组中元素的连续非空序列。

## 提示：
-- 1 ≤ nums.length ≤ 2 * 10^4
-- -1000 ≤ nums[i] ≤ 1000
-- -10^7 ≤ k ≤ 10^7
*/
public class question064 {
    public static void main(String[] args) {
        int[] nums = {1, 1, 1};
        int k = 2;
        question064 sl = new question064();
        System.out.println(sl.subarraySum(nums, k));
    }

    public int subarraySum(int[] nums, int K) {
        int count = 0;
        int preSum = 0;

        // Map 的 Key 是前缀和，Value 是该前缀和出现的次数
        Map<Integer, Integer> map = new HashMap<>();
        
        // 初始化: 前缀和为 0 出现过 1 次
        map.put(0, 1);

        for (int num : nums) {
            preSum += num;  // 更新当前前缀和

            // 查表: 查找的那个 preSum - k 是否在表中 ?
            if (map.containsKey(preSum - K)) {
                count += map.get(preSum - K);
            }

            // 将当前前缀和存入表, 为后面的数字服务
            map.put(preSum, map.getOrDefault(preSum, 0) + 1);
        }

        return count;
    }
}
