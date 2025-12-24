package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-24-15:28
 **/
/*
136. 只出现一次的数字
给你一个 非空 整数数组 nums ，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
你必须设计并实现线性时间复杂度的算法来解决此问题，且该算法只使用常量额外空间。
*/
public class question0022 {
    public static void main(String[] args) {
        int[] nums = {2,2,1};
        System.out.println(singleNumber(nums));
    }

    // 方法1:自己的第一理解
    public static int singleNumber(int[] nums) {
        int ans=0;
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (!map.containsKey(num)){
                map.put(num, 1);
            } else {
                map.put(num, map.get(num) + 1);
            }

        }
        Set<Map.Entry<Integer, Integer>> entries = map.entrySet();
        for (Map.Entry<Integer, Integer> entry : entries) {
            if (entry.getValue() == 1) {
                ans = entry.getKey();
            }
        }
        return ans;

    }

    // 方法2: 异或法
    /**
     * 基础知识: 异或的关键特性</br>
     * 1.自反性: a ^ a = 0; 相同的数异或结果为 0 </br>
     * 2.恒等性: a ^ 0 = a; 任何数与 0 异或结果为自身 </br>
     * 3.交换律 & 结合律: a ^ b = b ^ a; a ^ (b ^ c) = (a ^ b) ^ c;
     * */
    public static int singleNumber2(int[] nums) {
        int ans = nums[0];
        for (int i = 1; i < nums.length; i++) {
            // 出现两次的数异或结果为 0, 出现一次的数与 0 异或结果为自身
            ans = ans ^ nums[i];
        }
        return ans;
    }


}
