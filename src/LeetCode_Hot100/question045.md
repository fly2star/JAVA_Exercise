# 287. 寻找重复数

**难度: 中等**

## 题目描述
给定一个包含 `n + 1` 个整数的数组 `nums`，其数字都在 `[1, n]` 范围内（包括 1 和 n），可知至少存在一个重复的数。

假设 `nums` 只有一个重复的数，返回这个重复的数。

你设计的解决方案必须 **不修改** 数组 `nums` 且只用常量级 O(1) 的额外空间。

---

## 示例说明
### 示例 1：
**输入：** nums = [1,3,4,2,2]  
**输出：** 2  
**解释：** 
- 数组长度 n+1 = 5，数字范围 [1, 4]
- 数字 2 出现了两次

---

### 示例 2：
**输入：** nums = [3,1,3,4,2]  
**输出：** 3  
**解释：**
- 数组长度 n+1 = 5，数字范围 [1, 4]
- 数字 3 出现了两次

---

### 示例 3：
**输入：** nums = [3,3,3,3,3]  
**输出：** 3  
**解释：**
- 数组长度 n+1 = 5，数字范围 [1, 4]
- 数字 3 出现了五次

---

## 提示：
- 1 ≤ n ≤ 10^5
- nums.length == n + 1
- 1 ≤ nums[i] ≤ n
- nums 中只有一个重复的数字，但它可能重复出现多次

---

## 解题思路

### 核心思想
将数组看作链表，利用快慢指针找到环的入口（Floyd判圈算法）。

### 关键观察
1. 数组中的值在 [1, n] 范围内，共有 n+1 个元素
2. 可以将数组看作一个特殊的链表：
   - 下标 i 指向 nums[i]
   - 因为有重复数字，所以一定会形成环
3. 问题转化为：在链表中找到环的入口（即重复数字）

### 算法步骤（Floyd判圈算法）
1. **第一阶段：判断是否有环**
   - 使用快慢指针，快指针每次走两步，慢指针每次走一步
   - 如果存在环，快慢指针一定会相遇
   
2. **第二阶段：找到环的入口**
   - 当快慢指针相遇后，将慢指针移回起点
   - 快慢指针都改为每次走一步
   - 它们再次相遇的位置就是环的入口，即重复数字

### 为什么这样可行？
设：
- 起点到环入口的距离为 a
- 环入口到相遇点的距离为 b
- 相遇点回到环入口的距离为 c

当快慢指针相遇时：
- 慢指针走了：a + b
- 快指针走了：a + b + k(b + c)，其中 k ≥ 1

因为快指针速度是慢指针的2倍：
2(a + b) = a + b + k(b + c)
=> a = (k-1)(b + c) + c

所以从起点和相遇点同时出发，走 a 步后会在环入口相遇。

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List

class Solution:
    def findDuplicate(self, nums: List[int]) -> int:
        """Floyd判圈算法（快慢指针）"""
        # 第一阶段：找到相遇点
        slow = nums[0]
        fast = nums[0]
        
        while True:
            slow = nums[slow]          # 慢指针走一步
            fast = nums[nums[fast]]    # 快指针走两步
            if slow == fast:
                break
        
        # 第二阶段：找到环的入口（重复数字）
        slow = nums[0]  # 慢指针回到起点
        while slow != fast:
            slow = nums[slow]
            fast = nums[fast]
        
        return slow
    
    def findDuplicate_binary_search(self, nums: List[int]) -> int:
        """二分查找方法"""
        n = len(nums) - 1  # 数字范围是 1 到 n
        left, right = 1, n
        
        while left < right:
            mid = (left + right) // 2
            
            # 统计小于等于 mid 的数字个数
            count = 0
            for num in nums:
                if num <= mid:
                    count += 1
            
            # 如果计数大于 mid，说明重复数字在左半部分
            if count > mid:
                right = mid
            else:
                left = mid + 1
        
        return left
    
    def findDuplicate_bit_manipulation(self, nums: List[int]) -> int:
        """位运算方法（需要额外空间）"""
        n = len(nums) - 1
        result = 0
        
        # 检查每一位
        for bit in range(32):  # 假设整数在32位范围内
            mask = 1 << bit
            
            # 统计在 nums 中该位为1的个数
            count_nums = 0
            for num in nums:
                if num & mask:
                    count_nums += 1
            
            # 统计在 1..n 中该位为1的个数
            count_range = 0
            for i in range(1, n + 1):
                if i & mask:
                    count_range += 1
            
            # 如果在 nums 中的计数大于在 1..n 中的计数，说明重复数字该位为1
            if count_nums > count_range:
                result |= mask
        
        return result
