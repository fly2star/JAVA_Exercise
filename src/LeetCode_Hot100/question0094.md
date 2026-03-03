# 70. 爬楼梯

**难度: 简单**

## 题目描述
假设你正在爬楼梯。需要 `n` 阶你才能到达楼顶。

每次你可以爬 `1` 或 `2` 个台阶。你有多少种不同的方法可以爬到楼顶呢？

---

## 示例说明
### 示例 1：
输入：n = 2  
输出：2  
解释：有两种方法可以爬到楼顶：
1. 1 阶 + 1 阶
2. 2 阶

### 示例 2：
输入：n = 3  
输出：3  
解释：有三种方法可以爬到楼顶：
1. 1 阶 + 1 阶 + 1 阶
2. 1 阶 + 2 阶
3. 2 阶 + 1 阶

---

## 提示：
- 1 ≤ n ≤ 45

---

## 解题思路

### 核心思想
这是一个经典的**动态规划**问题，也可以看作是**斐波那契数列**的应用。到达第 n 阶的方法数等于到达第 n-1 阶的方法数加上到达第 n-2 阶的方法数。

### 关键观察
- 到达第 1 阶：只有 1 种方法（1 阶）
- 到达第 2 阶：有 2 种方法（1+1 或 2）
- 对于 n ≥ 3：`f(n) = f(n-1) + f(n-2)`
- 可以使用递归、迭代或矩阵快速幂等方法求解

### 算法步骤

#### 方法一：动态规划（迭代）
1. 如果 n == 1，返回 1
2. 创建数组 dp，dp[i] 表示到达第 i 阶的方法数
3. 初始化 dp[1] = 1，dp[2] = 2
4. 对于 i 从 3 到 n：
   - dp[i] = dp[i-1] + dp[i-2]
5. 返回 dp[n]

#### 方法二：滚动数组优化
1. 使用三个变量滚动更新，空间复杂度 O(1)

---

## 代码参考(python, java, c)

### Python 代码实现
```python
# 方法一：动态规划
class Solution:
    def climbStairs(self, n: int) -> int:
        if n <= 2:
            return n
        
        dp = [0] * (n + 1)
        dp[1] = 1
        dp[2] = 2
        
        for i in range(3, n + 1):
            dp[i] = dp[i - 1] + dp[i - 2]
        
        return dp[n]

# 方法二：滚动数组优化
class Solution:
    def climbStairs(self, n: int) -> int:
        if n <= 2:
            return n
        
        prev2, prev1 = 1, 2  # prev2 = f(1), prev1 = f(2)
        
        for i in range(3, n + 1):
            current = prev1 + prev2
            prev2, prev1 = prev1, current
        
        return prev1
```

### Java 代码实现
```java
// 方法一：动态规划
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        
        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        
        return dp[n];
    }
}

// 方法二：滚动数组优化
class Solution {
    public int climbStairs(int n) {
        if (n <= 2) {
            return n;
        }
        
        int prev2 = 1;  // f(1)
        int prev1 = 2;  // f(2)
        
        for (int i = 3; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        
        return prev1;
    }
}
```

### C 代码实现
```c
// 方法一：动态规划
int climbStairs(int n) {
    if (n <= 2) {
        return n;
    }
    
    int* dp = (int*)malloc((n + 1) * sizeof(int));
    dp[1] = 1;
    dp[2] = 2;
    
    for (int i = 3; i <= n; i++) {
        dp[i] = dp[i - 1] + dp[i - 2];
    }
    
    int result = dp[n];
    free(dp);
    return result;
}

// 方法二：滚动数组优化
int climbStairs(int n) {
    if (n <= 2) {
        return n;
    }
    
    int prev2 = 1;  // f(1)
    int prev1 = 2;  // f(2)
    
    for (int i = 3; i <= n; i++) {
        int current = prev1 + prev2;
        prev2 = prev1;
        prev1 = current;
    }
    
    return prev1;
}
```

---