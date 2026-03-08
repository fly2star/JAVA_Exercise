package LeetCode_Hot100;

/*
33. 搜索旋转排序数组

整数数组 `nums` 按升序排列，数组中的值 **互不相同**。

在传递给函数之前，`nums` 在预先未知的某个下标 `k`（0 ≤ k < nums.length）上进行了 **向左旋转**，
使数组变为 `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]`。例如，`[0,1,2,4,5,6,7]` 在下标 3 处旋转后可能变为 `[4,5,6,7,0,1,2]`。

给你 **旋转后的数组** `nums` 和一个整数 `target`，如果 `nums` 中存在这个目标值 `target`，则返回它的下标，否则返回 `-1`。

你必须设计一个时间复杂度为 **O(log n)** 的算法解决此问题。

*/
public class question059 {
    public static void main(String[] args) {
        int[] nums = {4,5,6,7,0,1,2};
        int target = 0;

        System.out.println(search(nums, target));
    }

    /*
    - 旋转后的数组有一个性质：将数组从中间分开，**至少有一半是有序的**
    - 通过比较 `nums[left]` 和 `nums[mid]` 可以判断左半部分是否有序
    - 如果左半部分有序，判断 target 是否在左半部分范围内；否则 target 可能在右半部分
    - 通过这种判断，可以每次将搜索范围缩小一半，达到 O(log n) 的时间复杂度
    */
    public static int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            // 找到目标值
            if (nums[mid] == target) {
                return mid;
            }

            // 判断左半部分是否有序
            if (nums[left] <= nums[mid]) {
                // 左半部分有序
                // 判断 target 是否在左边的有序区间内
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;    // 在左边
                } else {
                    left = mid + 1;     // 去右边找
                }
            } else {
                // 右半部分有序
                // 判断 target 是否在右边的有序区间内
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;     // 在右边
                } else {
                    right = mid - 1;    // 去左边找
                }
            }

        }

        return -1;
    }
}
