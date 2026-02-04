# 239. 滑动窗口最大值

**难度: 困难**

## 题目描述
给你一个整数数组 `nums`，有一个大小为 `k` 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 `k` 个数字。滑动窗口每次只向右移动一位。

返回滑动窗口中的最大值。

---

## 示例说明
### 示例 1：
**输入：** nums = [1,3,-1,-3,5,3,6,7], k = 3  
**输出：** [3,3,5,5,6,7]  

**解释：**
```
窗口位置                        最大值
---------                       -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
```

---

### 示例 2：
**输入：** nums = [1], k = 1  
**输出：** [1]  

**解释：** 只有一个元素，窗口大小也是1，最大值就是该元素

---

### 示例 3：
**输入：** nums = [1,-1], k = 1  
**输出：** [1,-1]  

**解释：**
```
窗口位置       最大值
[1] -1          1
 1 [-1]        -1
```

---

### 示例 4：
**输入：** nums = [9,11], k = 2  
**输出：** [11]  

**解释：** 只有一个窗口 [9,11]，最大值是11

---

## 提示：
- 1 ≤ nums.length ≤ 10^5
- -10^4 ≤ nums[i] ≤ 10^4
- 1 ≤ k ≤ nums.length

---

## 解题思路

### 核心思想
使用单调递减队列（双端队列）来维护当前窗口中的最大值候选。队列中存储的是元素的索引，按照对应的元素值从大到小排列。

### 关键观察
1. 当滑动窗口向右移动时：
   - 窗口左侧会移除一个元素
   - 窗口右侧会添加一个元素
2. 需要高效地获取当前窗口的最大值
3. 如果新加入的元素比队列中的某些元素大，那么这些较小的元素永远不可能成为当前窗口的最大值，可以移除

### 算法步骤（单调队列）
1. 初始化一个双端队列 `deque` 用于存储元素索引
2. 初始化结果数组 `result`，长度为 `n - k + 1`
3. 遍历数组：
   - **移除队首过期元素**：如果队首索引小于当前窗口左边界 `i - k`，则移除
   - **维护队列单调性**：从队尾开始，移除所有小于当前元素 `nums[i]` 的元素的索引
   - **加入当前元素**：将当前索引 `i` 加入队尾
   - **记录结果**：当 `i ≥ k-1` 时，队首元素就是当前窗口的最大值
4. 返回结果数组

### 算法复杂度
- **时间复杂度：** O(n)，每个元素最多入队和出队一次
- **空间复杂度：** O(k)，队列最多存储 k 个元素

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List
from collections import deque

class Solution:
    def maxSlidingWindow(self, nums: List[int], k: int) -> List[int]:
        """单调队列方法"""
        if not nums or k == 0:
            return []
        
        n = len(nums)
        if k == 1:
            return nums
        
        # 使用双端队列，存储索引
        deq = deque()
        result = []
        
        for i in range(n):
            # 1. 移除队首过期元素（索引小于窗口左边界）
            if deq and deq[0] < i - k + 1:
                deq.popleft()
            
            # 2. 维护队列单调递减：从队尾移除小于当前元素的索引
            while deq and nums[deq[-1]] < nums[i]:
                deq.pop()
            
            # 3. 加入当前元素索引
            deq.append(i)
            
            # 4. 当窗口形成时，记录结果
            if i >= k - 1:
                result.append(nums[deq[0]])
        
        return result
    
    def maxSlidingWindow_bruteforce(self, nums: List[int], k: int) -> List[int]:
        """暴力解法（会超时，仅用于理解）"""
        if not nums or k == 0:
            return []
        
        n = len(nums)
        result = []
        
        for i in range(n - k + 1):
            # 对每个窗口求最大值
            max_val = float('-inf')
            for j in range(i, i + k):
                if nums[j] > max_val:
                    max_val = nums[j]
            result.append(max_val)
        
        return result
```

---

### Java 代码实现

```java
import java.util.*;

