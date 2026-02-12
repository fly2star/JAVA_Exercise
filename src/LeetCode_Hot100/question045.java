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
        int res = arrTest[0];
        for (int i = 1; i < arrTest.length; i++) {
            res = res ^ arrTest[i];
        }
        System.out.println(res);
    }

    // 方法1: Floyd判圈算法(快慢指针)
    private static int findDuplicate(int[] nums) {
        // 第一阶段：找到相遇点
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // 第二阶段：找到环的入口(重复数字)
        slow = nums[0];     // 慢指针回到起点 
        while (slow != fast ) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }

    
    
}
