package LeetCode_Hot100;

/*
617. 合并二叉树

给你两棵二叉树：`root1` 和 `root2`。

想象一下，当你将其中一棵覆盖到另一棵之上时，两棵树上的某些节点将会重叠（而另一些不会）。
你需要将这两棵树合并成一棵新二叉树。
合并的规则是：如果两个节点重叠，那么将这两个节点的值相加作为合并后节点的新值；
            否则，不为 null 的节点将直接作为新二叉树的节点。

返回合并后的二叉树。

**注意**：合并过程必须从两个树的根节点开始。

## 提示：
    -- 两棵树中的节点数目在范围 [0, 2000] 内
    -- -10^4 ≤ Node.val ≤ 10^4
*/
public class question0079 {
    
    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(1, 
                                    new TreeNode(3, 
                                        new TreeNode(5),
                                        null),
                                    new TreeNode(2));
        TreeNode root2 = new TreeNode(2, 
                                    new TreeNode(1, 
                                        null, 
                                        new TreeNode(4)),
                                    new TreeNode(3,
                                        null,
                                        new TreeNode(7)));

        question0079 sl79 = new question0079();
        TreeNode resNode = sl79.mergeTrees(root1, root2);
        sl79.preOrder(resNode);
    }

    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }

        // 节点重叠，值相加
        root1.val += root2.val;

        // 递归合并左右子树
        root1.left = mergeTrees(root1.left, root2.left);
        root1.right = mergeTrees(root1.right, root2.right);

        return root1;
    }

    // 测试函数: 输出一棵树
    public void preOrder(TreeNode node) {
        if (node == null) {
            return;
        }

        System.out.print(node.val + " ");
        preOrder(node.left);
        preOrder(node.right);

    }
}
