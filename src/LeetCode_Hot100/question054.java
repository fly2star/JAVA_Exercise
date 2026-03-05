package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.List;

/*
46. 全排列

给定一个不含重复数字的数组 `nums`，返回其 **所有可能的全排列**。你可以按任意顺序返回答案。
*/
public class question054 {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3};
        List<List<Integer>> res = permute(nums);
        System.out.println(res);
    }

    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        boolean[] used = new boolean[nums.length];

        backtrack(nums, used, path, result);
        return result;
    }

    private static void backtrack(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
        // 如果当前路径长度等于数组长度，说明找到了一个完整排列
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }
        
        // 遍历所有数字
        for (int i = 0; i < nums.length; i++) {
            // 如果当前数字未被使用
            if (!used[i]) {
                // 选择
                used[i] = true;
                path.add(nums[i]);
                // 递归
                backtrack(nums, used, path, result);
                // 撤销选择
                path.remove(path.size() - 1);
                used[i] = false;
            }
        }
    }
}
