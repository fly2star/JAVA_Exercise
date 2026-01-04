package DailyQuestion.SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-13-20:19
 **/
/*
1470. 重新排列数组
给你一个数组 nums ，数组中有 2n 个元素，按 [x1,x2,...,xn,y1,y2,...,yn] 的格式排列。
请你将数组按 [x1,y1,x2,y2,...,xn,yn] 格式重新排列，返回重排后的数组。
* */
public class Question005 {
    public static void main(String[] args) {
        int[] nums = {2,5,1,3,4,7};
        int n = nums.length / 2;
        int[] res = new int[nums.length];
        res = shuffle(nums, n);
        for (int i = 0; i < res.length; i++) {
            System.out.print(res[i] + " ");
        }
    }

    public static int[] shuffle(int[] nums, int n) {
        int[] ans = new int[2*n];
        for (int i = 0; i < n ; i++) {
            ans[2*i] = nums[i];
            ans[2*i+1] = nums[n+i];
        }
        return ans;
    }
}


