package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.List;

/*
114. 二叉树展开为链表

给你二叉树的根结点 `root`，请你将它展开为一个单链表：

- 展开后的单链表应该同样使用 `TreeNode`，其中 `right` 子指针指向链表中下一个结点，而左子指针始终为 `null`。
- 展开后的单链表应该与二叉树 **先序遍历** 顺序相同。


## 提示：
    -- 树中结点数在范围 [0, 2000] 内
    -- -100 ≤ Node.val ≤ 100
*/
public class question077 {
    public static void main(String[] args) {
        
    }

    // 方法1: 递归 + 列表存储
    /*
    1. 对二叉树进行前序遍历，将节点按顺序存入列表
    2. 遍历列表，将每个节点的左子节点设为 null，右子节点设为列表中的下一个节点
    */
    public void flatten(TreeNode root) {
        if (root == null) {
            return;
        }

        List<TreeNode> nodes = new ArrayList<>();
        preorder(root, nodes);

        for (int i = 0; i < nodes.size() - 1; i++) {
            nodes.get(i).left = null;
            nodes.get(i).right = nodes.get(i + 1);
        }

    }

    private void preorder(TreeNode node, List<TreeNode> nodes) {
        if (node == null) {
            return;
        }
        nodes.add(node);
        preorder(node.left, nodes);
        preorder(node.right, nodes);
    }

    // 方法2: 原地算法 (O(1) 空间)
    /*
    1. 从根节点开始遍历：
        - 如果当前节点有左子树：
            - 找到左子树的最右节点（即左子树中先序遍历的最后一个节点）
            - 将当前节点的右子树接到这个最右节点的右边
            - 将当前节点的左子树移到右边，左子节点设为 null
        - 移动到当前节点的右子节点继续处理
    2. 重复直到所有节点处理完毕
    */
    public void flatten2(TreeNode root) {
        TreeNode curr = root;

        while (curr != null) {
            if (curr.left != null) {
                // 找到左子树的最右节点
                TreeNode rightmost = curr.left;
                while (rightmost.right != null) {
                    rightmost = rightmost.right;
                }

                // 将原右子树接到最右节点右边
                rightmost.right = curr.right;

                // 将左子树移到右边, 左子节点设为空
                curr.right = curr.left;
                curr.left = null;
            }

            // 继续处理下一个节点
            curr = curr.right;
        }

    }
}
