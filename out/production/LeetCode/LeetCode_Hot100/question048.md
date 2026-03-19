# 253. 会议室 II

**难度: 中等**

## 题目描述
给定一个会议时间安排的数组，每个会议时间都会包括开始和结束的时间 `[[s1,e1],[s2,e2],...]` (si < ei)，为避免会议冲突，同时要考虑充分利用会议室资源，请你计算至少需要多少间会议室，才能满足这些会议安排。

---

## 示例说明
### 示例 1：
输入: `[[0, 30],[5, 10],[15, 20]]`  
输出: `2`  
解释: 第一个会议 [0,30] 需要一间会议室，第二个会议 [5,10] 开始时第一个会议还未结束，需要新开一间；第三个会议 [15,20] 开始时第一个会议仍在进行，但第二个会议已结束，所以可以使用第二间会议室，因此最少需要 2 间。

### 示例 2：
输入: `[[7,10],[2,4]]`  
输出: `1`  
解释: 两个会议时间没有重叠，可以共用一间会议室。

---

## 提示：
- 会议个数范围：`[0, 10^4]`
- 会议时间满足：`0 <= si < ei <= 10^6`

---

## 解题思路

### 核心思想
本题的本质是**求同一时刻最多有多少个会议在同时进行**，这个最大并发数就是所需的最少会议室数量。

### 关键观察
- 会议的开始和结束就像一条时间线上的"进入"和"离开"事件
- 每当一个会议开始时，需要占用一间会议室（计数器+1）
- 每当一个会议结束时，释放一间会议室（计数器-1）
- 整个过程中的最大计数器值就是答案

### 算法步骤

#### 方法一：排序 + 最小堆（优先队列）
1. 将所有会议按开始时间升序排序
2. 使用一个最小堆来维护当前正在进行的所有会议的结束时间
3. 遍历每个会议：
   - 如果堆顶的结束时间 <= 当前会议的开始时间，说明有会议室空出，将其弹出
   - 将当前会议的结束时间压入堆中
4. 遍历结束后，堆的大小即为所需的最少会议室数量

#### 方法二：起止时间分别排序（扫描线）
1. 将所有的开始时间和结束时间分别提取出来并排序
2. 使用两个指针分别遍历开始时间和结束时间数组
3. 用 `activeMeeting` 记录当前正在进行的会议数量
4. 当 `start[i] < end[j]` 时，说明新会议开始时最早结束的会议还没结束，`activeMeeting++`，移动开始指针
5. 否则说明有会议结束，`activeMeeting--`，移动结束指针
6. 记录过程中的最大 `activeMeeting` 值

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def minMeetingRooms(self, intervals: List[List[int]]) -> int:
        if not intervals:
            return 0
        
        # 按开始时间排序
        intervals.sort(key=lambda x: x[0])
        
        # 最小堆，存放会议的结束时间
        import heapq
        heap = []
        heapq.heappush(heap, intervals[0][1])
        
        for i in range(1, len(intervals)):
            # 如果当前会议的开始时间 >= 堆顶的结束时间，说明有会议室空出
            if intervals[i][0] >= heap[0]:
                heapq.heappop(heap)
            heapq.heappush(heap, intervals[i][1])
        
        return len(heap)
```

### Java 代码实现
```java
class Solution {
    public int minMeetingRooms(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return 0;
        }
        
        // 按开始时间排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        // 最小堆，存放会议的结束时间
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
```

### C 代码实现
```c
// 排序用的比较函数
int cmp(const void* a, const void* b) {
    int* intervalA = *(int**)a;
    int* intervalB = *(int**)b;
    return intervalA[0] - intervalB[0];
}

int minMeetingRooms(int** intervals, int intervalsSize, int* intervalsColSize) {
    if (intervalsSize == 0) return 0;
    
    // 按开始时间排序
    qsort(intervals, intervalsSize, sizeof(int*), cmp);
    
    // 用数组模拟最小堆（简化版，实际可用优先队列）
    int* endTimes = (int*)malloc(intervalsSize * sizeof(int));
    int heapSize = 0;
    
    // 第一个会议
    endTimes[heapSize++] = intervals[0][1];
    
    for (int i = 1; i < intervalsSize; i++) {
        // 找到最小的结束时间（简化处理，实际需要维护堆序）
        int minEnd = endTimes[0];
        int minIndex = 0;
        for (int j = 1; j < heapSize; j++) {
            if (endTimes[j] < minEnd) {
                minEnd = endTimes[j];
                minIndex = j;
            }
        }
        
        // 如果当前会议开始时间 >= 最早结束时间，复用会议室
        if (intervals[i][0] >= minEnd) {
            // 移除最早结束的会议
            endTimes[minIndex] = endTimes[heapSize - 1];
            heapSize--;
        }
        
        // 添加当前会议的结束时间
        endTimes[heapSize++] = intervals[i][1];
    }
    
    free(endTimes);
    return heapSize;
}
```

---