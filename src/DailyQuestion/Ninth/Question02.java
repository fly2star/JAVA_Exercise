package DailyQuestion.Ninth;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-06-15:06
 **/
public class Question02 {
    public static void main(String[] args) {

    }

    public static long f1975(int[][] matrix) {
        long totalAbsSum = 0;
        int minAbsVal = Integer.MAX_VALUE;
        int negativeCount = 0;

        for (int[] row : matrix) {
            for (int val : row) {
                // 1. 累加绝对值
                totalAbsSum += Math.abs(val);

                // 2. 统计负数个数
                if (val < 0) {
                    negativeCount++;
                }

                // 3. 寻找绝对值最小的数
                minAbsVal = Math.min(minAbsVal, Math.abs(val));
            }
        }

        // 如果负数是奇数个，减去两倍的最小绝对值
        if (negativeCount % 2 != 0) {
            return totalAbsSum - 2L * minAbsVal;
        }

        return totalAbsSum;
    }
}
