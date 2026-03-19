# 1. 两数之和

**难度: 简单**

## 题目描述
给定一个整数数组 `nums` 和一个整数目标值 `target`，请你在该数组中找出和为目标值 `target` 的那两个整数，并返回它们的数组下标。

你可以假设每种输入只会对应一个答案，并且你不能使用两次相同的元素。

你可以按任意顺序返回答案。

---

## 示例说明
### 示例 1：
输入：nums = [2,7,11,15], target = 9  
输出：[0,1]  
解释：因为 nums[0] + nums[1] == 9，返回 [0,1]。

### 示例 2：
输入：nums = [3,2,4], target = 6  
输出：[1,2]

### 示例 3：
输入：nums = [3,3], target = 6  
输出：[0,1]

---

## 提示：
- 2 ≤ nums.length ≤ 10^4
- -10^9 ≤ nums[i] ≤ 10^9
- -10^9 ≤ target ≤ 10^9
- 只会存在一个有效答案

---

## 解题思路

### 核心思想
使用**哈希表**来存储已经遍历过的数字及其下标。在遍历数组时，对于每个数字 `nums[i]`，检查 `target - nums[i]` 是否已经在哈希表中，如果在，说明找到了答案；如果不在，将当前数字和下标存入哈希表。

### 关键观察
- 暴力解法需要 O(n²) 的时间复杂度，不适用于大规模数据
- 哈希表可以将查找时间降到 O(1)，总时间复杂度 O(n)
- 需要注意：不能使用同一个元素两次，但哈希表存储的是已经遍历过的元素，所以不会重复使用

### 算法步骤
1. 创建一个哈希表 `num_map`，键为数字，值为该数字在数组中的下标
2. 遍历数组 `nums`，对于每个元素 `nums[i]`：
   - 计算差值 `complement = target - nums[i]`
   - 如果 `complement` 在哈希表中，返回 `[map[complement], i]`
   - 否则，将 `(nums[i], i)` 存入哈希表
3. 根据题意，一定存在答案，所以不会执行到这里

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def twoSum(self, nums: List[int], target: int) -> List[int]:
        # 创建哈希表，存储数字和对应的下标
        num_map = {}
        
        for i, num in enumerate(nums):
            complement = target - num
            # 检查差值是否在哈希表中
            if complement in num_map:
                return [num_map[complement], i]
            # 将当前数字存入哈希表
            num_map[num] = i
        
        # 根据题意，不会执行到这里
        return []
```

### Java 代码实现
```java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        // 创建哈希表，存储数字和对应的下标
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            // 检查差值是否在哈希表中
            if (numMap.containsKey(complement)) {
                return new int[]{numMap.get(complement), i};
            }
            // 将当前数字存入哈希表
            numMap.put(nums[i], i);
        }
        
        // 根据题意，不会执行到这里
        return new int[0];
    }
}
```

### C 代码实现
```c
/**
 * Note: The returned array must be malloced, assume caller calls free().
 */

// 简单的哈希表实现（适用于本题范围）
typedef struct {
    int key;
    int value;
    UT_hash_handle hh;
} HashEntry;

int* twoSum(int* nums, int numsSize, int target, int* returnSize) {
    *returnSize = 2;
    int* result = (int*)malloc(2 * sizeof(int));
    
    // 创建哈希表
    HashEntry* map = NULL;
    
    for (int i = 0; i < numsSize; i++) {
        int complement = target - nums[i];
        
        // 查找差值
        HashEntry* entry;
        HASH_FIND_INT(map, &complement, entry);
        
        if (entry) {
            result[0] = entry->value;
            result[1] = i;
            
            // 释放哈希表内存
            HashEntry *cur, *tmp;
            HASH_ITER(hh, map, cur, tmp) {
                HASH_DEL(map, cur);
                free(cur);
            }
            
            return result;
        }
        
        // 将当前数字存入哈希表
        entry = (HashEntry*)malloc(sizeof(HashEntry));
        entry->key = nums[i];
        entry->value = i;
        HASH_ADD_INT(map, key, entry);
    }
    
    // 释放哈希表内存
    HashEntry *cur, *tmp;
    HASH_ITER(hh, map, cur, tmp) {
        HASH_DEL(map, cur);
        free(cur);
    }
    
    *returnSize = 0;
    return NULL;
}
```

---