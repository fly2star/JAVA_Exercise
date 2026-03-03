# 102. 二叉树的层序遍历

**难度: 中等**

## 题目描述
给你二叉树的根节点 `root`，返回其节点值的 **层序遍历**。（即逐层地，从左到右访问所有节点）。

---

## 示例说明
### 示例 1：

![tree1](../../readFile/image/tree1.jpg)

输入：root = [3,9,20,null,null,15,7]  
输出：[[3],[9,20],[15,7]]

### 示例 2：
输入：root = [1]  
输出：[[1]]

### 示例 3：
输入：root = []  
输出：[]

---

## 提示：
- 树中节点数目在范围 [0, 2000] 内
- -1000 ≤ Node.val ≤ 1000

---

## 解题思路

### 核心思想
使用**广度优先搜索（BFS）**，借助队列实现层次遍历。在遍历每一层时，需要知道当前层的节点数量，以便将同一层的节点放在同一个列表中。

### 关键观察
- 队列中存储的是待访问的节点
- 在开始遍历每一层之前，队列中的节点数就是当前层的节点数
- 遍历完一层后，队列中存放的是下一层的所有节点

### 算法步骤
1. 如果根节点为空，返回空列表
2. 初始化结果列表 `result` 和队列 `queue`，将根节点入队
3. 当队列不为空时循环：
   - 获取当前层的节点数 `levelSize = queue.size()`
   - 创建当前层的列表 `levelList`
   - 遍历当前层的所有节点：
     - 出队一个节点，将其值加入 `levelList`
     - 如果该节点有左子节点，左子节点入队
     - 如果该节点有右子节点，右子节点入队
   - 将 `levelList` 加入 `result`
4. 返回 `result`

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

class Solution:
    def levelOrder(self, root: Optional[TreeNode]) -> List[List[int]]:
        if not root:
            return []
        
        from collections import deque
        result = []
        queue = deque([root])
        
        while queue:
            level_size = len(queue)
            level_list = []
            
            for _ in range(level_size):
                node = queue.popleft()
                level_list.append(node.val)
                
                if node.left:
                    queue.append(node.left)
                if node.right:
                    queue.append(node.right)
            
            result.append(level_list)
        
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
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> levelList = new ArrayList<>();
            
            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelList.add(node.val);
                
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            
            result.add(levelList);
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
 * Return an array of arrays of size *returnSize.
 * The sizes of the arrays are returned as *returnColumnSizes array.
 * Note: Both returned array and *columnSizes array must be malloced, assume caller calls free().
 */

int** levelOrder(struct TreeNode* root, int* returnSize, int** returnColumnSizes) {
    *returnSize = 0;
    if (root == NULL) {
        *returnColumnSizes = NULL;
        return NULL;
    }
    
    // 分配最大可能的结果空间
    int** result = (int**)malloc(2000 * sizeof(int*));
    *returnColumnSizes = (int*)malloc(2000 * sizeof(int));
    
    // 使用数组模拟队列
    struct TreeNode** queue = (struct TreeNode**)malloc(2000 * sizeof(struct TreeNode*));
    int front = 0, rear = 0;
    queue[rear++] = root;
    
    while (front < rear) {
        int levelSize = rear - front;
        (*returnColumnSizes)[*returnSize] = levelSize;
        result[*returnSize] = (int*)malloc(levelSize * sizeof(int));
        
        for (int i = 0; i < levelSize; i++) {
            struct TreeNode* node = queue[front++];
            result[*returnSize][i] = node->val;
            
            if (node->left) {
                queue[rear++] = node->left;
            }
            if (node->right) {
                queue[rear++] = node->right;
            }
        }
        
        (*returnSize)++;
    }
    
    free(queue);
    return result;
}
```

---