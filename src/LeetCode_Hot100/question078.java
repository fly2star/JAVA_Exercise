package LeetCode_Hot100;

/*
621. 任务调度器

给你一个用字符数组 `tasks` 表示的 CPU 需要执行的任务列表，
用字母 A 到 Z 表示，以及一个冷却时间 `n`。
每个周期或时间间隔允许完成一项任务。
任务可以按任何顺序完成，但有一个限制:
    两个相同种类的任务之间必须有长度为 `n` 的冷却时间。

返回完成所有任务所需要的最短时间间隔。

## 提示：
    -- 1 ≤ tasks.length ≤ 10^4
    -- tasks[i] 是大写英文字母
    -- 0 ≤ n ≤ 100
*/
public class question078 {
    public static void main(String[] args) {
        
    }

    // 方法1: 贪心算法
    public int leastInterval(char[] tasks, int n) {
        // 统计每个任务出现的次数
        int[] taskCounts = new int[26];
        for (char task : tasks) {
            taskCounts[task - 'A']++;
        }

        // 找到最大出现次数
        // 出现次数最多的任务通过 n 将任务流水划分为不同的桶 
        int maxCount = 0;
        for (int count : taskCounts) {
            maxCount = Math.max(maxCount, count);
        }

        // 统计出现次数等于最大次数的任务数量
        int maxCountTasks = 0;
        for (int count : taskCounts) {
            if (count == maxCount) {
                maxCountTasks++;
            }
        }

        // 计算最少所需时间
        /**
         * 数学推导:
         *  1. 寻找最高频率: 设任务中出现次数最多的次数为 max_freq;
         *  2. 计算有多少"大头": 设出现次数同样为 max_freq 的任务种类有 count_max 个;
         *  3. 构造框架:
         *      - 前 `max_freq - 1` 组任务, 每组必须占据 n+1 个位置
         *      - 最后一组任务, 占据 count_max 个位置
         *  公式如下:
         * $$
         * TotalTime = (max_freq-1)*(n+1) + count_max
         * $$
         */
        /*
        个人理解:
            前 `max_freq - 1` 组任务, 可能需要安排 idel 以符合时间间隔要求,
            所以乘 `n+1`(n+1 的安排刚好创造 n 个时间间隔);
            最后一组不需要安排 idel 以确保时间间隔, 所以直接加上任务数即可. 
        */
        int res = (maxCount - 1) * (n + 1) + maxCountTasks;

        // 返回结果与任务总数的最大值
        return Math.max(res, tasks.length);
    }
}
