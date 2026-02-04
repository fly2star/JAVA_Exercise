# 279. 完全平方数

**难度: 中等**

## 题目描述
给你一个整数 `n`，返回和为 `n` 的完全平方数的最少数量。

**完全平方数** 是一个整数，其值等于另一个整数的平方；换句话说，其值等于一个整数自乘的积。例如，1、4、9 和 16 都是完全平方数，而 3 和 11 不是。

---

## 示例说明
### 示例 1：
**输入：** n = 12  
**输出：** 3  
**解释：** 12 = 4 + 4 + 4  
- 可以使用 3 个 4（4 是完全平方数）
- 其他分解：12 = 9 + 1 + 1 + 1（需要 4 个），不是最少

---

### 示例 2：
**输入：** n = 13  
**输出：** 2  
**解释：** 13 = 4 + 9  
- 可以使用 4 和 9 两个完全平方数
- 13 = 9 + 1 + 1 + 1 + 1（需要 5 个）不是最少

---

### 示例 3：
**输入：** n = 1  
**输出：** 1  
**解释：** 1 本身就是完全平方数

---

### 示例 4：
**输入：** n = 4  
**输出：** 1  
**解释：** 4 本身就是完全平方数

---

### 示例 5：
**输入：** n = 18  
**输出：** 2  
**解释：** 18 = 9 + 9

---

## 提示：
- 1 ≤ n ≤ 10⁴

---

## 解题思路

### 核心思想
这是一个典型的完全背包问题，可以使用动态规划解决。我们需要找到最少数量的完全平方数，使它们的和等于 `n`。

### 关键观察
1. 任何一个正整数都可以表示为最多 4 个完全平方数的和（拉格朗日四平方定理）
2. 对于给定的 `n`，我们需要考虑所有小于等于 `n` 的完全平方数
3. 这是一个最优化问题，可以使用动态规划求解

### 算法步骤（动态规划）
1. 初始化动态规划数组 `dp`，长度为 `n+1`
   - `dp[0] = 0`（和为0需要0个完全平方数）
   - 其他初始化为一个较大的数（如 `n+1`）
2. 生成所有小于等于 `n` 的完全平方数
3. 对于每个完全平方数 `square`：
   - 对于每个数字 `i` 从 `square` 到 `n`：
     - `dp[i] = min(dp[i], dp[i-square] + 1)`
4. 返回 `dp[n]`

### 算法优化（BFS方法）
可以将问题转化为图的最短路径问题：
- 节点：0 到 n 的每个整数
- 边：如果两个节点相差一个完全平方数，则存在一条边
- 问题：求从节点 0 到节点 n 的最短路径长度

### 算法步骤（BFS）
1. 生成所有小于等于 `n` 的完全平方数
2. 使用队列进行 BFS
3. 从 0 开始，每次加上一个完全平方数
4. 记录到达每个数字的步数（即完全平方数的个数）
5. 当到达 `n` 时，返回步数

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List
import math
from collections import deque

class Solution:
    def numSquares(self, n: int) -> int:
        """动态规划方法"""
        # 1. 生成所有小于等于n的完全平方数
        max_square = int(math.sqrt(n))
        squares = [i * i for i in range(1, max_square + 1)]
        
        # 2. 初始化dp数组
        dp = [float('inf')] * (n + 1)
        dp[0] = 0
        
        # 3. 动态规划
        for square in squares:
            for i in range(square, n + 1):
                dp[i] = min(dp[i], dp[i - square] + 1)
        
        return dp[n]
    
    def numSquares_bfs(self, n: int) -> int:
        """BFS方法"""
        # 1. 生成所有小于等于n的完全平方数
        squares = []
        i = 1
        while i * i <= n:
            squares.append(i * i)
            i += 1
        
        # 2. BFS初始化
        queue = deque([0])
        visited = [False] * (n + 1)
        visited[0] = True
        level = 0
        
        # 3. BFS搜索
        while queue:
            level += 1
            size = len(queue)
            
            for _ in range(size):
                current = queue.popleft()
                
                for square in squares:
                    next_num = current + square
                    
                    if next_num == n:
                        return level
                    
                    if next_num < n and not visited[next_num]:
                        visited[next_num] = True
                        queue.append(next_num)
        
        return level
    
    def numSquares_math(self, n: int) -> int:
        """数学方法：基于四平方定理"""
        # 检查是否是完全平方数
        def is_square(x: int) -> bool:
            root = int(math.sqrt(x))
            return root * root == x
        
        # 四平方定理特殊情况
        # 1. 如果n是完全平方数，返回1
        if is_square(n):
            return 1
        
        # 2. 检查是否能表示为两个完全平方数的和
        i = 1
        while i * i <= n:
            if is_square(n - i * i):
                return 2
            i += 1
        
        # 3. 根据勒让德三平方定理，如果n可以表示为4^k*(8m+7)形式，则需要4个
        # 否则需要3个
        temp = n
        while temp % 4 == 0:
            temp //= 4
        if temp % 8 == 7:
            return 4
        
        return 3
