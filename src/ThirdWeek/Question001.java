package ThirdWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-17-11:18
 **/
public class Question001 {
    public static void main(String[] args) {
        int[] nums = {1,0,0,0,1,0,0,1};
        System.out.println(KLengthApart(nums, 2));
        System.out.println(KLengthApart2(nums, 2));

    }

    public static boolean KLengthApart(int[] nums, int k) {
        int n = nums.length;
        int[] post = new int[n+1];
        post[0] = -1;
        int size = 1;
        for (int i = 0; i < n; i++) {
            if (nums[i] == 1) {
                post[size] = i;
                size++;
            }
        }
        for (int i = 2; i < size; i++) {
            if (post[i] - post[i-1] - 1 < k) {
                return false;
            }
        }
        return true;
    }

    public static boolean KLengthApart2(int[] nums, int k) {
        int n = nums.length;
        int prev = -1;
        for (int i = 0; i < n; ++i) {
            if (nums[i] == 1) {
                if (prev != -1 && i - prev - 1 < k) {
                    return false;
                }
                prev = i;
            }
        }
        return true;

    }
}
