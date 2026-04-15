package DailyQuestion.Sixteenth;

/*
189. 轮转数组

给定一个整数数组 `nums`，将数组中的元素向右旋转 `k` 个位置，其中 `k` 是非负数。
*/
public class question01 {
    
    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5,6,7};

        question01 sl01 = new question01();
        sl01.rotate(nums, 3);
        for (int i : nums) {
            System.out.print(i + " ");
        }
    }

    // 方法1: 多次反转
    // 反转整个数组, 反转前 k 个元素, 反转后 n-k 个元素
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        if (k == 0) {
            return;
        }

        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }

    public void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
