# 543. 二叉树的直径

**难度: 简单**

## 题目描述
给你一棵二叉树的根节点，返回该树的 **直径**。

二叉树的 **直径** 是指树中任意两个节点之间最长路径的长度。这条路径可能经过也可能不经过根节点 `root`。

两节点之间路径的长度由它们之间边数表示。

---

## 示例说明
### 示例 1：
![diamtree](../../readFile/image/diamtree.jpg)

输入：root = [1,2,3,4,5]  
输出：3  
解释：3，取路径 [4,2,1,3] 或 [5,2,1,3] 的长度。

### 示例 2：
输入：root = [1,2]  
输出：1

---

## 提示：
- 树中节点数目在范围 [1, 10^4] 内
- -100 ≤ Node.val ≤ 100

---

## 解题思路

### 核心思想
二叉树的直径本质上是**树中任意两个节点间的最长路径**，这个路径可以看作是从某个节点出发，经过其左右子树的最长路径之和。我们可以通过**深度优先搜索（DFS）**计算每个节点的左右子树深度，并更新全局最大值。

### 关键观察
- 直径不一定经过根节点，可能出现在任意子树中
- 对于每个节点，经过该节点的最长路径长度 = 左子树最大深度 + 右子树最大深度
- 树的深度定义为从根节点到最远叶子节点的路径上的节点数，而直径是路径上的边数，所以直径 = 左子树深度 + 右子树深度
- 需要在递归计算深度的同时，动态更新全局最大直径

### 算法步骤
1. 定义全局变量 `maxDiameter` 记录最大直径
2. 定义递归函数 `depth(node)`：
   - 如果节点为空，返回深度 0
   - 递归计算左子树深度 `leftDepth`
   - 递归计算右子树深度 `rightDepth`
   - 更新 `maxDiameter = max(maxDiameter, leftDepth + rightDepth)`
   - 返回当前节点的深度：`max(leftDepth, rightDepth) + 1`
3. 调用 `depth(root)` 开始递归
4. 返回 `maxDiameter`

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
    def diameterOfBinaryTree(self, root: TreeNode) -> int:
        self.max_diameter = 0
        
        def depth(node):
            if not node:
                return 0
            
            # 递归计算左右子树深度
            left_depth = depth(node.left)
            right_depth = depth(node.right)
            
            # 更新最大直径：经过当前节点的路径长度
            self.max_diameter = max(self.max_diameter, left_depth + right_depth)
            
            # 返回当前节点的深度
            return max(left_depth, right_depth) + 1
        
        depth(root)
        return self.max_diameter
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
    private int maxDiameter = 0;
    
    public int diameterOfBinaryTree(TreeNode root) {
        depth(root);
        return maxDiameter;
    }
    
    private int depth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        
        // 递归计算左右子树深度
        int leftDepth = depth(node.left);
        int rightDepth = depth(node.right);
        
        // 更新最大直径：经过当前节点的路径长度
        maxDiameter = Math.max(maxDiameter, leftDepth + rightDepth);
        
        // 返回当前节点的深度
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
```

### C 代码实现
```c
struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
};

int maxDiameter;

int depth(struct TreeNode* node) {
    if (node == NULL) {
        return 0;
    }
    
    // 递归计算左右子树深度
    int leftDepth = depth(node->left);
    int rightDepth = depth(node->right);
    
    // 更新最大直径：经过当前节点的路径长度
    if (leftDepth + rightDepth > maxDiameter) {
        maxDiameter = leftDepth + rightDepth;
    }
    
    // 返回当前节点的深度
    return (leftDepth > rightDepth ? leftDepth : rightDepth) + 1;
}

int diameterOfBinaryTree(struct TreeNode* root) {
    maxDiameter = 0;
    depth(root);
    return maxDiameter;
}
```

---