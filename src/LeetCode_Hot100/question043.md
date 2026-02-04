# 300. 最长递增子序列

**难度: 中等**

## 题目描述
给你一个整数数组 `nums`，找到其中最长严格递增子序列的长度。

**子序列** 是由数组派生而来的序列，删除（或不删除）数组中的元素而不改变其余元素的顺序。例如，[3,6,2,7] 是数组 [0,3,1,6,2,2,7] 的子序列。

---

## 示例说明
### 示例 1：
**输入：** nums = [10,9,2,5,3,7,101,18]  
**输出：** 4  
**解释：** 最长递增子序列是 [2,3,7,101]，因此长度为 4。

可能的递增子序列：
- [2,5,7,101] 长度4
- [2,3,7,101] 长度4
- [2,5,7,18] 长度4

---

### 示例 2：
**输入：** nums = [0,1,0,3,2,3]  
**输出：** 4  
**解释：** 最长递增子序列是 [0,1,2,3]，因此长度为 4。

---

### 示例 3：
**输入：** nums = [7,7,7,7,7,7,7]  
**输出：** 1  
**解释：** 最长递增子序列是任意一个 7，因此长度为 1。

---

## 提示：
- 1 ≤ nums.length ≤ 2500
- -10^4 ≤ nums[i] ≤ 10^4

---

## 解题思路

### 核心思想
经典动态规划问题，有两种主要解法：
1. **动态规划 (O(n²))**：定义 `dp[i]` 表示以 `nums[i]` 结尾的最长递增子序列长度
2. **贪心 + 二分查找 (O(nlogn))**：维护一个递增数组 `tail`，`tail[i]` 表示长度为 `i+1` 的递增子序列的最小末尾元素

### 关键观察
1. 对于动态规划：
   - `dp[i] = max(dp[j]) + 1`，其中 `0 ≤ j < i` 且 `nums[j] < nums[i]`
   - 最终结果是 `max(dp[i])`
2. 对于贪心+二分：
   - 如果 `nums[i]` 大于 `tail` 的最后一个元素，直接追加到末尾
   - 否则，二分查找 `tail` 中第一个大于等于 `nums[i]` 的位置并替换
   - `tail` 的长度就是最长递增子序列的长度

### 算法步骤（动态规划 O(n²)）
1. 初始化 `dp` 数组，长度与 `nums` 相同，每个元素初始值为 1
2. 对于每个 `i` 从 0 到 n-1：
   - 对于每个 `j` 从 0 到 i-1：
     - 如果 `nums[j] < nums[i]`，则 `dp[i] = max(dp[i], dp[j] + 1)`
3. 返回 `dp` 数组中的最大值

### 算法步骤（贪心+二分 O(nlogn)）
1. 初始化 `tail` 数组为空
2. 遍历 `nums` 数组：
   - 如果 `nums[i]` 大于 `tail` 的最后一个元素，添加到 `tail` 末尾
   - 否则，二分查找 `tail` 中第一个大于等于 `nums[i]` 的位置，替换该位置的元素
3. 返回 `tail` 的长度

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List
import bisect

class Solution:
    def lengthOfLIS(self, nums: List[int]) -> int:
        """动态规划方法 O(n²)"""
        if not nums:
            return 0
        
        n = len(nums)
        dp = [1] * n  # dp[i] 表示以 nums[i] 结尾的最长递增子序列长度
        
        for i in range(n):
            for j in range(i):
                if nums[j] < nums[i]:
                    dp[i] = max(dp[i], dp[j] + 1)
        
        return max(dp)
    
    def lengthOfLIS_greedy(self, nums: List[int]) -> int:
        """贪心+二分查找方法 O(nlogn)"""
        if not nums:
            return 0
        
        tail = []  # tail[i] 表示长度为 i+1 的递增子序列的最小末尾元素
        
        for num in nums:
            # 如果当前元素大于 tail 的最后一个元素，直接添加到末尾
            if not tail or num > tail[-1]:
                tail.append(num)
            else:
                # 二分查找第一个大于等于 num 的位置
                left, right = 0, len(tail) - 1
                while left < right:
                    mid = (left + right) // 2
                    if tail[mid] < num:
                        left = mid + 1
                    else:
                        right = mid
                tail[left] = num
        
        return len(tail)
    
    def lengthOfLIS_greedy_bisect(self, nums: List[int]) -> int:
        """贪心+二分查找（使用bisect库）"""
        import bisect
        tail = []
        
        for num in nums:
            pos = bisect.bisect_left(tail, num)
            if pos == len(tail):
                tail.append(num)
            else:
                tail[pos] = num
        
        return len(tail)
```

---

### Java 代码实现

```java
import java.util.Arrays;

class Solution {
    // 动态规划方法 O(n²)
    public int lengthOfLIS(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        int[] dp = new int[n];  // dp[i] 表示以 nums[i] 结尾的最长递增子序列长度
        Arrays.fill(dp, 1);
        
        int maxLen = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[j] < nums[i]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        
        return maxLen;
    }
    
