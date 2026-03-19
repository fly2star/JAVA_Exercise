# 283. 移动零

**难度: 简单**

## 题目描述
给定一个数组 `nums`，编写一个函数将所有 `0` 移动到数组的末尾，同时保持非零元素的相对顺序。

请注意，必须在不复制数组的情况下原地对数组进行操作。

---

## 示例说明
### 示例 1：
**输入：** nums = [0,1,0,3,12]  
**输出：** [1,3,12,0,0]  
**解释：**
- 将所有非零元素移到前面：1,3,12
- 剩余位置填充0：0,0
- 非零元素的相对顺序保持不变

---

### 示例 2：
**输入：** nums = [0]  
**输出：** [0]  
**解释：** 数组只有一个元素，已经是0

---

### 示例 3：
**输入：** nums = [1,0,2,0,3,0,4]  
**输出：** [1,2,3,4,0,0,0]  
**解释：**
- 非零元素：1,2,3,4
- 移动到前面后填充0

---

### 示例 4：
**输入：** nums = [1,2,3,4]  
**输出：** [1,2,3,4]  
**解释：** 没有0，数组保持不变

---

## 提示：
- 1 ≤ nums.length ≤ 10⁴
- -2³¹ ≤ nums[i] ≤ 2³¹ - 1

---

## 解题思路

### 核心思想
使用双指针技巧，一个指针遍历数组，另一个指针记录下一个非零元素应该放置的位置。

### 关键观察
1. 需要保持非零元素的相对顺序
2. 所有零应该移动到数组末尾
3. 可以分两个步骤：
   - 将所有非零元素移动到前面
   - 将剩余位置填充为0

### 算法步骤（双指针方法）
1. 初始化指针 `left = 0`，指向下一个非零元素应该放置的位置
2. 遍历数组，对于每个元素 `nums[i]`：
   - 如果 `nums[i] != 0`：
     - 将 `nums[i]` 移动到 `nums[left]`
     - `left` 向右移动一位
3. 将 `left` 之后的所有位置填充为0

### 算法优化（一次遍历）
实际上可以在一次遍历中完成：
1. 初始化 `left = 0`
2. 遍历数组：
   - 如果当前元素非零：
     - 交换 `nums[left]` 和 `nums[i]`
     - `left` 向右移动一位
3. 这样保证 `left` 左边都是非零元素，且相对顺序不变

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List

class Solution:
    def moveZeroes(self, nums: List[int]) -> None:
        """
        Do not return anything, modify nums in-place instead.
        """
        # 方法1：双指针，先移动非零元素，再填充0
        def method1(nums):
            n = len(nums)
            left = 0  # 指向下一个非零元素应该放置的位置
            
            # 第一步：将所有非零元素移到前面
            for i in range(n):
                if nums[i] != 0:
                    nums[left] = nums[i]
                    left += 1
            
            # 第二步：将剩余位置填充为0
            for i in range(left, n):
                nums[i] = 0
        
        # 方法2：一次遍历，交换元素
        def method2(nums):
            left = 0  # 指向下一个非零元素应该放置的位置
            
            for i in range(len(nums)):
                if nums[i] != 0:
                    # 交换 nums[left] 和 nums[i]
                    nums[left], nums[i] = nums[i], nums[left]
                    left += 1
        
        # 方法3：更简洁的一次遍历
        def method3(nums):
            left = 0
            for i in range(len(nums)):
                if nums[i] != 0:
                    if i != left:  # 避免不必要的交换
                        nums[left], nums[i] = nums[i], nums[left]
                    left += 1
        
        # 使用方法2
        method2(nums)
    
    def moveZeroes_two_pass(self, nums: List[int]) -> None:
        """两遍扫描方法"""
        # 第一遍：移动非零元素
        left = 0
        for i in range(len(nums)):
            if nums[i] != 0:
                nums[left] = nums[i]
                left += 1
        
        # 第二遍：填充0
        for i in range(left, len(nums)):
            nums[i] = 0
```

---

### Java 代码实现

```java
class Solution {
    // 方法1：一次遍历，交换元素
    public void moveZeroes(int[] nums) {
        int left = 0;  // 指向下一个非零元素应该放置的位置
        
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                // 交换 nums[left] 和 nums[i]
                int temp = nums[left];
                nums[left] = nums[i];
                nums[i] = temp;
                left++;
            }
        }
    }
    
    // 方法2：两遍扫描方法
    public void moveZeroesTwoPass(int[] nums) {
        // 第一遍：移动非零元素
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                nums[left] = nums[i];
                left++;
            }
        }
        
        // 第二遍：填充0
        for (int i = left; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
    
    // 方法3：优化的一次遍历，避免不必要的交换
    public void moveZeroesOptimized(int[] nums) {
        int left = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i != left) {  // 避免不必要的交换
                    nums[left] = nums[i];
                    nums[i] = 0;
                }
                left++;
            }
        }
    }
    
    // 方法4：使用快慢指针
    public void moveZeroesTwoPointers(int[] nums) {
        int slow = 0;  // 慢指针：指向当前处理的位置
        int fast = 0;  // 快指针：遍历数组
        
        while (fast < nums.length) {
            if (nums[fast] != 0) {
                if (slow != fast) {
                    nums[slow] = nums[fast];
                    nums[fast] = 0;
                }
                slow++;
            }
            fast++;
        }
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>

// 方法1：一次遍历，交换元素
void moveZeroes(int* nums, int numsSize) {
    int left = 0;  // 指向下一个非零元素应该放置的位置
    
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] != 0) {
            // 交换 nums[left] 和 nums[i]
            int temp = nums[left];
            nums[left] = nums[i];
            nums[i] = temp;
            left++;
        }
    }
}

