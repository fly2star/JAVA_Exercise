package LeetCode_Hot100;

/*
287. 寻找重复数

给定一个包含 `n + 1` 个整数的数组 `nums`，其数字都在 `[1, n]` 范围内（包括 1 和 n），可知至少存在一个重复的数。

假设 `nums` 只有一个重复的数，返回这个重复的数。

你设计的解决方案必须 **不修改** 数组 `nums` 且只用常量级 O(1) 的额外空间。
*/

public class question045 {
    public static void main(String[] args) {
        int[] arrTest = {1, 2, 3, 4, 3};
        System.out.println(findDuplicate(arrTest));
        System.out.println(findDuplicateBinarySearch(arrTest));
        System.out.println(findDuplicateBitManipulation(arrTest));
    }

    // 方法1: Floyd判圈算法(快慢指针)
    private static int findDuplicate(int[] nums) {
        // 第一阶段：找到相遇点
        // 使用快慢指针, 快指针每次走两步, 慢指针每次走一步;
        // 如果存在环，快慢指针一定会相遇
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // 第二阶段：找到环的入口(重复数字)
        // 当快慢指针相遇后, 将慢指针移回起点, 快慢指针都改为每次走一步
        // 它们再次相遇的位置就是环的入口，即重复数字
        slow = nums[0];     // 慢指针回到起点 
        while (slow != fast ) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    // 方法2: 二分查找
    private static int findDuplicateBinarySearch(int[] nums) {
        int n = nums.length - 1;    // 数字范围是 1-n 
        int left = 1, right = n;

        while (left < right) {
            int mid = left + (right - left) / 2;

            // 统计小于 mid 的数字个数
            int count = 0;
            for (int num : nums) {
                if (num < mid) {
                    count++;
                }
            }

            // 如果计数大于 mid, 说明重复数组在左半部分
            if (count > mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }

        return left;
    }

    // 方法3: 位运算
    private static int findDuplicateBitManipulation(int[] nums){
        int n = nums.length - 1;
        int result = 0;

        // 检查每一位(最多检查20位, 因为 n 最大为 10^5)
        for (int bit = 0; bit < 32; bit++) {
            int mask = 1 << bit;

            // 统计在 nums 中该位为 1 的个数
            int countNums = 0;
            for (int num : nums) {
                if ((num & mask) != 0) {
                    countNums++;
                }
            }

            // 统计在 1……n 中该位为 1 的个数
            int countRange = 0;
            for (int i = 0; i <= n; i++) {
                if ((i & mask) != 0) {
                    countRange++;
                }
            }

            // 如果在 nums 中的技术大于在 1……n 中的计数, 说明重复数字该位为1
            if (countNums > countRange) {
                result |= mask;
            }
        }
        return result;
    }

    
    
}
