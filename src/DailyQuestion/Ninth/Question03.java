package DailyQuestion.Ninth;
import DailyQuestion.TreeNode;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-06-15:06
 **/
public class Question03 {
    public static void main(String[] args) {

    }

    public static int f1161(TreeNode root) {
        if (root == null) return 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int maxLevel = 1;
        long maxSum = Long.MIN_VALUE; // 使用 Long 防止溢出
        int currentLevel = 1;

        while (!queue.isEmpty()) {
            int size = queue.size();
            long currentLevelSum = 0;

            // 处理当前层的所有节点
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                currentLevelSum += node.val;

                // 将下一层节点入队
                if (node.left != null) queue.offer(node.left);
                if (node.right != null) queue.offer(node.right);
            }

            // 更新最大值和层号
            if (currentLevelSum > maxSum) {
                maxSum = currentLevelSum;
                maxLevel = currentLevel;
            }

            currentLevel++;
        }

        return maxLevel;
    }
}
