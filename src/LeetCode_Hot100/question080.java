package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;

/*
105. 从前序与中序遍历序列构造二叉树

给定两个整数数组 preorder 和 inorder，
其中 preorder 是二叉树的先序遍历，inorder 是同一棵树的中序遍历，
请构造二叉树并返回其根节点。

## 提示：
-- 1 ≤ preorder.length ≤ 3000
-- inorder.length == preorder.length
-- -3000 ≤ preorder[i], inorder[i] ≤ 3000
-= preorder 和 inorder 均 **无重复** 元素
-- inorder 均出现在 preorder
-= preorder 保证为二叉树的前序遍历序列
-= inorder 保证为二叉树的中序遍历序列
*/
public class question080 {
    public static void main(String[] args) {
        int[] preorder = new int[]{3,9,20,15,7};
        int[] inorder = new int[]{9,3,15,20,7}; 

        question080 sl80 = new question080();
        TreeNode rootTree = sl80.buildTree(preorder, inorder); 

        System.out.println(rootTree.val);
    }

    private Map<Integer, Integer> inorderMap;
    private int[] preorder;

    public TreeNode buildTree(int[] preorder, int[] inorder){
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
