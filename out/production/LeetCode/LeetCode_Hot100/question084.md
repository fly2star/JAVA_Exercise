# 98. 验证二叉搜索树

**难度: 中等**

## 题目描述
给你一个二叉树的根节点 `root`，判断其是否是一个有效的二叉搜索树。

## 有效二叉搜索树定义如下：
- 节点的左子树只包含 **严格小于** 当前节点的数
- 节点的右子树只包含 **严格大于** 当前节点的数
- 所有左子树和右子树自身必须也是二叉搜索树

---

## 示例说明
### 示例 1：

![tree1](../../readFile/image/tree1_98.jpg)

输入：root = [2,1,3]  
输出：true  
解释：二叉树满足二叉搜索树的所有条件。

### 示例 2：

![tree2](../../readFile/image/tree2_98.jpg)

输入：root = [5,1,4,null,null,3,6]  
输出：false  
解释：根节点的值是 5，但右子节点的值是 4，不满足右子树所有节点都大于根节点的条件。

---

## 提示：
- 树中节点数目在范围 [1, 10^4] 内
- -2^31 ≤ Node.val ≤ 2^31 - 1

---

## 解题思路

### 核心思想
二叉搜索树的一个重要性质是：**中序遍历得到的序列是严格递增的**。可以利用这个性质来验证，也可以在递归过程中传递上下界来检查。

### 关键观察
- 方法一（中序遍历）：对二叉树进行中序遍历，检查遍历序列是否严格递增
- 方法二（递归边界）：在递归过程中，为每个节点维护一个允许的取值范围 (lower, upper)，确保节点值在这个范围内

### 算法步骤

#### 方法一：中序遍历
1. 对二叉树进行中序遍历
2. 在遍历过程中，记录前一个节点的值
3. 如果当前节点的值小于等于前一个节点的值，说明不是二叉搜索树
4. 遍历结束后返回 true

#### 方法二：递归边界
1. 定义递归函数 `isValid(node, lower, upper)`：
   - 如果节点为空，返回 true
   - 如果节点值不在 (lower, upper) 范围内，返回 false
   - 递归检查左子树：范围是 (lower, node.val)
   - 递归检查右子树：范围是 (node.val, upper)
2. 从根节点开始，初始范围是 (-∞, +∞)

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

# 方法一：中序遍历
class Solution:
    def isValidBST(self, root: Optional[TreeNode]) -> bool:
        self.prev = float('-inf')
        
        def inorder(node):
            if not node:
                return True
            
            # 检查左子树
            if not inorder(node.left):
                return False
            
            # 检查当前节点
            if node.val <= self.prev:
                return False
            self.prev = node.val
            
            # 检查右子树
            return inorder(node.right)
        
        return inorder(root)

# 方法二：递归边界
class Solution:
    def isValidBST(self, root: Optional[TreeNode]) -> bool:
        def validate(node, lower, upper):
            if not node:
                return True
            
            # 检查当前节点是否在范围内
            if node.val <= lower or node.val >= upper:
                return False
            
            # 递归检查左右子树
            return (validate(node.left, lower, node.val) and 
                    validate(node.right, node.val, upper))
        
        return validate(root, float('-inf'), float('inf'))
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

// 方法一：中序遍历
class Solution {
    private long prev = Long.MIN_VALUE;
    
    public boolean isValidBST(TreeNode root) {
        return inorder(root);
    }
    
    private boolean inorder(TreeNode node) {
        if (node == null) {
            return true;
        }
        
        // 检查左子树
        if (!inorder(node.left)) {
            return false;
        }
        
        // 检查当前节点
        if (node.val <= prev) {
            return false;
        }
        prev = node.val;
        
        // 检查右子树
        return inorder(node.right);
    }
}

// 方法二：递归边界
class Solution {
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    
    private boolean validate(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }
        
        // 检查当前节点是否在范围内
        if (node.val <= lower || node.val >= upper) {
            return false;
        }
        
        // 递归检查左右子树
        return validate(node.left, lower, node.val) && 
               validate(node.right, node.val, upper);
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

// 方法二：递归边界
bool validate(struct TreeNode* node, long lower, long upper) {
    if (node == NULL) {
        return true;
    }
    
    // 检查当前节点是否在范围内
    if (node->val <= lower || node->val >= upper) {
        return false;
    }
    
    // 递归检查左右子树
    return validate(node->left, lower, node->val) && 
           validate(node->right, node->val, upper);
}

bool isValidBST(struct TreeNode* root) {
    return validate(root, LONG_MIN, LONG_MAX);
}

// 方法一：中序遍历（使用全局变量）
long prev;

bool inorder(struct TreeNode* node) {
    if (node == NULL) {
        return true;
    }
    
    // 检查左子树
    if (!inorder(node->left)) {
        return false;
    }
    
    // 检查当前节点
    if (node->val <= prev) {
        return false;
    }
    prev = node->val;
    
    // 检查右子树
    return inorder(node->right);
}

bool isValidBST(struct TreeNode* root) {
    prev = LONG_MIN;
    return inorder(root);
}
```

---