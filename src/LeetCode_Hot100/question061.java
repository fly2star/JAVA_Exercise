package LeetCode_Hot100;

/*
31. 下一个排列
整数数组的一个 **排列** 就是将其所有成员以序列或线性顺序排列。

整数数组的下一个排列是指其整数的下一个字典序更大的排列。
更正式地，如果数组的所有排列根据字典顺序从小到大排列在一个容器中，那么数组的下一个排列就是这个有序容器中排在它后面的那个排列。
如果不存在下一个更大的排列，那么这个数组必须重新为字典序最小的排列（即，其元素按升序排列）。

必须原地修改，只允许使用额外常数空间。

## 提示：
    -- 1 ≤ nums.length ≤ 100
    -- 0 ≤ nums[i] ≤ 100
*/
public class question061 {
    public static void main(String[] args) {
        
    }

    public static void nextPermutation(int[] nums){
        if (nums == null || nums.length <= 1) {
            return;
        }
        // 1. 从后向前找第一个“下降点” i , 
        //      下降点: 从右往左看，找到第一个比右边邻居小的数字。
        // 使得 nums[i] < nums[i + 1]
        int i = nums.length - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }

        // 2. 如果找到了下降点（i >= 0）
        // 说明数组不是完全降序的（即不是最大的排列）
        if (i >= 0) {
            // 3. 在 [i+1, n-1] 范围内找比 nums[i] 大的最小数（接班人）
            // 因为右侧是降序的，从后往前找第一个大于 nums[i] 的就是我们要找的
            int j = nums.length - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            // 交换下降点和接班人
            swap(nums, i, j);
        }
        
        // 4. 反转 i 之后的部分
        // 既然刚才 i 右侧是降序，交换后依然是降序，反转后就变成了升序（最小）
        reverse(nums, i + 1);
    }

    private static void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    private static void reverse(int[] nums, int start) {
        int left = start, right = nums.length - 1;
        while (left < right) {
            swap(nums, right, right);
            left++;
            right--;
        }
    }
     
}
