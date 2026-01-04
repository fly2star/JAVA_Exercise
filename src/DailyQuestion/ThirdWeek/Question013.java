package DailyQuestion.ThirdWeek;

import java.util.*;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-23-19:51
 **/
public class Question013 {
    public static void main(String[] args) {
        int[] nums = {3,6,5,1,8};
        System.out.println(maxSumDivThree(nums));
        System.out.println(maxSumDivThree2(nums));
        System.out.println(maxSumDivThree3(nums));
    }

    public static int maxSumDivThree(int[] nums) {
        List<Integer>[] v = new List[3];
        for (int i = 0; i < 3; i++) {
            v[i] = new ArrayList<>();
        }
        for (int num : nums) {
            v[num % 3].add(num);
        }
        Collections.sort(v[1], new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });
        Collections.sort(v[2], (a, b) -> b - a);

        int ans = 0;
        int lb = v[1].size(), lc = v[2].size();
        for (int cntb = lb -2; cntb <= lb; cntb++) {
            if (cntb >= 0) {
                for (int cntc = lc - 2; cntc <= lc; cntc++) {
                    if (cntc >= 0 && (cntb - cntc) % 3 == 0) {
                        ans = Math.max(ans, getSum(v[1], 0, cntb) + getSum(v[2], 0, cntc));
                    }
                }
            }
        }
        return ans + getSum(v[0], 0, v[0].size());

    }

    public static int getSum(List<Integer> v, int start, int end) {
        int sum = 0;
        for (int i = start; i < end; i++) {
            sum += v.get(i);
        }
        return sum;
    }

    // 贪心 + 逆向思维
    public static int maxSumDivThree2(int[] nums) {
        // 使用 v[0], v[1], v[2] 分别表示 a, b, c
        List<Integer>[] v = new List[3];
        for (int i = 0; i < 3; ++i) {
            v[i] = new ArrayList<Integer>();
        }
        for (int num : nums) {
            v[num % 3].add(num);
        }
        Collections.sort(v[1], (a, b) -> b - a);
        Collections.sort(v[2], (a, b) -> b - a);

        int tot = Arrays.stream(nums).sum();
        int remove = Integer.MAX_VALUE;

        if (tot % 3 == 0) {
            remove = 0;
        } else if (tot % 3 == 1) {
            if (v[1].size() >= 1) {
                remove = Math.min(remove, v[1].get(v[1].size() - 1));
            }
            if (v[2].size() >= 2) {
                remove = Math.min(remove, v[2].get(v[2].size() - 2) + v[2].get(v[2].size() - 1));
            }
        } else {
            if (v[1].size() >= 2) {
                remove = Math.min(remove, v[1].get(v[1].size() - 2) + v[1].get(v[1].size() - 1));
            }
            if (v[2].size() >= 1) {
                remove = Math.min(remove, v[2].get(v[2].size() - 1));
            }
        }

        return tot - remove;

    }

    // 动态规划
    public static int maxSumDivThree3(int[] nums) {
        int[] f = {0, Integer.MIN_VALUE, Integer.MIN_VALUE};
        for (int num : nums) {
            int[] g = new int[3];
            System.arraycopy(f, 0, g, 0, 3);
            for (int i = 0; i < 3; ++i) {
                g[(i + num % 3) % 3] = Math.max(g[(i + num % 3) % 3], f[i] + num);
            }
            f = g;
        }
        return f[0];
    }

}
