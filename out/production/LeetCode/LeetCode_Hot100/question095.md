# 581. 最短无序连续子数组

**难度: 中等**

## 题目描述
给你一个整数数组 `nums`，你需要找出一个 **连续子数组**，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。

请你找出符合题意的最 **短** 子数组，并输出它的长度。

---

## 示例说明
### 示例 1：
输入：nums = [2,6,4,8,10,9,15]  
输出：5  
解释：你只需要对 [6,4,8,10,9] 进行升序排序，那么整个表都会变为升序排序。

### 示例 2：
输入：nums = [1,2,3,4]  
输出：0

### 示例 3：
输入：nums = [1]  
输出：0

---

## 提示：
- 1 ≤ nums.length ≤ 10^4
- -10^5 ≤ nums[i] ≤ 10^5

---

## 解题思路

### 核心思想
这个问题可以转化为找到数组中**需要排序的最短连续子数组**。我们可以通过一次遍历找到无序子数组的左右边界，而不需要对数组进行排序。

### 关键观察
- 最终排序后的数组应该是非递减的
- 如果数组已经有序，答案为 0
- 需要排序的子数组的特点是：子数组的最小值应该大于左边部分的最大值，子数组的最大值应该小于右边部分的最小值
- 可以通过两次遍历找到左右边界：
  - 从左到右找右边界：记录当前最大值，如果当前值小于最大值，说明需要排序，更新右边界
  - 从右到左找左边界：记录当前最小值，如果当前值大于最小值，说明需要排序，更新左边界

### 算法步骤
1. 初始化 left = -1, right = -1
2. 从左到右遍历，记录当前最大值 max：
   - 如果 nums[i] < max，说明 i 位置需要排序，更新 right = i
   - 否则更新 max = nums[i]
3. 从右到左遍历，记录当前最小值 min：
   - 如果 nums[i] > min，说明 i 位置需要排序，更新 left = i
   - 否则更新 min = nums[i]
4. 如果 left == -1，说明数组已经有序，返回 0
5. 否则返回 right - left + 1

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def findUnsortedSubarray(self, nums: List[int]) -> int:
        n = len(nums)
        if n <= 1:
            return 0
        
        left, right = -1, -1
        max_num = float('-inf')
        min_num = float('inf')
        
        # 从左到右找右边界
        for i in range(n):
            if nums[i] < max_num:
                right = i
            else:
                max_num = nums[i]
        
        # 从右到左找左边界
        for i in range(n - 1, -1, -1):
            if nums[i] > min_num:
                left = i
            else:
                min_num = nums[i]
        
        return 0 if left == -1 else right - left + 1
```

### Java 代码实现
```java
class Solution {
    public int findUnsortedSubarray(int[] nums) {
        int n = nums.length;
        if (n <= 1) {
            return 0;
        }
        
        int left = -1, right = -1;
        int max = Integer.MIN_VALUE;
        int min = Integer.MAX_VALUE;
        
        // 从左到右找右边界
        for (int i = 0; i < n; i++) {
            if (nums[i] < max) {
                right = i;
            } else {
                max = nums[i];
            }
        }
        
        // 从右到左找左边界
        for (int i = n - 1; i >= 0; i--) {
            if (nums[i] > min) {
                left = i;
            } else {
                min = nums[i];
            }
        }
        
        return left == -1 ? 0 : right - left + 1;
    }
}
```

### C 代码实现
```c
#include <limits.h>

int findUnsortedSubarray(int* nums, int numsSize) {
    if (numsSize <= 1) {
        return 0;
    }
    
    int left = -1, right = -1;
    int max = INT_MIN;
    int min = INT_MAX;
    
    // 从左到右找右边界
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] < max) {
            right = i;
        } else {
            max = nums[i];
        }
    }
    
    // 从右到左找左边界
    for (int i = numsSize - 1; i >= 0; i--) {
        if (nums[i] > min) {
            left = i;
        } else {
            min = nums[i];
        }
    }
    
    return left == -1 ? 0 : right - left + 1;
}
```

---