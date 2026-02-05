package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/*
301. 删除无效的括号

给你一个由若干括号和字母组成的字符串 `s`，删除最小数量的无效括号，使得输入的字符串有效。  
返回所有可能的结果。答案可以按任意顺序返回。
*/
public class question42 {
    public static void main(String[] args) {
        
    }
    // 方法 1 : BFS 方法
    public static List<String> removeInvalidParenttheses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null) return result;

        // BFS 队列
        Queue<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        queue.offer(s);
        visited.add(s);

        boolean found = false;

        while (!queue.isEmpty()) {
            String current = queue.poll();

            // 检查是否有效
            if (isValid(current)) {
                result.add(current);
                found = true;
            }

            // 如果已找到有效字符串，不再继续删除字符
            if (found) {
                continue;
            }

            // 尝试删除每个字符
            for (int i = 0; i < current.length(); i++) {
                char ch = current.charAt(i);
                // 只删除括号
                if (ch != '(' && ch != ')') {
                    continue;
                }

                // 生成新字符串
                String newStr = current.substring(0, i) + current.substring(i + 1);
                
                // 如果没访问过，加入队列
                if (!visited.contains(newStr)) {
                    visited.add(newStr);
                    queue.offer(newStr);
                }
            }

        }

        return result;
    }

    // 方法 1 辅助函数, 检查字符串是否有效
    private static boolean isValid(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                count++;
            } else if (ch == ')') {
                count--;
                if (count < 0) {
                    return false;
                }
            }
        }
        return count == 0;
    }

    // 方法 2 : DFS 回溯方法
    public static List<String> removeInvalidParenttheses2(String s) {
        // 计算需要删除的最少左右括号数量
        int leftRem = 0, rightRem = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                leftRem++;
            } else if (ch == ')') {
                if (leftRem > 0) {
                    leftRem--;
                } else {
                    rightRem++;
                }
            }
        }

        Set<String> result = new HashSet<>();
        dfs(s, 0, 0, 0, leftRem, rightRem, new StringBuilder(), result);
        return new ArrayList<>(result);
    }

    // 方法 2 辅助函数
    private static void dfs(String s, int index, int leftCount, int rightCount, int leftRem, int rightRem, StringBuilder expr, Set<String> result) {
        if (index == s.length()) {
            // 到达字符串末尾
            if (leftRem == 0 && rightRem == 0) {
                result.add(expr.toString());
            }
            return;
        }

        char ch = s.charAt(index);

        // 情况1：删除当前字符（如果是括号）
        if ((ch == '(' && leftRem > 0) || (ch == ')' && rightRem > 0)) {
            dfs(s, index + 1, leftCount, rightCount, 
                leftRem - (ch == '(' ? 1: 0), 
                rightRem - (ch == ')' ? 1: 0), 
                expr, result);
        }

        // 情况2：保留当前字符
        expr.append(ch);

        if (ch == '(') {
            dfs(s, index + 1, leftCount + 1, rightCount, 
                leftRem, rightRem, expr, result);
        } else if (ch == ')') {
            // 只有在左括号多于右括号时才有效
            if (leftCount > rightCount) {
                dfs(s, index + 1, leftCount, rightCount + 1, 
                    leftRem, rightRem, expr, result);
            }
        } else {
            // 普通字符
            dfs(s, index + 1, leftCount, rightCount, 
                leftRem, rightRem, expr, result);
        }

        // 回溯
        expr.deleteCharAt(expr.length() - 1);

    }

}
