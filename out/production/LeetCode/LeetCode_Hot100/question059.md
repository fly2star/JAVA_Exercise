# 33. 搜索旋转排序数组

**难度: 中等**

## 题目描述
整数数组 `nums` 按升序排列，数组中的值 **互不相同**。

在传递给函数之前，`nums` 在预先未知的某个下标 `k`（0 ≤ k < nums.length）上进行了 **向左旋转**，使数组变为 `[nums[k], nums[k+1], ..., nums[n-1], nums[0], nums[1], ..., nums[k-1]]`。例如，`[0,1,2,4,5,6,7]` 在下标 3 处旋转后可能变为 `[4,5,6,7,0,1,2]`。

给你 **旋转后的数组** `nums` 和一个整数 `target`，如果 `nums` 中存在这个目标值 `target`，则返回它的下标，否则返回 `-1`。

你必须设计一个时间复杂度为 **O(log n)** 的算法解决此问题。

---

## 示例说明
### 示例 1：
输入：nums = [4,5,6,7,0,1,2], target = 0  
输出：4

### 示例 2：
输入：nums = [4,5,6,7,0,1,2], target = 3  
输出：-1

### 示例 3：
输入：nums = [1], target = 0  
输出：-1

---

## 提示：
- 1 ≤ nums.length ≤ 5000
- -10^4 ≤ nums[i] ≤ 10^4
- nums 中的每个值都 **独一无二**
- 题目数据保证 nums 在预先未知的某个下标上进行了旋转
- -10^4 ≤ target ≤ 10^4

---

## 解题思路

### 核心思想
虽然整个数组不是完全有序的，但旋转后的数组可以看作是两个有序的子数组拼接而成。通过二分查找，每次根据中间位置判断哪一部分是有序的，然后在有序的部分中判断 target 是否在范围内，从而缩小搜索范围。

### 关键观察
- 旋转后的数组有一个性质：将数组从中间分开，**至少有一半是有序的**
- 通过比较 `nums[left]` 和 `nums[mid]` 可以判断左半部分是否有序
- 如果左半部分有序，判断 target 是否在左半部分范围内；否则 target 可能在右半部分
- 通过这种判断，可以每次将搜索范围缩小一半，达到 O(log n) 的时间复杂度

### 算法步骤
1. 初始化左右指针 `left = 0`, `right = len(nums) - 1`
2. 当 `left <= right` 时循环：
   - 计算中间位置 `mid = left + (right - left) / 2`
   - 如果 `nums[mid] == target`，直接返回 `mid`
   - 判断左半部分是否有序：`nums[left] <= nums[mid]`
     - 如果左半部分有序：
       - 如果 `nums[left] <= target < nums[mid]`，说明 target 在左半部分，`right = mid - 1`
       - 否则 target 在右半部分，`left = mid + 1`
     - 否则右半部分有序：
       - 如果 `nums[mid] < target <= nums[right]`，说明 target 在右半部分，`left = mid + 1`
       - 否则 target 在左半部分，`right = mid - 1`
3. 循环结束仍未找到，返回 `-1`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def search(self, nums: List[int], target: int) -> int:
        if not nums:
            return -1
        
        left, right = 0, len(nums) - 1
        
        while left <= right:
            mid = left + (right - left) // 2
            
            # 找到目标值
            if nums[mid] == target:
                return mid
            
            # 判断左半部分是否有序
            if nums[left] <= nums[mid]:
                # 左半部分有序
                if nums[left] <= target < nums[mid]:
                    right = mid - 1
                else:
                    left = mid + 1
            else:
                # 右半部分有序
                if nums[mid] < target <= nums[right]:
                    left = mid + 1
                else:
                    right = mid - 1
        
        return -1
```

### Java 代码实现
```java
class Solution {
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        
        int left = 0, right = nums.length - 1;
        
        while (left <= right) {
            int mid = left + (right - left) / 2;
            
            // 找到目标值
            if (nums[mid] == target) {
                return mid;
            }
            
            // 判断左半部分是否有序
            if (nums[left] <= nums[mid]) {
                // 左半部分有序
                // 判断 target 是否在左边的有序区间内
                if (nums[left] <= target && target < nums[mid]) {
                    right = mid - 1;    // 在左边
                } else {
                    left = mid + 1;     // 去右边找
                }
            } else {
                // 右半部分有序
                // 判断 target 是否在右边的有序区间内
                if (nums[mid] < target && target <= nums[right]) {
                    left = mid + 1;     // 在右边
                } else {
                    right = mid - 1;    // 去左边找
                }
            }
        }
        
        return -1;
    }
}
```

### C 代码实现
```c
int search(int* nums, int numsSize, int target) {
    if (numsSize == 0) {
        return -1;
    }
    
    int left = 0, right = numsSize - 1;
    
    while (left <= right) {
        int mid = left + (right - left) / 2;
        
        // 找到目标值
        if (nums[mid] == target) {
            return mid;
        }
        
        // 判断左半部分是否有序
        if (nums[left] <= nums[mid]) {
            // 左半部分有序
            if (nums[left] <= target && target < nums[mid]) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        } else {
            // 右半部分有序
            if (nums[mid] < target && target <= nums[right]) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
    }
    
    return -1;
}
```

---