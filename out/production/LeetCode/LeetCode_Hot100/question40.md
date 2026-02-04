# 312. 戳气球

**难度: 困难**

## 题目描述
有 `n` 个气球，编号为 `0` 到 `n-1`，每个气球上都标有一个数字，这些数字存在数组 `nums` 中。

现在要求你戳破所有的气球。戳破第 `i` 个气球，你可以获得 `nums[i-1] * nums[i] * nums[i+1]` 枚硬币。这里的 `i-1` 和 `i+1` 代表和 `i` 相邻的两个气球的序号。如果 `i-1` 或 `i+1` 超出了数组的边界，那么就当它是一个数字为 `1` 的气球。

求所能获得硬币的最大数量。

---

## 示例说明
### 示例 1：
**输入：** nums = [3,1,5,8]  
**输出：** 167  
**解释：**
1. 戳破气球 1 (nums[1] = 1)：获得 3×1×5 = 15 枚硬币，数组变为 [3,5,8]
2. 戳破气球 1 (nums[1] = 5)：获得 3×5×8 = 120 枚硬币，数组变为 [3,8]
3. 戳破气球 0 (nums[0] = 3)：获得 1×3×8 = 24 枚硬币，数组变为 [8]
4. 戳破气球 0 (nums[0] = 8)：获得 1×8×1 = 8 枚硬币，数组变为 []
5. 总硬币数：15 + 120 + 24 + 8 = 167

---

### 示例 2：
**输入：** nums = [1,5]  
**输出：** 10  
**解释：**
1. 戳破气球 0 (nums[0] = 1)：获得 1×1×5 = 5 枚硬币，数组变为 [5]
2. 戳破气球 0 (nums[0] = 5)：获得 1×5×1 = 5 枚硬币，数组变为 []
3. 总硬币数：5 + 5 = 10

---

### 示例 3：
**输入：** nums = [7]  
**输出：** 7  
**解释：**
1. 戳破气球 0 (nums[0] = 7)：获得 1×7×1 = 7 枚硬币
2. 总硬币数：7

---

## 提示：
- n = nums.length
- 1 ≤ n ≤ 300
- 0 ≤ nums[i] ≤ 100

---

## 解题思路

### 核心思想
这是一个经典的区间动态规划问题。关键在于逆向思考：不是考虑先戳哪个气球，而是考虑最后戳哪个气球。

### 关键观察
1. 如果考虑最后戳破气球 `k`，那么戳破它时，它的左右两边已经没有其他气球了，所以此时获得的硬币是：`nums[left] * nums[k] * nums[right]`
2. 在戳破 `k` 之前，它左右两侧的气球是相互独立的，可以分别计算
3. 定义 `dp[i][j]` 表示戳破区间 `(i, j)` 内所有气球能获得的最大硬币数（注意：这里 `i` 和 `j` 是开区间，表示边界不被戳破）
4. 状态转移方程：`dp[i][j] = max(dp[i][j], dp[i][k] + dp[k][j] + nums[i] * nums[k] * nums[j])`，其中 `k` 是最后戳破的气球

### 算法步骤
1. 在原始数组两端添加值为 1 的虚拟气球，方便处理边界
2. 定义 `dp[i][j]` 表示戳破区间 `(i, j)` 内所有气球能获得的最大硬币数
3. 按区间长度从小到大进行动态规划：
   - 外层循环：区间长度 `len` 从 2 到 n+1
   - 中层循环：区间起点 `i` 从 0 到 n+1-len
   - 内层循环：最后戳破的气球 `k` 从 i+1 到 j-1
4. 状态转移：`dp[i][j] = max(dp[i][j], dp[i][k] + dp[k][j] + nums[i] * nums[k] * nums[j])`
5. 返回 `dp[0][n+1]`，即整个区间的最大硬币数

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List

