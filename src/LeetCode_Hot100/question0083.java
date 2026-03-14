package LeetCode_Hot100;

/*
101. 对称二叉树

给你一个二叉树的根节点 `root`，检查它是否轴对称。

## 提示：
    -- 树中节点数目在范围 [1, 1000] 内
    -- -100 ≤ Node.val ≤ 100
*/
public class question0083 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(1,
                                        new TreeNode(2,
                                            new TreeNode(3),
                                            new TreeNode(4)),
                                        new TreeNode(2,
                                            new TreeNode(4), 
                                            new TreeNode(3)));
        
        question0083 sl83 = new question0083();
        System.out.println(sl83.isSymmetric(root));
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return isMirror(root.left, root.right);
    }

    private boolean isMirror(TreeNode leftNode, TreeNode rightNode) {
        // 如果左右节点都为空，返回 true
        if (leftNode == null && rightNode == null) {
            return true;
        }
        
        // 如果其中一个为空，返回 false
        if (leftNode == null || rightNode == null) {
            return false;
        }

        // 如果值不相等，返回 false
        if (leftNode.val != rightNode.val) {
            return false;
        }

        // 递归判断
        return isMirror(leftNode.left, rightNode.right) && isMirror(leftNode.right, rightNode.left);


        
    }
}
