# 121. 买卖股票的最佳时机

**难度: 简单**

## 题目描述
给定一个数组 `prices`，它的第 `i` 个元素 `prices[i]` 表示一支给定股票第 `i` 天的价格。

你只能选择 **某一天** 买入这只股票，并选择在 **未来的某一个不同的日子** 卖出该股票。设计一个算法来计算你能获取的最大利润。

返回你可以从这笔交易中获取的最大利润。如果你不能获取任何利润，返回 `0`。

---

## 示例说明
### 示例 1：
**输入：** prices = [7,1,5,3,6,4]  
**输出：** 5  
**解释：**
- 在第 2 天（股票价格 = 1）的时候买入
- 在第 5 天（股票价格 = 6）的时候卖出
- 最大利润 = 6 - 1 = 5
- 注意：不能在第 1 天买入，第 2 天卖出，因为卖出价格需要大于买入价格

---

### 示例 2：
**输入：** prices = [7,6,4,3,1]  
**输出：** 0  
**解释：**
- 在这种情况下，价格一直在下跌
- 没有交易可以完成，所以最大利润为 0

---

### 示例 3：
**输入：** prices = [2,4,1]  
**输出：** 2  
**解释：**
- 在第 1 天（股票价格 = 2）买入
- 在第 2 天（股票价格 = 4）卖出
- 最大利润 = 4 - 2 = 2

---

## 提示：
- 1 ≤ prices.length ≤ 10^5
- 0 ≤ prices[i] ≤ 10^4

---

## 解题思路

### 核心思想
一次遍历数组，在遍历过程中记录历史最低价格，然后计算当前价格与历史最低价格的差值，更新最大利润。

### 关键观察
1. 只能在买入之后卖出，所以买入日期必须早于卖出日期
2. 最大利润 = 卖出价格 - 买入价格
3. 为了最大化利润，需要在最低点买入，在最高点卖出（但必须确保卖出在买入之后）
4. 实际上，我们只需要知道：对于每个位置，如果在这一天卖出，那么应该在它之前的最低点买入

### 算法步骤
1. 初始化：
   - `min_price` = 第一天价格（或一个很大的数）
   - `max_profit` = 0
2. 遍历价格数组：
   - 更新历史最低价格：`min_price = min(min_price, prices[i])`
   - 计算当前利润：`profit = prices[i] - min_price`
   - 更新最大利润：`max_profit = max(max_profit, profit)`
3. 返回最大利润

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List

class Solution:
    def maxProfit(self, prices: List[int]) -> int:
        if not prices or len(prices) < 2:
            return 0
        
        min_price = prices[0]  # 历史最低价格
        max_profit = 0         # 最大利润
        
        for price in prices:
            # 更新历史最低价格
            if price < min_price:
                min_price = price
            
            # 计算当前利润并更新最大利润
            profit = price - min_price
            if profit > max_profit:
                max_profit = profit
        
        return max_profit
    
    # 方法二：更简洁的写法
    def maxProfit2(self, prices: List[int]) -> int:
        if not prices:
            return 0
        
        min_price = float('inf')  # 初始化为无穷大
        max_profit = 0
        
        for price in prices:
            min_price = min(min_price, price)
            max_profit = max(max_profit, price - min_price)
        
        return max_profit
```

---

### Java 代码实现

```java
class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }
        
        int minPrice = prices[0];  // 历史最低价格
        int maxProfit = 0;         // 最大利润
        
        for (int i = 1; i < prices.length; i++) {
            // 更新历史最低价格
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            }
            
            // 计算当前利润并更新最大利润
            int profit = prices[i] - minPrice;
            if (profit > maxProfit) {
                maxProfit = profit;
            }
        }
        
        return maxProfit;
    }
    
    // 方法二：更简洁的写法
    public int maxProfit2(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        
        int minPrice = Integer.MAX_VALUE;  // 初始化为最大值
        int maxProfit = 0;
        
        for (int price : prices) {
            minPrice = Math.min(minPrice, price);
            maxProfit = Math.max(maxProfit, price - minPrice);
        }
        
        return maxProfit;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <limits.h>

int maxProfit(int* prices, int pricesSize) {
    if (pricesSize < 2) {
        return 0;
    }
    
    int minPrice = prices[0];  // 历史最低价格
    int maxProfit = 0;         // 最大利润
    
    for (int i = 1; i < pricesSize; i++) {
        // 更新历史最低价格
        if (prices[i] < minPrice) {
            minPrice = prices[i];
        }
        
        // 计算当前利润并更新最大利润
        int profit = prices[i] - minPrice;
        if (profit > maxProfit) {
            maxProfit = profit;
        }
    }
    
    return maxProfit;
}

// 方法二：使用最大值初始化
int maxProfit2(int* prices, int pricesSize) {
    if (pricesSize == 0) {
        return 0;
    }
    
    int minPrice = INT_MAX;  // 初始化为最大值
    int maxProfit = 0;
    
    for (int i = 0; i < pricesSize; i++) {
        // 更新历史最低价格
        if (prices[i] < minPrice) {
            minPrice = prices[i];
        }
        
        // 计算当前利润并更新最大利润
        int profit = prices[i] - minPrice;
        if (profit > maxProfit) {
            maxProfit = profit;
        }
    }
    
    return maxProfit;
}

// 测试代码
int main() {
    // 测试示例1
    int prices1[] = {7, 1, 5, 3, 6, 4};
    int size1 = sizeof(prices1) / sizeof(prices1[0]);
    int result1 = maxProfit(prices1, size1);
    printf("测试1:\n输入: [7,1,5,3,6,4]\n输出: %d (期望: 5)\n\n", result1);
    
    // 测试示例2
    int prices2[] = {7, 6, 4, 3, 1};
    int size2 = sizeof(prices2) / sizeof(prices2[0]);
    int result2 = maxProfit(prices2, size2);
    printf("测试2:\n输入: [7,6,4,3,1]\n输出: %d (期望: 0)\n\n", result2);
    
    // 测试示例3
    int prices3[] = {2, 4, 1};
    int size3 = sizeof(prices3) / sizeof(prices3[0]);
    int result3 = maxProfit(prices3, size3);
    printf("测试3:\n输入: [2,4,1]\n输出: %d (期望: 2)\n\n", result3);
    
    // 测试示例4：价格持续上涨
    int prices4[] = {1, 2, 3, 4, 5};
    int size4 = sizeof(prices4) / sizeof(prices4[0]);
    int result4 = maxProfit(prices4, size4);
    printf("测试4:\n输入: [1,2,3,4,5]\n输出: %d (期望: 4)\n\n", result4);
    
    // 测试示例5：只有一个元素
    int prices5[] = {1};
    int size5 = sizeof(prices5) / sizeof(prices5[0]);
    int result5 = maxProfit(prices5, size5);
    printf("测试5:\n输入: [1]\n输出: %d (期望: 0)\n", result5);
    
    return 0;
}
```

---

### 复杂度分析
- **时间复杂度：** O(n)，只需要一次遍历数组
- **空间复杂度：** O(1)，只使用了常数级别的额外空间

### 算法特点
1. **一次遍历**：只需要遍历一次数组，效率高
2. **记录历史最低点**：在遍历过程中记录到当前位置为止的最低价格
3. **实时更新最大利润**：对于每个位置，计算如果在这一天卖出的利润，并与历史最大利润比较
4. **处理边界情况**：如果数组长度小于2，直接返回0