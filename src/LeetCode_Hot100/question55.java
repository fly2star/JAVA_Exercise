package LeetCode_Hot100;

/*
42. 接雨水

给定 `n` 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

*/
public class question55 {
    public static void main(String[] args) {
        
    }

    // 方法1: 动态规划
    // 思路: 对于第 i 根柱子, 能借多少水, 取决于左右两边最高的柱子种较矮的一根
    //          三次遍历
    public static int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        int n = height.length;
        int res = 0;

        // 创建 DP 数组
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];

        // 填充 leftMax, 从左向右
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i - 1], height[i]);
        }

        // 填充 rightMax, 从右向左
        rightMax[n - 1] = height[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i + 1], height[i]);
        }

        // 计算每一列的水量并相加
        for (int i = 0; i < n; i++) {
            // 木桶原理: 取左右最高柱子的最小值
            int minWall = Math.max(leftMax[i], rightMax[i]);
            // 只有水位高于柱子高度时, 才能接到水
            if (minWall > height[i]) {
                res += (minWall - height[i]);
            }
        }
        return res;
    }

    // 方法2: 双指针
    /*
    1. 初始化左指针 `left = 0`，右指针 `right = n-1`
    2. 维护左边最大值 `left_max` 和右边最大值 `right_max`
    3. 当 `left < right` 时循环：
        - 如果 `height[left] < height[right]`：
            - 如果 `height[left] >= left_max`，更新 `left_max`
            - 否则累加雨水量：`ans += left_max - height[left]`
            - `left++`
        - 否则：
            - 如果 `height[right] >= right_max`，更新 `right_max`
            - 否则累加雨水量：`ans += right_max - height[right]`
            - `right--`
    **/ 
    
    public static int trapTwoPoints(int[] height) {
        int left = 0, right = height.length - 1;
        int lMax = 0, rMax = 0;
        int res = 0;

        while (left < right) {
            // 更新当前的左右最大值
            lMax = Math.max(lMax, height[left]);
            rMax = Math.max(rMax, height[right]);

            // 哪边低, 就结算哪边
            if (height[left] < height[right]) {
                // 左边更低, 水位高度由 lMax 决定
                res += lMax - height[left];
                left++;
            } else {
                // 右边更低, 水位高度由 rMax 决定
                res += rMax - height[right];
                right--;
            }
        }
        return res;
    }
}