    // 贪心+二分查找方法 O(nlogn)
    public int lengthOfLISGreedy(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        
        int n = nums.length;
        int[] tail = new int[n];  // tail[i] 表示长度为 i+1 的递增子序列的最小末尾元素
        int len = 0;  // 当前 tail 的长度
        
        for (int num : nums) {
            // 如果当前元素大于 tail 的最后一个元素，直接添加到末尾
            if (len == 0 || num > tail[len - 1]) {
                tail[len] = num;
                len++;
            } else {
                // 二分查找第一个大于等于 num 的位置
                int left = 0, right = len - 1;
                while (left < right) {
                    int mid = left + (right - left) / 2;
                    if (tail[mid] < num) {
                        left = mid + 1;
                    } else {
                        right = mid;
                    }
                }
                tail[left] = num;
            }
        }
        
        return len;
    }
    
    // 贪心+二分查找（更简洁的实现）
    public int lengthOfLISGreedy2(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        
        for (int num : nums) {
            int left = 0, right = size;
            while (left != right) {
                int mid = left + (right - left) / 2;
                if (tails[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            tails[left] = num;
            if (left == size) {
                size++;
            }
        }
        
        return size;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 动态规划方法 O(n²)
int lengthOfLIS(int* nums, int numsSize) {
    if (numsSize == 0) {
        return 0;
    }
    
    // dp[i] 表示以 nums[i] 结尾的最长递增子序列长度
    int* dp = (int*)malloc(numsSize * sizeof(int));
    for (int i = 0; i < numsSize; i++) {
        dp[i] = 1;
    }
    
    int maxLen = 1;
    for (int i = 0; i < numsSize; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i] && dp[j] + 1 > dp[i]) {
                dp[i] = dp[j] + 1;
            }
        }
        if (dp[i] > maxLen) {
            maxLen = dp[i];
        }
    }
    
    free(dp);
    return maxLen;
}

// 贪心+二分查找方法 O(nlogn)
int lengthOfLISGreedy(int* nums, int numsSize) {
    if (numsSize == 0) {
        return 0;
    }
    
    // tail[i] 表示长度为 i+1 的递增子序列的最小末尾元素
    int* tail = (int*)malloc(numsSize * sizeof(int));
    int len = 0;  // 当前 tail 的长度
    
    for (int i = 0; i < numsSize; i++) {
        int num = nums[i];
        
        // 如果当前元素大于 tail 的最后一个元素，直接添加到末尾
        if (len == 0 || num > tail[len - 1]) {
            tail[len] = num;
            len++;
        } else {
            // 二分查找第一个大于等于 num 的位置
            int left = 0, right = len - 1;
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (tail[mid] < num) {
                    left = mid + 1;
                } else {
                    right = mid;
                }
            }
            tail[left] = num;
        }
    }
    
    free(tail);
    return len;
}

// 测试代码
int main() {
    // 测试示例1
    int nums1[] = {10, 9, 2, 5, 3, 7, 101, 18};
    int size1 = sizeof(nums1) / sizeof(nums1[0]);
    int result1 = lengthOfLIS(nums1, size1);
    printf("测试1 (动态规划):\n输入: [10,9,2,5,3,7,101,18]\n输出: %d (期望: 4)\n", result1);
    
    int result1_greedy = lengthOfLISGreedy(nums1, size1);
    printf("测试1 (贪心+二分):\n输出: %d (期望: 4)\n\n", result1_greedy);
    
    // 测试示例2
    int nums2[] = {0, 1, 0, 3, 2, 3};
    int size2 = sizeof(nums2) / sizeof(nums2[0]);
    int result2 = lengthOfLIS(nums2, size2);
    printf("测试2 (动态规划):\n输入: [0,1,0,3,2,3]\n输出: %d (期望: 4)\n", result2);
    
    int result2_greedy = lengthOfLISGreedy(nums2, size2);
    printf("测试2 (贪心+二分):\n输出: %d (期望: 4)\n\n", result2_greedy);
    
    // 测试示例3
    int nums3[] = {7, 7, 7, 7, 7, 7, 7};
    int size3 = sizeof(nums3) / sizeof(nums3[0]);
    int result3 = lengthOfLIS(nums3, size3);
    printf("测试3 (动态规划):\n输入: [7,7,7,7,7,7,7]\n输出: %d (期望: 1)\n", result3);
    
    int result3_greedy = lengthOfLISGreedy(nums3, size3);
    printf("测试3 (贪心+二分):\n输出: %d (期望: 1)\n\n", result3_greedy);
    
    // 测试示例4：递减序列
    int nums4[] = {5, 4, 3, 2, 1};
    int size4 = sizeof(nums4) / sizeof(nums4[0]);
    int result4 = lengthOfLIS(nums4, size4);
    printf("测试4 (动态规划):\n输入: [5,4,3,2,1]\n输出: %d (期望: 1)\n", result4);
    
    int result4_greedy = lengthOfLISGreedy(nums4, size4);
    printf("测试4 (贪心+二分):\n输出: %d (期望: 1)\n", result4_greedy);
    
    return 0;
}
```

---

### 复杂度分析
- **动态规划方法：**
  - 时间复杂度：O(n²)，两层循环
  - 空间复杂度：O(n)，dp数组
- **贪心+二分查找方法：**
  - 时间复杂度：O(nlogn)，遍历数组 + 二分查找
  - 空间复杂度：O(n)，tail数组

### 算法对比
| 方法 | 时间复杂度 | 空间复杂度 | 特点 |
|------|-----------|-----------|------|
| 动态规划 | O(n²) | O(n) | 直观易懂，可以重建具体序列 |
| 贪心+二分 | O(nlogn) | O(n) | 效率更高，但tail数组不一定是最长递增子序列本身 |
