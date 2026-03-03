# 56. 合并区间

**难度: 中等**

## 题目描述
以数组 `intervals` 表示若干个区间的集合，其中单个区间为 `intervals[i] = [start_i, end_i]`。请你合并所有重叠的区间，并返回一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间。

---

## 示例说明
### 示例 1：
输入：intervals = [[1,3],[2,6],[8,10],[15,18]]  
输出：[[1,6],[8,10],[15,18]]  
解释：区间 [1,3] 和 [2,6] 重叠，将它们合并为 [1,6]。

### 示例 2：
输入：intervals = [[1,4],[4,5]]  
输出：[[1,5]]  
解释：区间 [1,4] 和 [4,5] 可被视为重叠区间。

### 示例 3：
输入：intervals = [[4,7],[1,4]]  
输出：[[1,7]]  
解释：区间 [1,4] 和 [4,7] 可被视为重叠区间。

---

## 提示：
- 1 ≤ intervals.length ≤ 10^4
- intervals[i].length == 2
- 0 ≤ start_i < end_i ≤ 10^4

---

## 解题思路

### 核心思想
先对区间按**起始位置排序**，然后遍历区间，判断当前区间是否与结果中最后一个区间重叠。如果重叠，则合并（更新结束位置为较大值）；如果不重叠，则直接加入结果。

### 关键观察
- 重叠的条件：当前区间的起始位置 ≤ 结果中最后一个区间的结束位置
- 合并后的结束位置取两个区间结束位置的最大值
- 排序可以保证我们只需要顺序处理，不需要回头检查

### 算法步骤
1. 如果 intervals 为空，返回空数组
2. 按区间的起始位置对 intervals 进行排序
3. 初始化结果列表 result，将第一个区间加入 result
4. 遍历剩余的每个区间：
   - 如果当前区间的起始位置 ≤ result 中最后一个区间的结束位置：
     - 说明有重叠，更新最后一个区间的结束位置为 max(当前结束位置, 最后一个区间的结束位置)
   - 否则：
     - 说明没有重叠，将当前区间加入 result
5. 返回 result

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def merge(self, intervals: List[List[int]]) -> List[List[int]]:
        if not intervals:
            return []
        
        # 按起始位置排序
        intervals.sort(key=lambda x: x[0])
        
        result = [intervals[0]]
        
        for i in range(1, len(intervals)):
            current = intervals[i]
            last = result[-1]
            
            # 如果当前区间与最后一个区间重叠
            if current[0] <= last[1]:
                # 合并，更新结束位置
                last[1] = max(last[1], current[1])
            else:
                # 不重叠，直接添加
                result.append(current)
        
        return result
```

### Java 代码实现
```java
class Solution {
    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][];
        }
        
        // 按起始位置排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);
        
        List<int[]> result = new ArrayList<>();
        result.add(intervals[0]);
        
        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] last = result.get(result.size() - 1);
            
            // 如果当前区间与最后一个区间重叠
            if (current[0] <= last[1]) {
                // 合并，更新结束位置
                last[1] = Math.max(last[1], current[1]);
            } else {
                // 不重叠，直接添加
                result.add(current);
            }
        }
        
        return result.toArray(new int[result.size()][]);
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

int cmp(const void* a, const void* b) {
    int* intervalA = *(int**)a;
    int* intervalB = *(int**)b;
    return intervalA[0] - intervalB[0];
}

int** merge(int** intervals, int intervalsSize, int* intervalsColSize, int* returnSize, int** returnColumnSizes) {
    if (intervalsSize == 0) {
        *returnSize = 0;
        *returnColumnSizes = NULL;
        return NULL;
    }
    
    // 按起始位置排序
    qsort(intervals, intervalsSize, sizeof(int*), cmp);
    
    // 分配结果空间（最坏情况下没有合并）
    int** result = (int**)malloc(intervalsSize * sizeof(int*));
    *returnColumnSizes = (int*)malloc(intervalsSize * sizeof(int));
    
    int index = 0;
    result[index] = (int*)malloc(2 * sizeof(int));
    result[index][0] = intervals[0][0];
    result[index][1] = intervals[0][1];
    (*returnColumnSizes)[index] = 2;
    
    for (int i = 1; i < intervalsSize; i++) {
        int* current = intervals[i];
        int* last = result[index];
        
        // 如果当前区间与最后一个区间重叠
        if (current[0] <= last[1]) {
            // 合并，更新结束位置
            last[1] = (last[1] > current[1]) ? last[1] : current[1];
        } else {
            // 不重叠，添加新区间
            index++;
            result[index] = (int*)malloc(2 * sizeof(int));
            result[index][0] = current[0];
            result[index][1] = current[1];
            (*returnColumnSizes)[index] = 2;
        }
    }
    
    *returnSize = index + 1;
    return result;
}
```

---