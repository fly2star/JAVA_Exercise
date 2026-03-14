package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.List;

/*
94. 二叉树的中序遍历

给定一个二叉树的根节点 `root`，返回 **它的中序遍历**。

## 提示：
    -- 树中节点数目在范围 [0, 100] 内
    -- -100 ≤ Node.val ≤ 100
*/
public class question0086 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode r = new TreeNode(2);
        TreeNode rl = new TreeNode(3);
        root.right = r;
        r.left = rl;

        question0086 sl86 = new question0086();
        List<Integer> result = sl86.inorderTraversal(root);
        System.out.println(result); 
    }

    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        inorder(root, res);
        return res;
    }

    private void inorder(TreeNode node, List<Integer> res) {
        if (node == null) {
            return;
        }
        inorder(node.left, res);
        res.add(node.val);
        inorder(node.right, res);
    }
}
