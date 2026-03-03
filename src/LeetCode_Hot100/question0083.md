# 101. 对称二叉树

**难度: 简单**

## 题目描述
给你一个二叉树的根节点 `root`，检查它是否轴对称。

---

## 示例说明
### 示例 1：

![1698026966-JDYPDU-image](../../readFile/image/1698026966-JDYPDU-image.png)

输入：root = [1,2,2,3,4,4,3]  
输出：true  
解释：二叉树是对称的。

### 示例 2：

![1698027008-nPFLbM-image](../../readFile/image/1698027008-nPFLbM-image.png)

输入：root = [1,2,2,null,3,null,3]  
输出：false  
解释：二叉树不是对称的。

---

## 提示：
- 树中节点数目在范围 [1, 1000] 内
- -100 ≤ Node.val ≤ 100

---

## 解题思路

### 核心思想
判断一棵树是否轴对称，可以转化为判断它的左子树和右子树是否互为镜像。可以使用**递归**或**迭代**两种方法实现。

### 关键观察
- 两个树互为镜像的条件：
  - 它们的根节点值相等
  - 每个树的左子树与另一个树的右子树互为镜像
  - 每个树的右子树与另一个树的左子树互为镜像
- 空树是对称的
- 如果左右子树都为空，也是对称的

### 算法步骤

#### 方法一：递归
1. 定义递归函数 `isMirror(left, right)`：
   - 如果左右节点都为空，返回 true
   - 如果其中一个为空，返回 false
   - 如果两个节点的值不相等，返回 false
   - 递归判断：`left.left` 与 `right.right` 是否互为镜像，且 `left.right` 与 `right.left` 是否互为镜像
2. 调用 `isMirror(root.left, root.right)`

#### 方法二：迭代（使用队列）
1. 将根节点的左右子节点入队
2. 每次从队列中取出两个节点进行比较：
   - 如果都为空，继续循环
   - 如果其中一个为空，返回 false
   - 如果值不相等，返回 false
   - 将左节点的左子节点和右节点的右子节点入队
   - 将左节点的右子节点和右节点的左子节点入队
3. 队列为空时返回 true

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
    def isSymmetric(self, root: Optional[TreeNode]) -> bool:
        if not root:
            return True
        
        def isMirror(left: TreeNode, right: TreeNode) -> bool:
            # 如果左右节点都为空，返回 True
            if not left and not right:
                return True
            # 如果其中一个为空，返回 False
            if not left or not right:
                return False
            # 如果值不相等，返回 False
            if left.val != right.val:
                return False
            
            # 递归判断
            return isMirror(left.left, right.right) and isMirror(left.right, right.left)
        
        return isMirror(root.left, root.right)

# 方法二：迭代（使用队列）
class Solution:
    def isSymmetric(self, root: Optional[TreeNode]) -> bool:
        if not root:
            return True
        
        from collections import deque
        queue = deque([root.left, root.right])
        
        while queue:
            left = queue.popleft()
            right = queue.popleft()
            
            # 如果都为空，继续循环
            if not left and not right:
                continue
            # 如果其中一个为空
            if not left or not right:
                return False
            # 如果值不相等
            if left.val != right.val:
                return False
            
            # 将下一对节点入队
            queue.append(left.left)
            queue.append(right.right)
            queue.append(left.right)
            queue.append(right.left)
        
        return True
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
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isMirror(root.left, root.right);
    }
    
    private boolean isMirror(TreeNode left, TreeNode right) {
        // 如果左右节点都为空，返回 true
        if (left == null && right == null) {
            return true;
        }
        // 如果其中一个为空，返回 false
        if (left == null || right == null) {
            return false;
        }
        // 如果值不相等，返回 false
        if (left.val != right.val) {
            return false;
        }
        
        // 递归判断
        return isMirror(left.left, right.right) && isMirror(left.right, right.left);
    }
}

// 方法二：迭代（使用队列）
class Solution {
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root.left);
        queue.offer(root.right);
        
        while (!queue.isEmpty()) {
            TreeNode left = queue.poll();
            TreeNode right = queue.poll();
            
            // 如果都为空，继续循环
            if (left == null && right == null) {
                continue;
            }
            // 如果其中一个为空
            if (left == null || right == null) {
                return false;
            }
            // 如果值不相等
            if (left.val != right.val) {
                return false;
            }
            
            // 将下一对节点入队
            queue.offer(left.left);
            queue.offer(right.right);
            queue.offer(left.right);
            queue.offer(right.left);
        }
        
        return true;
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
bool isMirror(struct TreeNode* left, struct TreeNode* right) {
    // 如果左右节点都为空，返回 true
    if (left == NULL && right == NULL) {
        return true;
    }
    // 如果其中一个为空，返回 false
    if (left == NULL || right == NULL) {
        return false;
    }
    // 如果值不相等，返回 false
    if (left->val != right->val) {
        return false;
    }
    
    // 递归判断
    return isMirror(left->left, right->right) && isMirror(left->right, right->left);
}

bool isSymmetric(struct TreeNode* root) {
    if (root == NULL) {
        return true;
    }
    return isMirror(root->left, root->right);
}

// 方法二：迭代（使用数组模拟队列）
bool isSymmetric(struct TreeNode* root) {
    if (root == NULL) {
        return true;
    }
    
    // 使用数组模拟队列
    struct TreeNode** queue = (struct TreeNode**)malloc(2000 * sizeof(struct TreeNode*));
    int front = 0, rear = 0;
    queue[rear++] = root->left;
    queue[rear++] = root->right;
    
    while (front < rear) {
        struct TreeNode* left = queue[front++];
        struct TreeNode* right = queue[front++];
        
        // 如果都为空，继续循环
        if (left == NULL && right == NULL) {
            continue;
        }
        // 如果其中一个为空
        if (left == NULL || right == NULL) {
            free(queue);
            return false;
        }
        // 如果值不相等
        if (left->val != right->val) {
            free(queue);
            return false;
        }
        
        // 将下一对节点入队
        queue[rear++] = left->left;
        queue[rear++] = right->right;
        queue[rear++] = left->right;
        queue[rear++] = right->left;
    }
    
    free(queue);
    return true;
}
```

---