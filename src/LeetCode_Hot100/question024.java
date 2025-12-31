package LeetCode_Hot100;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-29-21:02
 **/
/*
128. 最长连续序列

给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。

请你设计并实现时间复杂度为 O(n) 的算法解决此问题。
*/
public class question024 {
    public static void main(String[] args) {
        int [] nums = {100,4,200,1,3,2};
        System.out.println(longestConsecutive(nums));
    }

    public static int longestConsecutive(int[] nums) {
        // 将所有数字放入 HashSet 中
        Set<Integer> numSet = new HashSet<>();
        for (int num : nums) {
            numSet.add(num);
        }

        int longestStreak = 0;

        // 遍历集合（或原数组）
        for (int num : numSet) {
            // 剪枝:检查当前数字是否是一个序列的起点
            // 如果集合里有 num - 1，说明 num 是序列中间的一部分，跳过它
            if (!numSet.contains(num - 1)) {
                int currentNum = num;
                int currentStreak = 1;

                // 只有是起点时，才开始向后数数
                while (numSet.contains(currentNum + 1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                // 更新全局最长长度
                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }
}
