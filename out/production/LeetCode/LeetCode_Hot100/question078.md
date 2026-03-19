# 621. 任务调度器

**难度: 中等**

## 题目描述
给你一个用字符数组 `tasks` 表示的 CPU 需要执行的任务列表，用字母 A 到 Z 表示，以及一个冷却时间 `n`。每个周期或时间间隔允许完成一项任务。任务可以按任何顺序完成，但有一个限制：两个相同种类的任务之间必须有长度为 `n` 的冷却时间。

返回完成所有任务所需要的最短时间间隔。

---

## 示例说明
### 示例 1：
输入：tasks = ["A","A","A","B","B","B"], n = 2  
输出：8  
解释：A -> B -> idle -> A -> B -> idle -> A -> B，需要 8 个时间间隔。

### 示例 2：
输入：tasks = ["A","C","A","B","D","B"], n = 1  
输出：6  
解释：A -> B -> C -> D -> A -> B，需要 6 个时间间隔。

### 示例 3：
输入：tasks = ["A","A","A","B","B","B"], n = 3  
输出：10  
解释：A -> B -> idle -> idle -> A -> B -> idle -> idle -> A -> B，需要 10 个时间间隔。

---

## 提示：
- 1 ≤ tasks.length ≤ 10^4
- tasks[i] 是大写英文字母
- 0 ≤ n ≤ 100

---

## 解题思路

### 核心思想
这是一个任务调度问题，目标是安排任务使得总时间最短。关键在于找出出现次数最多的任务，因为这类任务决定了调度的基本框架。

### 关键观察
- 假设出现次数最多的任务出现了 `maxCount` 次，那么至少需要 `(maxCount - 1) * (n + 1) + 1` 个时间间隔
- 如果有多个任务的出现次数都等于 `maxCount`，那么最后需要额外加上这些任务的个数
- 如果任务种类足够多，可以填满所有冷却时间，那么总时间就是任务总数
- 最终结果取上述两种情况的最大值

### 算法步骤
1. 统计每个任务出现的次数
2. 找出最大出现次数 `maxCount`
3. 统计出现次数等于 `maxCount` 的任务数量 `maxCountTasks`
4. 计算最少所需时间：`(maxCount - 1) * (n + 1) + maxCountTasks`
5. 与任务总数 `len(tasks)` 取最大值，因为如果冷却时间可以被填满，总时间就是任务总数

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def leastInterval(self, tasks: List[str], n: int) -> int:
        # 统计每个任务出现的次数
        task_counts = [0] * 26
        for task in tasks:
            task_counts[ord(task) - ord('A')] += 1
        
        # 找出最大出现次数
        max_count = max(task_counts)
        
        # 统计出现次数等于最大次数的任务数量
        max_count_tasks = task_counts.count(max_count)
        
        # 计算最少所需时间
        result = (max_count - 1) * (n + 1) + max_count_tasks
        
        # 返回结果与任务总数的最大值
        return max(result, len(tasks))
```

### Java 代码实现
```java
class Solution {
    public int leastInterval(char[] tasks, int n) {
        // 统计每个任务出现的次数
        int[] taskCounts = new int[26];
        for (char task : tasks) {
            taskCounts[task - 'A']++;
        }
        
        // 找出最大出现次数
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
        int result = (maxCount - 1) * (n + 1) + maxCountTasks;
        
        // 返回结果与任务总数的最大值
        return Math.max(result, tasks.length);
    }
}
```

### C 代码实现
```c
int leastInterval(char* tasks, int tasksSize, int n) {
    // 统计每个任务出现的次数
    int taskCounts[26] = {0};
    for (int i = 0; i < tasksSize; i++) {
        taskCounts[tasks[i] - 'A']++;
    }
    
    // 找出最大出现次数
    int maxCount = 0;
    for (int i = 0; i < 26; i++) {
        if (taskCounts[i] > maxCount) {
            maxCount = taskCounts[i];
        }
    }
    
    // 统计出现次数等于最大次数的任务数量
    int maxCountTasks = 0;
    for (int i = 0; i < 26; i++) {
        if (taskCounts[i] == maxCount) {
            maxCountTasks++;
        }
    }
    
    // 计算最少所需时间
    int result = (maxCount - 1) * (n + 1) + maxCountTasks;
    
    // 返回结果与任务总数的最大值
    return result > tasksSize ? result : tasksSize;
}
```

---