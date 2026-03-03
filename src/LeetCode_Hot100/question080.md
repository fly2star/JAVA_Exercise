# 105. 从前序与中序遍历序列构造二叉树

**难度: 中等**

## 题目描述
给定两个整数数组 preorder 和 inorder，其中 preorder 是二叉树的先序遍历，inorder 是同一棵树的中序遍历，请构造二叉树并返回其根节点。

---

## 示例说明
### 示例 1：

![tree](../../readFile/image/tree_105.jpg)

输入：preorder = [3,9,20,15,7], inorder = [9,3,15,20,7]  
输出：[3,9,20,null,null,15,7]

### 示例 2：
输入：preorder = [-1], inorder = [-1]  
输出：[-1]

---

## 提示：
- 1 ≤ preorder.length ≤ 3000
- inorder.length == preorder.length
- -3000 ≤ preorder[i], inorder[i] ≤ 3000
- preorder 和 inorder 均 **无重复** 元素
- inorder 均出现在 preorder
- preorder 保证为二叉树的前序遍历序列
- inorder 保证为二叉树的中序遍历序列

---

## 解题思路

### 核心思想
利用前序遍历和中序遍历的性质：前序遍历的第一个元素是根节点，在中序遍历中找到根节点的位置，其左边是左子树的中序遍历，右边是右子树的中序遍历。然后递归地构建左右子树。

### 关键观察
- 前序遍历顺序：根节点 → 左子树 → 右子树
- 中序遍历顺序：左子树 → 根节点 → 右子树
- 通过根节点在中序遍历中的位置，可以确定左右子树的节点数量
- 使用哈希表存储中序遍历中值和索引的映射，可以在 O(1) 时间内找到根节点位置

### 算法步骤
1. 创建哈希表，将中序遍历的值和索引对应起来
2. 定义递归函数 `build(preorderLeft, preorderRight, inorderLeft, inorderRight)`：
   - 如果 `preorderLeft > preorderRight`，返回 null
   - 前序遍历的第一个节点是根节点
   - 在中序遍历中找到根节点的位置 `inorderRootIndex`
   - 计算左子树的节点数量：`leftSize = inorderRootIndex - inorderLeft`
   - 递归构建左子树：
     - 前序遍历范围：`[preorderLeft + 1, preorderLeft + leftSize]`
     - 中序遍历范围：`[inorderLeft, inorderRootIndex - 1]`
   - 递归构建右子树：
     - 前序遍历范围：`[preorderLeft + leftSize + 1, preorderRight]`
     - 中序遍历范围：`[inorderRootIndex + 1, inorderRight]`
   - 返回根节点
3. 调用递归函数并返回结果

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
    def buildTree(self, preorder: List[int], inorder: List[int]) -> Optional[TreeNode]:
        # 创建中序遍历的值到索引的映射
        inorder_map = {val: idx for idx, val in enumerate(inorder)}
        
        def build(preorder_left: int, preorder_right: int, inorder_left: int, inorder_right: int):
            if preorder_left > preorder_right:
                return None
            
            # 前序遍历的第一个节点是根节点
            root_val = preorder[preorder_left]
            root = TreeNode(root_val)
            
            # 在中序遍历中找到根节点的位置
            inorder_root_index = inorder_map[root_val]
            
            # 计算左子树的节点数量
            left_size = inorder_root_index - inorder_left
            
            # 递归构建左子树
            root.left = build(preorder_left + 1, preorder_left + left_size, inorder_left, inorder_root_index - 1)
            
            # 递归构建右子树
            root.right = build(preorder_left + left_size + 1, preorder_right, inorder_root_index + 1, inorder_right)
            
            return root
        
        return build(0, len(preorder) - 1, 0, len(inorder) - 1)
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
    private Map<Integer, Integer> inorderMap;
    private int[] preorder;
    
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        this.preorder = preorder;
        // 创建中序遍历的值到索引的映射
        inorderMap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inorderMap.put(inorder[i], i);
        }
        
        return build(0, preorder.length - 1, 0, inorder.length - 1);
    }
    
    private TreeNode build(int preorderLeft, int preorderRight, int inorderLeft, int inorderRight) {
        if (preorderLeft > preorderRight) {
            return null;
        }
        
        // 前序遍历的第一个节点是根节点
        int rootVal = preorder[preorderLeft];
        TreeNode root = new TreeNode(rootVal);
        
        // 在中序遍历中找到根节点的位置
        int inorderRootIndex = inorderMap.get(rootVal);
        
        // 计算左子树的节点数量
        int leftSize = inorderRootIndex - inorderLeft;
        
        // 递归构建左子树
        root.left = build(preorderLeft + 1, preorderLeft + leftSize, inorderLeft, inorderRootIndex - 1);
        
        // 递归构建右子树
        root.right = build(preorderLeft + leftSize + 1, preorderRight, inorderRootIndex + 1, inorderRight);
        
        return root;
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

struct TreeNode* build(int* preorder, int preorderLeft, int preorderRight,
                       int* inorder, int inorderLeft, int inorderRight,
                       int* inorderMap) {
    if (preorderLeft > preorderRight) {
        return NULL;
    }
    
    // 前序遍历的第一个节点是根节点
    int rootVal = preorder[preorderLeft];
    struct TreeNode* root = (struct TreeNode*)malloc(sizeof(struct TreeNode));
    root->val = rootVal;
    
    // 在中序遍历中找到根节点的位置
    int inorderRootIndex = inorderMap[rootVal + 3000]; // 处理负数偏移
    
    // 计算左子树的节点数量
    int leftSize = inorderRootIndex - inorderLeft;
    
    // 递归构建左子树
    root->left = build(preorder, preorderLeft + 1, preorderLeft + leftSize,
                       inorder, inorderLeft, inorderRootIndex - 1, inorderMap);
    
    // 递归构建右子树
    root->right = build(preorder, preorderLeft + leftSize + 1, preorderRight,
                        inorder, inorderRootIndex + 1, inorderRight, inorderMap);
    
    return root;
}

struct TreeNode* buildTree(int* preorder, int preorderSize, int* inorder, int inorderSize) {
    // 创建中序遍历的值到索引的映射
    // 使用数组模拟哈希表，值范围是 -3000 到 3000，偏移 3000 作为索引
    int* inorderMap = (int*)malloc(6001 * sizeof(int));
    for (int i = 0; i < 6001; i++) {
        inorderMap[i] = -1;
    }
    
    for (int i = 0; i < inorderSize; i++) {
        inorderMap[inorder[i] + 3000] = i;  // 偏移 3000 处理负数
    }
    
    struct TreeNode* root = build(preorder, 0, preorderSize - 1,
                                   inorder, 0, inorderSize - 1,
                                   inorderMap);
    
    free(inorderMap);
    return root;
}
```

---