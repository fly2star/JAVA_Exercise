# 4. 寻找两个正序数组的中位数

**难度: 困难**

## 题目描述
给定两个大小分别为 `m` 和 `n` 的正序（从小到大）数组 `nums1` 和 `nums2`。请你找出并返回这两个正序数组的中位数。

算法的时间复杂度应该为 `O(log (m+n))`。

---

## 示例说明
### 示例 1：
输入：nums1 = [1,3], nums2 = [2]  
输出：2.00000  
解释：合并数组 = [1,2,3]，中位数 2

### 示例 2：
输入：nums1 = [1,2], nums2 = [3,4]  
输出：2.50000  
解释：合并数组 = [1,2,3,4]，中位数 (2 + 3) / 2 = 2.5

---

## 提示：
- nums1.length == m
- nums2.length == n
- 0 ≤ m ≤ 1000
- 0 ≤ n ≤ 1000
- 1 ≤ m + n ≤ 2000
- -10^6 ≤ nums1[i], nums2[i] ≤ 10^6

---

## 解题思路

### 核心思想
使用**二分查找**的思想，在两个有序数组中找到第 k 小的数。中位数就是第 (m+n+1)/2 小的数和第 (m+n+2)/2 小的数的平均值。

### 关键观察
- 要在 O(log(m+n)) 时间内解决，必须利用二分查找
- 可以转化为寻找两个有序数组中第 k 小的元素
- 比较两个数组的第 k/2 个元素，可以排除掉较小元素所在数组的前 k/2 个元素

### 算法步骤
1. 确保 nums1 是较短的数组（如果不是，交换）
2. 计算总长度 `total = m + n`
3. 寻找第 `(total + 1) // 2` 小的数和第 `(total + 2) // 2` 小的数，取平均值
4. 定义函数 `findKth(k)` 寻找第 k 小的数：
   - 如果其中一个数组为空，直接返回另一个数组的第 k 个元素
   - 如果 k == 1，返回两个数组第一个元素的最小值
   - 取两个数组的第 k/2 个元素进行比较（注意边界）
   - 如果 nums1 的第 k/2 个元素小于 nums2 的第 k/2 个元素，说明 nums1 的前 k/2 个元素都可以排除
   - 递归查找剩余部分

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def findMedianSortedArrays(self, nums1: List[int], nums2: List[int]) -> float:
        # 确保 nums1 是较短的数组
        if len(nums1) > len(nums2):
            nums1, nums2 = nums2, nums1
        
        m, n = len(nums1), len(nums2)
        total = m + n
        
        # 中位数是第 k 小的数
        def findKth(k):
            # 在 nums1 和 nums2 中找第 k 小的数
            index1, index2 = 0, 0
            
            while True:
                # 边界情况
                if index1 == m:
                    return nums2[index2 + k - 1]
                if index2 == n:
                    return nums1[index1 + k - 1]
                if k == 1:
                    return min(nums1[index1], nums2[index2])
                
                # 正常情况，比较两个数组的第 k/2 个元素
                newIndex1 = min(index1 + k // 2 - 1, m - 1)
                newIndex2 = min(index2 + k // 2 - 1, n - 1)
                
                if nums1[newIndex1] <= nums2[newIndex2]:
                    k -= newIndex1 - index1 + 1
                    index1 = newIndex1 + 1
                else:
                    k -= newIndex2 - index2 + 1
                    index2 = newIndex2 + 1
        
        # 如果总长度是奇数，中位数就是第 (total+1)//2 小的数
        # 如果总长度是偶数，中位数是第 total//2 和 total//2+1 小的数的平均值
        left = (total + 1) // 2
        right = (total + 2) // 2
        
        return (findKth(left) + findKth(right)) / 2
```

### Java 代码实现
```java
class Solution {
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        
        // 如果总长度是奇数，中位数就是第 (total+1)//2 小的数
        // 如果总长度是偶数，中位数是第 total//2 和 total//2+1 小的数的平均值
        int left = (total + 1) / 2;
        int right = (total + 2) / 2;
        
        return (findKth(nums1, 0, m - 1, nums2, 0, n - 1, left) + 
                findKth(nums1, 0, m - 1, nums2, 0, n - 1, right)) / 2.0;
    }
    
    private int findKth(int[] nums1, int start1, int end1, 
                        int[] nums2, int start2, int end2, int k) {
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;
        
        // 确保 nums1 是较短的数组
        if (len1 > len2) {
            return findKth(nums2, start2, end2, nums1, start1, end1, k);
        }
        
        // 如果 nums1 为空
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }
        
        // 如果 k == 1
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }
        
        // 比较两个数组的第 k/2 个元素
        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;
        
        if (nums1[i] > nums2[j]) {
            return findKth(nums1, start1, end1, nums2, j + 1, end2, k - (j - start2 + 1));
        } else {
            return findKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }
    }
}
```

### C 代码实现
```c
int findKth(int* nums1, int m, int* nums2, int n, int k) {
    // 确保 nums1 是较短的数组
    if (m > n) {
        return findKth(nums2, n, nums1, m, k);
    }
    
    // 如果 nums1 为空
    if (m == 0) {
        return nums2[k - 1];
    }
    
    // 如果 k == 1
    if (k == 1) {
        return nums1[0] < nums2[0] ? nums1[0] : nums2[0];
    }
    
    // 比较两个数组的第 k/2 个元素
    int i = (m < k / 2) ? m : k / 2;
    int j = (n < k / 2) ? n : k / 2;
    
    if (nums1[i - 1] > nums2[j - 1]) {
        return findKth(nums1, m, nums2 + j, n - j, k - j);
    } else {
        return findKth(nums1 + i, m - i, nums2, n, k - i);
    }
}

double findMedianSortedArrays(int* nums1, int nums1Size, int* nums2, int nums2Size) {
    int total = nums1Size + nums2Size;
    
    if (total % 2 == 1) {
        return findKth(nums1, nums1Size, nums2, nums2Size, total / 2 + 1);
    } else {
        int left = findKth(nums1, nums1Size, nums2, nums2Size, total / 2);
        int right = findKth(nums1, nums1Size, nums2, nums2Size, total / 2 + 1);
        return (left + right) / 2.0;
    }
}
```

---