package LeetCode_Hot100;

/*
543. 二叉树的直径

给你一棵二叉树的根节点，返回该树的 **直径**。

二叉树的 **直径** 是指树中任意两个节点之间最长路径的长度。这条路径可能经过也可能不经过根节点 `root`。
两节点之间路径的长度由它们之间边数表示。

## 提示：
    -- 树中节点数目在范围 [1, 10^4] 内
    -- -100 ≤ Node.val ≤ 100
*/
public class question0057 {

    private static int maxDiameter = 0;

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode left = new TreeNode(2);
        TreeNode right = new TreeNode(3);
        root.left = left;
        root.right = right;
        TreeNode ll = new TreeNode(4);
        TreeNode lr = new TreeNode(5);
        root.left.left = ll;
        root.left.right = lr;
        
        System.out.println(diameterOfBinaryTree(root));
    }

    // 方法1: DFS
    // 核心思想: 二叉树的直径 = 某个节点的左子树深度 + 右子树深度 的最大值
    public static int diameterOfBinaryTree(TreeNode root) {
        maxDepth(root);
        return maxDiameter;
    }

    private static int maxDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int left = maxDepth(node.left);
        int right = maxDepth(node.right);

        // 更新全局最大直径
        maxDiameter = Math.max(maxDiameter, left + right);

        // 返回当前字数的深度(用于上层计算)

        return Math.max(left, right) + 1; 
    }
}
