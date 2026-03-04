package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.List;

/*
22. 括号生成

数字 `n` 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 **有效的** 括号组合。

*/
public class question051 {
    public static void main(String[] args) {
        System.out.println(generateParenthesis(3));
    }

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0 , 0, n);
        return result;
    }

    private static void backtrack(List<String> result, String current, int left, int right, int n) {
        // 如果当前字符串长度达到 2n, 说明找到一个有效组合
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }

        // 可以添加左括号的条件: 左括号数量小于 n
        if (left < n) {
            backtrack(result, current + "(", left + 1, right, n);
        }

        // 可以添加右括号的条件: 右括号数量小于左括号数量
        if (right < left) {
            backtrack(result, current + ")", left, right + 1, n);
        }
    }
    
}