class Solution {
    // 单调队列方法
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }
        
        int n = nums.length;
        if (k == 1) {
            return nums;
        }
        
        // 使用双端队列存储索引
        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[n - k + 1];
        int resultIndex = 0;
        
        for (int i = 0; i < n; i++) {
            // 1. 移除队首过期元素（索引小于窗口左边界）
            if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) {
                deque.pollFirst();
            }
            
            // 2. 维护队列单调递减：从队尾移除小于当前元素的索引
            while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) {
                deque.pollLast();
            }
            
            // 3. 加入当前元素索引
            deque.offerLast(i);
            
            // 4. 当窗口形成时，记录结果
            if (i >= k - 1) {
                result[resultIndex++] = nums[deque.peekFirst()];
            }
        }
        
        return result;
    }
    
    // 使用优先队列（堆）的方法 - 时间复杂度 O(n log k)
    public int[] maxSlidingWindowHeap(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k == 0) {
            return new int[0];
        }
        
        int n = nums.length;
        int[] result = new int[n - k + 1];
        
        // 最大堆，存储值和索引
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
            (a, b) -> b[0] - a[0]  // 按值从大到小排序
        );
        
        for (int i = 0; i < n; i++) {
            // 添加当前元素
            maxHeap.offer(new int[]{nums[i], i});
            
            // 当窗口形成时
            if (i >= k - 1) {
                // 移除堆顶过期元素（索引小于窗口左边界）
                while (maxHeap.peek()[1] < i - k + 1) {
                    maxHeap.poll();
                }
                
                // 堆顶元素就是当前窗口的最大值
                result[i - k + 1] = maxHeap.peek()[0];
            }
        }
        
        return result;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* maxSlidingWindow(int* nums, int numsSize, int k, int* returnSize) {
    if (numsSize == 0 || k == 0) {
        *returnSize = 0;
        return NULL;
    }
    
    if (k == 1) {
        *returnSize = numsSize;
        int* result = (int*)malloc(numsSize * sizeof(int));
        for (int i = 0; i < numsSize; i++) {
            result[i] = nums[i];
        }
        return result;
    }
    
    // 结果数组长度
    *returnSize = numsSize - k + 1;
    int* result = (int*)malloc((*returnSize) * sizeof(int));
    
    // 使用数组模拟双端队列，存储索引
    int* deque = (int*)malloc(numsSize * sizeof(int));
    int front = 0, rear = 0;  // 队首和队尾指针
    
    for (int i = 0; i < numsSize; i++) {
        // 1. 移除队首过期元素（索引小于窗口左边界）
        if (front < rear && deque[front] < i - k + 1) {
            front++;
        }
        
        // 2. 维护队列单调递减：从队尾移除小于当前元素的索引
        while (front < rear && nums[deque[rear - 1]] < nums[i]) {
            rear--;
        }
        
        // 3. 加入当前元素索引
        deque[rear] = i;
        rear++;
        
        // 4. 当窗口形成时，记录结果
        if (i >= k - 1) {
            result[i - k + 1] = nums[deque[front]];
        }
    }
    
    free(deque);
    return result;
}

// 暴力解法（仅用于理解，会超时）
int* maxSlidingWindowBruteForce(int* nums, int numsSize, int k, int* returnSize) {
    if (numsSize == 0 || k == 0) {
        *returnSize = 0;
        return NULL;
    }
    
    *returnSize = numsSize - k + 1;
    int* result = (int*)malloc((*returnSize) * sizeof(int));
    
    for (int i = 0; i < *returnSize; i++) {
        int maxVal = INT_MIN;
        for (int j = i; j < i + k; j++) {
            if (nums[j] > maxVal) {
                maxVal = nums[j];
            }
        }
        result[i] = maxVal;
    }
    
    return result;
}

// 打印数组
void printArray(int* arr, int size) {
    printf("[");
    for (int i = 0; i < size; i++) {
        printf("%d", arr[i]);
        if (i < size - 1) {
            printf(", ");
        }
    }
    printf("]");
}

