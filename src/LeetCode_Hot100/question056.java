package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
39. 组合总和

给你一个 **无重复元素的整数数组 candidates** 和一个目标整数 target，找出 candidates 中可以使数字和为目标数 target 的所有不同组合，并以列表形式返回。
你可以按任意顺序返回这些组合。

candidates 中的 **同一个数字可以无限重复被选取**。如果至少一个数字的被选数量不同，则两种组合是不同的。

对于给定的输入，保证和为 target 的不同组合数少于 150 个。
*/
public class question056 {
    public static void main(String[] args) {
        int[] candidates = {2, 3, 6, 7};
        int target = 7;

        List<List<Integer>> result = combinationSum(candidates, target);
        System.out.println(result);
    }

    public static List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        // 排序可以帮助剪枝
        Arrays.sort(candidates);
        
        backtrack(candidates, target, 0, path, res);
        return res;

    }

    private static void backtrack(int[] candidates, int target, int start, List<Integer> path, List<List<Integer>> res) {
        // 终止条件
        if (target == 0) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = start; i < candidates.length; i++) {
            // 剪枝优化: 如果当前数字已经大于剩余的目标值, 后面的数字不用看了
            if (target - candidates[i] < 0) {
                break;
            }

            path.add(candidates[i]);    // 做出选择

            // 进入下一层递归
            // 传递的依然是 i, 而不是 i+1, 因为元素可以重复使用
            backtrack(candidates, target - candidates[i], i, path, res);
            
            path.remove(path.size() - 1);
        }
    }
}