```

---

### Java 代码实现

```java
class Solution {
    // 方法1：Floyd判圈算法（快慢指针）
    public int findDuplicate(int[] nums) {
        // 第一阶段：找到相遇点
        int slow = nums[0];
        int fast = nums[0];
        
        do {
            slow = nums[slow];          // 慢指针走一步
            fast = nums[nums[fast]];    // 快指针走两步
        } while (slow != fast);
        
        // 第二阶段：找到环的入口（重复数字）
        slow = nums[0];  // 慢指针回到起点
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }
        
        return slow;
    }
    
    // 方法2：二分查找
    public int findDuplicateBinarySearch(int[] nums) {
        int n = nums.length - 1;  // 数字范围是 1 到 n
        int left = 1, right = n;
        
        while (left < right) {
            int mid = left + (right - left) / 2;
            
            // 统计小于等于 mid 的数字个数
            int count = 0;
            for (int num : nums) {
                if (num <= mid) {
                    count++;
                }
            }
            
            // 如果计数大于 mid，说明重复数字在左半部分
            if (count > mid) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        
        return left;
    }
    
    // 方法3：位运算
    public int findDuplicateBitManipulation(int[] nums) {
        int n = nums.length - 1;
        int result = 0;
        
        // 检查每一位（最多检查20位，因为n最大为10^5）
        for (int bit = 0; bit < 32; bit++) {
            int mask = 1 << bit;
            
            // 统计在 nums 中该位为1的个数
            int countNums = 0;
            for (int num : nums) {
                if ((num & mask) != 0) {
                    countNums++;
                }
            }
            
            // 统计在 1..n 中该位为1的个数
            int countRange = 0;
            for (int i = 1; i <= n; i++) {
                if ((i & mask) != 0) {
                    countRange++;
                }
            }
            
            // 如果在 nums 中的计数大于在 1..n 中的计数，说明重复数字该位为1
            if (countNums > countRange) {
                result |= mask;
            }
        }
        
        return result;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>

// 方法1：Floyd判圈算法（快慢指针）
int findDuplicate(int* nums, int numsSize) {
    // 第一阶段：找到相遇点
    int slow = nums[0];
    int fast = nums[0];
    
    do {
        slow = nums[slow];          // 慢指针走一步
        fast = nums[nums[fast]];    // 快指针走两步
    } while (slow != fast);
    
    // 第二阶段：找到环的入口（重复数字）
    slow = nums[0];  // 慢指针回到起点
    while (slow != fast) {
        slow = nums[slow];
        fast = nums[fast];
    }
    
    return slow;
}

// 方法2：二分查找
int findDuplicateBinarySearch(int* nums, int numsSize) {
    int n = numsSize - 1;  // 数字范围是 1 到 n
    int left = 1, right = n;
    
    while (left < right) {
        int mid = left + (right - left) / 2;
        
        // 统计小于等于 mid 的数字个数
        int count = 0;
        for (int i = 0; i < numsSize; i++) {
            if (nums[i] <= mid) {
                count++;
            }
        }
        
        // 如果计数大于 mid，说明重复数字在左半部分
        if (count > mid) {
            right = mid;
        } else {
            left = mid + 1;
        }
    }
    
    return left;
}

// 方法3：位运算
int findDuplicateBitManipulation(int* nums, int numsSize) {
    int n = numsSize - 1;
    int result = 0;
    
    // 检查每一位
    for (int bit = 0; bit < 32; bit++) {
        int mask = 1 << bit;
        
        // 统计在 nums 中该位为1的个数
        int countNums = 0;
        for (int i = 0; i < numsSize; i++) {
            if (nums[i] & mask) {
                countNums++;
            }
        }
        
        // 统计在 1..n 中该位为1的个数
        int countRange = 0;
        for (int i = 1; i <= n; i++) {
            if (i & mask) {
                countRange++;
            }
        }
        
        // 如果在 nums 中的计数大于在 1..n 中的计数，说明重复数字该位为1
        if (countNums > countRange) {
            result |= mask;
        }
    }
    
    return result;
}

// 测试代码
int main() {
    // 测试示例1
    int nums1[] = {1, 3, 4, 2, 2};
    int size1 = sizeof(nums1) / sizeof(nums1[0]);
    int result1 = findDuplicate(nums1, size1);
    printf("测试1 (快慢指针):\n输入: [1,3,4,2,2]\n输出: %d (期望: 2)\n", result1);
    
    int result1_binary = findDuplicateBinarySearch(nums1, size1);
    printf("测试1 (二分查找):\n输出: %d (期望: 2)\n\n", result1_binary);
    
    // 测试示例2
    int nums2[] = {3, 1, 3, 4, 2};
    int size2 = sizeof(nums2) / sizeof(nums2[0]);
    int result2 = findDuplicate(nums2, size2);
    printf("测试2 (快慢指针):\n输入: [3,1,3,4,2]\n输出: %d (期望: 3)\n", result2);
    
    int result2_binary = findDuplicateBinarySearch(nums2, size2);
    printf("测试2 (二分查找):\n输出: %d (期望: 3)\n\n", result2_binary);
    
    // 测试示例3
    int nums3[] = {3, 3, 3, 3, 3};
    int size3 = sizeof(nums3) / sizeof(nums3[0]);
    int result3 = findDuplicate(nums3, size3);
    printf("测试3 (快慢指针):\n输入: [3,3,3,3,3]\n输出: %d (期望: 3)\n", result3);
    
    int result3_binary = findDuplicateBinarySearch(nums3, size3);
    printf("测试3 (二分查找):\n输出: %d (期望: 3)\n\n", result3_binary);
    
    // 测试示例4：边界情况
    int nums4[] = {1, 1};
    int size4 = sizeof(nums4) / sizeof(nums4[0]);
    int result4 = findDuplicate(nums4, size4);
    printf("测试4 (快慢指针):\n输入: [1,1]\n输出: %d (期望: 1)\n", result4);
    
    return 0;
}
```

---

### 复杂度分析

| 方法 | 时间复杂度 | 空间复杂度 | 特点 |
|------|-----------|-----------|------|
| 快慢指针 | O(n) | O(1) | 最优解，满足题目所有要求 |
| 二分查找 | O(nlogn) | O(1) | 不修改数组，思路直观 |
| 位运算 | O(nlogC) | O(1) | C为数字范围，适用于更大范围 |

### 算法详解

#### 1. 快慢指针（Floyd判圈算法）
- **时间复杂度**：O(n)，每个元素最多被访问两次
- **空间复杂度**：O(1)，只用了两个指针
- **适用条件**：数组中的值在 [1, n] 范围内
- **证明**：将数组视为链表，因为值在 [1, n] 范围内，所以索引不会越界

#### 2. 二分查找
- **时间复杂度**：O(nlogn)，每次二分需要遍历整个数组
- **空间复杂度**：O(1)
- **思路**：对于 mid，统计 ≤ mid 的数字个数
  - 如果个数 > mid，说明重复数字在左半部分 [1, mid]
  - 否则，在右半部分 [mid+1, n]

#### 3. 位运算
- **时间复杂度**：O(nlogC)，C 为数字范围的最大值
- **空间复杂度**：O(1)
- **思路**：统计每个位上 1 的个数
  - 正常情况（无重复）：1..n 中每个数字出现一次
  - 有重复数字：重复数字的位会多出现
