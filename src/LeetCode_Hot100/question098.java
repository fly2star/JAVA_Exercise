package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
56. 合并区间

以数组 `intervals` 表示若干个区间的集合，其中单个区间为 `intervals[i] = [start_i, end_i]`。
请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。

## 提示：
    -- 1 ≤ intervals.length ≤ 10^4
    -- intervals[i].length == 2
    -- 0 ≤ start_i < end_i ≤ 10^4
*/
public class question098 {
    public static void main(String[] args) {
        int[][] intervals = new int[][]{{1, 3}, {2, 6}, {8, 10}, {15, 18}};

        question098 sl98 = new question098();
        int[][] result = sl98.merge(intervals);
        for (int[] arr : result) {
            System.out.println(arr[0] + " " + arr[1]);
        }
    }

    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][];
        }

        // 按起始位置排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        List<int[]> res = new ArrayList<>();
        res.add(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            int[] curr = intervals[i];
            int[] last = res.get(res.size() - 1);

            // 如果当前区间与最后一个区间重叠
            if (curr[0] <= last[1]) {
                // 合并，更新结束位置
                last[1] = Math.max(last[1], curr[1]);
            } else {
                // 不重叠，直接添加
                res.add(curr);
            }
        }
        return res.toArray(new int[res.size()][]);
    }
    
}
