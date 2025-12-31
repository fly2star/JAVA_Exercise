package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-30-15:18
 **/
/*
124. 二叉树中的最大路径和

二叉树中的 路径 被定义为一条节点序列，序列中每对相邻节点之间都存在一条边。
同一个节点在一条路径序列中 至多出现一次 。该路径 至少包含一个 节点，且不一定经过根节点。

路径和 是路径中各节点值的总和。
给你一个二叉树的根节点 root ，返回其 最大路径和 。
*/
public class question25 {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        root.left = node1;
        root.right = node2;
        System.out.println(maxPathSum(root));
    }

    // 方法1: DFS
    static int maxSum = Integer.MIN_VALUE;
    public static int maxPathSum(TreeNode root) {
        maxGain(root);
        return maxSum;
    }

    // 该函数返回：以当前节点为起点的最大贡献值（只能选左或右一边）
    private static int maxGain(TreeNode node) {
        if (node == null) return 0;

        // 1. 递归计算左右子树的贡献。如果贡献是负数，我们宁愿不选（即取 0）
        int leftGain = Math.max(maxGain(node.left), 0);
        int rightGain = Math.max(maxGain(node.right), 0);

        // 2. 价格“拐点”路径和：当前节点值 + 左贡献 + 右贡献
        // 这种情况下，路径在当前节点处转折，连接了左右子树
        int priceNewPath = node.val + leftGain + rightGain;

        // 3. 更新全局最大值
        maxSum = Math.max(maxSum, priceNewPath);

        // 4. 返回节点对父节点的最大贡献（只能走一条路：左或右）
        return node.val + Math.max(leftGain, rightGain);
    }


    // 方法2: 动态规划: DFS 后序遍历
    //  所有树的题目，都想成一颗只有根、左节点、右节点 的小树。然后一颗颗小树构成整棵大树，所以只需要考虑这颗小树即可。
    //  接下来分情况， 按照题意：一颗三个节点的小树的结果只可能有如下6种情况：
    //      1.根 + 左 + 右
    //      2.根 + 左
    //      3.根 + 右
    //      4.根
    //      5.左
    //      6.右
    //  分析上述6种情况， 只有 2,3,4 可以向上累加，而1,5,6不可以累加（这个很好想，情况1向上累加的话，必然出现分叉，
    //  情况5和6直接就跟上面的树枝断开的，没法累加），所以我们找一个全局变量存储 1,5,6这三种不可累加的最大值，
    //  另一方面咱们用遍历树的方法求2,3,4这三种可以累加的情况。 最后把两类情况得到的最大值再取一个最大值即可。
    static long maxSum2 = Integer.MIN_VALUE;
    public static int maxPathSum2(TreeNode root) {
        // 这里的 newMax 对应你 Python 里的 new_max，即“可累加”类的全局最大
        long newMax = scan(root);

        // 最后两类情况取最大，并转回 int
        return (int) Math.max(maxSum, newMax);
    }


    private static long scan(TreeNode root) {
        if (root == null) {
            // 对应 Python 的 -sys.maxsize - 1
            // 这里用一个足够小的数，代表空节点不贡献任何值
            return Integer.MIN_VALUE;
        }

        long left = scan(root.left);
        long right = scan(root.right);

        // --- 情况 1, 5, 6: 不累加，直接存入变量 ---
        // root.val + left + right 是情况 1 (左+根+右)
        // left 是情况 5 (纯左)
        // right 是情况 6 (纯右)
        long case1 = root.val + left + right;
        maxSum2 = Math.max(maxSum2, Math.max(case1, Math.max(left, right)));

        // --- 情况 2, 3, 4: 可累加，返回给上一层 ---
        // root.val 是情况 4 (只有根)
        // root.val + left 是情况 2 (根+左)
        // root.val + right 是情况 3 (根+右)
        return Math.max(root.val, Math.max(root.val + left, root.val + right));
    }

}
