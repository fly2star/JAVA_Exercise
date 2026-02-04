# 399. 除法求值

**难度: 中等**

## 题目描述
给你一个变量对数组 `equations` 和一个实数值数组 `values` 作为已知条件，其中 `equations[i] = [Ai, Bi]` 和 `values[i]` 共同表示等式 $A_i / B_i = values[i]$ 。
每个 `Ai` 或 `Bi` 是一个表示单个变量的字符串。

另有一些以数组 queries 表示的问题，其中 queries[j] = [Cj, Dj] 表示第 j 个问题，请你根据已知条件找出 Cj / Dj = ? 的结果作为答案。

返回所有问题的答案。如果存在某个无法确定的答案，则用 -1.0 替代这个答案。如果问题中出现了给定的已知条件中没有出现的字符串，也需要用 -1.0 替代这个答案。

注意：输入总是有效的。你可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。

---

## 示例说明
### 示例 1：
**输入：**
equations = [["a","b"],["b","c"]], values = [2.0,3.0], queries = [["a","c"],["b","a"],["a","e"],["a","a"],["x","x"]]  

**输出：**[6.00000,0.50000,-1.00000,1.00000,-1.00000]  

**解释：**
条件：a / b = 2.0，b / c = 3.0  
查询：  
a / c = 6.0  
b / a = 0.5  
a / e = -1.0（e 未定义）  
a / a = 1.0
x / x = -1.0（x 未定义）

---

### 示例 2：
**输入：**
equations = [["a","b"],["b","c"],["bc","cd"]], values = [1.5,2.5,5.0], queries = [["a","c"],["c","b"],["bc","cd"],["cd","bc"]]  

**输出：**[3.75000,0.40000,5.00000,0.20000]  

**解释：**
a / c = a / b × b / c = 1.5 × 2.5 = 3.75  
c / b = 1 / (b / c) = 1 / 2.5 = 0.4  
bc / cd = 5.0  
cd / bc = 1 / 5.0 = 0.2

---

### 示例 3：
**输入：**
equations = [["a","b"]], values = [0.5], queries = [["a","b"],["b","a"],["a","c"],["x","y"]]  

**输出：**[0.50000,2.00000,-1.00000,-1.00000]  

**解释：**
a / b = 0.5  
b / a = 2.0  
a / c = -1.0（c 未定义）  
x / y = -1.0（x, y 都未定义）

---

## 提示：
- 1 <= equations.length <= 20
- equations[i].length == 2
- 1 <= Ai.length, Bi.length <= 5
- values.length == equations.length
- 0.0 < values[i] <= 20.0
- 1 <= queries.length <= 20
- queries[i].length == 2
- 1 <= Cj.length, Dj.length <= 5
- Ai, Bi, Cj, Dj 由小写英文字母与数字组成

---

## 解题思路

### 核心思想
将变量看作图中的节点，除法关系看作带权重的有向边。如果 a / b = v，则从 a 到 b 有一条权重为 v 的边，从 b 到 a 有一条权重为 1/v 的边。

### 关键观察
1. 查询 Cj / Dj 相当于在图中从 Cj 出发，寻找一条到 Dj 的路径，并将路径上的权重相乘
2. 如果两个变量不在同一个连通分量中，则无法计算它们的比值
3. 并查集可以高效地维护变量之间的连通性和比值关系

### 算法步骤
1. 初始化并查集，每个节点的父节点是自己，权重为 1.0
2. 遍历 equations，对于每个等式 a / b = v：
   - 将 a 和 b 合并到同一个集合
   - 在合并过程中更新权重关系
3. 遍历 queries，对于每个查询 Cj / Dj：
   - 如果 Cj 或 Dj 不存在，返回 -1.0
   - 如果 Cj 和 Dj 不在同一个集合，返回 -1.0
   - 否则，返回 weight[Cj] / weight[Dj]

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List

class Solution:
    def calcEquation(self, equations: List[List[str]], values: List[float], queries: List[List[str]]) -> List[float]:
        parent = {}  # 父节点
        weight = {}  # 节点到根节点的权重
        
        def find(x):
            """查找根节点，并路径压缩"""
            if x != parent[x]:
                origin_parent = parent[x]
                parent[x] = find(parent[x])
                weight[x] *= weight[origin_parent]
            return parent[x]
        
        def union(x, y, value):
            """合并两个节点，并更新权重"""
            root_x = find(x)
            root_y = find(y)
            if root_x != root_y:
                parent[root_x] = root_y
                # weight[x] * weight[root_x] = weight[y] * value
                weight[root_x] = weight[y] * value / weight[x]
        
        # 初始化并查集
        for a, b in equations:
            if a not in parent:
                parent[a] = a
                weight[a] = 1.0
            if b not in parent:
                parent[b] = b
                weight[b] = 1.0
        
        # 构建并查集关系
        for (a, b), v in zip(equations, values):
            union(a, b, v)
        
        # 处理查询
        result = []
        for a, b in queries:
            if a not in parent or b not in parent:
                result.append(-1.0)
            else:
                root_a = find(a)
                root_b = find(b)
                if root_a != root_b:
                    result.append(-1.0)
                else:
                    result.append(weight[a] / weight[b])
        return result
