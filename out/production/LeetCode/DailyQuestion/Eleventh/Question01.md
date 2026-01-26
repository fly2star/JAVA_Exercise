# 1292. 元素和小于等于阈值的正方形的最大边长

**难度：中等**

## 题目描述

给你一个大小为 `m x n` 的矩阵 `mat` 和一个整数阈值 `threshold`。

请你返回元素总和小于或等于阈值的正方形区域的**最大边长**；如果没有这样的正方形区域，则返回 `0`。

---

## 示例说明

**示例 1：**  
输入：`mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]]`, `threshold = 4`  
输出：`2`  
解释：满足条件的最大正方形边长为 2，例如左上角 `(0,0)` 开始的 2x2 区域和为 `1+1+1+1=4 ≤ 4`。

**示例 2：**  
输入：`mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]]`, `threshold = 1`  
输出：`0`  
解释：所有元素都是 2，最小正方形（1x1）和也为 2 > 1，因此无解。

---

## 解题思路

### 核心思想：
- 要快速计算任意子矩阵的和，使用 **二维前缀和（2D Prefix Sum）**。
- 枚举所有可能的正方形边长 `k`，从大到小尝试，找到第一个满足“存在某个 k×k 正方形区域和 ≤ threshold”的边长。
- 使用二分查找优化枚举过程，提高效率。

### 步骤：
1. 构建二维前缀和数组 `prefix[i][j]`，表示从 `(0,0)` 到 `(i-1,j-1)` 的矩形区域和。
2. 对于每个可能的边长 `k`，遍历所有可能的左上角位置 `(i,j)`，计算以 `(i,j)` 为左上角、边长为 `k` 的正方形的和。
3. 如果找到至少一个正方形满足条件，则该 `k` 是可行的。
4. 用二分法找最大可行边长。

---

## 算法步骤

1. 计算二维前缀和数组。
2. 定义函数 `isValid(k)`：检查是否存在边长为 `k` 的正方形，其和 ≤ threshold。
3. 使用二分查找在 `[1, min(m,n)]` 范围内寻找最大合法边长。
4. 若无合法边长，返回 0。

---

## 复杂度分析

- **时间复杂度**：O(mn × log(min(m,n)))，其中 mn 是矩阵大小。
- **空间复杂度**：O(mn)，用于存储前缀和数组。

---

## 参考代码（Python、Java、C）

### Python 实现

```python
def maxSideLength(mat, threshold):
    m, n = len(mat), len(mat[0])
    
    # 构建二维前缀和
    prefix = [[0] * (n + 1) for _ in range(m + 1)]
    for i in range(m):
        for j in range(n):
            prefix[i+1][j+1] = mat[i][j] + prefix[i][j+1] + prefix[i+1][j] - prefix[i][j]
    
    def getSum(r1, c1, r2, c2):
        return prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1]
    
    def isValid(k):
        if k == 0:
            return True
        for i in range(m - k + 1):
            for j in range(n - k + 1):
                if getSum(i, j, i+k-1, j+k-1) <= threshold:
                    return True
        return False
    
    # 二分查找最大边长
    left, right = 0, min(m, n)
    ans = 0
    while left <= right:
        mid = (left + right) // 2
        if isValid(mid):
            ans = mid
            left = mid + 1
        else:
            right = mid - 1
    return ans
```


### Java 实现

```Java
public int maxSideLength(int[][] mat, int threshold) {
    int m = mat.length, n = mat[0].length;
    
    // 前缀和
    int[][] prefix = new int[m + 1][n + 1];
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            prefix[i+1][j+1] = mat[i][j] + prefix[i][j+1] + prefix[i+1][j] - prefix[i][j];
        }
    }
    
    // 获取子矩阵和
    java.util.function.IntBinaryOperator sum = (r1, c1, r2, c2) -> {
        return prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1];
    };
    
    // 检查边长 k 是否可行
    boolean isValid(int k) {
        if (k == 0) return true;
        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {
                if (sum.applyAsInt(i, j, i+k-1, j+k-1) <= threshold) {
                    return true;
                }
            }
        }
        return false;
    }
    
    // 二分查找
    int left = 0, right = Math.min(m, n), ans = 0;
    while (left <= right) {
        int mid = (left + right) / 2;
        if (isValid(mid)) {
            ans = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    return ans;
}
```

### C 实现

```C
#include <stdio.h>
#include <stdlib.h>

int maxSideLength(int** mat, int matSize, int* matColSize, int threshold) {
    int m = matSize, n = matColSize[0];
    
    // 构建前缀和
    int** prefix = (int**)malloc((m+1) * sizeof(int*));
    for (int i = 0; i <= m; i++) {
        prefix[i] = (int*)calloc(n+1, sizeof(int));
    }
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            prefix[i+1][j+1] = mat[i][j] + prefix[i][j+1] + prefix[i+1][j] - prefix[i][j];
        }
    }
    
    // 获取子矩阵和
    int getSum(int r1, int c1, int r2, int c2) {
        return prefix[r2+1][c2+1] - prefix[r1][c2+1] - prefix[r2+1][c1] + prefix[r1][c1];
    }
    
    // 检查边长 k 是否可行
    int isValid(int k) {
        if (k == 0) return 1;
        for (int i = 0; i <= m - k; i++) {
            for (int j = 0; j <= n - k; j++) {
                if (getSum(i, j, i+k-1, j+k-1) <= threshold) {
                    return 1;
                }
            }
        }
        return 0;
    }
    
    // 二分查找
    int left = 0, right = (m < n) ? m : n, ans = 0;
    while (left <= right) {
        int mid = (left + right) / 2;
        if (isValid(mid)) {
            ans = mid;
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }
    
    // 释放内存
    for (int i = 0; i <= m; i++) free(prefix[i]);
    free(prefix);
    
    return ans;
}
```

