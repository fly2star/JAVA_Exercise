package DailyQuestion.Eleventh;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-23-11:19
 **/
public class Question4 {
    public static void main(String[] args) {

    }


    // 方法一: 借鉴 3507 , 方法超时
    public static int f3510(int[] nums) {
        int operations = 0;
        while (true) {
            // 检查是否非递减
            boolean nonDecreasing = true;
            for (int i = 0; i < nums.length - 1; i++) {
                if (nums[i] > nums[i + 1]) {
                    nonDecreasing = false;
                    break;
                }
            }
            if (nonDecreasing) {
                return operations;
            }

            // 找到和最小的相邻对
            int minSum = Integer.MAX_VALUE;
            int minIdx = -1;
            for (int i = 0; i < nums.length - 1; i++) {
                int sum = nums[i] + nums[i + 1];
                if (sum < minSum) {
                    minSum = sum;
                    minIdx = i;
                }
            }

            // 替换并移除下一个元素
            nums[minIdx] = minSum;
            System.arraycopy(nums, minIdx + 2, nums, minIdx + 1, nums.length - minIdx - 2);
            nums = java.util.Arrays.copyOf(nums, nums.length - 1);
            operations++;
        }
    }
}
