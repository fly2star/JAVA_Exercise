# 338. 比特位计数

**难度: 简单**

## 题目描述
给你一个整数 n，对于 0 ≤ i ≤ n 中的每个 i，计算其二进制表示中 1 的个数，返回一个长度为 n + 1 的数组 ans 作为答案。

---

## 示例说明
### 示例 1：
**输入：** n = 2  
**输出：** [0, 1, 1]  
**解释：**
- 0 → 0 (0个1)
- 1 → 1 (1个1)
- 2 → 10 (1个1)

---

### 示例 2：
**输入：** n = 5  
**输出：** [0, 1, 1, 2, 1, 2]  
**解释：**
- 0 → 0 (0个1)
- 1 → 1 (1个1)
- 2 → 10 (1个1)
- 3 → 11 (2个1)
- 4 → 100 (1个1)
- 5 → 101 (2个1)

---

### 示例 3：
**输入：** n = 0  
**输出：** [0]  
**解释：**
- 0 → 0 (0个1)

---

## 提示：
- 0 ≤ n ≤ 10^5

---

## 解题思路

### 核心思想
利用动态规划的思想，基于已有的计算结果来推导新的结果，避免重复计算每个数字的比特位。

### 关键观察
1. 对于任意整数 i，其比特位中1的个数可以通过已经计算过的结果得到
2. 主要规律：
   - 如果 i 是偶数：i 和 i/2 有相同数量的1（因为二进制右移一位相当于除以2）
   - 如果 i 是奇数：i 比 i-1 多一个1（因为奇数比前一个偶数多一个最低位的1）
3. 更通用的动态规划公式：`bits[i] = bits[i >> 1] + (i & 1)`

### 算法步骤
1. 创建一个长度为 n+1 的数组 ans
2. 初始化 ans[0] = 0
3. 对于 i 从 1 到 n：
   - 使用公式 `ans[i] = ans[i >> 1] + (i & 1)` 计算
   - 或者：`ans[i] = ans[i & (i-1)] + 1`（利用 i & (i-1) 可以去掉最低位的1）
4. 返回 ans 数组

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List

class Solution:
    def countBits(self, n: int) -> List[int]:
        # 方法1：使用 i >> 1 的公式
        ans = [0] * (n + 1)
        for i in range(1, n + 1):
            # i >> 1 相当于 i // 2
            # i & 1 判断i是奇数还是偶数（奇数得1，偶数得0）
            ans[i] = ans[i >> 1] + (i & 1)
        return ans
    
    def countBits2(self, n: int) -> List[int]:
        # 方法2：使用 i & (i-1) 的公式
        ans = [0] * (n + 1)
        for i in range(1, n + 1):
            # i & (i-1) 可以去掉i的最低位的1
            ans[i] = ans[i & (i - 1)] + 1
        return ans
    
    def countBits3(self, n: int) -> List[int]:
        # 方法3：根据奇偶性
        ans = [0] * (n + 1)
        for i in range(1, n + 1):
            if i % 2 == 0:  # 偶数
                ans[i] = ans[i // 2]
            else:  # 奇数
                ans[i] = ans[i - 1] + 1
        return ans
```

---

### Java 代码实现

```java
class Solution {
    // 方法1：使用 i >> 1 的公式
    public int[] countBits(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // i >> 1 相当于 i / 2
            // i & 1 判断i是奇数还是偶数
            ans[i] = ans[i >> 1] + (i & 1);
        }
        return ans;
    }
    
    // 方法2：使用 i & (i-1) 的公式
    public int[] countBits2(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            // i & (i-1) 可以去掉i的最低位的1
            ans[i] = ans[i & (i - 1)] + 1;
        }
        return ans;
    }
    
    // 方法3：根据奇偶性
    public int[] countBits3(int n) {
        int[] ans = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0) {  // 偶数
                ans[i] = ans[i / 2];
            } else {  // 奇数
                ans[i] = ans[i - 1] + 1;
            }
        }
        return ans;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */
int* countBits(int n, int* returnSize) {
    // 分配结果数组
    int* ans = (int*)malloc((n + 1) * sizeof(int));
    *returnSize = n + 1;
    
    // 初始化
    ans[0] = 0;
    
    // 方法1：使用 i >> 1 的公式
    for (int i = 1; i <= n; i++) {
        // i >> 1 相当于 i / 2
        // i & 1 判断i是奇数还是偶数
        ans[i] = ans[i >> 1] + (i & 1);
    }
    
    return ans;
}

// 方法2：使用 i & (i-1) 的公式
int* countBits2(int n, int* returnSize) {
    int* ans = (int*)malloc((n + 1) * sizeof(int));
    *returnSize = n + 1;
    
    ans[0] = 0;
    for (int i = 1; i <= n; i++) {
        // i & (i-1) 可以去掉i的最低位的1
        ans[i] = ans[i & (i - 1)] + 1;
    }
    
    return ans;
}

// 方法3：根据奇偶性
int* countBits3(int n, int* returnSize) {
    int* ans = (int*)malloc((n + 1) * sizeof(int));
    *returnSize = n + 1;
    
    ans[0] = 0;
    for (int i = 1; i <= n; i++) {
        if (i % 2 == 0) {  // 偶数
            ans[i] = ans[i / 2];
        } else {  // 奇数
            ans[i] = ans[i - 1] + 1;
        }
    }
    
    return ans;
}

// 测试代码
int main() {
    // 测试示例1
    printf("测试1: n = 2\n");
    int returnSize1;
    int* result1 = countBits(2, &returnSize1);
    printf("输出: [");
    for (int i = 0; i < returnSize1; i++) {
        printf("%d", result1[i]);
        if (i < returnSize1 - 1) printf(", ");
    }
    printf("]\n\n");
    free(result1);
    
    // 测试示例2
    printf("测试2: n = 5\n");
    int returnSize2;
    int* result2 = countBits(5, &returnSize2);
    printf("输出: [");
    for (int i = 0; i < returnSize2; i++) {
        printf("%d", result2[i]);
        if (i < returnSize2 - 1) printf(", ");
    }
    printf("]\n\n");
    free(result2);
    
    // 测试示例3
    printf("测试3: n = 0\n");
    int returnSize3;
    int* result3 = countBits(0, &returnSize3);
    printf("输出: [");
    for (int i = 0; i < returnSize3; i++) {
        printf("%d", result3[i]);
        if (i < returnSize3 - 1) printf(", ");
    }
    printf("]\n");
    free(result3);
    
    return 0;
}
```

---

### 复杂度分析
- **时间复杂度：** O(n)，只需要遍历一次
- **空间复杂度：** O(n)，需要存储结果数组
- 三种方法的时间复杂度都是 O(n)，其中方法1（`ans[i] = ans[i >> 1] + (i & 1)`）通常是最简洁高效的实现