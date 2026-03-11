package LeetCode_Hot100;

import java.util.ArrayDeque;
import java.util.Deque;

/*
20. 有效的括号

给定一个只包括 `'('`，`')'`，`'{'`，`'}'`，`'['`，`']'` 的字符串 s，判断字符串是否有效。

有效字符串需满足：
1. 左括号必须用相同类型的右括号闭合。
2. 左括号必须以正确的顺序闭合。
3. 每个右括号都有一个对应的相同类型的左括号。

## 提示：
    -- 1 ≤ s.length ≤ 10^4
    -- s 仅由括号 `'()[]{}'` 组成
*/
public class question0066 {
    public static void main(String[] args) {
        String s = "()[]{}";

        question0066 sl66 = new question0066();
        System.out.println(sl66.isValid(s));
    }

    public boolean isValid(String str) {
        int n = str.length();
        Deque<Character> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {
            char c = str.charAt(i);

            // 左括号入栈
            if (c == '(' || c == '[' || c == '{') {
                stack.push(c);
            }
            // 右括号检查
            else {
                if (stack.isEmpty()) {
                    return false;
                }

                char top = stack.pop();
                // 检查是否匹配
                if (c == ')' && top != '(') {
                    return false;
                }
                if (c == ']' && top != '[') {
                    return false;
                }
                if (c == '}' && top != '{') {
                    return false;
                }

            }
        }

        return stack.isEmpty();
        
    }


}
