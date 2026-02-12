package LeetCode_Hot100;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class question44 {
    public static void main(String[] args) {
        String str = "1,2";
        Codec cc = new Codec();
        TreeNode root = cc.deserializePreoder(str);
        String str_re = cc.serialize(root);
        System.out.println(str_re);
    }
}

// 层次遍历
class Codec {
    // ===== 方法1:层次遍历 =====
    // Encodes a tree to a single string.
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }

        // 移除末尾多余的 "null,"
        String result = sb.toString();
        while (result.endsWith("null,")) {
            result = result.substring(0, result.length() - 5);
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }

        return result;
    }

    // Decodes your encoded data to tree.
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }

        String[] values = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();

            // 左子节点
            if (i < values.length && !values[i].equals("null")) {
                TreeNode leftNode = new TreeNode(Integer.parseInt(values[i]));
                node.left = leftNode;
                queue.offer(leftNode);
                
            }
            i++;

            // 右子节点
            if (i < values.length && !values[i].equals("null")) {
                TreeNode rightNode = new TreeNode(Integer.parseInt(values[i]));
                node.right = rightNode;
                queue.offer(rightNode);
            }
            i ++;
        }

        return root;
    }

    // ===== 方法2:前序遍历 =====
    public String serializePerorder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }

    public TreeNode deserializePreoder(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String[] nodes = data.split(",");
        Queue<String> queue = new LinkedList<>(Arrays.asList(nodes));
        return deserializeHelper(queue);
    }

    private TreeNode deserializeHelper(Queue<String> queue) {
        String val = queue.poll();
        if (val == null || val.equals("null")) {
            return null;
        }

        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserializeHelper(queue);
        node.right = deserializeHelper(queue);
        return node;
    }
}