class Solution:
    def maxCoins(self, nums: List[int]) -> int:
        n = len(nums)
        # 在数组两端添加值为1的虚拟气球
        new_nums = [1] + nums + [1]
        m = n + 2
        
        # dp[i][j] 表示戳破区间(i,j)内所有气球能获得的最大硬币数
        dp = [[0] * m for _ in range(m)]
        
        # 按区间长度从小到大计算
        for length in range(2, m):  # 区间长度至少为2
            for i in range(m - length):  # 区间起点
                j = i + length  # 区间终点
                
                # 枚举最后戳破的气球k
                for k in range(i + 1, j):
                    # 状态转移
                    dp[i][j] = max(dp[i][j], 
                                   dp[i][k] + dp[k][j] + new_nums[i] * new_nums[k] * new_nums[j])
        
        return dp[0][m-1]
    
    def maxCoins_memo(self, nums: List[int]) -> int:
        """记忆化搜索版本"""
        n = len(nums)
        # 在数组两端添加值为1的虚拟气球
        new_nums = [1] + nums + [1]
        
        from functools import lru_cache
        
        @lru_cache(None)
        def dfs(left, right):
            """返回戳破区间(left, right)内所有气球能获得的最大硬币数"""
            if left + 1 == right:  # 区间内没有气球
                return 0
            
            res = 0
            # 枚举最后戳破的气球
            for k in range(left + 1, right):
                # 最后戳破气球k的收益
                coins = new_nums[left] * new_nums[k] * new_nums[right]
                # 左右两边的收益
                coins += dfs(left, k) + dfs(k, right)
                res = max(res, coins)
            
            return res
        
        return dfs(0, n + 1)
```

---

### Java 代码实现

```java
class Solution {
    public int maxCoins(int[] nums) {
        int n = nums.length;
        // 在数组两端添加值为1的虚拟气球
        int[] newNums = new int[n + 2];
        newNums[0] = 1;
        newNums[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            newNums[i + 1] = nums[i];
        }
        
        int m = n + 2;
        // dp[i][j] 表示戳破区间(i,j)内所有气球能获得的最大硬币数
        int[][] dp = new int[m][m];
        
        // 按区间长度从小到大计算
        for (int length = 2; length < m; length++) {  // 区间长度至少为2
            for (int i = 0; i < m - length; i++) {    // 区间起点
                int j = i + length;                   // 区间终点
                
                // 枚举最后戳破的气球k
                for (int k = i + 1; k < j; k++) {
                    // 状态转移
                    dp[i][j] = Math.max(dp[i][j],
                            dp[i][k] + dp[k][j] + newNums[i] * newNums[k] * newNums[j]);
                }
            }
        }
        
        return dp[0][m - 1];
    }
    
    // 记忆化搜索版本
    public int maxCoinsMemo(int[] nums) {
        int n = nums.length;
        // 在数组两端添加值为1的虚拟气球
        int[] newNums = new int[n + 2];
        newNums[0] = 1;
        newNums[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            newNums[i + 1] = nums[i];
        }
        
        int[][] memo = new int[n + 2][n + 2];
        return dfs(newNums, memo, 0, n + 1);
    }
    
