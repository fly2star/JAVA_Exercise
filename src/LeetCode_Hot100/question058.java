package LeetCode_Hot100;

/*
34. 在排序数组中查找元素的第一个和最后一个位置

给你一个按照非递减顺序排列的整数数组 `nums`，和一个目标值 `target`。请你找出给定目标值在数组中的开始位置和结束位置。
如果数组中不存在目标值 `target`，返回 `[-1, -1]`。

你必须设计并实现时间复杂度为 \( O(\log n) \) 的算法解决此问题。

## 提示：
    -- 0 ≤ nums.length ≤ 10^5
    -- -10^9 ≤ nums[i] ≤ 10^9
    -- nums 是一个非递减数组
    -- -10^9 ≤ target ≤ 10^9

*/
public class question058 {
    public static void main(String[] args) {
        int[] nums = {5,7,7,8,8,10};
        int target = 8;

        int[] res = searchRange(nums, target);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }

    public static int[] searchRange(int[] nums, int target) {
        int[] res = new int[]{-1, -1};
        if (nums == null || nums.length == 0) {
            return res;
        }

        res[0] = findLeft(nums, target);
        res[1] = findRight(nums, target);
        return res;
    }

    private static int findLeft(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // 继续向左搜索
                right = mid - 1;
            }
        }

        // 检查 left 是否越界且等于 target
        if (left >= 0 && nums[left] == target) {
            return left;
        }
        return -1;
    }


    private static int findRight(int[] nums, int target){
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                // 继续向右搜索
                left = mid + 1;
            }
        }

        // 检查 right 是否越界且等于 target
        if (right >= 0 && nums[right] == target) {
            return right;
        }
        return -1;
    }
}
