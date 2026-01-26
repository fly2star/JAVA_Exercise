# 1200. 最小绝对差

**难度：简单**

## 题目描述

给你一个整数数组 `arr`，其中每个元素都**不相同**。

请你找到所有具有**最小绝对差**的元素对，并且按升序的顺序返回。

每对元素对 `[a, b]` 满足以下条件：

- `a` 和 `b` 均为数组 `arr` 中的元素；
- `a < b`；
- `b - a` 等于 `arr` 中任意两个元素的**最小绝对差**。

---

## 示例说明

**示例 1：**  
输入：`arr = [4, 2, 1, 3]`  
输出：`[[1,2], [2,3], [3,4]]`  
解释：
- 所有相邻差值：`|4-2|=2`, `|2-1|=1`, `|1-3|=2`
- 最小绝对差为 `1`
- 满足差为 1 的对：`(1,2)`、`(2,3)`
- 但注意：`[3,4]`？`3` 和 `4` 在原数组中吗？`arr = [4,2,1,3]` → 有 `1,2,3,4`，所以 `3` 和 `4` 存在。
- 实际上：`|4-3|=1`，所以 `(3,4)` 也满足。
- 排序后：`[1,2], [2,3], [3,4]`

✅ 正确。

**示例 2：**  
输入：`arr = [1, 3, 6]`  
输出：`[[1,3]]`  
解释：
- 差值：`|1-3|=2`, `|3-6|=3`, `|1-6|=5` → 最小是 2
- 只有 `(1,3)` 满足差为 2

**示例 3：**  
输入：`arr = [3, 8, -10, 23, 19, -4, -14, 27]`  
输出：`[[-14,-10], [19,23], [23,27]]`  
解释：
- 排序后数组：`[-14, -10, 3, 8, 19, 23, 27]`
- 相邻差：`4, 13, 5, 11, 4, 4` → 最小是 4
- 差为 4 的对：`(-14,-10), (19,23), (23,27)`

---

## 解题思路

### 核心思想：
- **最小绝对差一定出现在排序后的相邻元素之间**。
- 因此，我们可以先对数组排序，然后遍历相邻元素，找出最小差值。
- 再次遍历，收集所有差值等于最小值的对。

### 步骤：
1. 对数组排序。
2. 遍历相邻元素，计算差值，记录最小差值。
3. 再次遍历，将所有差值等于最小值的对加入结果。
4. 返回结果。

---

## 复杂度分析

- **时间复杂度**：O(n log n)，主要由排序决定。
- **空间复杂度**：O(1)（不考虑结果数组），若考虑结果则为 O(k)，k 是满足条件的对数。

---

## 参考代码（Python、Java、C）

### Python 实现
```python
def minimumAbsDifference(arr):
    arr.sort()
    min_diff = float('inf')
    
    # 找最小差值
    for i in range(len(arr) - 1):
        diff = arr[i+1] - arr[i]
        min_diff = min(min_diff, diff)
    
    # 收集所有差值等于最小值的对
    result = []
    for i in range(len(arr) - 1):
        if arr[i+1] - arr[i] == min_diff:
            result.append([arr[i], arr[i+1]])
    
    return result
```

### Java 实现
```Java
import java.util.*;

public List<List<Integer>> minimumAbsDifference(int[] arr) {
    Arrays.sort(arr);
    int minDiff = Integer.MAX_VALUE;

    // 找最小差值
    for (int i = 0; i < arr.length - 1; i++) {
        int diff = arr[i+1] - arr[i];
        minDiff = Math.min(minDiff, diff);
    }

    // 收集结果
    List<List<Integer>> result = new ArrayList<>();
    for (int i = 0; i < arr.length - 1; i++) {
        if (arr[i+1] - arr[i] == minDiff) {
            List<Integer> pair = Arrays.asList(arr[i], arr[i+1]);
            result.add(pair);
        }
    }

    return result;
}
```

### C 实现
```C
#include <stdio.h>
#include <stdlib.h>

// 快速排序比较函数
int compare(const void* a, const void* b) {
    return (*(int*)a - *(int*)b);
}

int** minimumAbsDifference(int* arr, int arrSize, int* returnSize, int** returnColumnSizes) {
    qsort(arr, arrSize, sizeof(int), compare);
    
    int minDiff = INT_MAX;
    for (int i = 0; i < arrSize - 1; i++) {
        int diff = arr[i+1] - arr[i];
        if (diff < minDiff) {
            minDiff = diff;
        }
    }
    
    // 统计有多少对
    int count = 0;
    for (int i = 0; i < arrSize - 1; i++) {
        if (arr[i+1] - arr[i] == minDiff) {
            count++;
        }
    }
    
    // 分配结果数组
    int** result = (int**)malloc(count * sizeof(int*));
    *returnSize = count;
    *returnColumnSizes = (int*)malloc(count * sizeof(int));
    
    for (int i = 0; i < count; i++) {
        result[i] = (int*)malloc(2 * sizeof(int));
        (*returnColumnSizes)[i] = 2;
    }
    
    // 填充结果
    int idx = 0;
    for (int i = 0; i < arrSize - 1; i++) {
        if (arr[i+1] - arr[i] == minDiff) {
            result[idx][0] = arr[i];
            result[idx][1] = arr[i+1];
            idx++;
        }
    }
    
    return result;
}
```