# 309. 买卖股票的最佳时机含冷冻期

**难度: 中等**

## 题目描述
给定一个整数数组 prices，其中第 prices[i] 表示第 i 天的股票价格。

设计一个算法计算出最大利润。在满足以下约束条件下，你可以尽可能地完成更多的交易（多次买卖一支股票）：
- 卖出股票后，你无法在第二天买入股票（即冷冻期为 1 天）。
- 你不能同时参与多笔交易（你必须在再次购买前出售掉之前的股票）。

---

## 示例说明
### 示例 1：
**输入：** prices = [1,2,3,0,2]  
**输出：** 3  
**解释：** 对应的交易状态为：[买入，卖出，冷冻期，买入，卖出]
- 第 1 天：买入（价格=1）
- 第 2 天：卖出（价格=2），利润=1
- 第 3 天：冷冻期
- 第 4 天：买入（价格=0）
- 第 5 天：卖出（价格=2），利润=2
- 总利润：1 + 2 = 3

---

### 示例 2：
**输入：** prices = [1]  
**输出：** 0  
**解释：** 只有一天，无法完成交易

---

### 示例 3：
**输入：** prices = [1,2,4]  
**输出：** 3  
**解释：**
- 第 1 天：买入（价格=1）
- 第 3 天：卖出（价格=4），利润=3

---

## 提示：
- 1 ≤ prices.length ≤ 5000
- 0 ≤ prices[i] ≤ 1000

---

## 解题思路

### 核心思想
这是一个状态机动态规划问题。由于有冷冻期的限制，每天结束后可能处于以下三种状态之一：
1. **持有股票** (hold)：当天结束时持有股票（可能今天买入，也可能之前买入一直持有）
2. **不持有股票，且处于冷冻期** (cold)：今天卖出了股票，明天不能买入
3. **不持有股票，且不处于冷冻期** (free)：可以随时买入股票

### 关键观察
1. **持有股票**的状态转移：
   - 可能今天买入：从"不持有且不处于冷冻期"状态转移而来
   - 可能昨天就持有：从昨天的"持有"状态转移而来
2. **不持有且处于冷冻期**的状态转移：
   - 只能是今天卖出了股票：从昨天的"持有"状态转移而来
3. **不持有且不处于冷冻期**的状态转移：
   - 可能昨天就是"不持有且不处于冷冻期"
   - 可能昨天是冷冻期，今天冷冻期结束

### 算法步骤（状态机DP）
1. 定义三个状态：
   - `hold[i]`：第 i 天结束时持有股票的最大利润
   - `cold[i]`：第 i 天结束时处于冷冻期的最大利润
   - `free[i]`：第 i 天结束时不持有股票且不处于冷冻期的最大利润
2. 状态转移方程：
   - `hold[i] = max(hold[i-1], free[i-1] - prices[i])`
   - `cold[i] = hold[i-1] + prices[i]`
   - `free[i] = max(free[i-1], cold[i-1])`
3. 初始化：
   - `hold[0] = -prices[0]`（第一天买入）
   - `cold[0] = 0`（第一天不可能卖出）
   - `free[0] = 0`（第一天不持有）
4. 最终结果：`max(cold[n-1], free[n-1])`（最后一天不能持有股票）

### 空间优化
由于第 i 天的状态只依赖于第 i-1 天的状态，可以使用三个变量而不是数组。

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List

class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        if not prices or len(prices) < 2:
            return 0
        
        n = len(prices)
        
        # 方法1：使用数组存储状态（易于理解）
        hold = [0] * n  # 持有股票
        cold = [0] * n  # 不持有，处于冷冻期
        free = [0] * n  # 不持有，不处于冷冻期
        
        # 初始化
        hold[0] = -prices[0]  # 第一天买入
        cold[0] = 0           # 第一天不可能卖出
        free[0] = 0           # 第一天不持有
        
        for i in range(1, n):
            # 状态转移
            hold[i] = max(hold[i-1], free[i-1] - prices[i])
            cold[i] = hold[i-1] + prices[i]
            free[i] = max(free[i-1], cold[i-1])
        
        # 最后一天不能持有股票
        return max(cold[n-1], free[n-1])
    
    def maxProfit_optimized(self, prices: List[int]) -> int:
        """空间优化版本，只使用三个变量"""
        if not prices or len(prices) < 2:
            return 0
        
        # 初始化状态
        hold = -prices[0]   # 持有股票
        cold = 0            # 处于冷冻期
        free = 0            # 不持有且不处于冷冻期
        
        for i in range(1, len(prices)):
            # 保存前一天的状态
            pre_hold, pre_cold, pre_free = hold, cold, free
            
            # 状态转移
            hold = max(pre_hold, pre_free - prices[i])
            cold = pre_hold + prices[i]
            free = max(pre_free, pre_cold)
        
        return max(cold, free)
