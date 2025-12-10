package ThirdWeek;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-19-09:42
 **/
/*
2154. 将找到的值乘以 2
给你一个整数数组 nums ，另给你一个整数 original ，这是需要在 nums 中搜索的第一个数字。
接下来，你需要按下述步骤操作：
    1.如果在 nums 中找到 original ，将 original 乘以 2 ，得到新 original（即，令 original = 2 * original）。
    2.否则，停止这一过程。
    3.只要能在数组中找到新 original ，就对新 original 继续 重复 这一过程。
返回 original 的 最终 值
*/
public class Question0010 {
    public static void main(String[] args) {
        int[] nums = {5,3,6,1,12};
        int[] nums2 = {1,16,13,19,12,10};
        System.out.println(findFinalValue(nums, 3));
        System.out.println(findFinalValue(nums2, 2));
    }
    public static int findFinalValue(int[] nums, int original) {
        int n = nums.length;
        int res = original;
        Arrays.sort(nums);
        for (int i = 0; i < n; i++) {
            if (nums[i] == original) {
                original *= 2;
                res = original;
            }
            if (original < nums[i]) {
                break;
            }
        }
        return res;
    }

    public static int findFinalValue2(int[] nums, int original) {
        Arrays.sort(nums);
        for (int num : nums) {
            if (original == num) {
                original *= 2;
            }
        }
        return original;

    }

    public static int findFinalValue3(int[] nums, int original) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        while (set.contains(original)){
            original *= 2;
        }
        return original;
    }

}
