package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
279. 完全平方数

给你一个整数 `n`，返回和为 `n` 的完全平方数的最少数量。

**完全平方数** 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。
*/
public class question047 {
    public static void main(String[] args) {
        
    }

    // 方法1: 动态规划
    public static int numSquares(int n) {
        // 生成所有用于填充背包的数
        List<Integer> squares = new ArrayList<>();
        for (int i = 0; i * i < n; i++) {
            squares.add(i * i);
        }

        //动态规划
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        for (int square : squares) {
            for (int i = square; i <= n; i++) {
                if (dp[i - square] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - square] + 1);
                }
            }
        }
        return dp[n];
    }

    // 方法2: BFS
    public static int numSquaresBFS(int n) {
        // 生成所有小于等于n的完全平方数
        List<Integer> squares = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            squares.add(i * i);
        }
        
        // BFS初始化
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        queue.offer(0);
        visited[0] = true;
        int level = 0;
        
        // BFS搜索
        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int current = queue.poll();
                
                for (int square : squares) {
                    int next = current + square;
                    
                    if (next == n) {
                        return level;
                    }
                    
                    if (next < n && !visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
        }
        
        return level;
    }

    // 方法3: 数学方法
    public static int numSquaresMath(int n) {
        // 检查是否是完全平方数
        if (isSquare(n)) {
            return 1;
        }
        
        // 检查是否能表示为两个完全平方数的和
        for (int i = 1; i * i <= n; i++) {
            if (isSquare(n - i * i)) {
                return 2;
            }
        }
        
        // 检查是否能表示为4个完全平方数的和（四平方定理）
        // 如果n可以表示为4^k*(8m+7)形式，则需要4个
        int temp = n;
        while (temp % 4 == 0) {
            temp /= 4;
        }
        if (temp % 8 == 7) {
            return 4;
        }
        
        return 3;
    }

    private static boolean isSquare(int x) {
        int root = (int) Math.sqrt(x);
        return root * root == x;
    }

}
