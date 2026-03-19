# 78. 子集

**难度: 中等**

## 题目描述
给你一个整数数组 `nums`，数组中的元素 **互不相同**。返回该数组所有可能的子集（**幂集**）。

解集 **不能** 包含重复的子集。你可以按任意顺序返回解集。

---

## 示例说明
### 示例 1：
输入：nums = [1,2,3]  
输出：[[],[1],[2],[1,2],[3],[1,3],[2,3],[1,2,3]]

### 示例 2：
输入：nums = [0]  
输出：[[],[0]]

---

## 提示：
- 1 ≤ nums.length ≤ 10
- -10 ≤ nums[i] ≤ 10
- nums 中的所有元素 **互不相同**

---

## 解题思路

### 核心思想
子集问题可以使用**回溯法**或**位运算**来解决。由于元素互不相同，每个元素都有"选"和"不选"两种选择，所有子集的总数为 2^n 个。

### 关键观察
- 方法一（回溯）：遍历数组，每个元素可以选择加入当前路径或不加入
- 方法二（位运算）：用 n 位二进制数表示每个元素的选或不选，0 到 2^n-1 对应所有子集

### 算法步骤

#### 方法一：回溯
1. 定义回溯函数 `backtrack(start, path)`：
   - 将当前路径加入结果集
   - 从 start 开始遍历数组：
     - 选择当前元素，加入 path
     - 递归调用 `backtrack(i + 1, path)`
     - 回溯，将当前元素从 path 移除
2. 调用 `backtrack(0, [])`

#### 方法二：位运算
1. 结果集大小 = 2^n
2. 遍历 mask 从 0 到 2^n - 1：
   - 创建当前子集
   - 遍历每个位置 i，如果 mask 的第 i 位为 1，将 nums[i] 加入子集
   - 将子集加入结果集

---

## 代码参考(python, java, c)

### Python 代码实现
```python
# 方法一：回溯
class Solution:
    def subsets(self, nums: List[int]) -> List[List[int]]:
        def backtrack(start: int, path: List[int]):
            # 将当前路径加入结果集
            result.append(path[:])
            
            # 从 start 开始遍历，避免重复
            for i in range(start, len(nums)):
                # 选择当前元素
                path.append(nums[i])
                # 递归
                backtrack(i + 1, path)
                # 回溯
                path.pop()
        
        result = []
        backtrack(0, [])
        return result

# 方法二：位运算
class Solution:
    def subsets(self, nums: List[int]) -> List[List[int]]:
        n = len(nums)
        result = []
        
        # 遍历所有掩码
        for mask in range(1 << n):
            subset = []
            # 检查每一位
            for i in range(n):
                if mask & (1 << i):
                    subset.append(nums[i])
            result.append(subset)
        
        return result
```

### Java 代码实现
```java
// 方法一：回溯
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(nums, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
        // 将当前路径加入结果集
        result.add(new ArrayList<>(path));
        
        // 从 start 开始遍历，避免重复
        for (int i = start; i < nums.length; i++) {
            // 选择当前元素
            path.add(nums[i]);
            // 递归
            backtrack(nums, i + 1, path, result);
            // 回溯
            path.remove(path.size() - 1);
        }
    }
}

// 方法二：位运算
class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        
        // 遍历所有掩码
        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            // 检查每一位
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            result.add(subset);
        }
        
        return result;
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

// 方法一：回溯
void backtrack(int* nums, int numsSize, int start, int* path, int pathSize,
               int*** result, int* returnSize, int** returnColumnSizes) {
    // 将当前路径加入结果集
    (*result)[*returnSize] = (int*)malloc(pathSize * sizeof(int));
    for (int i = 0; i < pathSize; i++) {
        (*result)[*returnSize][i] = path[i];
    }
    (*returnColumnSizes)[*returnSize] = pathSize;
    (*returnSize)++;
    
    // 从 start 开始遍历，避免重复
    for (int i = start; i < numsSize; i++) {
        // 选择当前元素
        path[pathSize] = nums[i];
        // 递归
        backtrack(nums, numsSize, i + 1, path, pathSize + 1, result, returnSize, returnColumnSizes);
        // 回溯
    }
}

int** subsets(int* nums, int numsSize, int* returnSize, int** returnColumnSizes) {
    int total = 1 << numsSize;  // 2^n
    int** result = (int**)malloc(total * sizeof(int*));
    *returnColumnSizes = (int*)malloc(total * sizeof(int));
    *returnSize = 0;
    
    int* path = (int*)malloc(numsSize * sizeof(int));
    backtrack(nums, numsSize, 0, path, 0, &result, returnSize, returnColumnSizes);
    
    free(path);
    return result;
}

// 方法二：位运算
int** subsets(int* nums, int numsSize, int* returnSize, int** returnColumnSizes) {
    int total = 1 << numsSize;
    int** result = (int**)malloc(total * sizeof(int*));
    *returnColumnSizes = (int*)malloc(total * sizeof(int));
    *returnSize = 0;
    
    // 遍历所有掩码
    for (int mask = 0; mask < total; mask++) {
        // 先计算当前子集的大小
        int size = 0;
        for (int i = 0; i < numsSize; i++) {
            if (mask & (1 << i)) {
                size++;
            }
        }
        
        // 分配空间并填充
        result[*returnSize] = (int*)malloc(size * sizeof(int));
        int index = 0;
        for (int i = 0; i < numsSize; i++) {
            if (mask & (1 << i)) {
                result[*returnSize][index++] = nums[i];
            }
        }
        (*returnColumnSizes)[*returnSize] = size;
        (*returnSize)++;
    }
    
    return result;
}
```

---