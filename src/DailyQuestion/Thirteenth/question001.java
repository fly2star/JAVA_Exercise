package DailyQuestion.Thirteenth;

import DailyQuestion.TreeNode;

public class question001 {
    public static void main(String[] args) {
        
    }

    public static boolean isBalaced(TreeNode root) {
        // return dfsHeight(root) == -1 ? false : true;
        return dfsHeight(root) != -1;
    }

    private static int dfsHeight(TreeNode node) {
        if (node == null) {
            return 0;
        }

        int leftHeight = dfsHeight(node.left);
        if (leftHeight == -1) {
            return -1;
        }
        
        int rightHeight = dfsHeight(node.right);
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(rightHeight - leftHeight) > 1) {
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }
}
