# 31. 下一个排列

**难度: 中等**

## 题目描述
整数数组的一个 **排列** 就是将其所有成员以序列或线性顺序排列。

整数数组的下一个排列是指其整数的下一个字典序更大的排列。更正式地，如果数组的所有排列根据字典顺序从小到大排列在一个容器中，那么数组的下一个排列就是这个有序容器中排在它后面的那个排列。如果不存在下一个更大的排列，那么这个数组必须重新为字典序最小的排列（即，其元素按升序排列）。

必须原地修改，只允许使用额外常数空间。

---

## 示例说明
### 示例 1：
输入：nums = [1,2,3]  
输出：[1,3,2]

### 示例 2：
输入：nums = [3,2,1]  
输出：[1,2,3]

### 示例 3：
输入：nums = [1,1,5]  
输出：[1,5,1]

---

## 提示：
- 1 ≤ nums.length ≤ 100
- 0 ≤ nums[i] ≤ 100

---

## 解题思路

### 核心思想
寻找下一个排列的算法可以总结为以下步骤：**从右向左找到第一个升序对，然后在右侧找到比它大的最小数交换，最后将右侧序列反转**。

### 关键观察
- 下一个排列比当前排列要大，但又要尽可能小
- 需要从右向左找到第一个下降的位置，因为越靠右的变化影响越小
- 找到下降点后，需要在右侧找到比它大的最小数交换
- 交换后，右侧应该是降序的，需要反转成升序，使其成为最小的可能

### 算法步骤
1. 从右向左遍历，找到第一个满足 `nums[i] < nums[i+1]` 的位置 `i`（即找到需要增大的位置）
2. 如果找不到这样的 `i`，说明整个数组是降序的，已经是最大排列，直接反转整个数组返回
3. 从右向左遍历，找到第一个大于 `nums[i]` 的数 `nums[j]`
4. 交换 `nums[i]` 和 `nums[j]`
5. 将 `i+1` 到末尾的元素反转（因为原来它们是降序的，反转后变成升序，即最小排列）

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def nextPermutation(self, nums: List[int]) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        n = len(nums)
        
        # 1. 从右向左找到第一个升序对
        i = n - 2
        while i >= 0 and nums[i] >= nums[i + 1]:
            i -= 1
        
        # 2. 如果找到了这样的 i
        if i >= 0:
            # 3. 从右向左找到第一个大于 nums[i] 的数
            j = n - 1
            while j >= 0 and nums[j] <= nums[i]:
                j -= 1
            # 4. 交换
            nums[i], nums[j] = nums[j], nums[i]
        
        # 5. 反转 i+1 到末尾的部分
        left, right = i + 1, n - 1
        while left < right:
            nums[left], nums[right] = nums[right], nums[left]
            left += 1
            right -= 1
```

### Java 代码实现
```java
class Solution {
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        
        // 1. 从右向左找到第一个升序对
        int i = n - 2;
        while (i >= 0 && nums[i] >= nums[i + 1]) {
            i--;
        }
        
        // 2. 如果找到了这样的 i
        if (i >= 0) {
            // 3. 从右向左找到第一个大于 nums[i] 的数
            int j = n - 1;
            while (j >= 0 && nums[j] <= nums[i]) {
                j--;
            }
            // 4. 交换
            swap(nums, i, j);
        }
        
        // 5. 反转 i+1 到末尾的部分
        reverse(nums, i + 1, n - 1);
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }
}
```

### C 代码实现
```c
void swap(int* nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}

void reverse(int* nums, int left, int right) {
    while (left < right) {
        swap(nums, left, right);
        left++;
        right--;
    }
}

void nextPermutation(int* nums, int numsSize) {
    // 1. 从右向左找到第一个升序对
    int i = numsSize - 2;
    while (i >= 0 && nums[i] >= nums[i + 1]) {
        i--;
    }
    
    // 2. 如果找到了这样的 i
    if (i >= 0) {
        // 3. 从右向左找到第一个大于 nums[i] 的数
        int j = numsSize - 1;
        while (j >= 0 && nums[j] <= nums[i]) {
            j--;
        }
        // 4. 交换
        swap(nums, i, j);
    }
    
    // 5. 反转 i+1 到末尾的部分
    reverse(nums, i + 1, numsSize - 1);
}
```

---