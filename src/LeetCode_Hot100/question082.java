package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/*
102. 二叉树的层序遍历

给你二叉树的根节点 `root`，返回其节点值的 **层序遍历**。
（即逐层地，从左到右访问所有节点）。

## 提示：
    -- 树中节点数目在范围 [0, 2000] 内
    -- -1000 ≤ Node.val ≤ 1000
*/
public class question082 {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3,
                                    new TreeNode(9),
                                    new TreeNode(20, 
                                        new TreeNode(15),
                                        new TreeNode(7))
        );

        question082 sl82 = new question082();
        System.out.println(sl82.levelOrder(root));
    }

    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> levelList = new ArrayList<>();

            for (int i = 0; i < levelSize; i++) {
                TreeNode node = queue.poll();
                levelList.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            res.add(levelList);

        }

        return res;

    }
}
