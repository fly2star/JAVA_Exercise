# 1980. 找出不同的二进制字符串

**难度: 中等**

## 题目描述
给你一个字符串数组 `nums`，该数组由 n 个互不相同的二进制字符串组成，且每个字符串长度都是 n。请你找出并返回一个长度为 n 且没有出现在 nums 中的二进制字符串。如果存在多种答案，只需返回任意一个即可。

---

## 示例说明
### 示例 1：
输入：nums = ["01", "10"]  
输出："11"  
解释："11" 没有出现在 nums 中。"00" 也是正确答案。

### 示例 2：
输入：nums = ["00", "01"]  
输出："11"  
解释："11" 没有出现在 nums 中。"10" 也是正确答案。

### 示例 3：
输入：nums = ["111", "011", "001"]  
输出："101"  
解释："101" 没有出现在 nums 中。"000"、"010"、"100"、"110" 也是正确答案。

---

## 提示：
- n = nums.length
- 1 ≤ n ≤ 16
- nums[i].length == n
- nums[i] 为 '0' 或 '1'
- nums 中的所有字符串互不相同

---

## 解题思路

### 核心思想
这是一个**构造**问题。总共有 2^n 个可能的长度为 n 的二进制字符串，但 nums 中只包含 n 个，所以一定存在至少一个不在 nums 中的字符串。我们可以使用**康托对角线法**来构造答案。

### 关键观察
- 对角线法：构造一个字符串，使得它的第 i 位与 nums[i] 的第 i 位不同
- 这样构造出的字符串一定与所有 nums[i] 不同，因为它在第 i 位上与 nums[i] 不同
- 这种方法保证构造出的字符串长度为 n，且不在 nums 中

### 算法步骤
1. 创建一个长度为 n 的字符数组 result
2. 遍历 i 从 0 到 n-1：
   - 取 nums[i] 的第 i 个字符
   - 如果该字符是 '0'，则 result[i] = '1'
   - 如果该字符是 '1'，则 result[i] = '0'
3. 将字符数组转换为字符串并返回

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def findDifferentBinaryString(self, nums: List[str]) -> str:
        n = len(nums)
        result = []
        
        for i in range(n):
            # 取 nums[i] 的第 i 位，取反
            if nums[i][i] == '0':
                result.append('1')
            else:
                result.append('0')
        
        return ''.join(result)
```

### Java 代码实现
```java
class Solution {
    public String findDifferentBinaryString(String[] nums) {
        int n = nums.length;
        char[] result = new char[n];
        
        for (int i = 0; i < n; i++) {
            // 取 nums[i] 的第 i 位，取反
            if (nums[i].charAt(i) == '0') {
                result[i] = '1';
            } else {
                result[i] = '0';
            }
        }
        
        return new String(result);
    }
}
```

### C 代码实现
```c
#include <stdlib.h>
#include <string.h>

char* findDifferentBinaryString(char** nums, int numsSize) {
    int n = numsSize;
    char* result = (char*)malloc((n + 1) * sizeof(char));
    
    for (int i = 0; i < n; i++) {
        // 取 nums[i] 的第 i 位，取反
        if (nums[i][i] == '0') {
            result[i] = '1';
        } else {
            result[i] = '0';
        }
    }
    result[n] = '\0';
    
    return result;
}
```

---