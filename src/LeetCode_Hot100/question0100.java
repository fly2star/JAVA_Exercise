package LeetCode_Hot100;

/*
53. 最大子数组和

给你一个整数数组 `nums`，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

子数组是数组中的一个连续部分。

## 提示：
    -- 1 ≤ nums.length ≤ 10^5
    -- -10^4 ≤ nums[i] ≤ 10^4
*/
public class question0100 {
    public static void main(String[] args) {
        int[] nums = new int[]{-2,1,-3,4,-1,2,1,-5,4};

        question0100 sl100 = new question0100();
        System.out.println(sl100.maxSubArray(nums));
    }

    // 方法1: 动态规划
    public int maxSubArray(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int currentPrefixSum = 0;
        // minPrefixSum 初始化为 0，用来处理从索引 0 开始的子数组
        int minPrefixSum = 0;

        for (int x : nums) {
            // 累加当前前缀和
            currentPrefixSum += x;
            
            // 当前前缀和减去之前出现过的最小前缀和，即为当前最优解
            maxSum = Math.max(maxSum, currentPrefixSum - minPrefixSum);
            
            // 更新最小前缀和，为下一步做准备
            minPrefixSum = Math.min(minPrefixSum, currentPrefixSum);
        }
        
        return maxSum;
    }

    // 方法2: 前缀和 + 最小偏移量
    public int maxSubArray2(int[] nums) {
        int maxSum = Integer.MIN_VALUE;
        int currentPrefixSum = 0;
        // minPrefixSum 初始化为 0，用来处理从索引 0 开始的子数组
        int minPrefixSum = 0;

        for (int x : nums) {
            // 累加当前前缀和
            currentPrefixSum += x;
            
            // 当前前缀和减去之前出现过的最小前缀和，即为当前最优解
            maxSum = Math.max(maxSum, currentPrefixSum - minPrefixSum);
            
            // 更新最小前缀和，为下一步做准备
            minPrefixSum = Math.min(minPrefixSum, currentPrefixSum);
        }
        
        return maxSum;
    }

    // 方法3: 分治法
    public int maxSubArray3(int[] nums) {
    return helper(nums, 0, nums.length - 1);
    }

    private int helper(int[] nums, int left, int right) {
        if (left == right) return nums[left];

        int mid = left + (right - left) / 2;

        // 递归求左半部分最大和
        int leftSum = helper(nums, left, mid);
        // 递归求右半部分最大和
        int rightSum = helper(nums, mid + 1, right);
        // 求跨越中间点的最大和
        int crossSum = crossSum(nums, left, right, mid);

        // 返回三者中的最大值
        return Math.max(Math.max(leftSum, rightSum), crossSum);
    }

    private int crossSum(int[] nums, int left, int right, int mid) {
        // 从中间往左扫描，找最大和
        int leftPart = Integer.MIN_VALUE;
        int sum = 0;
        for (int i = mid; i >= left; i--) {
            sum += nums[i];
            leftPart = Math.max(leftPart, sum);
        }

        // 从中间往右扫描，找最大和
        int rightPart = Integer.MIN_VALUE;
        sum = 0;
        for (int i = mid + 1; i <= right; i++) {
            sum += nums[i];
            rightPart = Math.max(rightPart, sum);
        }

        return leftPart + rightPart;
    }


}
