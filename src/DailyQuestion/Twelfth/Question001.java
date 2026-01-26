package DailyQuestion.Twelfth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-26-10:09
 **/
public class Question001 {
    public static void main(String[] args) {

    }

    public static List<List<Integer>> f1200(int[] arr) {
        Arrays.sort(arr);
        int minDiff = Integer.MAX_VALUE;

        // 找最小差值
        for (int i = 0; i < arr.length - 1; i++) {
            int diff = arr[i+1] - arr[i];
            minDiff = Math.min(minDiff, diff);
        }

        // 收集结果
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < arr.length - 1; i++) {
            if (arr[i+1] - arr[i] == minDiff) {
                List<Integer> pair = Arrays.asList(arr[i], arr[i+1]);
                result.add(pair);
            }
        }

        return result;
    }
}
