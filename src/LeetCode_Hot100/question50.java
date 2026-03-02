package LeetCode_Hot100;

import java.util.Deque;
import java.util.LinkedList;

/*
239. 滑动窗口最大值

给你一个整数数组 `nums`，有一个大小为 `k` 的滑动窗口从数组的最左侧移动到数组的最右侧。
你只可以看到在滑动窗口内的 `k` 个数字。滑动窗口每次只向右移动一位。

返回滑动窗口中的最大值。

提示:
    -- 1 ≤ nums.length ≤ 10^5
    -- -10^4 ≤ nums[i] ≤ 10^4
    -- 1 ≤ k ≤ nums.length

*/
public class question50 {
    public static void main(String[] args) {
        int[] nums = {1,3,-1,-3,5,3,6,7};
        int[] result = maxSlidingWindow(nums, 3);
        for (int i : result) {
            System.out.print(i + " ");
        }
    }

    // 方法1: 单调队列方法
    public static int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }

        int n = nums.length;
        if (k == 1) {
            return nums;
        }

        // 使用双端队列存储索引
        Deque<Integer> deque = new LinkedList<>();
        int[] res = new int[n - k + 1];
        int resIndex = 0;

        for (int i = 0; i < n; i++) {
            // 移除队首过期元素 (索引小于窗口左边界)
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }

            // 维护队列单调递减, 从队尾移除小于当前元素的索引
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }

            // 加入当前元素索引
            deque.offerLast(i);

            // 当窗口形成时, 记录结果
            if (i >= k - 1) {
                res[resIndex++] = nums[deque.peekFirst()];
            }
        }
        return res;
    }
}
