package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-13-20:47
 **/
/*
1422. 分割字符串的最大得分
给你一个由若干 0 和 1 组成的字符串 s ，请你计算并返回将该字符串分割成两个 非空 子字符串（即 左 子字符串和 右 子字符串）所能获得的最大得分。
「分割字符串的得分」为 左 子字符串中 0 的数量加上 右 子字符串中 1 的数量。
*/
public class Question007 {
    public static void main(String[] args) {
        String s = "011101";
        int score = maxScore(s);
        System.out.println( score);
    }

    public static int maxScore(String s) {
        int res = 0;
        int n = s.length();
        for (int i = 1; i < n; i++) {
            int score = 0;
            for (int j = 0; j < i; j++){
                if (s.charAt(j) == '0'){
                    score++;
                }
            }
            for (int j = i; j < n; j++){
                if (s.charAt(j) == '1'){
                    score++;
                }
            }
            res = Math.max(res, score);
        }
        return res;
    }

}
