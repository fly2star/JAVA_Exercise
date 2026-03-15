package LeetCode_Hot100;

/*
55. 跳跃游戏

给你一个非负整数数组 `nums`，你最初位于数组的第一个下标。数组中的每个元素代表你在该位置可以跳跃的最大长度。

判断你是否能够到达最后一个下标，如果可以，返回 `true`；否则，返回 `false`。

## 提示：
    -- 1 ≤ nums.length ≤ 10^4
    -- 0 ≤ nums[i] ≤ 10^5
*/
public class question099 {
    public static void main(String[] args) {
        int[] nums = new int[]{2,3,1,1,4};

        question099 sl99 = new question099();
        System.out.println(sl99.canJump(nums));
    }

    public boolean canJump(int[] nums) {
        int n = nums.length;
        int maxReach = 0;

        for (int i = 0; i < n; i++) {
            if (i > maxReach) {
                return false;
            }
            maxReach = Math.max(maxReach, i + nums[i]);
            if (maxReach >= n - 1) {
                return true;
            }
        }

        return true;
    }
}
