package DailyQuestion.Eleventh;

import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-20-20:13
 **/
public class Question002 {
    public static void main(String[] args) {
        List<Integer> nums = List.of(2, 3, 5, 7);
        int[] res = f3314(nums);
        for (int i : res) {
            System.out.print(i + " ");
        }

    }

    public static int[] f3314(List<Integer> nums) {
        int size = nums.size();
        int[] res = new int[size];
        for (int i = 0; i < size; i++) {
            int num = nums.get(i);
            boolean found = false;
            for (int j = 0; j < num; j++) {
                if (( j | (j + 1)) == num) {
                    res[i] = j;
                    found = true;
                    break;
                }
            }
            if (!found) {
                res[i] = -1;
            }
        }
        return res;

    }
}
