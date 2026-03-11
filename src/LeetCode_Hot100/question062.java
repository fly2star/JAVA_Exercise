package LeetCode_Hot100;

/*
538. 把二叉搜索树转换为累加树

给出二叉搜索树的根节点，该树的节点值各不相同，请你将其转换为累加树（Greater Sum Tree），
使每个节点的值的新值等于原树中大于或等于该节点值的所有节点值之和。

二叉搜索树满足下列约束条件：
- 节点的左子树仅包含键 **小于** 节点键的节点
- 节点的右子树仅包含键 **大于** 节点键的节点
- 左右子树也必须是二叉搜索树

## 提示：
    -- 树的节点数介于 0 和 10^4 之间
    -- 每个节点的值介于 -10^4 和 10^4 之间
    -- 树中的所有值互不相同
    -- 给定的树为二叉搜索树
*/
public class question062 {

    private int total = 0;

    public static void main(String[] args) {
        TreeNode root = new TreeNode(4);
        TreeNode lNode = new TreeNode(1);
        TreeNode rNode = new TreeNode(6);
        root.left = lNode;
        root.right = rNode;
        TreeNode llNode = new TreeNode(0);
        TreeNode lrNode = new TreeNode(2);
        lNode.left = llNode;
        lNode.right = lrNode;
        TreeNode rlNode = new TreeNode(5);
        TreeNode rrNode = new TreeNode(7);
        rNode.left = rlNode;
        rNode.right = rrNode;
        TreeNode lrrNode = new TreeNode(3);
        lrNode.right = lrrNode;
        TreeNode rrrNode = new TreeNode(8);
        rrNode.right = rrrNode;

        question062 sl = new question062();
        sl.convertBST(root);
        sl.printTree(root, 0);
        
        
    }

    public TreeNode convertBST(TreeNode root) {
        dfs(root);
        return root;
    }

    public void dfs(TreeNode node) {
        if (node == null) {
            return;
        }

        // 先遍历右子树
        dfs(node.right);

        // 处理当前节点
        total += node.val;
        node.val = total;

        // 再遍历左子树
        dfs(node.left);

    }

    public void printTree(TreeNode root, int level) {
        if (root == null) return;

        // 1. 先处理右子树（在上方显示）
        printTree(root.right, level + 1);

        // 2. 打印当前节点，根据层级进行缩进
        if (level != 0) {
            for (int i = 0; i < level - 1; i++) System.out.print("|\t");
            System.out.println("|-------" + root.val);
        } else {
            System.out.println(root.val);
        }

        // 3. 处理左子树（在下方显示）
        printTree(root.left, level + 1);
    }
}
