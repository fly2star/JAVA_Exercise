package LeetCode_Hot100;

/*
98. 验证二叉搜索树

给你一个二叉树的根节点 `root`，判断其是否是一个有效的二叉搜索树。

## 有效二叉搜索树定义如下：
    -- 节点的左子树只包含 **严格小于** 当前节点的数
    -- 节点的右子树只包含 **严格大于** 当前节点的数
    -- 所有左子树和右子树自身必须也是二叉搜索树

## 提示：
    -- 树中节点数目在范围 [1, 10^4] 内
    -- -2^31 ≤ Node.val ≤ 2^31 - 1
*/
public class question084 {
    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(0,
                                    new TreeNode(1),
                                    new TreeNode(4,
                                        new TreeNode(3),
                                        new TreeNode(6)));
                                        
        question084 sl84 = new question084();
        System.err.println(sl84.isValidBST(root1));
    }

    // 方法1: 递归边界
    public boolean isValidBST(TreeNode root) {
        return validate(root, Long.MIN_VALUE, Long.MAX_VALUE);    
    }

    private boolean validate(TreeNode node, long lower, long upper) {
        if (node == null) {
            return true;
        }

        // 检查当前节点是否在范围内
        if (node.val <= lower || node.val >= upper) {
            return false;
        }

        // 递归检查左右子树
        return validate(node.left, lower, node.val) &&
                validate(node.right, node.val, upper);
    }

    // 方法2: 中序遍历
    /**
     * 1. 对二叉树进行中序遍历
     * 2. 在遍历过程中，记录前一个节点的值
     * 3. 如果当前节点的值小于等于前一个节点的值，说明不是二叉搜索树
     * 4. 遍历结束后返回 true
    */
    private long prev = Long.MIN_VALUE;

    public boolean isValidBST2(TreeNode root) {
        return inorder(root);
    }

    private boolean inorder(TreeNode node) {
        if (node == null) {
            return true;
        }

        // 检查左子树
        if (!inorder(node.left)) {
            return false;
        }

        // 检查当前节点
        if (node.val <= prev) {
            return false;
        }
        prev = node.val;

        // 检查右子树
        return inorder(node.right);

    }
}
