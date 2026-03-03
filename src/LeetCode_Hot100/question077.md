# 114. 二叉树展开为链表

**难度: 中等**

## 题目描述
给你二叉树的根结点 `root`，请你将它展开为一个单链表：

- 展开后的单链表应该同样使用 `TreeNode`，其中 `right` 子指针指向链表中下一个结点，而左子指针始终为 `null`。
- 展开后的单链表应该与二叉树 **先序遍历** 顺序相同。

---

## 示例说明
### 示例 1：

![flaten](../../readFile/image/flaten.jpg)

输入：root = [1,2,5,3,4,null,6]  
输出：[1,null,2,null,3,null,4,null,5,null,6]  
解释：展开后的链表与先序遍历顺序相同：1 → 2 → 3 → 4 → 5 → 6

### 示例 2：
输入：root = []  
输出：[]

### 示例 3：
输入：root = [0]  
输出：[0]

---

## 提示：
- 树中结点数在范围 [0, 2000] 内
- -100 ≤ Node.val ≤ 100

进阶：你可以使用原地算法（O(1) 额外空间）展开这棵树吗？

---

## 解题思路

### 核心思想
将二叉树展开为链表，可以按照先序遍历的顺序重新连接节点。有多种实现方式：递归、迭代、以及原地算法（寻找前驱节点）。

### 关键观察
- 先序遍历的顺序是：根 → 左子树 → 右子树
- 展开后，每个节点的左子节点应为 null，右子节点指向下一个节点
- 原地算法的关键是：对于每个节点，将其右子树接到左子树的最右节点后面，然后将左子树移到右边

### 算法步骤

#### 方法一：递归（前序遍历 + 重新连接）
1. 对二叉树进行前序遍历，将节点按顺序存入列表
2. 遍历列表，将每个节点的左子节点设为 null，右子节点设为列表中的下一个节点

#### 方法二：原地算法（寻找前驱节点）
1. 从根节点开始遍历：
   - 如果当前节点有左子树：
     - 找到左子树的最右节点（即左子树中先序遍历的最后一个节点）
     - 将当前节点的右子树接到这个最右节点的右边
     - 将当前节点的左子树移到右边，左子节点设为 null
   - 移动到当前节点的右子节点继续处理
2. 重复直到所有节点处理完毕

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

# 方法一：递归 + 列表存储
class Solution:
    def flatten(self, root: TreeNode) -> None:
        """
        Do not return anything, modify root in-place instead.
        """
        if not root:
            return
        
        # 前序遍历存储节点
        nodes = []
        def preorder(node):
            if not node:
                return
            nodes.append(node)
            preorder(node.left)
            preorder(node.right)
        
        preorder(root)
        
        # 重新连接
        for i in range(len(nodes) - 1):
            nodes[i].left = None
            nodes[i].right = nodes[i + 1]

# 方法二：原地算法（O(1) 空间）
class Solution:
    def flatten(self, root: TreeNode) -> None:
        curr = root
        
        while curr:
            if curr.left:
                # 找到左子树的最右节点
                rightmost = curr.left
                while rightmost.right:
                    rightmost = rightmost.right
                
                # 将原右子树接到最右节点右边
                rightmost.right = curr.right
                
                # 将左子树移到右边，左子节点设为空
                curr.right = curr.left
                curr.left = None
            
            # 继续处理下一个节点
            curr = curr.right
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

// 方法一：递归 + 列表存储
class Solution {
    public void flatten(TreeNode root) {
        if (root == null) return;
        
        List<TreeNode> nodes = new ArrayList<>();
        preorder(root, nodes);
        
        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).left = null;
            nodes.get(i).right = nodes.get(i + 1);
        }
    }
    
    private void preorder(TreeNode node, List<TreeNode> nodes) {
        if (node == null) return;
        nodes.add(node);
        preorder(node.left, nodes);
        preorder(node.right, nodes);
    }
}

// 方法二：原地算法（O(1) 空间）
class Solution {
    public void flatten(TreeNode root) {
        TreeNode curr = root;
        
        while (curr != null) {
            if (curr.left != null) {
                // 找到左子树的最右节点
                TreeNode rightmost = curr.left;
                while (rightmost.right != null) {
                    rightmost = rightmost.right;
                }
                
                // 将原右子树接到最右节点右边
                rightmost.right = curr.right;
                
                // 将左子树移到右边，左子节点设为空
                curr.right = curr.left;
                curr.left = null;
            }
            
            // 继续处理下一个节点
            curr = curr.right;
        }
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

// 原地算法（O(1) 空间）
void flatten(struct TreeNode* root) {
    struct TreeNode* curr = root;
    
    while (curr != NULL) {
        if (curr->left != NULL) {
            // 找到左子树的最右节点
            struct TreeNode* rightmost = curr->left;
            while (rightmost->right != NULL) {
                rightmost = rightmost->right;
            }
            
            // 将原右子树接到最右节点右边
            rightmost->right = curr->right;
            
            // 将左子树移到右边，左子节点设为空
            curr->right = curr->left;
            curr->left = NULL;
        }
        
        // 继续处理下一个节点
        curr = curr->right;
    }
}
```

---