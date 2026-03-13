package LeetCode_Hot100;

/*
104. 二叉树的最大深度

给定一个二叉树 `root`，返回其最大深度。

二叉树的 **最大深度** 是指从根节点到最远叶子节点的最长路径上的节点数。
*/
public class question0081 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
                                    new TreeNode(9),
                                    new TreeNode(20, 
                                        new TreeNode(15),
                                        new TreeNode(7))
        );

        question0081 sl81 = new question0081();
        System.out.println(sl81.maxDepth(root));

    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int left = maxDepth(root.left);
        int right = maxDepth(root.right);

        return Math.max(left, right) + 1;

    }
}
