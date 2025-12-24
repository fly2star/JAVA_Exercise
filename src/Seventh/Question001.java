package Seventh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-24-10:47
 **/
public class Question001 {
    public static void main(String[] args) {
        int[] apple = {1, 3, 2};
        int[] capacity = {4, 3, 1, 5, 2};
        System.out.println(minimumBoxes(apple, capacity));
    }

    // 方法1
    public static int minimumBoxes(int[] apple, int[] capacity) {
        int n = apple.length;
        int m = capacity.length;
        int ans = 0;
        int sumApple = Arrays.stream(apple).sum();
        List<Integer> list = new ArrayList<>();
        for (int cap : capacity) {
            list.add(cap);
        }
        while (sumApple > 0) {
            int max = Collections.max(list);
            ans++;
            sumApple -= max;
            list.remove(Integer.valueOf(max));
        }
        return ans;

    }

    // 方法2：排序贪心
    public static int minimumBoxes2(int[] apple, int[] capacity) {
        int sum = 0;
        for (int x : apple) {
            sum += x;
        }

        Arrays.sort(capacity);

        int m = capacity.length;
        int i = m - 1;      // 先用大箱子，再用小箱子
        while (sum > 0){
            sum -= capacity[i--];
        }
        return m - 1 - i;
        // m-(i+1), i表示当前准备用的箱子序号，实际上上一个使用的箱子的序号是i+1
        // m-未使用的箱子数(0-i总共i+1个)

    }


}
