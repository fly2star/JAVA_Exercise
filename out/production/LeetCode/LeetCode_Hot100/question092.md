# 75. 颜色分类

**难度: 中等**

## 题目描述
给定一个包含红色、白色和蓝色、共 `n` 个元素的数组 `nums`，原地对它们进行排序，使得相同颜色的元素相邻，并按照红色、白色、蓝色顺序排列。

我们使用整数 `0`、`1` 和 `2` 分别表示红色、白色和蓝色。

必须在不使用库内置的 `sort` 函数的情况下解决这个问题。

---

## 示例说明
### 示例 1：
输入：nums = [2,0,2,1,1,0]  
输出：[0,0,1,1,2,2]

### 示例 2：
输入：nums = [2,0,1]  
输出：[0,1,2]

---

## 提示：
- n = nums.length
- 1 ≤ n ≤ 300
- nums[i] 为 0、1 或 2

---

## 解题思路

### 核心思想
这是一个经典的**荷兰国旗问题**，可以使用**三指针**方法在一次遍历中完成排序。用两个指针分别指向 0 的右边界和 2 的左边界，用一个遍历指针扫描数组。

### 关键观察
- 最终结果应该是：所有 0 在最左边，所有 2 在最右边，1 在中间
- 使用指针 `p0` 指向下一个 0 应该放置的位置
- 使用指针 `p2` 指向下一个 2 应该放置的位置
- 使用指针 `i` 遍历数组

### 算法步骤
1. 初始化 `p0 = 0`，`p2 = n - 1`，`i = 0`
2. 当 `i <= p2` 时循环：
   - 如果 `nums[i] == 0`：
     - 交换 `nums[i]` 和 `nums[p0]`
     - `p0++`，`i++`
   - 如果 `nums[i] == 2`：
     - 交换 `nums[i]` 和 `nums[p2]`
     - `p2--`
     - （注意：此时不移动 i，因为交换过来的数还需要检查）
   - 如果 `nums[i] == 1`：
     - `i++`
3. 数组排序完成

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def sortColors(self, nums: List[int]) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        n = len(nums)
        p0, p2 = 0, n - 1
        i = 0
        
        while i <= p2:
            if nums[i] == 0:
                # 将 0 交换到前面
                nums[i], nums[p0] = nums[p0], nums[i]
                p0 += 1
                i += 1
            elif nums[i] == 2:
                # 将 2 交换到后面
                nums[i], nums[p2] = nums[p2], nums[i]
                p2 -= 1
                # 注意：这里不移动 i，因为交换过来的数还需要检查
            else:  # nums[i] == 1
                i += 1
```

### Java 代码实现
```java
class Solution {
    public void sortColors(int[] nums) {
        int n = nums.length;
        int p0 = 0, p2 = n - 1;
        int i = 0;
        
        while (i <= p2) {
            if (nums[i] == 0) {
                // 将 0 交换到前面
                swap(nums, i, p0);
                p0++;
                i++;
            } else if (nums[i] == 2) {
                // 将 2 交换到后面
                swap(nums, i, p2);
                p2--;
                // 注意：这里不移动 i，因为交换过来的数还需要检查
            } else { // nums[i] == 1
                i++;
            }
        }
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
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

void sortColors(int* nums, int numsSize) {
    int p0 = 0, p2 = numsSize - 1;
    int i = 0;
    
    while (i <= p2) {
        if (nums[i] == 0) {
            // 将 0 交换到前面
            swap(nums, i, p0);
            p0++;
            i++;
        } else if (nums[i] == 2) {
            // 将 2 交换到后面
            swap(nums, i, p2);
            p2--;
            // 注意：这里不移动 i，因为交换过来的数还需要检查
        } else { // nums[i] == 1
            i++;
        }
    }
}
```

---