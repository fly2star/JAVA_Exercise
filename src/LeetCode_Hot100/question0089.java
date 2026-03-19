package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;

/*
1. 两数之和

给定一个整数数组 `nums` 和一个整数目标值 `target`，
请你在该数组中找出和为目标值 `target` 的那两个整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。

你可以按任意顺序返回答案。

## 提示：
    -- 2 ≤ nums.length ≤ 10^4
    -- -10^9 ≤ nums[i] ≤ 10^9
    -- -10^9 ≤ target ≤ 10^9
    -- 只会存在一个有效答案
*/
public class question0089 {
    public static void main(String[] args) {
        // int[] nums = new int[]{2,7,11,15};
        int[] nums = new int[]{3,3};
        int target = 9;

        question0089 sl89 = new question0089();
        int[] res = sl89.twoSum(nums, target);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }

    // hand
    // [3,3] target=6, 类似的例子重复数
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            map.put(nums[i], i);
        }

        for (int key : map.keySet()) {
            if (map.containsKey(target - key)) {
                res[0] = map.get(key);
                res[1] = map.get(target - key);
                break;
            }
        }
        return res;
    }

    // ai
    public int[] twoSum2(int[] nums, int target) {
        // 创建哈希表，存储数字和对应的下标
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // 检查差值是否在哈希表中
            if (numMap.containsKey(complement)) {
                return new int[]{numMap.get(complement), i};
            }
            // 将当前数字存入哈希表
            numMap.put(nums[i], i);
        }
        
        // 根据题意，不会执行到这里
        return new int[0];
    }

    
}
