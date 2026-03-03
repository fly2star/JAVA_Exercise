# 96. 不同的二叉搜索树

**难度: 中等**

## 题目描述
给你一个整数 `n`，求恰由 `n` 个节点组成且节点值从 1 到 `n` 互不相同的 **二叉搜索树** 有多少种？返回满足题意的二叉搜索树的种数。

---

## 示例说明
### 示例 1：

![uniquebstn3](../../readFile/image/uniquebstn3.jpg)

输入：n = 3  
输出：5  
解释：由节点值 1,2,3 组成的二叉搜索树共有 5 种：
- 1 为根，右子树有 2,3（2 种）
- 2 为根，左右子树各有 1 个节点（1 种）
- 3 为根，左子树有 1,2（2 种）

### 示例 2：
输入：n = 1  
输出：1

---

## 提示：
- 1 ≤ n ≤ 19

---

## 解题思路

### 核心思想
这是一个**卡特兰数**问题。对于二叉搜索树，当选择不同的根节点时，左右子树的节点数不同，且左右子树本身也是二叉搜索树。因此，可以使用动态规划求解。

### 关键观察
- 设 `dp[i]` 表示 i 个节点能组成的不同二叉搜索树的个数
- 对于序列 1...i，选择 j 作为根节点，则：
  - 左子树有 j-1 个节点，有 `dp[j-1]` 种可能
  - 右子树有 i-j 个节点，有 `dp[i-j]` 种可能
  - 以 j 为根的 BST 数量 = `dp[j-1] * dp[i-j]`
- 对所有可能的根节点求和：`dp[i] = sum(dp[j-1] * dp[i-j]) for j in 1..i`

### 算法步骤
1. 创建长度为 n+1 的数组 `dp`，`dp[0] = 1`（空树也是一种情况）
2. 对于 i 从 1 到 n：
   - 初始化 `dp[i] = 0`
   - 对于 j 从 1 到 i：
     - `dp[i] += dp[j-1] * dp[i-j]`
3. 返回 `dp[n]`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def numTrees(self, n: int) -> int:
        # dp[i] 表示 i 个节点能组成的 BST 个数
        dp = [0] * (n + 1)
        dp[0] = 1  # 空树
        
        for i in range(1, n + 1):
            for j in range(1, i + 1):
                # j 作为根节点
                # 左子树有 j-1 个节点，右子树有 i-j 个节点
                dp[i] += dp[j - 1] * dp[i - j]
        
        return dp[n]
```

### Java 代码实现
```java
class Solution {
    public int numTrees(int n) {
        // dp[i] 表示 i 个节点能组成的 BST 个数
        int[] dp = new int[n + 1];
        dp[0] = 1;  // 空树
        
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                // j 作为根节点
                // 左子树有 j-1 个节点，右子树有 i-j 个节点
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }
        
        return dp[n];
    }
}
```

### C 代码实现
```c
int numTrees(int n) {
    // dp[i] 表示 i 个节点能组成的 BST 个数
    int* dp = (int*)malloc((n + 1) * sizeof(int));
    dp[0] = 1;  // 空树
    
    for (int i = 1; i <= n; i++) {
        dp[i] = 0;
        for (int j = 1; j <= i; j++) {
            // j 作为根节点
            // 左子树有 j-1 个节点，右子树有 i-j 个节点
            dp[i] += dp[j - 1] * dp[i - j];
        }
    }
    
    int result = dp[n];
    free(dp);
    return result;
}
```

---