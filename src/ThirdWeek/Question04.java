package ThirdWeek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-17-19:53
 **/
/*
120. 三角形最小路径和
给定一个三角形 triangle ，找出自顶向下的最小路径和。
每一步只能移动到下一行中相邻的结点上。相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
也就是说，如果正位于当前行的下标 i ，那么下一步可以移动到下一行的下标 i 或 i + 1 。
*/
public class Question04 {
    public static void main(String[] args) {
        List<List<Integer>> triangle = new ArrayList<>();
        // 也可使用 triangle.add(new ArrayList<>(List.of(2)));
        triangle.add(new ArrayList<>(Arrays.asList(2)));
        triangle.add(new ArrayList<>(Arrays.asList(3,4)));
        triangle.add(new ArrayList<>(Arrays.asList(6,5,7)));
        triangle.add(new ArrayList<>(Arrays.asList(4,1,8,3)));


        // 工厂定义不可修改
//        triangle.add(List.of(2));
//        triangle.add(List.of(3,4));
//        triangle.add(List.of(6,5,7));
//        triangle.add(List.of(1,2,3,4));
        System.out.println(minimumTotal(triangle));
        System.out.println(minimumTotal4(triangle));
    }

    public static int minimumTotal(List<List<Integer>> triangle) {
        int n = triangle.size();
        int m = triangle.get(n-1).size();
        Integer[][] dp = new Integer[n][m];
        for (int i = 0; i < n; i++) {
            triangle.get(i).toArray(dp[i]);
        }
        for (int i = n-2; i >= 0; i--) {
            for (int j = 0; j <= i; j++) {
                dp[i][j] = Math.min(dp[i+1][j], dp[i+1][j+1]) + dp[i][j];
            }
        }
        return dp[0][0];

    }
    public static int minimumTotal2(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[][] f = new int[2][n];
        f[0][0] = triangle.get(0).get(0);
        for (int i = 1; i < n; ++i) {
            int curr = i % 2;
            int prev = 1 - curr;
            f[curr][0] = f[prev][0] + triangle.get(i).get(0);
            for (int j = 1; j < i; ++j) {
                f[curr][j] = Math.min(f[prev][j - 1], f[prev][j]) + triangle.get(i).get(j);
            }
            f[curr][i] = f[prev][i - 1] + triangle.get(i).get(i);
        }
        int minTotal = f[(n - 1) % 2][0];
        for (int i = 1; i < n; ++i) {
            minTotal = Math.min(minTotal, f[(n - 1) % 2][i]);
        }
        return minTotal;

    }

    public static int minimumTotal3(List<List<Integer>> triangle) {
        int n = triangle.size();
        int[] f = new int[n];
        f[0] = triangle.get(0).get(0);
        for (int i = 1; i < n; ++i) {
            f[i] = f[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; --j) {
                f[j] = Math.min(f[j - 1], f[j]) + triangle.get(i).get(j);
            }
            f[0] += triangle.get(i).get(0);
        }
        int minTotal = f[0];
        for (int i = 1; i < n; ++i) {
            minTotal = Math.min(minTotal, f[i]);
        }
        return minTotal;

    }

    public static int minimumTotal4(List<List<Integer>> triangle) {
        int n = triangle.size();
        for (int i = n - 2; i >= 0; --i) {
            for (int j = 0; j <=  i; ++j) {
                int min = Math.min(triangle.get(i + 1).get(j), triangle.get(i + 1).get(j + 1));
                triangle.get(i).set(j, triangle.get(i).get(j) + min);
            }
        }
        return triangle.get(0).get(0);
    }

}
