# 189. 轮转数组

**难度: 中等**

## 题目描述
给定一个整数数组 `nums`，将数组中的元素向右旋转 `k` 个位置，其中 `k` 是非负数。

---

## 示例说明
### 示例 1：
输入：nums = [1,2,3,4,5,6,7], k = 3  
输出：[5,6,7,1,2,3,4]  
解释：
- 向右旋转 1 步：[7,1,2,3,4,5,6]
- 向右旋转 2 步：[6,7,1,2,3,4,5]
- 向右旋转 3 步：[5,6,7,1,2,3,4]

### 示例 2：
输入：nums = [-1,-100,3,99], k = 2  
输出：[3,99,-1,-100]  
解释：
- 向右旋转 1 步：[99,-1,-100,3]
- 向右旋转 2 步：[3,99,-1,-100]

---

## 提示：
- 1 ≤ nums.length ≤ 10^5
- -2^31 ≤ nums[i] ≤ 2^31 - 1
- 0 ≤ k ≤ 10^5

---

## 解题思路

### 核心思想
将数组向右旋转 k 步，等价于将数组的后 k 个元素移动到前面。需要注意 k 可能大于数组长度，因此实际移动步数为 `k % n`。有多种方法实现：使用额外数组、多次反转、或环状替换。

### 关键观察
- **方法一（额外数组）**：创建一个新数组，将原数组元素放到正确位置，再复制回去。时间 O(n)，空间 O(n)。
- **方法二（多次反转）**：
  - 反转整个数组
  - 反转前 k 个元素
  - 反转后 n-k 个元素
  例如：`[1,2,3,4,5,6,7]`, k=3 → 全反转 `[7,6,5,4,3,2,1]` → 反转前3个 `[5,6,7,4,3,2,1]` → 反转后4个 `[5,6,7,1,2,3,4]`。
- **方法三（环状替换）**：直接计算每个元素最终位置，用循环替换。时间 O(n)，空间 O(1)。

### 算法步骤（反转法）
1. 取 `k = k % n`，避免无效移动
2. 反转整个数组：`reverse(nums, 0, n-1)`
3. 反转前 k 个元素：`reverse(nums, 0, k-1)`
4. 反转后 n-k 个元素：`reverse(nums, k, n-1)`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def rotate(self, nums: List[int], k: int) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        n = len(nums)
        k %= n
        if k == 0:
            return
        
        def reverse(l, r):
            while l < r:
                nums[l], nums[r] = nums[r], nums[l]
                l += 1
                r -= 1
        
        reverse(0, n-1)
        reverse(0, k-1)
        reverse(k, n-1)
```

### Java 代码实现
```java
class Solution {
    public void rotate(int[] nums, int k) {
        int n = nums.length;
        k %= n;
        if (k == 0) return;
        
        reverse(nums, 0, n - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, n - 1);
    }
    
    private void reverse(int[] nums, int left, int right) {
        while (left < right) {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }
}
```

### C 代码实现
```c
void reverse(int* nums, int left, int right) {
    while (left < right) {
        int temp = nums[left];
        nums[left] = nums[right];
        nums[right] = temp;
        left++;
        right--;
    }
}

void rotate(int* nums, int numsSize, int k) {
    k %= numsSize;
    if (k == 0) return;
    
    reverse(nums, 0, numsSize - 1);
    reverse(nums, 0, k - 1);
    reverse(nums, k, numsSize - 1);
}
```

---