```

---

### Java 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        int n = prices.length;
        
        // 方法1：使用数组存储状态
        int[] hold = new int[n];  // 持有股票
        int[] cold = new int[n];  // 不持有，处于冷冻期
        int[] free = new int[n];  // 不持有，不处于冷冻期
        
        // 初始化
        hold[0] = -prices[0];  // 第一天买入
        cold[0] = 0;           // 第一天不可能卖出
        free[0] = 0;           // 第一天不持有
        
        for (int i = 1; i < n; i++) {
            // 状态转移
            hold[i] = Math.max(hold[i-1], free[i-1] - prices[i]);
            cold[i] = hold[i-1] + prices[i];
            free[i] = Math.max(free[i-1], cold[i-1]);
        }
        
        // 最后一天不能持有股票
        return Math.max(cold[n-1], free[n-1]);
    }
    
    public int maxProfitOptimized(int[] prices) {
        // 空间优化版本
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        // 初始化状态
        int hold = -prices[0];   // 持有股票
        int cold = 0;            // 处于冷冻期
        int free = 0;            // 不持有且不处于冷冻期
        
        for (int i = 1; i < prices.length; i++) {
            // 保存前一天的状态
            int preHold = hold;
            int preCold = cold;
            int preFree = free;
            
            // 状态转移
            hold = Math.max(preHold, preFree - prices[i]);
            cold = preHold + prices[i];
            free = Math.max(preFree, preCold);
        }
        
        return Math.max(cold, free);
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>

int maxProfit(int* prices, int pricesSize) {
    if (pricesSize < 2) {
        return 0;
    }
    
    // 方法1：使用数组存储状态
    int* hold = (int*)malloc(pricesSize * sizeof(int));  // 持有股票
    int* cold = (int*)malloc(pricesSize * sizeof(int));  // 不持有，处于冷冻期
    int* free = (int*)malloc(pricesSize * sizeof(int));  // 不持有，不处于冷冻期
    
    // 初始化
    hold[0] = -prices[0];  // 第一天买入
    cold[0] = 0;           // 第一天不可能卖出
    free[0] = 0;           // 第一天不持有
    
    for (int i = 1; i < pricesSize; i++) {
        // 状态转移
        hold[i] = (hold[i-1] > (free[i-1] - prices[i])) ? hold[i-1] : (free[i-1] - prices[i]);
        cold[i] = hold[i-1] + prices[i];
        free[i] = (free[i-1] > cold[i-1]) ? free[i-1] : cold[i-1];
    }
    
    // 最后一天不能持有股票
    int result = (cold[pricesSize-1] > free[pricesSize-1]) ? cold[pricesSize-1] : free[pricesSize-1];
    
    // 释放内存
    free(hold);
    free(cold);
    free(free);
    
    return result;
}

int maxProfitOptimized(int* prices, int pricesSize) {
    // 空间优化版本
    if (pricesSize < 2) {
        return 0;
    }
    
    // 初始化状态
    int hold = -prices[0];   // 持有股票
    int cold = 0;            // 处于冷冻期
    int free_state = 0;      // 不持有且不处于冷冻期（避免与free函数重名）
    
    for (int i = 1; i < pricesSize; i++) {
        // 保存前一天的状态
        int preHold = hold;
        int preCold = cold;
        int preFree = free_state;
        
        // 状态转移
        hold = (preHold > (preFree - prices[i])) ? preHold : (preFree - prices[i]);
        cold = preHold + prices[i];
        free_state = (preFree > preCold) ? preFree : preCold;
    }
    
    return (cold > free_state) ? cold : free_state;
}

// 测试代码
int main() {
    // 测试示例1
    int prices1[] = {1, 2, 3, 0, 2};
    int size1 = sizeof(prices1) / sizeof(prices1[0]);
    int result1 = maxProfit(prices1, size1);
    printf("测试1:\n输入: [1,2,3,0,2]\n输出: %d (期望: 3)\n\n", result1);
    
    // 测试示例2
    int prices2[] = {1};
    int size2 = sizeof(prices2) / sizeof(prices2[0]);
    int result2 = maxProfit(prices2, size2);
    printf("测试2:\n输入: [1]\n输出: %d (期望: 0)\n\n", result2);
    
    // 测试示例3
    int prices3[] = {1, 2, 4};
    int size3 = sizeof(prices3) / sizeof(prices3[0]);
    int result3 = maxProfit(prices3, size3);
    printf("测试3:\n输入: [1,2,4]\n输出: %d (期望: 3)\n\n", result3);
    
    // 测试空间优化版本
    int result1_opt = maxProfitOptimized(prices1, size1);
    printf("空间优化版本测试1: %d (期望: 3)\n", result1_opt);
    
    return 0;
}
```

---

### 复杂度分析
- **时间复杂度：** O(n)，只需要遍历一次价格数组
- **空间复杂度：** 
  - 未优化版本：O(n)，需要三个长度为n的数组
  - 优化版本：O(1)，只需要三个变量

### 状态转移图
```
        买入          卖出
free ---------> hold ---------> cold
   ^                                |
   |                                |
   +------------- 等待 -------------+
   (冷冻期结束变为free)
```

### 算法特点
1. **状态机模型**：清晰定义三种状态，便于理解和实现
2. **动态规划**：当前状态只依赖于前一天的状态
3. **空间优化**：可以优化到O(1)空间复杂度
4. **边界处理**：注意第一天的初始化和最后一天的状态选择