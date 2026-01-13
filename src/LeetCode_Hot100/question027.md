# 494. 目标和

**难度：中等**

## 题目描述

给你一个非负整数数组 `nums` 和一个整数 `target`。

向数组中的每个整数前添加 `'+'` 或 `'-'`，然后串联起所有整数，可以构造一个表达式：

- 例如，`nums = [2, 1]`，可以在 `2` 之前添加 `'+'`，在 `1` 之前添加 `'-'`，然后串联起来得到表达式 `"2-1"`。

返回可以通过上述方法构造的、运算结果等于 `target` 的不同 **表达式** 的数目。

---

## 示例说明

**示例 1：**  
输入：`nums = [1, 1, 1, 1, 1]`, `target = 3`  
输出：`5`  
解释：有 5 种方式可以使得表达式结果为 3：
- `+1+1+1+1+1 = 5`
- `-1+1+1+1+1 = 3`
- `+1-1+1+1+1 = 3`
- `+1+1-1+1+1 = 3`
- `+1+1+1-1+1 = 3`

**示例 2：**  
输入：`nums = [0, 0, 0, 0, 0, 0, 0, 0, 1]`, `target = 1`  
输出：`256`  
解释：由于前 8 个数字是 0，它们对结果无影响，因此每种符号组合都有效，共 $2^8 = 256$ 种。

---

## 解题思路

### 核心思想：
这是一个典型的 **递归回溯 + 记忆化搜索** 或 **动态规划** 问题。

我们可以将问题转化为：  
设正数之和为 `P`，负数之和为 `N`，则：
$$
P - N = \text{target} \\
P + N = \text{sum(nums)}
$$
联立得：
$$
P = \frac{\text{sum} + \text{target}}{2}
$$
所以问题转化为：从数组中选出若干数，使其和为 `P`，即 **子集和问题**。

### 转换后的目标：
- 找出有多少种方式选择部分数字（加号），使得它们的和为 `(sum + target) / 2`。
- 如果 `(sum + target)` 为奇数或小于 0，则无解。

---

## 方法一：动态规划（推荐）

### 状态定义：
- `dp[i][j]` 表示从前 `i` 个数中选出一些数，使其和为 `j` 的方案数。

### 状态转移：
$$
dp[i][j] = dp[i-1][j] + dp[i-1][j - nums[i-1]]
$$
- 不选当前数：`dp[i-1][j]`
- 选当前数（加号）：`dp[i-1][j - nums[i-1]]`

### 初始化：
- `dp[0][0] = 1`（空集和为 0，有一种方式）

### 最终答案：
- `dp[n][P]`，其中 `P = (sum + target) / 2`

---

## 复杂度分析

- **时间复杂度**：O(n × sum)，其中 n 是数组长度，sum 是总和。
- **空间复杂度**：O(sum)，使用滚动数组优化。

---

## 参考代码（Python、Java、C）

### Python 实现

```python
def findTargetSumWays(nums, target):
    total_sum = sum(nums)
    if abs(target) > total_sum or (total_sum + target) % 2 != 0:
        return 0
    
    P = (total_sum + target) // 2
    dp = [0] * (P + 1)
    dp[0] = 1  # 和为 0 有一种方式
    
    for num in nums:
        for j in range(P, num - 1, -1):
            dp[j] += dp[j - num]
    
    return dp[P]
```

### Java 实现

```Java
public int findTargetSumWays(int[] nums, int target) {
    int totalSum = Arrays.stream(nums).sum();
    if (Math.abs(target) > totalSum || (totalSum + target) % 2 != 0) {
        return 0;
    }

    int P = (totalSum + target) / 2;
    int[] dp = new int[P + 1];
    dp[0] = 1;

    for (int num : nums) {
        for (int j = P; j >= num; j--) {
            dp[j] += dp[j - num];
        }
    }

    return dp[P];
}
```

### C 实现

```C
#include <stdio.h>
#include <stdlib.h>

int findTargetSumWays(int* nums, int numsSize, int target) {
    int totalSum = 0;
    for (int i = 0; i < numsSize; i++) {
        totalSum += nums[i];
    }
    
    if (abs(target) > totalSum || (totalSum + target) % 2 != 0) {
        return 0;
    }
    
    int P = (totalSum + target) / 2;
    int* dp = (int*)calloc(P + 1, sizeof(int));
    dp[0] = 1;
    
    for (int i = 0; i < numsSize; i++) {
        for (int j = P; j >= nums[i]; j--) {
            dp[j] += dp[j - nums[i]];
        }
    }
    
    int result = dp[P];
    free(dp);
    return result;
}
```