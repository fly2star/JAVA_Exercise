# 538. 把二叉搜索树转换为累加树

**难度: 中等**

## 题目描述
给出二叉搜索树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），使每个节点的值的新值等于原树中大于或等于该节点值的所有节点值之和。

二叉搜索树满足下列约束条件：
- 节点的左子树仅包含键 **小于** 节点键的节点
- 节点的右子树仅包含键 **大于** 节点键的节点
- 左右子树也必须是二叉搜索树

注意：本题和 1038 题相同。

---

## 示例说明
### 示例 1：

![tree](../../readFile/image/tree.png)

输入：root = [4,1,6,0,2,5,7,null,null,null,3,null,null,null,8]  
输出：[30,36,21,36,35,26,15,null,null,null,33,null,null,null,8]  
解释：累加树中，每个节点的值等于原树中所有大于等于该节点值的节点值之和。

### 示例 2：
输入：root = [0,null,1]  
输出：[1,null,1]

### 示例 3：
输入：root = [1,0,2]  
输出：[3,3,2]

---

## 提示：
- 树的节点数介于 0 和 10^4 之间
- 每个节点的值介于 -10^4 和 10^4 之间
- 树中的所有值互不相同
- 给定的树为二叉搜索树

---

## 解题思路

### 核心思想
利用二叉搜索树的性质：中序遍历得到的是升序序列。而累加树要求每个节点的新值等于原树中所有大于等于它的节点值之和，这相当于**反序中序遍历**（右-根-左），在遍历过程中累加节点的值。

### 关键观察
- 二叉搜索树的中序遍历（左-根-右）得到升序序列
- 反序中序遍历（右-根-左）得到降序序列
- 在反序中序遍历过程中，可以维护一个累加和，每访问一个节点，就将当前累加和加到节点值上，并更新累加和

### 算法步骤
1. 定义一个全局变量 `total` 记录累加和，初始为 0
2. 从根节点开始进行反序中序遍历（右子树 → 根节点 → 左子树）：
   - 递归遍历右子树
   - 访问当前节点：将 `total` 加到当前节点值上，然后更新 `total` 为当前节点的新值
   - 递归遍历左子树
3. 返回根节点

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
    def convertBST(self, root: TreeNode) -> TreeNode:
        self.total = 0
        
        def dfs(node):
            if not node:
                return
            
            # 先遍历右子树
            dfs(node.right)
            
            # 处理当前节点
            self.total += node.val
            node.val = self.total
            
            # 再遍历左子树
            dfs(node.left)
        
        dfs(root)
        return root
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
    private int total = 0;
    
    public TreeNode convertBST(TreeNode root) {
        dfs(root);
        return root;
    }
    
    private void dfs(TreeNode node) {
        if (node == null) {
            return;
        }
        
        // 先遍历右子树
        dfs(node.right);
        
        // 处理当前节点
        total += node.val;
        node.val = total;
        
        // 再遍历左子树
        dfs(node.left);
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

int total;

void dfs(struct TreeNode* node) {
    if (node == NULL) {
        return;
    }
    
    // 先遍历右子树
    dfs(node->right);
    
    // 处理当前节点
    total += node->val;
    node->val = total;
    
    // 再遍历左子树
    dfs(node->left);
}

struct TreeNode* convertBST(struct TreeNode* root) {
    total = 0;
    dfs(root);
    return root;
}
```

---