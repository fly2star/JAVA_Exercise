# 1356. 根据数字二进制下 1 的数目排序

**难度: 简单**

## 题目描述
给你一个整数数组 `arr`。请你将数组中的元素按照其二进制表示中数字 1 的数目升序排列。如果存在多个数字二进制中 1 的数目相同，则必须将它们按照数值大小升序排列。请你返回排序后的数组。

---

## 示例说明
### 示例 1：
输入：arr = [0,1,2,3,4,5,6,7,8]  
输出：[0,1,2,4,8,3,5,6,7]  
解释：
- [0] 是唯一一个有 0 个 1 的数。
- [1,2,4,8] 都有 1 个 1。
- [3,5,6] 有 2 个 1。
- [7] 有 3 个 1。

### 示例 2：
输入：arr = [1024,512,256,128,64,32,16,8,4,2,1]  
输出：[1,2,4,8,16,32,64,128,256,512,1024]  
解释：数组中所有整数二进制下都只有 1 个 1，所以按照数值大小排序。

### 示例 3：
输入：arr = [10000,10000]  
输出：[10000,10000]

### 示例 4：
输入：arr = [2,3,5,7,11,13,17,19]  
输出：[2,3,5,17,7,11,13,19]

### 示例 5：
输入：arr = [10,100,1000,10000]  
输出：[10,100,10000,10000]

---

## 提示：
- 1 ≤ arr.length ≤ 500
- 0 ≤ arr[i] ≤ 10^4

---

## 解题思路

### 核心思想
自定义排序规则：先按二进制中 1 的个数升序排列，若个数相同则按数值大小升序排列。

### 关键观察
- 需要计算每个数字的二进制表示中 1 的个数
- 排序规则是复合的：主键是 1 的个数，次键是数字本身
- 可以预计算每个数字的 1 的个数，避免重复计算

### 算法步骤
1. 对于数组中的每个数字，计算其二进制中 1 的个数
2. 使用自定义排序规则：
   - 首先比较两个数字的 1 的个数
   - 如果个数相等，则比较数字本身
3. 返回排序后的数组

### 计算 1 的个数的方法
- 内置函数：`bin(x).count('1')` (Python)
- 位运算法：`Integer.bitCount(x)` (Java)
- 手动位运算循环统计

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def sortByBits(self, arr: List[int]) -> List[int]:
        # 使用内置函数计算1的个数，然后按照(1的个数, 数值)排序
        return sorted(arr, key=lambda x: (bin(x).count('1'), x))
```

### Java 代码实现
```java
class Solution {
    public int[] sortByBits(int[] arr) {
        // 将int[]转换为Integer[]以便使用Arrays.sort()的自定义比较器
        Integer[] nums = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            nums[i] = arr[i];
        }
        
        Arrays.sort(nums, (a, b) -> {
            int countA = Integer.bitCount(a);
            int countB = Integer.bitCount(b);
            if (countA != countB) {
                return countA - countB;
            }
            return a - b;
        });
        
        // 转换回int[]
        for (int i = 0; i < arr.length; i++) {
            arr[i] = nums[i];
        }
        return arr;
    }
}
```

### C 代码实现
```c
// 计算二进制中1的个数
int countBits(int num) {
    int count = 0;
    while (num) {
        count += num & 1;
        num >>= 1;
    }
    return count;
}

// 比较函数
int cmp(const void* a, const void* b) {
    int x = *(int*)a;
    int y = *(int*)b;
    int countX = countBits(x);
    int countY = countBits(y);
    
    if (countX != countY) {
        return countX - countY;
    }
    return x - y;
}

int* sortByBits(int* arr, int arrSize, int* returnSize) {
    *returnSize = arrSize;
    qsort(arr, arrSize, sizeof(int), cmp);
    return arr;
}
```

---