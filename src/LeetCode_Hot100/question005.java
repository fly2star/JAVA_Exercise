package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-01-15:42
 **/
/*
226. 翻转二叉树
给你一棵二叉树的根节点 root ，翻转这棵二叉树，并返回其根节点。
*/
public class question005 {
    public static void main(String[] args) {

    }

    public static TreeNode invertTree(TreeNode root) {
        if (root == null) return null;
        TreeNode temp = root.left;
        root.left = invertTree(root.right);
        root.right = invertTree(temp);
        return root;
    }

    public static TreeNode invertTree2(TreeNode root) {
        if (root == null) return null;
        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);
        root.left = right;
        root.right = left;
        return root;

     }
}
