# 3129. 找出所有稳定的二进制数组 I

**难度: 中等**

## 题目描述
给你 3 个正整数 `zero`、`one` 和 `limit`。

一个 **二进制数组** `arr` 如果满足以下条件，那么我们称它是 **稳定的**：
- 0 在 `arr` 中出现次数 **恰好为** `zero`。
- 1 在 `arr` 中出现次数 **恰好为** `one`。
- `arr` 中每个长度超过 `limit` 的子数组 **都同时包含** 0 和 1。

请你返回 **稳定** 二进制数组的总数目。

由于答案可能很大，将它对 \(10^9 + 7\) 取余后返回。

---

## 示例说明
### 示例 1：
输入：zero = 1, one = 1, limit = 2  
输出：2  
解释：两个稳定的二进制数组为 [1,0] 和 [0,1]，两个数组都有一个 0 和一个 1，且没有子数组长度大于 2。

### 示例 2：
输入：zero = 1, one = 2, limit = 1  
输出：1  
解释：唯一稳定的二进制数组是 [1,0,1]。二进制数组 [1,1,0] 和 [0,1,1] 都有长度为 2 且元素全都相同的子数组，所以它们不稳定。

### 示例 3：
输入：zero = 3, one = 3, limit = 2
输出：14
解释：
所有稳定的二进制数组包括 [0,0,1,0,1,1] ，[0,0,1,1,0,1] ，[0,1,0,0,1,1] ，[0,1,0,1,0,1] ，[0,1,0,1,1,0] ，[0,1,1,0,0,1] ，[0,1,1,0,1,0] ，[1,0,0,1,0,1] ，[1,0,0,1,1,0] ，[1,0,1,0,0,1] ，[1,0,1,0,1,0] ，[1,0,1,1,0,0] ，[1,1,0,0,1,0] 和 [1,1,0,1,0,0] 。

---

## 提示：
- 1 ≤ zero, one, limit ≤ 200

---

## 解题思路

### 核心思想
这是一个**动态规划**问题。我们需要计算长度为 zero+one 的二进制数组的数量，其中 0 恰好出现 zero 次，1 恰好出现 one 次，且任意长度超过 limit 的连续子数组不能全部由相同元素组成（即不能有超过 limit 个连续的 0 或 1）。

### 关键观察
- 条件 "每个长度超过 limit 的子数组都同时包含 0 和 1" 等价于：**不能有连续 limit+1 个相同的数字**
- 可以使用三维 DP：`dp[i][j][k]` 表示已经使用了 i 个 0 和 j 个 1，且最后一个数字是 k（0 或 1）的方案数
- 需要额外记录连续相同数字的长度

### 算法步骤
1. 定义 `dp[i][j][k][l]` 表示已经使用了 i 个 0 和 j 个 1，当前最后一个数字是 k，且最后有 l 个连续相同的数字 k
2. 初始化：`dp[1][0][0][1] = 1`，`dp[0][1][1][1] = 1`（如果 zero 或 one 为 0 的情况需要特殊处理）
3. 状态转移：
   - 如果当前最后一个数字是 0：
     - 下一个放 0：需要 i+1 ≤ zero 且 l+1 ≤ limit，转移到 `dp[i+1][j][0][l+1]`
     - 下一个放 1：需要 j+1 ≤ one，转移到 `dp[i][j+1][1][1]`
   - 如果当前最后一个数字是 1：
     - 下一个放 1：需要 j+1 ≤ one 且 l+1 ≤ limit，转移到 `dp[i][j+1][1][l+1]`
     - 下一个放 0：需要 i+1 ≤ zero，转移到 `dp[i+1][j][0][1]`
4. 最终答案 = `sum(dp[zero][one][0][l] + dp[zero][one][1][l])` 对 l 从 1 到 limit 求和

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def numberOfStableArrays(self, zero: int, one: int, limit: int) -> int:
        MOD = 10**9 + 7
        
        # dp[i][j][k][l]: i个0, j个1, 最后数字是k, 最后连续相同长度为l
        dp = [[[[0] * (limit + 1) for _ in range(2)] for _ in range(one + 1)] for _ in range(zero + 1)]
        
        # 初始化
        if zero > 0:
            dp[1][0][0][1] = 1
        if one > 0:
            dp[0][1][1][1] = 1
        
        for i in range(zero + 1):
            for j in range(one + 1):
                for k in range(2):
                    for l in range(1, limit + 1):
                        if dp[i][j][k][l] == 0:
                            continue
                        val = dp[i][j][k][l]
                        
                        if k == 0:  # 当前最后是0
                            # 下一个放0
                            if i < zero and l < limit:
                                dp[i + 1][j][0][l + 1] = (dp[i + 1][j][0][l + 1] + val) % MOD
                            # 下一个放1
                            if j < one:
                                dp[i][j + 1][1][1] = (dp[i][j + 1][1][1] + val) % MOD
                        else:  # 当前最后是1
                            # 下一个放1
                            if j < one and l < limit:
                                dp[i][j + 1][1][l + 1] = (dp[i][j + 1][1][l + 1] + val) % MOD
                            # 下一个放0
                            if i < zero:
                                dp[i + 1][j][0][1] = (dp[i + 1][j][0][1] + val) % MOD
        
        # 统计结果
        result = 0
        for k in range(2):
            for l in range(1, limit + 1):
                result = (result + dp[zero][one][k][l]) % MOD
        
        return result