// 方法2：两遍扫描方法
void moveZeroesTwoPass(int* nums, int numsSize) {
    // 第一遍：移动非零元素
    int left = 0;
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] != 0) {
            nums[left] = nums[i];
            left++;
        }
    }
    
    // 第二遍：填充0
    for (int i = left; i < numsSize; i++) {
        nums[i] = 0;
    }
}

// 方法3：优化的一次遍历，避免不必要的交换
void moveZeroesOptimized(int* nums, int numsSize) {
    int left = 0;
    for (int i = 0; i < numsSize; i++) {
        if (nums[i] != 0) {
            if (i != left) {  // 避免不必要的交换
                nums[left] = nums[i];
                nums[i] = 0;
            }
            left++;
        }
    }
}

// 方法4：使用快慢指针
void moveZeroesTwoPointers(int* nums, int numsSize) {
    int slow = 0;  // 慢指针：指向当前处理的位置
    int fast = 0;  // 快指针：遍历数组
    
    while (fast < numsSize) {
        if (nums[fast] != 0) {
            if (slow != fast) {
                nums[slow] = nums[fast];
                nums[fast] = 0;
            }
            slow++;
        }
        fast++;
    }
}

// 打印数组
void printArray(int* nums, int numsSize) {
    printf("[");
    for (int i = 0; i < numsSize; i++) {
        printf("%d", nums[i]);
        if (i < numsSize - 1) {
            printf(", ");
        }
    }
    printf("]");
}

// 测试代码
int main() {
    // 测试示例1
    int nums1[] = {0, 1, 0, 3, 12};
    int size1 = sizeof(nums1) / sizeof(nums1[0]);
    
    printf("测试1:\n");
    printf("输入: ");
    printArray(nums1, size1);
    printf("\n");
    
    moveZeroes(nums1, size1);
    
    printf("输出: ");
    printArray(nums1, size1);
    printf(" (期望: [1, 3, 12, 0, 0])\n\n");
    
    // 测试示例2
    int nums2[] = {0};
    int size2 = sizeof(nums2) / sizeof(nums2[0]);
    
    printf("测试2:\n");
    printf("输入: ");
    printArray(nums2, size2);
    printf("\n");
    
    moveZeroes(nums2, size2);
    
    printf("输出: ");
    printArray(nums2, size2);
    printf(" (期望: [0])\n\n");
    
    // 测试示例3
    int nums3[] = {1, 0, 2, 0, 3, 0, 4};
    int size3 = sizeof(nums3) / sizeof(nums3[0]);
    
    printf("测试3:\n");
    printf("输入: ");
    printArray(nums3, size3);
    printf("\n");
    
    moveZeroesTwoPass(nums3, size3);
    
    printf("输出: ");
    printArray(nums3, size3);
    printf(" (期望: [1, 2, 3, 4, 0, 0, 0])\n\n");
    
    // 测试示例4：没有0的情况
    int nums4[] = {1, 2, 3, 4};
    int size4 = sizeof(nums4) / sizeof(nums4[0]);
    
    printf("测试4:\n");
    printf("输入: ");
    printArray(nums4, size4);
    printf("\n");
    
    moveZeroesOptimized(nums4, size4);
    
    printf("输出: ");
    printArray(nums4, size4);
    printf(" (期望: [1, 2, 3, 4])\n\n");
    
    // 测试示例5：全是0的情况
    int nums5[] = {0, 0, 0, 0};
    int size5 = sizeof(nums5) / sizeof(nums5[0]);
    
    printf("测试5:\n");
    printf("输入: ");
    printArray(nums5, size5);
    printf("\n");
    
    moveZeroesTwoPointers(nums5, size5);
    
    printf("输出: ");
    printArray(nums5, size5);
    printf(" (期望: [0, 0, 0, 0])\n");
    
    return 0;
}
```

---

### 复杂度分析
| 方法 | 时间复杂度 | 空间复杂度 | 交换次数 | 特点 |
|------|-----------|-----------|----------|------|
| 两遍扫描 | O(n) | O(1) | 0次交换 | 简单直观，需要两次遍历 |
| 一次遍历交换 | O(n) | O(1) | 最多n次交换 | 一次遍历，但交换次数可能较多 |
| 优化一次遍历 | O(n) | O(1) | 最少交换 | 避免不必要的交换，最优 |

### 算法详解

#### 1. 两遍扫描方法
1. **第一遍**：移动所有非零元素到前面
   - 使用指针 `left` 记录下一个非零元素的位置
   - 遍历数组，遇到非零元素就放到 `nums[left]`，然后 `left++`
2. **第二遍**：填充0
   - 从 `left` 开始到数组末尾，全部填充为0

#### 2. 一次遍历交换方法
- 使用双指针：`left` 指向下一个非零元素位置，`i` 遍历数组
- 当 `nums[i] != 0` 时，交换 `nums[left]` 和 `nums[i]`，然后 `left++`
- 这样可以保证 `left` 左边都是非零元素，且相对顺序不变

#### 3. 优化的一次遍历
- 在交换前检查 `i != left`，避免相同位置的交换
- 如果 `i == left`，说明不需要交换，直接 `left++`
- 这样可以减少不必要的操作
