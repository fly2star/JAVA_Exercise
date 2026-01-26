# 448. 找到所有数组中消失的数字

**难度：简单**

## 题目描述

给你一个含 `n` 个整数的数组 `nums`，其中 `nums[i]` 在区间 `[1, n]` 内。

请你找出所有在 `[1, n]` 范围内但没有出现在 `nums` 中的数字，并以数组的形式返回结果。

---

## 示例说明

**示例 1：**  
输入：`nums = [4,3,2,7,8,2,3,1]`  
输出：`[5,6]`  
解释：
- 数组长度为 8，所以范围是 `[1,8]`
- 出现的数字：1,2,3,4,7,8 → 缺少的是 5 和 6

**示例 2：**  
输入：`nums = [1,1]`  
输出：`[2]`  
解释：
- 长度为 2，范围是 `[1,2]`
- 出现了 1（重复），缺少 2

**示例 3：**  
输入：`nums = [1,2,3]`  
输出：`[]`  
解释：所有数字都在，无缺失。

---

## 解题思路

### 核心思想：
- 数组长度为 `n`，值域为 `[1, n]`，因此最多有 `n` 个不同的数字。
- 我们需要找出 `[1, n]` 中哪些数字未出现。

### 方法一：使用集合（Set）

- 将 `nums` 转为集合，然后遍历 `1` 到 `n`，检查是否在集合中。

### 方法二：原地修改（推荐）

- 利用数组索引作为标记：将 `nums[i]` 对应的下标位置的值变为负数，表示该数字已存在。
- 最后遍历数组，正数对应的索引即为缺失的数字。

> ✅ 原地修改法空间复杂度 O(1)，适合面试场景。

---

## 算法步骤（原地修改）

1. 遍历数组，对每个值 `num`，将其对应下标 `abs(num) - 1` 的元素取负（若为正）。
2. 再次遍历数组，若 `nums[i] > 0`，则 `i+1` 是缺失的数字。
3. 返回结果。

---

## 复杂度分析

| 方法 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| 集合 | O(n) | O(n) |
| 原地修改 | O(n) | O(1) |

---

## 参考代码（Python、Java、C）

### Python 实现
```python
def findDisappearedNumbers(nums):
    # 原地修改法
    for num in nums:
        idx = abs(num) - 1
        if nums[idx] > 0:
            nums[idx] = -nums[idx]
    
    # 收集正数对应的索引
    result = []
    for i in range(len(nums)):
        if nums[i] > 0:
            result.append(i + 1)
    return result
```

### Java 实现
```Java
public List<Integer> findDisappearedNumbers(int[] nums) {
    List<Integer> result = new ArrayList<>();
    
    // 原地修改
    for (int num : nums) {
        int idx = Math.abs(num) - 1;
        if (nums[idx] > 0) {
            nums[idx] = -nums[idx];
        }
    }
    
    // 收集正数索引
    for (int i = 0; i < nums.length; i++) {
        if (nums[i] > 0) {
            result.add(i + 1);
        }
    }
    
    return result;
}
```

### C 实现
```C
#include <stdio.h>
#include <stdlib.h>

int* findDisappearedNumbers(int* nums, int numsSize, int* returnSize) {
    // 原地修改
    for (int i = 0; i < numsSize; i++) {
        int idx = abs(nums[i]) - 1;
        if (nums[idx] > 0) {
            nums[idx] = -nums[idx];
        }
    }
    
    // 统计缺失数字
    int count = 0;
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] > 0) {
            count++;
        }
    }
    
    // 分配结果数组
    int* result = (int*)malloc(count * sizeof(int));
    *returnSize = count;
    
    int idx = 0;
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] > 0) {
            result[idx++] = i + 1;
        }
    }
    
    return result;
}
```