// 测试代码
int main() {
    // 测试示例1
    int nums1[] = {1, 3, -1, -3, 5, 3, 6, 7};
    int k1 = 3;
    int returnSize1;
    int* result1 = maxSlidingWindow(nums1, 8, k1, &returnSize1);
    
    printf("测试1:\n");
    printf("输入: nums = [1,3,-1,-3,5,3,6,7], k = %d\n", k1);
    printf("输出: ");
    printArray(result1, returnSize1);
    printf(" (期望: [3,3,5,5,6,7])\n\n");
    free(result1);
    
    // 测试示例2
    int nums2[] = {1};
    int k2 = 1;
    int returnSize2;
    int* result2 = maxSlidingWindow(nums2, 1, k2, &returnSize2);
    
    printf("测试2:\n");
    printf("输入: nums = [1], k = %d\n", k2);
    printf("输出: ");
    printArray(result2, returnSize2);
    printf(" (期望: [1])\n\n");
    free(result2);
    
    // 测试示例3
    int nums3[] = {1, -1};
    int k3 = 1;
    int returnSize3;
    int* result3 = maxSlidingWindow(nums3, 2, k3, &returnSize3);
    
    printf("测试3:\n");
    printf("输入: nums = [1,-1], k = %d\n", k3);
    printf("输出: ");
    printArray(result3, returnSize3);
    printf(" (期望: [1,-1])\n\n");
    free(result3);
    
    // 测试示例4
    int nums4[] = {9, 11};
    int k4 = 2;
    int returnSize4;
    int* result4 = maxSlidingWindow(nums4, 2, k4, &returnSize4);
    
    printf("测试4:\n");
    printf("输入: nums = [9,11], k = %d\n", k4);
    printf("输出: ");
    printArray(result4, returnSize4);
    printf(" (期望: [11])\n\n");
    free(result4);
    
    // 测试递减序列
    int nums5[] = {7, 6, 5, 4, 3, 2, 1};
    int k5 = 3;
    int returnSize5;
    int* result5 = maxSlidingWindow(nums5, 7, k5, &returnSize5);
    
    printf("测试5 (递减序列):\n");
    printf("输入: nums = [7,6,5,4,3,2,1], k = %d\n", k5);
    printf("输出: ");
    printArray(result5, returnSize5);
    printf(" (期望: [7,6,5,4,3])\n\n");
    free(result5);
    
    // 测试递增序列
    int nums6[] = {1, 2, 3, 4, 5, 6, 7};
    int k6 = 3;
    int returnSize6;
    int* result6 = maxSlidingWindow(nums6, 7, k6, &returnSize6);
    
    printf("测试6 (递增序列):\n");
    printf("输入: nums = [1,2,3,4,5,6,7], k = %d\n", k6);
    printf("输出: ");
    printArray(result6, returnSize6);
    printf(" (期望: [3,4,5,6,7])\n");
    free(result6);
    
    return 0;
}
```

---

### 复杂度分析

| 方法 | 时间复杂度 | 空间复杂度 | 特点 |
|------|-----------|-----------|------|
| 单调队列 | O(n) | O(k) | 最优解，每个元素处理一次 |
| 最大堆 | O(n log k) | O(k) | 实现简单，但效率较低 |
| 暴力法 | O(nk) | O(1) | 会超时，仅用于理解 |

### 算法详解

#### 1. 单调队列方法（最优）
- **队列性质**：存储元素索引，按照对应的元素值从大到小排列
- **关键操作**：
  1. **移除过期元素**：当队首索引不在当前窗口内时，移除
  2. **维护单调性**：新元素入队前，移除所有小于它的元素
  3. **获取最大值**：队首元素就是当前窗口的最大值
- **为什么有效**：
  - 队列中的元素是递减的，所以队首是最大值
  - 当新元素较大时，移除所有小于它的元素，因为它们不可能成为最大值
  - 队列中存储索引，可以方便地判断元素是否过期

#### 2. 最大堆方法
- **使用优先队列（堆）** 存储（值，索引）对
- **获取最大值**：堆顶元素是最大值
- **移除过期元素**：检查堆顶元素的索引是否在窗口内
- **缺点**：需要频繁调整堆，时间复杂度较高

#### 3. 暴力法
- 对每个窗口，遍历所有元素找最大值
- 简单直观，但效率太低，会超时
