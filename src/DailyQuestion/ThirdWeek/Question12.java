package DailyQuestion.ThirdWeek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-20-15:12
 **/
public class Question12 {
    public static void main(String[] args) {
        int[][] intervals = {{1, 2}, {2, 3}, {2, 4}, {4, 5}};
        System.out.println(intersectionSizeTwo(intervals));
    }

    public static int intersectionSizeTwo(int[][] intervals) {
        int n = intervals.length;
        int res = 0;
        int m = 2;
        Arrays.sort(intervals, (a, b) -> {
            if (a[0] == b[0]) {
                return b[1] - a[1];
            }
            return a[0] - b[0];
        });
        List<Integer>[] temp = new List[n];
        for (int i = 0; i < n; i++) {
            temp[i] = new ArrayList<Integer>();
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = intervals[i][0], k = temp[i].size(); k < m; j++, k++) {
                res++;
                help(intervals, temp, i - 1, j);
            }
        }
        return res;

    }

    public static void help(int[][] intervals, List<Integer>[] temp, int pos, int num) {
        for (int i = pos; i >= 0; i--) {
            if (intervals[i][1] < num) {
                break;
            }
            temp[i].add(num);
        }
    }

    public static int intersectionSizeTwo2(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[1] - b[1]);
        // 栈中保存闭区间左右端点，栈底到栈顶的区间长度的和
        List<int[]> st = new ArrayList<>();
        st.add(new int[]{-2, -2, 0}); // 哨兵，保证不和任何区间相交
        for (int[] t : intervals) {
            int start = t[0], end = t[1];
            int[] e = st.get(lowerBound(st, start) - 1);
            int d = 2 - (st.get(st.size() - 1)[2] - e[2]); // 去掉运行中的时间点
            if (start <= e[1]) { // start 在区间 st[i] 内
                d -= e[1] - start + 1; // 去掉运行中的时间点
            }
            if (d <= 0) {
                continue;
            }
            while (end - st.get(st.size() - 1)[1] <= d) { // 剩余的 d 填充区间后缀
                e = st.remove(st.size() - 1);
                d += e[1] - e[0] + 1; // 合并区间
            }
            st.add(new int[]{end - d + 1, end, st.get(st.size() - 1)[2] + d});
        }
        return st.get(st.size() - 1)[2];

    }

    private static int lowerBound(List<int[]> st, int target) {
        int left = -1, right = st.size(); // 开区间 (left, right)
        while (left + 1 < right) { // 区间不为空
            // 循环不变量：
            // st[left] < target
            // st[right] >= target
            int mid = (left + right) >>> 1;
            if (st.get(mid)[0] < target) {
                left = mid; // 范围缩小到 (mid, right)
            } else {
                right = mid; // 范围缩小到 (left, mid)
            }
        }
        return right;
    }



}
