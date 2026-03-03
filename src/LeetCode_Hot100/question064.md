# 560. 和为 K 的子数组

**难度: 中等**

## 题目描述
给你一个整数数组 `nums` 和一个整数 `k`，请你统计并返回该数组中和为 `k` 的子数组的个数。

子数组是数组中元素的连续非空序列。

---

## 示例说明
### 示例 1：
输入：nums = [1,1,1], k = 2  
输出：2  
解释：和为 2 的子数组有 [1,1]（索引 0-1）和 [1,1]（索引 1-2）

### 示例 2：
输入：nums = [1,2,3], k = 3  
输出：2  
解释：和为 3 的子数组有 [1,2] 和 [3]

---

## 提示：
- 1 ≤ nums.length ≤ 2 * 10^4
- -1000 ≤ nums[i] ≤ 1000
- -10^7 ≤ k ≤ 10^7

---

## 解题思路

### 核心思想
使用**前缀和 + 哈希表**的方法，将问题转化为寻找两个前缀和之差等于 k 的问题。时间复杂度 O(n)，空间复杂度 O(n)。

### 关键观察
- 定义前缀和 `prefixSum[i]` 为数组前 i 个元素的和（即 `nums[0] + nums[1] + ... + nums[i-1]`）
- 子数组 `nums[j...i]` 的和 = `prefixSum[i+1] - prefixSum[j]`
- 要求和为 k，即 `prefixSum[i+1] - prefixSum[j] = k`，等价于 `prefixSum[j] = prefixSum[i+1] - k`
- 因此，在遍历过程中，我们可以用哈希表记录每个前缀和出现的次数，然后对于当前前缀和 `currentSum`，我们只需要查找 `currentSum - k` 出现的次数

### 算法步骤
1. 初始化哈希表 `prefixSumCount`，键为前缀和，值为该前缀和出现的次数
2. 初始化 `prefixSumCount[0] = 1`，表示前缀和为 0 出现了 1 次（空数组）
3. 初始化 `currentSum = 0` 和 `count = 0`
4. 遍历数组每个元素 `num`：
   - `currentSum += num`
   - 计算 `need = currentSum - k`，在哈希表中查找 `need` 出现的次数，加到 `count` 上
   - 将 `currentSum` 的出现次数加 1
5. 返回 `count`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def subarraySum(self, nums: List[int], k: int) -> int:
        # 哈希表存储前缀和出现的次数
        prefix_sum_count = {0: 1}
        current_sum = 0
        count = 0
        
        for num in nums:
            current_sum += num
            # 查找 current_sum - k 出现的次数
            need = current_sum - k
            if need in prefix_sum_count:
                count += prefix_sum_count[need]
            
            # 更新当前前缀和的次数
            prefix_sum_count[current_sum] = prefix_sum_count.get(current_sum, 0) + 1
        
        return count
```

### Java 代码实现
```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        // 哈希表存储前缀和出现的次数
        Map<Integer, Integer> prefixSumCount = new HashMap<>();
        prefixSumCount.put(0, 1);
        
        int currentSum = 0;
        int count = 0;
        
        for (int num : nums) {
            currentSum += num;
            
            // 查找 currentSum - k 出现的次数
            int need = currentSum - k;
            count += prefixSumCount.getOrDefault(need, 0);
            
            // 更新当前前缀和的次数
            prefixSumCount.put(currentSum, prefixSumCount.getOrDefault(currentSum, 0) + 1);
        }
        
        return count;
    }
}
```

### C 代码实现
```c
typedef struct {
    int key;
    int value;
    UT_hash_handle hh;
} HashEntry;

int subarraySum(int* nums, int numsSize, int k) {
    // 创建哈希表
    HashEntry* map = NULL;
    
    // 初始化 prefixSum = 0 出现 1 次
    HashEntry* entry = (HashEntry*)malloc(sizeof(HashEntry));
    entry->key = 0;
    entry->value = 1;
    HASH_ADD_INT(map, key, entry);
    
    int currentSum = 0;
    int count = 0;
    
    for (int i = 0; i < numsSize; i++) {
        currentSum += nums[i];
        
        // 查找 currentSum - k 出现的次数
        int need = currentSum - k;
        HashEntry* find;
        HASH_FIND_INT(map, &need, find);
        if (find) {
            count += find->value;
        }
        
        // 更新当前前缀和的次数
        HASH_FIND_INT(map, &currentSum, find);
        if (find) {
            find->value++;
        } else {
            entry = (HashEntry*)malloc(sizeof(HashEntry));
            entry->key = currentSum;
            entry->value = 1;
            HASH_ADD_INT(map, key, entry);
        }
    }
    
    // 释放哈希表内存
    HashEntry *cur, *tmp;
    HASH_ITER(hh, map, cur, tmp) {
        HASH_DEL(map, cur);
        free(cur);
    }
    
    return count;
}
```

---