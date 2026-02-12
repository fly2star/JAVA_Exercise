# 110. 平衡二叉树

**难度: 简单**

## 题目描述
给定一个二叉树，判断它是否是平衡二叉树。平衡二叉树的定义是：一个二叉树每个节点的左右两个子树的高度差的绝对值不超过 1。

---

## 示例说明
### 示例 1：

![balance_tree2](../../../readFile/image/balance_1.jpg)

输入: root = [3,9,20,null,null,15,7]  
输出: true  

### 示例 2：

![balabce_tree2](../../../readFile/image/balance_2.jpg)

输入: root = [1,2,2,3,3,null,null,4,4]  
输出: false  

### 示例 3：
输入: root = []  
输出: true  

---

## 提示：
- 树中的节点数在范围 [0, 5000] 内  
- -10^4 <= Node.val <= 10^4

---

## 解题思路

### 核心思想
采用**自底向上**的后序遍历方式，在计算每个节点高度的同时判断其左右子树是否平衡。如果某个节点不平衡，可以提前返回一个标记（例如 -1），避免重复计算。

### 关键观察
- 平衡二叉树要求**每个节点**的左右子树高度差 ≤ 1。
- 空树（节点数为 0）视为平衡树。
- 若某个子树不平衡，则整个树一定不平衡。

### 算法步骤
1. 从根节点开始递归地计算每个节点的高度。
2. 对于当前节点：
   - 若为空，返回高度 0。
   - 递归计算左子树高度，若返回 -1 表示左子树不平衡，则直接返回 -1。
   - 递归计算右子树高度，若返回 -1 表示右子树不平衡，则直接返回 -1。
   - 若左右子树高度差的绝对值大于 1，返回 -1 表示当前子树不平衡。
   - 否则返回当前节点的高度 `max(left_height, right_height) + 1`。
3. 最后检查根节点的返回值是否不等于 -1，若不为 -1 则说明整个树平衡。

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    def isBalanced(self, root: TreeNode) -> bool:
        def dfs(node):
            if not node:
                return 0
            left = dfs(node.left)
            right = dfs(node.right)
            if left == -1 or right == -1 or abs(left - right) > 1:
                return -1
            return max(left, right) + 1
        
        return dfs(root) != -1
```

### Java 代码实现
```java
class TreeNode {
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

class Solution {
    public boolean isBalanced(TreeNode root) {
        return dfs(root) != -1;
    }
    
    private int dfs(TreeNode node) {
        if (node == null) return 0;
        int left = dfs(node.left);
        if (left == -1) return -1;
        int right = dfs(node.right);
        if (right == -1) return -1;
        if (Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }
}
```

### C 代码实现
```c
#include <stdbool.h>
#include <stdlib.h>

struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
};

int dfs(struct TreeNode* node) {
    if (node == NULL) return 0;
    int left = dfs(node->left);
    if (left == -1) return -1;
    int right = dfs(node->right);
    if (right == -1) return -1;
    if (abs(left - right) > 1) return -1;
    return (left > right ? left : right) + 1;
}

bool isBalanced(struct TreeNode* root) {
    return dfs(root) != -1;
}
```

---