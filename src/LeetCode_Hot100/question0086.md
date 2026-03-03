# 94. 二叉树的中序遍历

**难度: 简单**

## 题目描述
给定一个二叉树的根节点 `root`，返回 **它的中序遍历**。

---

## 示例说明
### 示例 1：

![inorder_1](../../readFile/image/inorder_1.jpg)

输入：root = [1,null,2,3]  
输出：[1,3,2]

### 示例 2：
输入：root = []  
输出：[]

### 示例 3：
输入：root = [1]  
输出：[1]

---

## 提示：
- 树中节点数目在范围 [0, 100] 内
- -100 ≤ Node.val ≤ 100

---

## 解题思路

### 核心思想
二叉树的中序遍历顺序为：**左子树 → 根节点 → 右子树**。可以通过**递归**或**迭代**（使用栈）两种方式实现。

### 关键观察
- 递归方法简洁直观，但可能受递归深度限制
- 迭代方法使用栈模拟递归过程，空间复杂度与递归相同，但更灵活

### 算法步骤

#### 方法一：递归
1. 定义辅助函数 `inorder(node)`：
   - 如果节点为空，返回
   - 递归遍历左子树
   - 将节点值加入结果列表
   - 递归遍历右子树
2. 调用 `inorder(root)` 并返回结果

#### 方法二：迭代（使用栈）
1. 初始化栈 `stack` 和结果列表 `result`
2. 初始化当前节点 `curr = root`
3. 当 `curr != null` 或栈不为空时：
   - 将当前节点及其所有左子节点入栈
   - 弹出栈顶节点，将其值加入结果列表
   - 将当前节点指向弹出节点的右子节点
4. 返回结果列表

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
    def inorderTraversal(self, root: Optional[TreeNode]) -> List[int]:
        result = []
        
        def inorder(node):
            if not node:
                return
            inorder(node.left)
            result.append(node.val)
            inorder(node.right)
        
        inorder(root)
        return result

# 方法二：迭代
class Solution:
    def inorderTraversal(self, root: Optional[TreeNode]) -> List[int]:
        result = []
        stack = []
        curr = root
        
        while curr or stack:
            # 将当前节点及其所有左子节点入栈
            while curr:
                stack.append(curr)
                curr = curr.left
            
            # 弹出栈顶节点
            curr = stack.pop()
            result.append(curr.val)
            
            # 转向右子树
            curr = curr.right
        
        return result
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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        inorder(root, result);
        return result;
    }
    
    private void inorder(TreeNode node, List<Integer> result) {
        if (node == null) {
            return;
        }
        inorder(node.left, result);
        result.add(node.val);
        inorder(node.right, result);
    }
}

// 方法二：迭代
class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();
        TreeNode curr = root;
        
        while (curr != null || !stack.isEmpty()) {
            // 将当前节点及其所有左子节点入栈
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            
            // 弹出栈顶节点
            curr = stack.pop();
            result.add(curr.val);
            
            // 转向右子树
            curr = curr.right;
        }
        
        return result;
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

/**
 * Note: The returned array must be malloced, assume caller calls free().
 */

// 方法一：递归
void inorder(struct TreeNode* node, int* result, int* returnSize) {
    if (node == NULL) {
        return;
    }
    inorder(node->left, result, returnSize);
    result[(*returnSize)++] = node->val;
    inorder(node->right, result, returnSize);
}

int* inorderTraversal(struct TreeNode* root, int* returnSize) {
    *returnSize = 0;
    int* result = (int*)malloc(100 * sizeof(int));
    inorder(root, result, returnSize);
    return result;
}

// 方法二：迭代
int* inorderTraversal(struct TreeNode* root, int* returnSize) {
    *returnSize = 0;
    int* result = (int*)malloc(100 * sizeof(int));
    
    // 使用数组模拟栈
    struct TreeNode** stack = (struct TreeNode**)malloc(100 * sizeof(struct TreeNode*));
    int top = -1;
    struct TreeNode* curr = root;
    
    while (curr != NULL || top >= 0) {
        // 将当前节点及其所有左子节点入栈
        while (curr != NULL) {
            stack[++top] = curr;
            curr = curr->left;
        }
        
        // 弹出栈顶节点
        curr = stack[top--];
        result[(*returnSize)++] = curr->val;
        
        // 转向右子树
        curr = curr->right;
    }
    
    free(stack);
    return result;
}
```

---