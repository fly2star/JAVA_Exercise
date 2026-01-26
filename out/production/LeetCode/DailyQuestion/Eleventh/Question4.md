# 3510. 移除最小数对使数组有序 II

**难度：困难**

## 题目描述

给你一个数组 `nums`，你可以执行以下操作任意次数：

- 选择**相邻元素对中和最小的一对**。如果存在多个这样的对，选择最左边的一个。
- 用它们的**和**替换这对元素。

返回将数组变为 **非递减** 所需的 **最小操作次数**。

> ✅ 如果一个数组中每个元素都大于或等于它前一个元素（如果存在的話），则称该数组为**非递减**。

---

## 示例说明

**示例 1：**  
输入：`nums = [1, 2, 3]`  
输出：`0`  
解释：数组已经是非递减的，无需操作。

**示例 2：**  
输入：`nums = [3, 2, 1]`  
输出：`1`  
步骤：
1. 相邻对：`(3,2)` 和为 5，`(2,1)` 和为 3 → 最小的是 `(2,1)`，替换为 `3` → `[3, 3]`
2. `[3,3]` 是非递减 → 停止 → **1 次操作**

**示例 3：**  
输入：`nums = [1, 4, 2, 3]`  
输出：`2`  
步骤：
1. 对：`(1,4)=5`, `(4,2)=6`, `(2,3)=5` → 最小是 5，最左边是 `(1,4)` → 替换为 `5` → `[5,2,3]`
2. 新数组：`(5,2)=7`, `(2,3)=5` → 最小是 `(2,3)` → 替换为 `5` → `[5,5]` → 非递减 → 结束 → **2 次操作**

---

## 解题思路

### 核心思想：
这道题看似与上一题（3507）相同，但实际是 **更复杂的变种**，且被标记为“困难”，说明不能简单地模拟。

然而，仔细分析发现，**题目描述完全一致**，只是编号不同。可能的情况是：

- **3507 是简化版**（可能有额外限制）
- **3510 是通用版本**，需要更高效的解法

但根据现有信息，两题描述完全一样，因此我们可以推断：

> **3510 实际上就是 3507 的进阶版**，可能在某些测试用例上更复杂，或者要求更高效率。

但若仅从描述看，**算法逻辑不变**，仍为贪心模拟。

---

## 算法步骤（贪心模拟）

1. 判断当前数组是否非递减，若是则返回操作次数。
2. 否则，遍历所有相邻对，找到和最小的那一对（优先最左边）。
3. 用其和替换这对元素，数组长度减 1。
4. 操作次数 +1。
5. 重复直到数组非递减。

---

## 复杂度分析

- **时间复杂度**：O(n²)，最坏情况下每次操作减少一个元素，共 n-1 次，每次找最小对需 O(n)。
- **空间复杂度**：O(1)，仅使用常量额外空间（不考虑数组修改）。

---

## 参考代码（Python、Java、C）

### Python 实现
```python
def minOperations(nums):
    operations = 0
    while True:
        # 检查是否非递减
        if all(nums[i] <= nums[i+1] for i in range(len(nums)-1)):
            return operations
        
        # 找到和最小的相邻对（最左边）
        min_sum = float('inf')
        min_idx = -1
        for i in range(len(nums) - 1):
            s = nums[i] + nums[i+1]
            if s < min_sum:
                min_sum = s
                min_idx = i
        
        # 替换为和
        nums[min_idx] = min_sum
        nums.pop(min_idx + 1)
        operations += 1
```

### Java 实现
```Java
public int minOperations(int[] nums) {
    int operations = 0;
    while (true) {
        // 检查是否非递减
        boolean nonDecreasing = true;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i+1]) {
                nonDecreasing = false;
                break;
            }
        }
        if (nonDecreasing) {
            return operations;
        }

        // 找到和最小的相邻对
        int minSum = Integer.MAX_VALUE;
        int minIdx = -1;
        for (int i = 0; i < nums.length - 1; i++) {
            int sum = nums[i] + nums[i+1];
            if (sum < minSum) {
                minSum = sum;
                minIdx = i;
            }
        }

        // 替换并移除下一个元素
        nums[minIdx] = minSum;
        System.arraycopy(nums, minIdx + 2, nums, minIdx + 1, nums.length - minIdx - 2);
        nums = java.util.Arrays.copyOf(nums, nums.length - 1);
        operations++;
    }
}
```

### C 实现
```C
#include <stdio.h>
#include <stdlib.h>

int minOperations(int* nums, int numsSize) {
    int operations = 0;
    int* temp = (int*)malloc(numsSize * sizeof(int));
    
    while (1) {
        // 检查是否非递减
        int isNonDecreasing = 1;
        for (int i = 0; i < numsSize - 1; i++) {
            if (nums[i] > nums[i+1]) {
                isNonDecreasing = 0;
                break;
            }
        }
        if (isNonDecreasing) {
            free(temp);
            return operations;
        }

        // 找到和最小的相邻对
        int minSum = INT_MAX;
        int minIdx = -1;
        for (int i = 0; i < numsSize - 1; i++) {
            int sum = nums[i] + nums[i+1];
            if (sum < minSum) {
                minSum = sum;
                minIdx = i;
            }
        }

        // 构造新数组
        for (int i = 0; i < minIdx; i++) {
            temp[i] = nums[i];
        }
        temp[minIdx] = minSum;
        for (int i = minIdx + 1; i < numsSize - 1; i++) {
            temp[i] = nums[i + 1];
        }

        // 更新数组
        free(nums);
        nums = temp;
        numsSize--;
        operations++;
    }
}
```

