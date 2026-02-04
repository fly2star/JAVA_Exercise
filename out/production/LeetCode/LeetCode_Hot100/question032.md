# 416. 分割等和子集

**难度：中等**

## 题目描述

给你一个只包含正整数的非空数组 `nums`。请你判断是否可以将这个数组分割成两个子集，使得两个子集的元素和相等。

---

## 示例说明

**示例 1：**  
输入：`nums = [1,5,11,5]`  
输出：`true`  
解释：数组可以分割成 `[1, 5, 5]` 和 `[11]`，两部分和均为 11。

**示例 2：**  
输入：`nums = [1,2,3,5]`  
输出：`false`  
解释：数组总和为 11，无法分成两个和为 5.5 的子集（必须是整数），因此不能分割。

---

## 解题思路

### 核心思想：
- 若能分割成两个和相等的子集，则总和必须是偶数。
- 每个子集的和应为 `total_sum / 2`。
- 问题转化为：**是否存在一个子集，其和等于 `target = total_sum // 2`？**

这是一个经典的 **0-1 背包问题**：

- 物品：数组中的每个数字
- 背包容量：`target`
- 是否能恰好装满背包？

### 方法：动态规划（DP）

定义 `dp[i]` 表示：是否可以用数组中的某些数凑出和为 `i`。

#### 状态转移：
```text
dp[j] = dp[j] || dp[j - nums[i]]
```

## 代码

### Python
```Python
def canPartition(nums):
    total = sum(nums)
    if total % 2 != 0:
        return False
    
    target = total // 2
    dp = [False] * (target + 1)
    dp[0] = True  # 和为 0 总是可以凑出
    
    for num in nums:
        # 从后往前遍历，避免重复使用同一个数
        for j in range(target, num - 1, -1):
            dp[j] = dp[j] or dp[j - num]
    
    return dp[target]
```

### Java
```Java
public boolean canPartition(int[] nums) {
    int total = 0;
    for (int num : nums) {
        total += num;
    }
    
    if (total % 2 != 0) {
        return false;
    }
    
    int target = total / 2;
    boolean[] dp = new boolean[target + 1];
    dp[0] = true;
    
    for (int num : nums) {
        for (int j = target; j >= num; j--) {
            dp[j] = dp[j] || dp[j - num];
        }
    }
    
    return dp[target];
}
```

### C
```C
#include <stdio.h>
#include <stdbool.h>

bool canPartition(int* nums, int numsSize) {
    int total = 0;
    for (int i = 0; i < numsSize; i++) {
        total += nums[i];
    }
    
    if (total % 2 != 0) {
        return false;
    }
    
    int target = total / 2;
    bool dp[target + 1];
    for (int i = 0; i <= target; i++) {
        dp[i] = false;
    }
    dp[0] = true;
    
    for (int i = 0; i < numsSize; i++) {
        for (int j = target; j >= nums[i]; j--) {
            dp[j] = dp[j] || dp[j - nums[i]];
        }
    }
    
    return dp[target];
}
```