# 46. 全排列

**难度: 中等**

## 题目描述
给定一个不含重复数字的数组 `nums`，返回其 **所有可能的全排列**。你可以按任意顺序返回答案。

---

## 示例说明
### 示例 1：
输入：nums = [1,2,3]  
输出：[[1,2,3],[1,3,2],[2,1,3],[2,3,1],[3,1,2],[3,2,1]]

### 示例 2：
输入：nums = [0,1]  
输出：[[0,1],[1,0]]

---

## 提示：
- 1 <= nums.length <= 6
- -10 <= nums[i] <= 10
- nums 中的所有整数互不相同

---

## 解题思路

### 核心思想
使用**回溯法**（深度优先搜索）来生成所有可能的排列。通过维护一个当前路径和已使用元素的标记数组，在每一步尝试所有未使用的数字，直到路径长度等于原数组长度。

### 关键观察
- 由于数字互不相同，我们只需要关心某个数字是否已经被使用
- 排列的顺序不重要，关键是要包含所有可能的组合
- 回溯的经典模板：**选择 → 递归 → 撤销选择**

### 算法步骤
1. 初始化结果列表 `result` 和当前路径 `path`
2. 创建布尔数组 `used` 标记每个元素是否被使用
3. 定义回溯函数 `backtrack()`：
   - 如果 `path` 长度等于 `nums` 长度，说明找到了一个完整排列，将其加入结果集
   - 否则，遍历所有数字：
     - 如果当前数字未被使用：
       - 将其加入 `path`，标记为已使用
       - 递归调用 `backtrack()`
       - 回溯：将其移出 `path`，标记为未使用
4. 返回结果集

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def permute(self, nums: List[int]) -> List[List[int]]:
        def backtrack(path):
            # 如果当前路径长度等于数组长度，说明找到了一个完整排列
            if len(path) == len(nums):
                result.append(path[:])
                return
            
            # 遍历所有数字
            for i in range(len(nums)):
                # 如果当前数字未被使用
                if not used[i]:
                    # 选择
                    used[i] = True
                    path.append(nums[i])
                    # 递归
                    backtrack(path)
                    # 撤销选择
                    path.pop()
                    used[i] = False
        
        result = []
        used = [False] * len(nums)
        backtrack([])
        return result
```

### Java 代码实现
```java
class Solution {
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];
        
        backtrack(nums, used, path, result);
        return result;
    }
    
    private void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        // 如果当前路径长度等于数组长度，说明找到了一个完整排列
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        // 遍历所有数字
        for (int i = 0; i < nums.length; i++) {
            // 如果当前数字未被使用
            if (!used[i]) {
                // 选择
                used[i] = true;
                path.add(nums[i]);
                // 递归
                backtrack(nums, used, path, result);
                // 撤销选择
                path.remove(path.size() - 1);
                used[i] = false;
            }
        }
    }
}
```

### C 代码实现
```c
/**
 * Return an array of arrays of size *returnSize.
 * The sizes of the arrays are returned as *returnColumnSizes array.
 * Note: Both returned array and *columnSizes array must be malloced, assume caller calls free().
 */

void backtrack(int* nums, int numsSize, int* path, int pathSize, int* used, 
               int*** result, int* returnSize, int** returnColumnSizes) {
    // 如果当前路径长度等于数组长度，说明找到了一个完整排列
    if (pathSize == numsSize) {
        // 分配空间存储当前排列
        (*result)[*returnSize] = (int*)malloc(numsSize * sizeof(int));
        for (int i = 0; i < numsSize; i++) {
            (*result)[*returnSize][i] = path[i];
        }
        (*returnColumnSizes)[*returnSize] = numsSize;
        (*returnSize)++;
        return;
    }
    
    // 遍历所有数字
    for (int i = 0; i < numsSize; i++) {
        // 如果当前数字未被使用
        if (!used[i]) {
            // 选择
            used[i] = 1;
            path[pathSize] = nums[i];
            // 递归
            backtrack(nums, numsSize, path, pathSize + 1, used, result, returnSize, returnColumnSizes);
            // 撤销选择（回溯）
            used[i] = 0;
        }
    }
}

int** permute(int* nums, int numsSize, int* returnSize, int** returnColumnSizes) {
    // 计算总排列数：numsSize!
    int total = 1;
    for (int i = 2; i <= numsSize; i++) {
        total *= i;
    }
    
    // 分配结果空间
    int** result = (int**)malloc(total * sizeof(int*));
    *returnColumnSizes = (int*)malloc(total * sizeof(int));
    *returnSize = 0;
    
    // 辅助数组
    int* path = (int*)malloc(numsSize * sizeof(int));
    int* used = (int*)calloc(numsSize, sizeof(int));
    
    // 开始回溯
    backtrack(nums, numsSize, path, 0, used, &result, returnSize, returnColumnSizes);
    
    // 释放辅助空间
    free(path);
    free(used);
    
    return result;
}
```

---