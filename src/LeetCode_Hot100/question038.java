package LeetCode_Hot100;

/*
337. 打家劫舍 III

小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 `root`。

除了 `root` 之外，每栋房子有且只有一个"父"房子与之相连。
一番侦察之后，聪明的小偷意识到"这个地方的所有房屋的排列类似于一棵二叉树"。
如果两个直接相连的房子在同一晚上被打劫，房屋将自动报警。

给定二叉树的 `root`。返回 **在不触动警报的情况下，小偷能够盗取的最高金额**。
*/
public class question038 {
    public static void main(String[] args) {
        // root = [3,2,3,null,3,null,1]
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = null;
        root.left.right = new TreeNode(3);
        root.right.left = null;
        root.right.right = new TreeNode(1);
        
        System.out.println(rob(root));

    }

    public static int rob(TreeNode root) {
        int[] res = dfs(root);
        return Math.max(res[0], res[1]);
    }

    // 后序遍历
    public static int[] dfs(TreeNode node) {
        if (node == null) {
            return new int[]{0, 0};
        }

        // 递归遍历左右子节点
        int[] left = dfs(node.left);
        int[] right = dfs(node.right);

        // 偷当前节点：不能偷子节点
        int robCurrent = node.val + left[1] + right[1];

        // 不偷当前节点：可以偷子节点，也可以不偷子节点，取最大值
        int notRobCurrent = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);

        return new int[]{robCurrent, notRobCurrent};
    }
}