```

---

### Java 代码实现

```java
import java.util.*;

class Solution {
    // 方法1：动态规划
    public int numSquares(int n) {
        // 1. 生成所有小于等于n的完全平方数
        List<Integer> squares = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            squares.add(i * i);
        }
        
        // 2. 初始化dp数组
        int[] dp = new int[n + 1];
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;
        
        // 3. 动态规划
        for (int square : squares) {
            for (int i = square; i <= n; i++) {
                if (dp[i - square] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - square] + 1);
                }
            }
        }
        
        return dp[n];
    }
    
    // 方法2：BFS
    public int numSquaresBFS(int n) {
        // 1. 生成所有小于等于n的完全平方数
        List<Integer> squares = new ArrayList<>();
        for (int i = 1; i * i <= n; i++) {
            squares.add(i * i);
        }
        
        // 2. BFS初始化
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[n + 1];
        queue.offer(0);
        visited[0] = true;
        int level = 0;
        
        // 3. BFS搜索
        while (!queue.isEmpty()) {
            level++;
            int size = queue.size();
            
            for (int i = 0; i < size; i++) {
                int current = queue.poll();
                
                for (int square : squares) {
                    int next = current + square;
                    
                    if (next == n) {
                        return level;
                    }
                    
                    if (next < n && !visited[next]) {
                        visited[next] = true;
                        queue.offer(next);
                    }
                }
            }
        }
        
        return level;
    }
    
    // 方法3：数学方法
    public int numSquaresMath(int n) {
        // 检查是否是完全平方数
        if (isSquare(n)) {
            return 1;
        }
        
        // 检查是否能表示为两个完全平方数的和
        for (int i = 1; i * i <= n; i++) {
            if (isSquare(n - i * i)) {
                return 2;
            }
        }
        
        // 检查是否能表示为4个完全平方数的和（四平方定理）
        // 如果n可以表示为4^k*(8m+7)形式，则需要4个
        int temp = n;
        while (temp % 4 == 0) {
            temp /= 4;
        }
        if (temp % 8 == 7) {
            return 4;
        }
        
        return 3;
    }
    
    private boolean isSquare(int x) {
        int root = (int) Math.sqrt(x);
        return root * root == x;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <limits.h>

// 方法1：动态规划
int numSquares(int n) {
    // 1. 初始化dp数组
    int* dp = (int*)malloc((n + 1) * sizeof(int));
    for (int i = 0; i <= n; i++) {
        dp[i] = INT_MAX;
    }
    dp[0] = 0;
    
    // 2. 动态规划
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j * j <= i; j++) {
            int square = j * j;
            if (dp[i - square] != INT_MAX) {
                dp[i] = (dp[i] < dp[i - square] + 1) ? dp[i] : (dp[i - square] + 1);
            }
        }
    }
    
    int result = dp[n];
    free(dp);
    return result;
}

// 方法2：BFS
int numSquaresBFS(int n) {
    // 生成所有小于等于n的完全平方数
    int* squares = (int*)malloc((int)sqrt(n) * sizeof(int));
    int squaresCount = 0;
    for (int i = 1; i * i <= n; i++) {
        squares[squaresCount++] = i * i;
    }
    
    // BFS初始化
    int* queue = (int*)malloc((n + 1) * sizeof(int));
    int* visited = (int*)malloc((n + 1) * sizeof(int));
    for (int i = 0; i <= n; i++) {
        visited[i] = 0;
    }
    
    int front = 0, rear = 0;
    queue[rear++] = 0;
    visited[0] = 1;
    int level = 0;
    
    // BFS搜索
    while (front < rear) {
        level++;
        int size = rear - front;
        
        for (int i = 0; i < size; i++) {
            int current = queue[front++];
            
            for (int j = 0; j < squaresCount; j++) {
                int next = current + squares[j];
                
                if (next == n) {
                    free(squares);
                    free(queue);
                    free(visited);
                    return level;
                }
                
                if (next < n && !visited[next]) {
                    visited[next] = 1;
                    queue[rear++] = next;
                }
            }
        }
    }
    
    free(squares);
    free(queue);
    free(visited);
    return level;
}

