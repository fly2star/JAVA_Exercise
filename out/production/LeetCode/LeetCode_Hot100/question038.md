# 337. 打家劫舍 III

**难度: 中等**

## 题目描述
小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 `root`。

除了 `root` 之外，每栋房子有且只有一个"父"房子与之相连。一番侦察之后，聪明的小偷意识到"这个地方的所有房屋的排列类似于一棵二叉树"。如果两个直接相连的房子在同一晚上被打劫，房屋将自动报警。

给定二叉树的 `root`。返回 **在不触动警报的情况下，小偷能够盗取的最高金额**。

---

## 示例说明
### 示例 1：
![rob1-tree](../../readFile/image/rob1-tree.jpg)

输入：root = [3,2,3,null,3,null,1]
输出：7
解释：
小偷一晚能够盗取的最高金额为 3 + 3 + 1 = 7

- 最优选择：偷根节点3（金额3），不偷左子节点2，偷左子节点的右子节点3（金额3），偷右子节点的右子节点1（金额1）

---

### 示例 2：
![rob2-tree](../../readFile/image/rob2-tree.jpg)

输入：root = [3,4,5,1,3,null,1]
输出：9
解释：
小偷一晚能够盗取的最高金额为 4 + 5 = 9

- 最优选择：不偷根节点3，偷左子节点4（金额4），偷右子节点5（金额5）

---

## 提示：
- 树的节点数在 [1, 10^4] 范围内
- 0 ≤ Node.val ≤ 10^4

---

## 解题思路

### 核心思想
这是一个树形动态规划问题。对于每个节点，有两种选择：
1. **偷当前节点**：则不能偷其左右子节点，但可以偷其孙子节点
2. **不偷当前节点**：则可以偷其左右子节点

### 关键观察
1. 每个节点的最优解依赖于其子节点的最优解
2. 可以使用后序遍历（左右根）来处理二叉树，因为需要先知道子节点的结果
3. 对于每个节点，返回两个值：
   - `rob`: 偷当前节点能获得的最大金额
   - `not_rob`: 不偷当前节点能获得的最大金额

### 算法步骤
1. 使用后序遍历遍历二叉树
2. 对于每个节点：
   - 如果偷当前节点：金额 = 当前节点值 + 左子节点不偷的金额 + 右子节点不偷的金额
   - 如果不偷当前节点：金额 = max(左子节点偷，左子节点不偷) + max(右子节点偷，右子节点不偷)
3. 递归计算每个节点的这两种状态
4. 返回根节点的两种状态中的最大值

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import Optional

# Definition for a binary tree node.
class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right

class Solution:
    def rob(self, root: Optional[TreeNode]) -> int:
        # 返回一个元组：(偷当前节点的最大金额，不偷当前节点的最大金额)
        def dfs(node):
            if not node:
                return (0, 0)
            
            # 递归计算左右子节点
            left_rob, left_not_rob = dfs(node.left)
            right_rob, right_not_rob = dfs(node.right)
            
            # 偷当前节点：不能偷子节点
            rob_current = node.val + left_not_rob + right_not_rob
            
            # 不偷当前节点：可以偷子节点，也可以不偷子节点，取最大值
            not_rob_current = max(left_rob, left_not_rob) + max(right_rob, right_not_rob)
            
            return (rob_current, not_rob_current)
        
        rob_root, not_rob_root = dfs(root)
        return max(rob_root, not_rob_root)
```

---

### Java 代码实现

```java
/**
 * Definition for a binary tree node.
 */
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
    public int rob(TreeNode root) {
        int[] result = dfs(root);
        return Math.max(result[0], result[1]);
    }
    
    // 返回一个数组：[偷当前节点的最大金额，不偷当前节点的最大金额]
    private int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }
        
        // 递归计算左右子节点
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);
        
        // 偷当前节点：不能偷子节点
        int robCurrent = node.val + left[1] + right[1];
        
        // 不偷当前节点：可以偷子节点，也可以不偷子节点，取最大值
        int notRobCurrent = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        
        return new int[]{robCurrent, notRobCurrent};
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

// 二叉树节点定义
struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
};

// 结果结构体：存储两种状态
struct Result {
    int rob;      // 偷当前节点的最大金额
    int not_rob;  // 不偷当前节点的最大金额
};

// 深度优先搜索
struct Result dfs(struct TreeNode* node) {
    if (node == NULL) {
        struct Result result = {0, 0};
        return result;
    }
    
    // 递归计算左右子节点
    struct Result left = dfs(node->left);
    struct Result right = dfs(node->right);
    
    // 偷当前节点：不能偷子节点
    int rob_current = node->val + left.not_rob + right.not_rob;
    
    // 不偷当前节点：可以偷子节点，也可以不偷子节点，取最大值
    int not_rob_current = 0;
    not_rob_current += (left.rob > left.not_rob) ? left.rob : left.not_rob;
    not_rob_current += (right.rob > right.not_rob) ? right.rob : right.not_rob;
    
    struct Result result;
    result.rob = rob_current;
    result.not_rob = not_rob_current;
    
    return result;
}

int rob(struct TreeNode* root) {
    struct Result result = dfs(root);
    return (result.rob > result.not_rob) ? result.rob : result.not_rob;
}

// 辅助函数：创建新节点
struct TreeNode* createNode(int val) {
    struct TreeNode* node = (struct TreeNode*)malloc(sizeof(struct TreeNode));
    node->val = val;
    node->left = NULL;
    node->right = NULL;
    return node;
}

// 测试代码
int main() {
    // 测试示例1
    printf("测试1:\n");
    struct TreeNode* root1 = createNode(3);
    root1->left = createNode(2);
    root1->right = createNode(3);
    root1->left->right = createNode(3);
    root1->right->right = createNode(1);
    
    int result1 = rob(root1);
    printf("输入: [3,2,3,null,3,null,1]\n");
    printf("输出: %d (期望: 7)\n\n", result1);
    
    // 释放内存
    free(root1->left->right);
    free(root1->right->right);
    free(root1->left);
    free(root1->right);
    free(root1);
    
    // 测试示例2
    printf("测试2:\n");
    struct TreeNode* root2 = createNode(3);
    root2->left = createNode(4);
    root2->right = createNode(5);
    root2->left->left = createNode(1);
    root2->left->right = createNode(3);
    root2->right->right = createNode(1);
    
    int result2 = rob(root2);
    printf("输入: [3,4,5,1,3,null,1]\n");
    printf("输出: %d (期望: 9)\n\n", result2);
    
    // 释放内存
    free(root2->left->left);
    free(root2->left->right);
    free(root2->right->right);
    free(root2->left);
    free(root2->right);
    free(root2);
    
    // 测试示例3
    printf("测试3:\n");
    struct TreeNode* root3 = createNode(1);
    int result3 = rob(root3);
    printf("输入: [1]\n");
    printf("输出: %d (期望: 1)\n", result3);
    
    free(root3);
    
    return 0;
}
```

---

### 复杂度分析
- **时间复杂度：** O(n)，其中 n 是二叉树的节点数，每个节点只访问一次
- **空间复杂度：** O(h)，其中 h 是二叉树的高度，递归调用栈的深度为树的高度

### 算法特点
1. **自底向上计算**：使用后序遍历，先计算子节点的结果，再计算父节点
2. **状态定义清晰**：每个节点维护两个状态值，便于状态转移
3. **避免重复计算**：每个节点的结果只计算一次
4. **适用于任意二叉树**：不要求是完全二叉树或平衡二叉树