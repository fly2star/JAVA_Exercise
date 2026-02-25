package LeetCode_Hot100;

/*
283. 移动零

给定一个数组 `nums`，编写一个函数将所有 `0` 移动到数组的末尾，同时保持非零元素的相对顺序。

请注意，必须在不复制数组的情况下原地对数组进行操作。
*/
public class question0046 {
    public static void main(String[] args) {
        
    }

    // 方法1: 一次遍历, 交换元素
    public static void moveZeros(int[] nums) {
        // 指向下一个非零元素应该放置的位置
        int left = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // 交换 nums[left] 和 nums[i]
                int temp = nums[left];
                nums[left] = nums[i];
                nums[i] = temp;
                left++;
            }
        }
    }

    // 方法2: 两遍扫描元素
    public static void moveZerosTwoPass(int[] nums) {
        // 第一次遍历, 移动非零元素
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[left] = nums[i];
                left++;
            }
        }

        // 第二次遍历, 填充 0
        for (int i = left; i < nums.length; i++) {
            nums[i] = 0;
        }

    }

    // 方法3: 优化的一次遍历, 避免不必要的交换
    public static void moveZerosOptimized(int[] nums) {
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != left) {
                    nums[left] = nums[i];
                    nums[i] = 0;
                }
                left++;
            }
        }
    }

    // 方法4: 使用快慢指针
    public static void moveZerosTwoPoints(int[] nums) {
        int slow = 0;   // 慢指针: 指向当前处理的位置
        int fast = 0;   // 快指针: 遍历数组

        while (fast < nums.length) {
            if (nums[fast] != 0) {
                if (slow != fast) {
                    nums[slow] = nums[fast];
                    nums[fast] = 0;
                }
                slow++;
            }
            fast++;
        }


    }
}
