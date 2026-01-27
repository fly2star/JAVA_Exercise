package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-26-22:01
 **/
public class question031 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.right = new TreeNode(-3);
        root.left.left = new TreeNode(3);
        root.left.right = new TreeNode(2);
        root.right.right = new TreeNode(11);
        root.left.left.left = new TreeNode(3);
        root.left.left.right = new TreeNode(-2);
        root.left.right.right = new TreeNode(1);

        int targetSum = 8;
        System.out.println(pathSum(root, targetSum));
    }

    public static int pathSum(TreeNode root, int targetSum) {
        return traverse(root, targetSum);
    }

    private static int traverse(TreeNode node, int targetSum) {
        if(node==null) return 0;
        
        // 以当前节点为起点的路径数
        int count = dfs(node, 0, targetSum);
        count+=traverse(node.left, targetSum);
        count+=traverse(node.right, targetSum);
        return count;
    }

    private static int dfs(TreeNode node, int currSum, int targetSum) {
        if(node==null) return 0;

        currSum+=node.val;
        int count = currSum==targetSum ? 1 : 0;
        count+=dfs(node.left, currSum, targetSum);
        count+=dfs(node.right, currSum, targetSum);
        return count;
    }
}
