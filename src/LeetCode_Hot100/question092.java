package LeetCode_Hot100;

/*
75. 颜色分类

给定一个包含红色、白色和蓝色、共 `n` 个元素的数组 `nums`，
原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。

我们使用整数 `0`、`1` 和 `2` 分别表示红色、白色和蓝色。

必须在不使用库内置的 `sort` 函数的情况下解决这个问题。

## 提示：
    -- n = nums.length
    -- 1 ≤ n ≤ 300
    -- nums[i] 为 0、1 或 2
*/
public class question092 {
    public static void main(String[] args) {
        
    }

    // 三指针
    // 最终结果: -0...01...12...2
    public void sortColors(int[] nums) {
        int n = nums.length;
        int p0 = 0, p2 = n - 1;
        int i = 0;

        while (i <= p2) {
            if (nums[i] == 0) {
                // 将 0 交换到前面
                swap(nums, i, p0);
                p0++;
                i++;
            } else if (nums[i] == 2) {
                // 将 2 交换到后面
                swap(nums, i, p2);
                p2--;
                // 注意：这里不移动 i，因为交换过来的数还需要检查
            } else {
                i++;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
