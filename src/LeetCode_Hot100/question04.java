package LeetCode_Hot100;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-28-15:45
 **/
/*
739. 每日温度
给定一个整数数组 temperatures ，表示每天的温度，返回一个数组 answer ，其中 answer[i] 是指对于第 i 天，下一个更高温度出现在几天后。
如果气温在这之后都不会升高，请在该位置用 0 来代替。
*/
public class question04 {
    public static void main(String[] args) {
        int[] temperatures = {73, 74, 75, 71, 69, 72, 76, 73};
        for (int i = 0; i < temperatures.length; i++) {
            System.out.println(dailyTemperatures(temperatures)[i]);
        }
    }

    public static int[] dailyTemperatures(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (temperatures[j] > temperatures[i]) {
                    ans[i] = j - i;
                    break;
                }
            }
        }
        return ans;
    }

    /**
     * Calculates the number of days until a higher temperature occurs for each day.
     * For each day, finds the next day with a warmer temperature.
     * 
     * @param temperatures An array of integers representing daily temperatures
     * @return An array where each element represents the number of days until a warmer temperature,
     *         or 0 if there is no warmer day in the future
     */
    public static int[] dailyTemperatures2(int[] temperatures) {
        int n = temperatures.length;
        int[] ans = new int[n];
        // Array to store the index of the next occurrence of each temperature (0-100)
        int[] next = new int[101];
        Arrays.fill(next, Integer.MAX_VALUE);
        
        // Traverse the temperatures array from right to left
        for (int i = n - 1; i >= 0; i--) {
            // Find the nearest index with a warmer temperature
            int  warmerIndex = Integer.MAX_VALUE;
            for (int t = temperatures[i] + 1; t <= 100; t++) {
                if (next[t] < warmerIndex) {
                    warmerIndex = next[t];
                }
            }
            // Calculate the distance to the warmer day if it exists
            if (warmerIndex < Integer.MAX_VALUE) {
                ans[i] = warmerIndex - i;
            }
            // Update the index of the current temperature
            next[temperatures[i]] = i;
        }
        return ans;
    }

    // 方法思想: 使用单调栈 ------> 顺序遍历温度数组，找到第一个比自己大的元素
    public static int[] dailyTemperatures3(int[] temperatures) {
        int length = temperatures.length;
        int[] ans = new int[length];
        Deque<Integer> stack = new ArrayDeque<>();  // 用 LinkedList 替代 ArrayDeque, 会更快
        for (int i = 0; i < length; i++) {
            int temperature = temperatures[i];
            // 栈顶元素比当前元素小，则出栈并更新ans
            while (!stack.isEmpty() && temperature > temperatures[stack.peek()]) {
                int prevIndex = stack.pop();
                ans[prevIndex] = i - prevIndex;
            }
            stack.push(i);
        }
        return ans;
    }
}