```

### Java 代码实现
```java
class Solution {
    public int numberOfStableArrays(int zero, int one, int limit) {
        final int MOD = 1000000007;
        
        // dp[i][j][k][l]: i个0, j个1, 最后数字是k, 最后连续相同长度为l
        int[][][][] dp = new int[zero + 1][one + 1][2][limit + 1];
        
        // 初始化
        if (zero > 0) {
            dp[1][0][0][1] = 1;
        }
        if (one > 0) {
            dp[0][1][1][1] = 1;
        }
        
        for (int i = 0; i <= zero; i++) {
            for (int j = 0; j <= one; j++) {
                for (int k = 0; k < 2; k++) {
                    for (int l = 1; l <= limit; l++) {
                        if (dp[i][j][k][l] == 0) continue;
                        int val = dp[i][j][k][l];
                        
                        if (k == 0) { // 当前最后是0
                            // 下一个放0
                            if (i < zero && l < limit) {
                                dp[i + 1][j][0][l + 1] = (dp[i + 1][j][0][l + 1] + val) % MOD;
                            }
                            // 下一个放1
                            if (j < one) {
                                dp[i][j + 1][1][1] = (dp[i][j + 1][1][1] + val) % MOD;
                            }
                        } else { // 当前最后是1
                            // 下一个放1
                            if (j < one && l < limit) {
                                dp[i][j + 1][1][l + 1] = (dp[i][j + 1][1][l + 1] + val) % MOD;
                            }
                            // 下一个放0
                            if (i < zero) {
                                dp[i + 1][j][0][1] = (dp[i + 1][j][0][1] + val) % MOD;
                            }
                        }
                    }
                }
            }
        }
        
        // 统计结果
        int result = 0;
        for (int k = 0; k < 2; k++) {
            for (int l = 1; l <= limit; l++) {
                result = (result + dp[zero][one][k][l]) % MOD;
            }
        }
        
        return result;
    }
}
```

### C 代码实现
```c
int numberOfStableArrays(int zero, int one, int limit) {
    const int MOD = 1000000007;
    
    // dp[i][j][k][l]: i个0, j个1, 最后数字是k, 最后连续相同长度为l
    // 使用动态内存分配
    int**** dp = (int****)malloc((zero + 1) * sizeof(int***));
    for (int i = 0; i <= zero; i++) {
        dp[i] = (int***)malloc((one + 1) * sizeof(int**));
        for (int j = 0; j <= one; j++) {
            dp[i][j] = (int**)malloc(2 * sizeof(int*));
            for (int k = 0; k < 2; k++) {
                dp[i][j][k] = (int*)calloc((limit + 1), sizeof(int));
            }
        }
    }
    
    // 初始化
    if (zero > 0) {
        dp[1][0][0][1] = 1;
    }
    if (one > 0) {
        dp[0][1][1][1] = 1;
    }
    
    for (int i = 0; i <= zero; i++) {
        for (int j = 0; j <= one; j++) {
            for (int k = 0; k < 2; k++) {
                for (int l = 1; l <= limit; l++) {
                    if (dp[i][j][k][l] == 0) continue;
                    int val = dp[i][j][k][l];
                    
                    if (k == 0) { // 当前最后是0
                        // 下一个放0
                        if (i < zero && l < limit) {
                            dp[i + 1][j][0][l + 1] = (dp[i + 1][j][0][l + 1] + val) % MOD;
                        }
                        // 下一个放1
                        if (j < one) {
                            dp[i][j + 1][1][1] = (dp[i][j + 1][1][1] + val) % MOD;
                        }
                    } else { // 当前最后是1
                        // 下一个放1
                        if (j < one && l < limit) {
                            dp[i][j + 1][1][l + 1] = (dp[i][j + 1][1][l + 1] + val) % MOD;
                        }
                        // 下一个放0
                        if (i < zero) {
                            dp[i + 1][j][0][1] = (dp[i + 1][j][0][1] + val) % MOD;
                        }
                    }
                }
            }
        }
    }
    
    // 统计结果
    int result = 0;
    for (int k = 0; k < 2; k++) {
        for (int l = 1; l <= limit; l++) {
            result = (result + dp[zero][one][k][l]) % MOD;
        }
    }
    
    // 释放内存
    for (int i = 0; i <= zero; i++) {
        for (int j = 0; j <= one; j++) {
            for (int k = 0; k < 2; k++) {
                free(dp[i][j][k]);
            }
            free(dp[i][j]);
        }
        free(dp[i]);
    }
    free(dp);
    
    return result;
}
```

---