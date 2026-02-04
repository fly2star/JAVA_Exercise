# 406. 根据身高重建队列

**难度：中等**

## 题目描述

假设有一群人被打乱顺序地站成一个队列，数组 `people` 表示队列中一些人的属性（不一定按顺序）。  
每个 `people[i] = [h_i, k_i]` 表示第 `i` 个人的身高为 `h_i`，前面**正好有** `k_i` 个身高大于或等于 `h_i` 的人。

请你重新构建并返回输入数组 `people` 所表示的队列。  
返回的队列应该格式化为数组 `queue`，其中 `queue[j] = [h_j, k_j]` 是队列中第 `j` 个人的属性（`queue[0]` 是排在队列前面的人）。

---

## 示例说明

### 示例 1：
**输入**：`people = [[7,0],[4,4],[7,1],[5,0],[6,1],[5,2]]`  
**输出**：`[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]`

**解释**：
- 编号为 0 的人身高为 5，前面没有身高 ≥ 5 的人 → `k=0`
- 编号为 1 的人身高为 7，前面没有身高 ≥ 7 的人 → `k=0`
- 编号为 2 的人身高为 5，前面有 2 个身高 ≥ 5 的人 → `k=2`（即编号 0 和 1）
- 编号为 3 的人身高为 6，前面有 1 个身高 ≥ 6 的人 → `k=1`（即编号 1）
- 编号为 4 的人身高为 4，前面有 4 个身高 ≥ 4 的人 → `k=4`（即编号 0、1、2、3）
- 编号为 5 的人身高为 7，前面有 1 个身高 ≥ 7 的人 → `k=1`（即编号 1）

因此 `[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]` 是重建后的队列。

---

### 示例 2：
**输入**：`people = [[6,0],[5,0],[4,0],[3,2],[2,2],[1,4]]`  
**输出**：`[[4,0],[5,0],[2,2],[3,2],[1,4],[6,0]]`

---

## 解题思路

### 核心思想：
这是一个经典的 **贪心 + 排序 + 插入** 问题。

#### 关键观察：
- 如果我们先处理**身高较高**的人，再处理较矮的人，那么较矮的人不会影响较高的人的 `k` 值。
- 对于相同身高的人，`k` 值较小的应排在前面（因为高个子不会挡住他们）。

### 算法步骤：

1. **排序**：
   - 按身高降序排列；
   - 若身高相同，按 `k` 升序排列。

2. **插入**：
   - 遍历排序后的数组，将每个人插入到当前结果数组的 `k` 位置。
   - 因为前面都是更高或等高的，所以插入后 `k` 自动满足。

>  为什么这样可行？
> - 身高从高到低处理，确保后面插入的人不会影响前面的人。
> - 相同身高按 `k` 升序，保证 `k` 小的排前面。

---

## 复杂度分析

| 方法 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| 排序 + 插入 | O(n²) | O(1)（不考虑结果） |

> 其中 `n` 是人数。

---

## 参考代码（Python、Java、C）

### Python 实现
```python
def reconstructQueue(people):
    # 按身高降序，k 升序排序
    people.sort(key=lambda x: (-x[0], x[1]))
    
    result = []
    for person in people:
        result.insert(person[1], person)
    
    return result
```

### Java 实现
```Java
import java.util.*;

public int[][] reconstructQueue(int[][] people) {
    // 按身高降序，k 升序排序
    Arrays.sort(people, (a, b) -> {
        if (a[0] != b[0]) {
            return b[0] - a[0]; // 身高降序
        }
        return a[1] - b[1]; // k 升序
    });
    
    List<int[]> result = new ArrayList<>();
    for (int[] person : people) {
        result.add(person[1], person);
    }
    
    return result.toArray(new int[result.size()][]);
}
```

### C 实现
```C
#include <stdio.h>
#include <stdlib.h>

// 比较函数：按身高降序，k 升序
int compare(const void* a, const void* b) {
    int* p1 = *(int**)a;
    int* p2 = *(int**)b;
    
    if (p1[0] != p2[0]) {
        return p2[0] - p1[0]; // 身高降序
    }
    return p1[1] - p2[1]; // k 升序
}

int** reconstructQueue(int** people, int peopleSize, int* peopleColSize, int* returnSize, int** returnColumnSizes) {
    // 排序
    qsort(people, peopleSize, sizeof(int*), compare);
    
    // 使用链表模拟插入（避免频繁移动）
    int** result = (int**)malloc(peopleSize * sizeof(int*));
    for (int i = 0; i < peopleSize; i++) {
        result[i] = (int*)malloc(2 * sizeof(int));
    }
    
    int idx = 0;
    for (int i = 0; i < peopleSize; i++) {
        int pos = people[i][1];
        // 向后移动元素
        for (int j = idx; j > pos; j--) {
            result[j] = result[j - 1];
        }
        result[pos][0] = people[i][0];
        result[pos][1] = people[i][1];
        idx++;
    }
    
    *returnSize = peopleSize;
    *returnColumnSizes = (int*)malloc(peopleSize * sizeof(int));
    for (int i = 0; i < peopleSize; i++) {
        (*returnColumnSizes)[i] = 2;
    }
    
    return result;
}
```

