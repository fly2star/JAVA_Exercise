package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.List;

/*
78. 子集

给你一个整数数组 `nums`，数组中的元素 **互不相同**。
返回该数组所有可能的子集（**幂集**）。

解集 **不能** 包含重复的子集。你可以按任意顺序返回解集。

## 提示：
    -- 1 ≤ nums.length ≤ 10
    -- -10 ≤ nums[i] ≤ 10
    -- nums 中的所有元素 **互不相同**
*/
public class question090 {
    public static void main(String[] args) {
        int[] nums = new int[]{1 ,2, 3};

        question090 sl90 = new question090();
        System.out.println(sl90.subsets(nums));
    }

    // 方法1: 回溯
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        backTrack(nums, 0, path, res);
        return res;
    }

    private void backTrack(int[] nums, int start, List<Integer> path, List<List<Integer>> res){
        // 将当前路劲加入结果集
        res.add(new ArrayList<>(path));

        // 从 start 开始遍历，避免重复
        for (int i = start; i < nums.length; i++) {
            // 选择当前元素
            path.add(nums[i]);
            // 递归
            backTrack(nums, i + 1, path, res);
            // 回溯
            path.remove(path.size() - 1);
        }
    }

    // 方法2: 位运算
    public List<List<Integer>> subsets2(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;

        // 遍历所有掩码
        for (int mask = 0; mask < (1 << n); mask++) {
            List<Integer> subset = new ArrayList<>();
            // 检查每一位
            for (int i = 0; i < n; i++) {
                if ((mask & (1 << i)) != 0) {
                    subset.add(nums[i]);
                }
            }
            res.add(subset);
        }

        return res;
    }
}
