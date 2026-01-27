package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-26-22:01
 **/
public class question031 {
    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(10);
        root1.left = new TreeNode(5);
        root1.right = new TreeNode(-3);
        root1.left.left = new TreeNode(3);
        root1.left.right = new TreeNode(2);
        root1.right.right = new TreeNode(11);
        root1.left.left.left = new TreeNode(3);
        root1.left.left.right = new TreeNode(-2);
        root1.left.right.right = new TreeNode(1);

        int targetSum1 = 8;
        System.out.println(pathSum(root1, targetSum1));

        // 第二个测试案例
        // [1000000000,1000000000,null,294967296,null,1000000000,null,1000000000,null,1000000000]
        TreeNode root2 = new TreeNode(1000000000);
        root2.left = new TreeNode(1000000000);
        root2.left.left = new TreeNode(294967296);
        root2.left.left.left = new TreeNode(1000000000);
        root2.left.left.left.left = new TreeNode(1000000000);
        root2.left.left.left.left.left = new TreeNode(1000000000);

        int targetSum2 = 0;
        System.out.println(pathSum2(root2, targetSum2));
    }

    // 方法 1: 暴力解法, 两层DFS 
    public static int pathSum(TreeNode root, int targetSum) {
        return traverse(root, targetSum);
    }

    private static int traverse(TreeNode node, int targetSum) {
        if(node==null) return 0;
        
        // 以当前节点为起点的路径数
        int count = dfs(node, 0L, targetSum);
        count+=traverse(node.left, targetSum);
        count+=traverse(node.right, targetSum);
        return count;
    }

    private static int dfs(TreeNode node, long currSum, int targetSum) {
        if(node==null) return 0;

        currSum+=node.val;
        int count = currSum==targetSum ? 1 : 0;
        count+=dfs(node.left, currSum, targetSum);
        count+=dfs(node.right, currSum, targetSum);
        return count;
    }

    // 方法 2: 前缀和 + 哈希表
    public static int pathSum2(TreeNode root, int targetSum) {
        // key: 前缀和, value: 该前缀和出现的次数
        Map<Long, Integer> prefixSumMap = new HashMap<>();
        // 初始化: 前缀和为 0 的路劲默认有 1 次 (用于处理从根节点开始就满足条件的情况)
        prefixSumMap.put(0L, 1);

        return dfs2(root, 0L, targetSum, prefixSumMap);

    }

    private static int dfs2(TreeNode node, long currSum, int targetSum, Map<Long, Integer> map) {
        if (node == null) {
            return 0;
        }
        
        // 更新当前的前缀和
        currSum += node.val;

        // 查找满足条件的路劲数
        // 如果 currSum - targetSum = 某个历史前缀和，说明找到了一段满足条件的路径
        int count = map.getOrDefault(currSum - targetSum, 0);

        // 将当前前缀和存入 Map. 供子节点使用
        map.put(currSum, map.getOrDefault(currSum, 1) + 1);

        // 递归左右子树
        count += dfs2(node.left, currSum, targetSum, map);
        count += dfs2(node.right, currSum, targetSum, map);

        // 回溯: 移除当前节点的前缀和，避免影响其他路径的计算
        map.put(currSum, map.get(currSum) - 1);

        return count;
    }
}
