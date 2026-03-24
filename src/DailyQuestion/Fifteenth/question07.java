package DailyQuestion.Fifteenth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

public class question07 {
    public static void main(String[] args) {
        int[][] grid = new int[][]{{1, -2, 3}, {2, 3, 5}};

        question07 sl07 = new question07();
        int[][] res = sl07.minAbsDiff2(grid, 2);
        for (int[] arr : res) {
            for (int i : arr) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }

    // 方法1: 滑动窗口
    public int[][] minAbsDiff(int[][] grid, int k) {
        
        int m = grid.length;
        int n = grid[0].length;

        int rows = m - k + 1;
        int cols = n - k + 1;

        int[][] ans = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 收集子矩阵的所有元素
                List<Integer> values = new ArrayList<>();
                for (int x = i; x < i + k; x++) {
                    for (int y = j; y < j + k; y++) {
                        values.add(grid[x][y]);
                    }
                }

                // 排序计算最小据对值差
                Collections.sort(values);
                int minDiff = Integer.MAX_VALUE;
                for (int idx = 1; idx < values.size(); idx++) {
                    int diff = Math.abs(values.get(idx) - values.get(idx - 1));
                    minDiff = Math.min(minDiff, diff);
                }

                // 如果所有元素相同，minDiff 仍为 MAX_VALUE，设为 0
                ans[i][j] = (minDiff == Integer.MAX_VALUE) ? 0 : minDiff;
            }
        }

        return ans;
    }

    // 方法2
    public int[][] minAbsDiff2(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        // 结果矩阵的大小
        int[][] res = new int[m - k + 1][n - k + 1];

        // 遍历所有可能的左上角起点 (i, j)
        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {
                res[i][j] = getMinDiff(grid, i, j, k);
            }
        }
        return res;
    }

    private int getMinDiff(int[][] grid, int r, int c, int k) {
        // 收集当前 k*k 窗口内的所有不同元素
        TreeSet<Integer> set = new TreeSet<>();
        for (int i = r; i < r + k; i++) {
            for (int j = c; j < c + k; j++) {
                set.add(grid[i][j]);
            }
        }

        // 如果元素种类少于 2，说明要么都一样，要么没法比，按题意返回 0
        if (set.size() < 2) return 0;

        // 利用 TreeSet 的有序性，寻找相邻元素间的最小差值
        int minDiff = Integer.MAX_VALUE;
        Integer prev = null;
        for (int val : set) {
            if (prev != null) {
                minDiff = Math.min(minDiff, val - prev);
            }
            prev = val;
            // 剪枝：如果已经发现差值为 1（最小正整数差），可以直接返回
            if (minDiff == 1) return 1;
        }

        return minDiff;
    }
}
