# 104. 二叉树的最大深度

**难度: 简单**

## 题目描述
给定一个二叉树 `root`，返回其最大深度。

二叉树的 **最大深度** 是指从根节点到最远叶子节点的最长路径上的节点数。

---

## 示例说明
### 示例 1：

![tmp-tree](../../readFile/image/tmp-tree.jpg)

输入：root = [3,9,20,null,null,15,7]  
输出：3

### 示例 2：
输入：root = [1,null,2]  
输出：2

---

## 提示：
- 树中节点的数量在 [0, 10^4] 区间内
- -100 ≤ Node.val ≤ 100

---

## 解题思路

### 核心思想
使用**深度优先搜索（DFS）**递归地计算每个节点的深度。一个节点的深度等于其左子树和右子树深度的最大值加 1。

### 关键观察
- 空节点的深度为 0
- 叶子节点的深度为 1
- 可以通过递归或迭代（层次遍历）两种方式实现

### 算法步骤

#### 方法一：递归（DFS）
1. 如果根节点为空，返回 0
2. 递归计算左子树的最大深度
3. 递归计算右子树的最大深度
4. 返回左右子树最大深度的较大值加 1

#### 方法二：迭代（BFS）
1. 使用队列进行层次遍历
2. 每遍历一层，深度加 1
3. 当队列为空时，返回深度

---

## 代码参考(python, java, c)

### Python 代码实现
```python
# Definition for a binary tree node.
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

# 方法一：递归
class Solution:
    def maxDepth(self, root: Optional[TreeNode]) -> int:
        if not root:
            return 0
        
        left_depth = self.maxDepth(root.left)
        right_depth = self.maxDepth(root.right)
        
        return max(left_depth, right_depth) + 1

# 方法二：迭代（层次遍历）
class Solution:
    def maxDepth(self, root: Optional[TreeNode]) -> int:
        if not root:
            return 0
        
        from collections import deque
        queue = deque([root])
        depth = 0
        
        while queue:
            depth += 1
            level_size = len(queue)
            
            for _ in range(level_size):
                node = queue.popleft()
                if node.left:
                    queue.append(node.left)
                if node.right:
                    queue.append(node.right)
        
        return depth
```

### Java 代码实现
```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */

// 方法一：递归
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        
        return Math.max(leftDepth, rightDepth) + 1;
    }
}

// 方法二：迭代（层次遍历）
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int depth = 0;
        
        while (!queue.isEmpty()) {
            depth++;
            int levelSize = queue.size();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }
        
        return depth;
    }
}
```

### C 代码实现
```c
/**
 * Definition for a binary tree node.
 * struct TreeNode {
 *     int val;
 *     struct TreeNode *left;
 *     struct TreeNode *right;
 * };
 */

// 方法一：递归
int maxDepth(struct TreeNode* root) {
    if (root == NULL) {
        return 0;
    }
    
    int leftDepth = maxDepth(root->left);
    int rightDepth = maxDepth(root->right);
    
    return (leftDepth > rightDepth ? leftDepth : rightDepth) + 1;
}

// 方法二：迭代（层次遍历）
// 需要队列实现，这里提供一个简化版本的思路
int maxDepth(struct TreeNode* root) {
    if (root == NULL) {
        return 0;
    }
    
    // 使用数组模拟队列
    struct TreeNode** queue = (struct TreeNode**)malloc(10000 * sizeof(struct TreeNode*));
    int front = 0, rear = 0;
    queue[rear++] = root;
    int depth = 0;
    
    while (front < rear) {
        depth++;
        int levelSize = rear - front;
        
        for (int i = 0; i < levelSize; i++) {
            struct TreeNode* node = queue[front++];
            if (node->left) {
                queue[rear++] = node->left;
            }
            if (node->right) {
                queue[rear++] = node->right;
            }
        }
    }
    
    free(queue);
    return depth;
}
```

---