// 方法3：数学方法
int numSquaresMath(int n) {
    // 检查是否是完全平方数
    int root = (int)sqrt(n);
    if (root * root == n) {
        return 1;
    }
    
    // 检查是否能表示为两个完全平方数的和
    for (int i = 1; i * i <= n; i++) {
        int remaining = n - i * i;
        int remainingRoot = (int)sqrt(remaining);
        if (remainingRoot * remainingRoot == remaining) {
            return 2;
        }
    }
    
    // 检查是否能表示为4个完全平方数的和（四平方定理）
    int temp = n;
    while (temp % 4 == 0) {
        temp /= 4;
    }
    if (temp % 8 == 7) {
        return 4;
    }
    
    return 3;
}

// 测试代码
int main() {
    // 测试示例1
    int n1 = 12;
    int result1 = numSquares(n1);
    printf("测试1 (动态规划):\n输入: n = %d\n输出: %d (期望: 3)\n\n", n1, result1);
    
    int result1_bfs = numSquaresBFS(n1);
    printf("测试1 (BFS):\n输出: %d (期望: 3)\n\n", result1_bfs);
    
    // 测试示例2
    int n2 = 13;
    int result2 = numSquares(n2);
    printf("测试2 (动态规划):\n输入: n = %d\n输出: %d (期望: 2)\n\n", n2, result2);
    
    // 测试示例3
    int n3 = 1;
    int result3 = numSquaresMath(n3);
    printf("测试3 (数学方法):\n输入: n = %d\n输出: %d (期望: 1)\n\n", n3, result3);
    
    // 测试示例4
    int n4 = 4;
    int result4 = numSquares(n4);
    printf("测试4 (动态规划):\n输入: n = %d\n输出: %d (期望: 1)\n\n", n4, result4);
    
    // 测试示例5
    int n5 = 18;
    int result5 = numSquares(n5);
    printf("测试5 (动态规划):\n输入: n = %d\n输出: %d (期望: 2)\n\n", n5, result5);
    
    // 测试四平方定理的例子
    int n6 = 7;  // 7 = 4 + 1 + 1 + 1
    int result6 = numSquaresMath(n6);
    printf("测试6 (数学方法):\n输入: n = %d\n输出: %d (期望: 4)\n", n6, result6);
    
    return 0;
}
```

---

### 复杂度分析
| 方法 | 时间复杂度 | 空间复杂度 | 特点 |
|------|-----------|-----------|------|
| 动态规划 | O(n√n) | O(n) | 通用性强，适合所有n |
| BFS | O(n√n) | O(n) | 图搜索方法，可能更快找到解 |
| 数学方法 | O(√n) | O(1) | 基于数学定理，最快但只适用于特定定理 |

### 算法详解

#### 1. 动态规划方法
- **状态定义**：`dp[i]` 表示和为 `i` 的完全平方数的最少数量
- **状态转移**：`dp[i] = min(dp[i], dp[i-square] + 1)`，其中 `square` 是完全平方数
- **初始化**：`dp[0] = 0`，其他为最大值
- **结果**：`dp[n]`

#### 2. BFS方法
- 将问题转化为图的最短路径问题
- 节点：数字 0 到 n
- 边：如果两个数字相差一个完全平方数，则存在一条边
- 使用 BFS 寻找从 0 到 n 的最短路径

#### 3. 数学方法（基于四平方定理）
- **四平方定理**：任何正整数都可以表示为最多4个完全平方数的和
- **特殊情况**：
  1. 如果 n 是完全平方数：返回 1
  2. 如果 n 可以表示为两个完全平方数的和：返回 2
  3. 如果 n = 4^k × (8m + 7)：返回 4
  4. 否则：返回 3