    private int dfs(int[] nums, int[][] memo, int left, int right) {
        if (left + 1 == right) {  // 区间内没有气球
            return 0;
        }
        
        if (memo[left][right] > 0) {
            return memo[left][right];
        }
        
        int res = 0;
        // 枚举最后戳破的气球
        for (int k = left + 1; k < right; k++) {
            // 最后戳破气球k的收益
            int coins = nums[left] * nums[k] * nums[right];
            // 左右两边的收益
            coins += dfs(nums, memo, left, k) + dfs(nums, memo, k, right);
            res = Math.max(res, coins);
        }
        
        memo[left][right] = res;
        return res;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int maxCoins(int* nums, int numsSize) {
    // 在数组两端添加值为1的虚拟气球
    int n = numsSize;
    int* newNums = (int*)malloc((n + 2) * sizeof(int));
    newNums[0] = 1;
    newNums[n + 1] = 1;
    for (int i = 0; i < n; i++) {
        newNums[i + 1] = nums[i];
    }
    
    int m = n + 2;
    // 创建动态规划表
    int** dp = (int**)malloc(m * sizeof(int*));
    for (int i = 0; i < m; i++) {
        dp[i] = (int*)calloc(m, sizeof(int));
    }
    
    // 按区间长度从小到大计算
    for (int length = 2; length < m; length++) {  // 区间长度至少为2
        for (int i = 0; i < m - length; i++) {    // 区间起点
            int j = i + length;                   // 区间终点
            
            // 枚举最后戳破的气球k
            for (int k = i + 1; k < j; k++) {
                // 状态转移
                int value = dp[i][k] + dp[k][j] + newNums[i] * newNums[k] * newNums[j];
                if (value > dp[i][j]) {
                    dp[i][j] = value;
                }
            }
        }
    }
    
    int result = dp[0][m - 1];
    
    // 释放内存
    free(newNums);
    for (int i = 0; i < m; i++) {
        free(dp[i]);
    }
    free(dp);
    
    return result;
}

// 记忆化搜索版本
int dfs(int* nums, int** memo, int left, int right) {
    if (left + 1 == right) {  // 区间内没有气球
        return 0;
    }
    
    if (memo[left][right] > 0) {
        return memo[left][right];
    }
    
    int res = 0;
    // 枚举最后戳破的气球
    for (int k = left + 1; k < right; k++) {
        // 最后戳破气球k的收益
        int coins = nums[left] * nums[k] * nums[right];
        // 左右两边的收益
        coins += dfs(nums, memo, left, k) + dfs(nums, memo, k, right);
        if (coins > res) {
            res = coins;
        }
    }
    
    memo[left][right] = res;
    return res;
}

int maxCoinsMemo(int* nums, int numsSize) {
    // 在数组两端添加值为1的虚拟气球
    int n = numsSize;
    int* newNums = (int*)malloc((n + 2) * sizeof(int));
    newNums[0] = 1;
    newNums[n + 1] = 1;
    for (int i = 0; i < n; i++) {
        newNums[i + 1] = nums[i];
    }
    
    // 创建记忆化数组
    int** memo = (int**)malloc((n + 2) * sizeof(int*));
    for (int i = 0; i < n + 2; i++) {
        memo[i] = (int*)calloc(n + 2, sizeof(int));
    }
    
    int result = dfs(newNums, memo, 0, n + 1);
    
    // 释放内存
    free(newNums);
    for (int i = 0; i < n + 2; i++) {
        free(memo[i]);
    }
    free(memo);
    
    return result;
}

// 测试代码
int main() {
    // 测试示例1
    int nums1[] = {3, 1, 5, 8};
    int size1 = sizeof(nums1) / sizeof(nums1[0]);
    int result1 = maxCoins(nums1, size1);
    printf("测试1:\n输入: [3,1,5,8]\n输出: %d (期望: 167)\n\n", result1);
    
    // 测试示例2
    int nums2[] = {1, 5};
    int size2 = sizeof(nums2) / sizeof(nums2[0]);
    int result2 = maxCoins(nums2, size2);
    printf("测试2:\n输入: [1,5]\n输出: %d (期望: 10)\n\n", result2);
    
    // 测试示例3
    int nums3[] = {7};
    int size3 = sizeof(nums3) / sizeof(nums3[0]);
    int result3 = maxCoins(nums3, size3);
    printf("测试3:\n输入: [7]\n输出: %d (期望: 7)\n\n", result3);
    
    return 0;
}
```

---

### 复杂度分析
- **时间复杂度：** O(n³)，其中 n 是气球的数量。需要三层循环：区间长度、区间起点、最后戳破的气球
- **空间复杂度：** O(n²)，用于存储动态规划表

### 算法特点
1. **逆向思维**：考虑最后戳破哪个气球，而不是第一个戳破哪个
2. **区间DP**：典型区间动态规划问题，按区间长度从小到大计算
3. **虚拟气球**：在数组两端添加值为1的气球，简化边界处理
4. **分治思想**：最后戳破气球k后，问题分解为左右两个子问题