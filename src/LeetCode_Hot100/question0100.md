# 53. 最大子数组和

**难度: 中等**

## 题目描述
给你一个整数数组 `nums`，请你找出一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。

子数组是数组中的一个连续部分。

---

## 示例说明
### 示例 1：
输入：nums = [-2,1,-3,4,-1,2,1,-5,4]  
输出：6  
解释：连续子数组 [4,-1,2,1] 的和最大，为 6。

### 示例 2：
输入：nums = [1]  
输出：1

### 示例 3：
输入：nums = [5,4,-1,7,8]  
输出：23

---

## 提示：
- 1 ≤ nums.length ≤ 10^5
- -10^4 ≤ nums[i] ≤ 10^4

---

## 解题思路

### 核心思想
使用**动态规划**（Kadane算法）来求解最大子数组和。定义 `dp[i]` 表示以第 i 个元素结尾的连续子数组的最大和。状态转移方程为：`dp[i] = max(nums[i], dp[i-1] + nums[i])`。

### 关键观察
- 如果之前的子数组和为负数，那么加上当前元素只会使和更小，不如从当前元素重新开始
- 否则，将当前元素加到之前的子数组上
- 只需要维护一个变量记录当前最大和，不需要整个 dp 数组

### 算法步骤
1. 初始化 `currentSum = nums[0]`，`maxSum = nums[0]`
2. 从 i = 1 遍历到 n-1：
   - `currentSum = max(nums[i], currentSum + nums[i])`
   - `maxSum = max(maxSum, currentSum)`
3. 返回 `maxSum`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def maxSubArray(self, nums: List[int]) -> int:
        current_sum = max_sum = nums[0]
        
        for i in range(1, len(nums)):
            # 如果之前的和为负，就从当前元素重新开始；否则累加
            current_sum = max(nums[i], current_sum + nums[i])
            # 更新最大和
            max_sum = max(max_sum, current_sum)
        
        return max_sum
```

### Java 代码实现
```java
class Solution {
    public int maxSubArray(int[] nums) {
        int currentSum = nums[0];
        int maxSum = nums[0];
        
        for (int i = 1; i < nums.length; i++) {
            // 如果之前的和为负，就从当前元素重新开始；否则累加
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            // 更新最大和
            maxSum = Math.max(maxSum, currentSum);
        }
        
        return maxSum;
    }
}
```

### C 代码实现
```c
int maxSubArray(int* nums, int numsSize) {
    int currentSum = nums[0];
    int maxSum = nums[0];
    
    for (int i = 1; i < numsSize; i++) {
        // 如果之前的和为负，就从当前元素重新开始；否则累加
        if (currentSum + nums[i] > nums[i]) {
            currentSum = currentSum + nums[i];
        } else {
            currentSum = nums[i];
        }
        
        // 更新最大和
        if (currentSum > maxSum) {
            maxSum = currentSum;
        }
    }
    
    return maxSum;
}
```

---