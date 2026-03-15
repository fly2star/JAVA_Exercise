package LeetCode_Hot100;

/*
581. 最短无序连续子数组

给你一个整数数组 `nums`，你需要找出一个 **连续子数组**，
如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。

请你找出符合题意的最 **短** 子数组，并输出它的长度。
*/
public class question095 {
    public static void main(String[] args) {
        int[] nums = new int[]{2,6,4,8,10,9,15};

        question095 sl95 = new question095();
        System.out.println(sl95.findUnsortedSubarray(nums));
    }

    public int findUnsortedSubarray(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }

        int left = -1, right = -1;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;

        // 从左到右找右边界
        for (int i = 0; i < n; i++) {
            if (nums[i] < max) {
                right = i;
            } else {
                max = nums[i];
            }
        }

        // 从右到左找左边界
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] > min) {
                left = i;
            } else {
                min = nums[i];
            }
        }
        return left == -1 ? 0 : right - left + 1;
    }
}
