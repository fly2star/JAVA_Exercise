package LeetCode_Hot100;

import java.util.Arrays;
import java.util.PriorityQueue;

/*
253. 会议室 II

给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 `[[s1,e1],[s2,e2],...]` (si < ei)，
为避免会议冲突，同时要考虑充分利用会议室资源，请你计算至少需要多少间会议室，才能满足这些会议安排。

提示:
    -- 会议个数范围: [0, 10^4]
    -- 会议时间满足: 0 <= si < ei <= 10^6
*/

public class question048 {
    public static void main(String[] args) {
        int[][] arr = {{0, 30}, {5, 10}, {15, 20}};
        System.out.println(minMeetingRoom(arr));
    }

    public static int minMeetingRoom(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }

        // 按开始时间排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        // 最小堆, 存放会议的结束时间
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.offer(intervals[0][1]);

        for (int i = 1; i < intervals.length; i++) {
            // 如果当前会议的开始时间 >= 堆顶的结束时间，说明有会议室空出
            if (intervals[i][0] >= heap.peek()) {
                heap.poll();
            }
            heap.offer(intervals[i][1]);
        }

        return heap.size();
    }
}