```

---

### Java 代码实现

```java
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    private Map<String, String> parent = new HashMap<>();
    private Map<String, Double> weight = new HashMap<>();

    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // 初始化并查集
        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            if (!parent.containsKey(a)) {
                parent.put(a, a);
                weight.put(a, 1.0);
            }
            if (!parent.containsKey(b)) {
                parent.put(b, b);
                weight.put(b, 1.0);
            }
            union(a, b, values[i]);
        }

        // 处理查询
        double[] result = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String a = queries.get(i).get(0);
            String b = queries.get(i).get(1);
            if (!parent.containsKey(a) || !parent.containsKey(b)) {
                result[i] = -1.0;
            } else {
                String rootA = find(a);
                String rootB = find(b);
                if (!rootA.equals(rootB)) {
                    result[i] = -1.0;
                } else {
                    result[i] = weight.get(a) / weight.get(b);
                }
            }
        }
        return result;
    }

    private String find(String x) {
        if (!parent.get(x).equals(x)) {
            String originParent = parent.get(x);
            String root = find(originParent);
            weight.put(x, weight.get(x) * weight.get(originParent));
            parent.put(x, root);
        }
        return parent.get(x);
    }

    private void union(String x, String y, double value) {
        String rootX = find(x);
        String rootY = find(y);
        if (!rootX.equals(rootY)) {
            parent.put(rootX, rootY);
            weight.put(rootX, weight.get(y) * value / weight.get(x));
        }
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_NODES 40
#define STR_LEN 6

typedef struct {
    char name[STR_LEN];
    int index;
} NodeMap;

NodeMap nodes[MAX_NODES];
int nodeCount = 0;
int parent[MAX_NODES];
double weight[MAX_NODES];

// 查找变量名对应的索引
int findIndex(char* name) {
    for (int i = 0; i < nodeCount; i++) {
        if (strcmp(nodes[i].name, name) == 0) {
            return i;
        }
    }
    return -1;
}

// 获取或创建变量索引
int getIndex(char* name) {
    int idx = findIndex(name);
    if (idx == -1) {
        strcpy(nodes[nodeCount].name, name);
        nodes[nodeCount].index = nodeCount;
        parent[nodeCount] = nodeCount;
        weight[nodeCount] = 1.0;
        nodeCount++;
        return nodeCount - 1;
    }
    return idx;
}

// 查找根节点
int find(int x) {
    if (parent[x] != x) {
        int originParent = parent[x];
        parent[x] = find(parent[x]);
        weight[x] *= weight[originParent];
    }
    return parent[x];
}

// 合并两个节点
void unionNodes(int x, int y, double value) {
    int rootX = find(x);
    int rootY = find(y);
    if (rootX != rootY) {
        parent[rootX] = rootY;
        weight[rootX] = weight[y] * value / weight[x];
    }
}

double* calcEquation(char*** equations, int equationsSize, int* equationsColSize, double* values, int valuesSize, char*** queries, int queriesSize, int* queriesColSize, int* returnSize) {
    nodeCount = 0;
    
    // 构建并查集
    for (int i = 0; i < equationsSize; i++) {
        char* a = equations[i][0];
        char* b = equations[i][1];
        int idxA = getIndex(a);
        int idxB = getIndex(b);
        unionNodes(idxA, idxB, values[i]);
    }

    double* result = (double*)malloc(queriesSize * sizeof(double));
    *returnSize = queriesSize;

    // 处理查询
    for (int i = 0; i < queriesSize; i++) {
        char* a = queries[i][0];
        char* b = queries[i][1];
        int idxA = findIndex(a);
        int idxB = findIndex(b);
        
        if (idxA == -1 || idxB == -1) {
            result[i] = -1.0;
        } else {
            int rootA = find(idxA);
            int rootB = find(idxB);
            if (rootA != rootB) {
                result[i] = -1.0;
            } else {
                result[i] = weight[idxA] / weight[idxB];
            }
        }
    }
    return result;
}
```

---