package FourthWeek;

import java.util.Arrays;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-05-16:43
 **/
/*
3432. 统计元素和差值为偶数的分区方案
给你一个长度为 n 的整数数组 nums 。
分区 是指将数组按照下标 i （0 <= i < n - 1）划分成两个 非空 子数组，其中：
    左子数组包含区间 [0, i] 内的所有下标。
    右子数组包含区间 [i + 1, n - 1] 内的所有下标。
对左子数组和右子数组先求元素 和 再做 差 ，统计并返回差值为 偶数 的 分区 方案数。
*/
public class question004 {
    public static void main(String[] args) {
        int[] nums = {10, 10, 3, 7, 6};
        System.out.println(countPartition(nums));
    }

    public static int countPartition(int[] nums) {
        int n = nums.length;
        int sum = 0;
        int count = 0;
        for (int num : nums) {
            sum += num;
        }
        int left = 0;
        int right = sum;
        for (int i = 0; i < n-1; i++) {
            left += nums[i];
            right -= nums[i];
            if ((right- left)% 2 == 0) {
                count++;
            }
        }
        return count;

    }

    //  设 nums 的元素和为 S，左子数组元素和为 L，那么右子数组的元素和为 S−L。
    //  题目要求 L−(S−L)=2L−S 是偶数。由于 2L 一定是偶数，所以只需关注 S 的奇偶性：
    //      如果 S 是奇数，偶数减奇数一定是奇数，答案是 0。
    //      如果 S 是偶数，偶数减偶数一定是偶数，所有分区方案都符合要求，答案是 n−1。
    //  上述结论与 i 无关。
    public static int countPartition2(int[] nums) {
        int s = Arrays.stream(nums).sum();
        return s % 2 != 0 ? 0 : nums.length - 1;

    }
}
