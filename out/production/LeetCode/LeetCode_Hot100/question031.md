# 437. 路径总和 III

**难度：中等**

## 题目描述

给定一个二叉树的根节点 `root`，和一个整数 `targetSum`，求该二叉树里节点值之和等于 `targetSum` 的路径的数目。

> **路径** 不需要从根节点开始，也不需要在叶子节点结束，但是路径方向必须是向下的（只能从父节点到子节点）。

---

## 示例说明

**示例 1：** 

![tree image](../../readFile/image/pathsum3-1-tree.jpg )

输入：`root = [10,5,-3,3,2,null,11,3,-2,null,1]`, `targetSum = 8`  
输出：`3`  
解释：
- 路径 `5 → 3`：`5 + 3 = 8`
- 路径 `5 → 2 → 1`：`5 + 2 + 1 = 8`
- 路径 `-3 → 11`：`-3 + 11 = 8`

**示例 2：**  
输入：`root = [1]`, `targetSum = 1`  
输出：`1`  
解释：只有一个节点，值为 1，满足条件。

**示例 3：**  
输入：`root = [1,2,3]`, `targetSum = 3`  
输出：`2`  
解释：
- 路径 `2`：`2 = 3`？不成立
- 路径 `3`：`3 = 3` ✔️
- 路径 `1 → 2`：`1 + 2 = 3` ✔️
- 所以有两条路径：`[3]` 和 `[1,2]`

---

## 解题思路

### 核心思想：
- 每个节点都可以作为路径的起点。
- 对于每个节点，我们计算从它出发的所有向下路径中，和等于 `targetSum` 的路径数量。
- 使用 **深度优先搜索（DFS）** 遍历每个节点，并在每个节点上进行一次“从当前节点开始”的路径查找。

### 方法一：双重 DFS（推荐）

1. 外层 DFS：遍历每个节点。
2. 内层 DFS：从当前节点开始，向下查找所有和为 `targetSum` 的路径。

### 方法二：前缀和 + 哈希表（高级）

- 使用前缀和记录从根到当前节点的路径和。
- 利用哈希表存储出现过的前缀和，快速判断是否存在某个子路径和为 `targetSum`。
- 但注意：本题路径不需要从根开始，所以不能直接用根路径前缀和。

> 由于路径可以任意起点，**双重 DFS 更直观易懂**。

---

## 复杂度分析

| 方法 | 时间复杂度 | 空间复杂度 |
|------|------------|------------|
| 双重 DFS | O(n²) | O(h)，h 是树高（递归栈） |
| 前缀和 + 哈希表 | O(n) | O(n) |

> 其中 `n` 是节点总数。

---

## 参考代码（Python、Java、C）

### Python 实现
```python
# Definition for a binary tree node.
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

def pathSum(root, targetSum):
    def dfs(node, current_sum):
        if not node:
            return 0
        current_sum += node.val
        # 检查是否等于 targetSum
        count = 1 if current_sum == targetSum else 0
        # 递归子节点
        count += dfs(node.left, current_sum)
        count += dfs(node.right, current_sum)
        return count
    
    def traverse(node):
        if not node:
            return 0
        # 以当前节点为起点的路径数
        count = dfs(node, 0)
        # 加上左右子树的结果
        count += traverse(node.left)
        count += traverse(node.right)
        return count
    
    return traverse(root)
```

### Java 实现
```Java
public class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode() {}
    TreeNode(int val) { this.val = val; }
    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }
}

public int pathSum(TreeNode root, int targetSum) {
    return traverse(root, targetSum);
}

private int traverse(TreeNode node) {
    if (node == null) return 0;
    
    // 以当前节点为起点的路径数
    int count = dfs(node, 0, targetSum);
    // 加上左右子树的结果
    count += traverse(node.left);
    count += traverse(node.right);
    
    return count;
}

private int dfs(TreeNode node, int currentSum, int targetSum) {
    if (node == null) return 0;
    
    currentSum += node.val;
    int count = currentSum == targetSum ? 1 : 0;
    count += dfs(node.left, currentSum, targetSum);
    count += dfs(node.right, currentSum, targetSum);
    
    return count;
}
```

### C 实现
```C
#include <stdio.h>
#include <stdlib.h>

// Definition for a binary tree node.
struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
};

int dfs(struct TreeNode* node, int currentSum, int targetSum) {
    if (!node) return 0;
    
    currentSum += node->val;
    int count = (currentSum == targetSum) ? 1 : 0;
    count += dfs(node->left, currentSum, targetSum);
    count += dfs(node->right, currentSum, targetSum);
    
    return count;
}

int traverse(struct TreeNode* node, int targetSum) {
    if (!node) return 0;
    
    int count = dfs(node, 0, targetSum);
    count += traverse(node->left, targetSum);
    count += traverse(node->right, targetSum);
    
    return count;
}

int pathSum(struct TreeNode* root, int targetSum) {
    return traverse(root, targetSum);
}
```