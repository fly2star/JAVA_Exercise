# 322. 零钱兑换

**难度：中等**

## 题目描述

给你一个整数数组 `coins`，表示不同面额的硬币；以及一个整数 `amount`，表示总金额。

计算并返回可以凑成总金额所需的 **最少的硬币个数**。如果没有任何一种硬币组合能组成总金额，返回 `-1`。

你可以认为每种硬币的数量是无限的。

---

## 示例说明

**示例 1：**  
输入：`coins = [1, 2, 5]`, `amount = 11`  
输出：`3`  
解释：11 = 5 + 5 + 1（共 3 枚硬币）

**示例 2：**  
输入：`coins = [2]`, `amount = 3`  
输出：`-1`  
解释：无法用面值为 2 的硬币凑出 3。

**示例 3：**  
输入：`coins = [1]`, `amount = 0`  
输出：`0`  
解释：目标金额为 0，不需要任何硬币。

---

## 解题思路

这是一个经典的 **动态规划（DP）问题**，类似于“完全背包”问题。

### 核心思想：
- 定义 `dp[i]` 表示凑成金额 `i` 所需的最少硬币数。
- 初始状态：`dp[0] = 0`（凑成 0 需要 0 枚硬币）
- 对于每个金额 `i`，尝试使用每种硬币 `coin`，若 `i >= coin`，则更新：
  $$
  dp[i] = \min(dp[i], dp[i - coin] + 1)
  $$

### 状态转移方程：
$$
dp[i] = \min_{\text{coin} \in \text{coins}, i \geq \text{coin}} (dp[i - \text{coin}] + 1)
$$

---

## 算法步骤

1. 创建长度为 `amount + 1` 的数组 `dp`，初始化为无穷大（或一个较大值），`dp[0] = 0`。
2. 遍历从 1 到 `amount` 的每个金额。
3. 对每个金额，遍历所有硬币面额，若当前金额大于等于该硬币面额，则尝试更新 `dp[i]`。
4. 最终返回 `dp[amount]`，若仍为无穷大则返回 `-1`。

---

## 复杂度分析

- **时间复杂度**：O(amount × coins.length)，双重循环。
- **空间复杂度**：O(amount)，用于存储 DP 数组。

---

## 参考代码（Python、Java、C）

### Python 实现

```Python
def coinChange(coins, amount):
    if amount == 0:
        return 0
    
    dp = [float('inf')] * (amount + 1)
    dp[0] = 0
    
    for i in range(1, amount + 1):
        for coin in coins:
            if i >= coin:
                dp[i] = min(dp[i], dp[i - coin] + 1)
    
    return dp[amount] if dp[amount] != float('inf') else -1
```

### JAVA 实现
```Java
public int coinChange(int[] coins, int amount) {
    if (amount == 0) return 0;
    
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    
    return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
}
```

### C 实现
```C
public int coinChange(int[] coins, int amount) {
    if (amount == 0) return 0;
    
    int[] dp = new int[amount + 1];
    Arrays.fill(dp, Integer.MAX_VALUE);
    dp[0] = 0;
    
    for (int i = 1; i <= amount; i++) {
        for (int coin : coins) {
            if (i >= coin && dp[i - coin] != Integer.MAX_VALUE) {
                dp[i] = Math.min(dp[i], dp[i - coin] + 1);
            }
        }
    }
    
    return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
}
```