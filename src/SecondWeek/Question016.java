package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-14-18:35
 **/
/*
740. 删除并获得点数
给你一个整数数组 nums ，你可以对它进行一些操作。
每次操作中，选择任意一个 nums[i] ，删除它并获得 nums[i] 的点数。之后，你必须删除 所有 等于 nums[i] - 1 和 nums[i] + 1 的元素。
开始你拥有 0 个点数。返回你能通过这些操作获得的最大点数。
*/
public class Question016 {
    public static void main(String[] args) {
        int[] nums = {3, 4, 2};
        System.out.println(deleteAndEarn(nums));
    }

    public static int deleteAndEarn(int[] nums) {
        int maxVal = 0;
        for (int val : nums) {
            maxVal = Math.max(maxVal, val);
        }
        int[] count = new int[maxVal + 1];
        int[] dp = new int[maxVal + 1];
        for (int num : nums) {
            count[num]++;
        }
        dp[0] = 0;
        dp[1] = count[1];
        for (int i = 2; i <= maxVal; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + i * count[i]);
        }
        return dp[maxVal];
    }

    public static int deleteAndEarn2(int[] nums) {
        //边界
        if ( nums == null || nums.length == 0){
            return 0;
        }
        //找max
        int maxx=0;
        for (int num : nums){
            if (num>maxx){maxx=num;}
        }

        int[] dp=new int[maxx+1];
        for (int num:nums){
            dp[num]+=num;
        }

        int prev2=0;
        int prev1=dp[0];
        for (int i=1;i<=maxx;i++){
            int a=Math.max(prev1,prev2+dp[i]);
            prev2=prev1;
            prev1=a;
        }

        return prev1;
    }
}
