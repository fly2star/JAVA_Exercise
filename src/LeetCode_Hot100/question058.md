# 34. 在排序数组中查找元素的第一个和最后一个位置

**难度: 中等**

## 题目描述
给你一个按照非递减顺序排列的整数数组 `nums`，和一个目标值 `target`。请你找出给定目标值在数组中的开始位置和结束位置。

如果数组中不存在目标值 `target`，返回 `[-1, -1]`。

你必须设计并实现时间复杂度为 \( O(\log n) \) 的算法解决此问题。

---

## 示例说明
### 示例 1：
输入：nums = [5,7,7,8,8,10], target = 8  
输出：[3,4]

### 示例 2：
输入：nums = [5,7,7,8,8,10], target = 6  
输出：[-1,-1]

### 示例 3：
输入：nums = [], target = 0  
输出：[-1,-1]

---

## 提示：
- 0 ≤ nums.length ≤ 10^5
- -10^9 ≤ nums[i] ≤ 10^9
- nums 是一个非递减数组
- -10^9 ≤ target ≤ 10^9

---

## 解题思路

### 核心思想
由于数组已排序且要求 O(log n) 的时间复杂度，很自然地想到使用**二分查找**。我们需要分别找到目标值的**第一个出现位置**（左边界）和**最后一个出现位置**（右边界）。

### 关键观察
- 标准的二分查找找到一个目标值后，无法确定是否是第一个或最后一个
- 需要分别实现两个二分查找：
  - 找左边界：当 `nums[mid] == target` 时，收缩右边界 `right = mid - 1`
  - 找右边界：当 `nums[mid] == target` 时，收缩左边界 `left = mid + 1`
- 需要注意边界条件和数组为空的情况

### 算法步骤
1. 实现二分查找左边界函数 `findLeft(nums, target)`：
   - 初始化 `left = 0, right = len(nums) - 1`
   - 当 `left <= right` 时循环：
     - `mid = left + (right - left) // 2`
     - 如果 `nums[mid] < target`，`left = mid + 1`
     - 如果 `nums[mid] > target`，`right = mid - 1`
     - 如果 `nums[mid] == target`，`right = mid - 1`（继续向左搜索）
   - 循环结束后，检查 `left` 是否越界且 `nums[left] == target`，是则返回 `left`，否则返回 -1

2. 实现二分查找右边界函数 `findRight(nums, target)`：
   - 初始化 `left = 0, right = len(nums) - 1`
   - 当 `left <= right` 时循环：
     - `mid = left + (right - left) // 2`
     - 如果 `nums[mid] < target`，`left = mid + 1`
     - 如果 `nums[mid] > target`，`right = mid - 1`
     - 如果 `nums[mid] == target`，`left = mid + 1`（继续向右搜索）
   - 循环结束后，检查 `right` 是否越界且 `nums[right] == target`，是则返回 `right`，否则返回 -1

3. 返回 `[leftIndex, rightIndex]`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def searchRange(self, nums: List[int], target: int) -> List[int]:
        if not nums:
            return [-1, -1]
        
        def find_left():
            left, right = 0, len(nums) - 1
            while left <= right:
                mid = left + (right - left) // 2
                if nums[mid] < target:
                    left = mid + 1
                elif nums[mid] > target:
                    right = mid - 1
                else:  # nums[mid] == target
                    right = mid - 1  # 继续向左搜索
            # 检查 left 是否越界且等于 target
            if left < len(nums) and nums[left] == target:
                return left
            return -1
        
        def find_right():
            left, right = 0, len(nums) - 1
            while left <= right:
                mid = left + (right - left) // 2
                if nums[mid] < target:
                    left = mid + 1
                elif nums[mid] > target:
                    right = mid - 1
                else:  # nums[mid] == target
                    left = mid + 1  # 继续向右搜索
            # 检查 right 是否越界且等于 target
            if right >= 0 and nums[right] == target:
                return right
            return -1
        
        return [find_left(), find_right()]
```

### Java 代码实现
```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] result = new int[]{-1, -1};
        if (nums == null || nums.length == 0) {
            return result;
        }
        
        result[0] = findLeft(nums, target);
        result[1] = findRight(nums, target);
        return result;
    }
    
    private int findLeft(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                right = mid - 1; // 继续向左搜索
            }
        }
        // 检查 left 是否越界且等于 target
        if (left < nums.length && nums[left] == target) {
            return left;
        }
        return -1;
    }
    
    private int findRight(int[] nums, int target) {
        int left = 0, right = nums.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1; // 继续向右搜索
            }
        }
        // 检查 right 是否越界且等于 target
        if (right >= 0 && nums[right] == target) {
            return right;
        }
        return -1;
    }
}
```

### C 代码实现
```c
/**
 * Note: The returned array must be malloced, assume caller calls free().
 */

int findLeft(int* nums, int numsSize, int target) {
    int left = 0, right = numsSize - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid - 1;
        } else {
            right = mid - 1; // 继续向左搜索
        }
    }
    // 检查 left 是否越界且等于 target
    if (left < numsSize && nums[left] == target) {
        return left;
    }
    return -1;
}

int findRight(int* nums, int numsSize, int target) {
    int left = 0, right = numsSize - 1;
    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (nums[mid] < target) {
            left = mid + 1;
        } else if (nums[mid] > target) {
            right = mid - 1;
        } else {
            left = mid + 1; // 继续向右搜索
        }
    }
    // 检查 right 是否越界且等于 target
    if (right >= 0 && nums[right] == target) {
        return right;
    }
    return -1;
}

int* searchRange(int* nums, int numsSize, int target, int* returnSize) {
    int* result = (int*)malloc(2 * sizeof(int));
    *returnSize = 2;
    
    if (numsSize == 0) {
        result[0] = -1;
        result[1] = -1;
        return result;
    }
    
    result[0] = findLeft(nums, numsSize, target);
    result[1] = findRight(nums, numsSize, target);
    
    return result;
}
```

---