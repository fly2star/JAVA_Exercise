package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-26-20:23
 **/
/*
448. 找到所有数组中消失的数字

给你一个含 `n` 个整数的数组 `nums`，其中 `nums[i]` 在区间 `[1, n]` 内。

请你找出所有在 `[1, n]` 范围内但没有出现在 `nums` 中的数字，并以数组的形式返回结果。

*/
public class question0029 {
    public static void main(String[] args) {
        int[] nums = {4, 3, 2, 7, 8, 2, 3, 1};
        System.out.println(findDisappearedNumbers(nums));
    }

    // 方法 1: 先将 num 转化为 集合, 在使用集合的 contain() 方法
    public static List<Integer> findDisappearedNumbers(int[] nums) {
        int n = nums.length;
        List<Integer> res = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }

        for (int i = 1; i <= n; i++) {
            if (!set.contains(i)) {
                res.add(i);
            }
        }

        return res;
    }

    // 方法 2: 直接在原先数组的基础上进行更改
    public static List<Integer> findDisappearedNumbers2(int[] nums) {
        //将 nums[i] 对应的下标位置的值变为负数，表示该数字已存在。
        List<Integer> res = new ArrayList<>();

        for (int num : nums) {
            // 数组中元素是从 1 到 n 的,
            int idx = Math.abs(num) - 1;
            if (nums[idx] > 0) {
                nums[idx] *= -1;
            }
        }

        // 收集整数的索引
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] > 0) {
                res.add(i + 1);
            }
        }

        return res;

    }
}
