# 39. 组合总和

**难度: 中等**

## 题目描述
给你一个 **无重复元素的整数数组 candidates** 和一个目标整数 target，找出 candidates 中可以使数字和为目标数 target 的所有不同组合，并以列表形式返回。你可以按任意顺序返回这些组合。

candidates 中的 **同一个数字可以无限重复被选取**。如果至少一个数字的被选数量不同，则两种组合是不同的。

对于给定的输入，保证和为 target 的不同组合数少于 150 个。

---

## 示例说明
### 示例 1：
输入：candidates = [2,3,6,7], target = 7  
输出：[[2,2,3],[7]]  
解释：
- 2 和 3 可以形成一组候选，2 + 2 + 3 = 7。注意 2 可以使用多次。
- 7 也是一个候选，7 = 7。
- 仅有这两种组合。

### 示例 2：
输入：candidates = [2,3,5], target = 8  
输出：[[2,2,2,2],[2,3,3],[3,5]]

### 示例 3：
输入：candidates = [2], target = 1  
输出：[]

---

## 提示：
- 1 ≤ candidates.length ≤ 30
- 2 ≤ candidates[i] ≤ 40
- candidates 的所有元素互不相同
- 1 ≤ target ≤ 40

---

## 解题思路

### 核心思想
使用**回溯法**（深度优先搜索）来探索所有可能的组合。由于每个数字可以无限次使用，我们需要在递归时控制搜索的起点，避免产生重复组合（如 [2,2,3] 和 [2,3,2] 被视为相同组合）。

### 关键观察
- 数组元素都是正数，因此可以在和超过 target 时剪枝
- 每个数字可以重复使用，所以递归时可以从当前索引开始，而不是 i+1
- 先对数组排序可以提前剪枝：如果当前数字已经大于剩余目标值，后面的数字更大，可以直接跳过

### 算法步骤
1. 对 candidates 进行排序（可选，用于剪枝优化）
2. 定义回溯函数 `backtrack(start, target, path)`：
   - `start`: 当前可选的起始索引
   - `target`: 剩余需要凑成的目标值
   - `path`: 当前已选的数字组合
3. 终止条件：
   - 如果 `target == 0`，说明找到一组有效组合，加入结果集
   - 如果 `target < 0`，说明当前组合无效，直接返回
4. 从 `start` 开始遍历 candidates：
   - 如果当前数字大于 `target`，由于数组已排序，后面的数字更大，可以直接跳出循环（剪枝）
   - 选择当前数字：加入 path，递归调用 `backtrack(i, target - candidates[i], path)`
   - 回溯：将当前数字从 path 中移除

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def combinationSum(self, candidates: List[int], target: int) -> List[List[int]]:
        def backtrack(start: int, target: int, path: List[int]):
            # 找到一组有效组合
            if target == 0:
                result.append(path[:])
                return
            # 剪枝：target < 0 的情况已经不可能
            if target < 0:
                return
            
            # 从 start 开始遍历，避免重复组合
            for i in range(start, len(candidates)):
                # 剪枝：如果当前数字已经大于 target，后面更大的数字可以直接跳过
                if candidates[i] > target:
                    continue
                
                # 选择当前数字
                path.append(candidates[i])
                # 递归，注意索引仍为 i（因为可以重复使用）
                backtrack(i, target - candidates[i], path)
                # 回溯
                path.pop()
        
        result = []
        # 排序可以帮助剪枝
        candidates.sort()
        backtrack(0, target, [])
        return result
```

### Java 代码实现
```java
class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        // 排序可以帮助剪枝
        Arrays.sort(candidates);
        backtrack(candidates, target, 0, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int[] candidates, int target, int start, 
                          List<Integer> path, List<List<Integer>> result) {
        // 找到一组有效组合
        if (target == 0) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        // 从 start 开始遍历，避免重复组合
        for (int i = start; i < candidates.length; i++) {
            // 剪枝：如果当前数字已经大于 target，后面更大的数字可以直接跳过
            if (candidates[i] > target) {
                break;
            }
            
            // 选择当前数字
            path.add(candidates[i]);
            // 递归，注意索引仍为 i（因为可以重复使用）
            backtrack(candidates, target - candidates[i], i, path, result);
            // 回溯
            path.remove(path.size() - 1);
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

void backtrack(int* candidates, int candidatesSize, int target, int start,
               int* path, int pathSize, int*** result, int* returnSize, 
               int** returnColumnSizes) {
    // 找到一组有效组合
    if (target == 0) {
        (*result)[*returnSize] = (int*)malloc(pathSize * sizeof(int));
        for (int i = 0; i < pathSize; i++) {
            (*result)[*returnSize][i] = path[i];
        }
        (*returnColumnSizes)[*returnSize] = pathSize;
        (*returnSize)++;
        return;
    }
    
    // 从 start 开始遍历，避免重复组合
    for (int i = start; i < candidatesSize; i++) {
        // 剪枝：如果当前数字已经大于 target，后面更大的数字可以直接跳过
        if (candidates[i] > target) {
            continue;
        }
        
        // 选择当前数字
        path[pathSize] = candidates[i];
        // 递归，注意索引仍为 i（因为可以重复使用）
        backtrack(candidates, candidatesSize, target - candidates[i], i,
                 path, pathSize + 1, result, returnSize, returnColumnSizes);
        // 回溯（通过递归返回后自动实现）
    }
}

// 比较函数用于排序
int cmp(const void* a, const void* b) {
    return *(int*)a - *(int*)b;
}

int** combinationSum(int* candidates, int candidatesSize, int target, 
                     int* returnSize, int** returnColumnSizes) {
    // 先排序
    qsort(candidates, candidatesSize, sizeof(int), cmp);
    
    // 预估最大可能结果数
    int maxResults = 150;
    int** result = (int**)malloc(maxResults * sizeof(int*));
    *returnColumnSizes = (int*)malloc(maxResults * sizeof(int));
    *returnSize = 0;
    
    // 辅助路径数组
    int* path = (int*)malloc(target * sizeof(int));  // 最坏情况全是1，长度最大为target
    
    backtrack(candidates, candidatesSize, target, 0, path, 0, &result, returnSize, returnColumnSizes);
    
    free(path);
    return result;
}
```

---