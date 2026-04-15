package LeetCode_Hot100;

import java.util.Stack;

/*
42. 接雨水

给定 `n` 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

*/
public class question55 {
    public static void main(String[] args) {
        int[] height = new int[]{0,1,0,2,1,0,1,3,2,1,2,1};
        System.out.println(trapMonotonicStack(height));
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

    // 方法3: 单调栈
    /**
     * 1. 维护一个单调递减栈（栈底到栈顶递减），存储柱子的下标
     * 2. 遍历每个柱子：
     *      - 如果当前柱子高度大于栈顶柱子高度，说明形成了凹槽
     *      - 弹出栈顶作为底部，新的栈顶作为左边界，当前柱子作为右边界
     *      - 计算宽度和高度差，累加雨水量
     *      - 重复直到栈空或栈顶高度 >= 当前高度
     *      - 将当前柱子下标入栈
    */
    public static int trapMonotonicStack(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int ans = 0;

        for (int i = 0; i < height.length; i++) {
            // 遇到一根比栈顶更高的柱子时, 说明找到"右墙"
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                // 确定"底座"
                int bottom = stack.pop();
                
                // 只有底座和右墙, 没有左墙, 接不住水
                if (stack.isEmpty()) {
                    break;
                }

                // 确立"左墙"
                int left = stack.peek();

                // 计算水面积
                int width = i - left - 1;
                int heightDiff = Math.min(height[left], height[i]) - height[bottom];
                ans += width * heightDiff;
            }
            stack.push(i);
        }

        return ans;
    }
}
