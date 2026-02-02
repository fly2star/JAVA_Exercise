package LeetCode_Hot100;

import java.util.Stack;


/*
394. 字符串解码

给定一个经过编码的字符串，返回它解码后的字符串。
编码规则为：`k[encoded_string]`，表示其中方括号内部的 `encoded_string` 正好重复 k 次。注意 k 保证为正整数。

你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。

此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k，例如不会出现像 `3a` 或 `2[4]` 的输入。
测试用例保证输出的长度不会超过 10^5。
*/
public class question035 {
    public static void main(String[] args) {
        String s = "3[a]2[bc]";
        System.out.println(decodeString(s));
    }

    public static String decodeString(String s) {
        char[] strArr = s.toCharArray();
        
        Stack<Integer> numStack = new Stack<>();
        Stack<StringBuilder> strStack = new Stack<>();
        StringBuilder currentStr = new StringBuilder();
        int currentNum = 0 ;

        for (char c : strArr) {
            if (Character.isDigit(c)) {
                // 数字可能有多位, 例如 100, 所以需要先将当前的数乘十再加上目前的数
                currentNum = currentNum * 10 + (c - '0');
            } else if (c == '[') {
                // 将当前数字和字符串入栈
                // 把 currentNum 和 currentStr 扔进栈, 然后将它们清空
                numStack.push(currentNum);
                strStack.push(currentStr);
                currentNum = 0;
                currentStr = new StringBuilder();
            } else if (c == ']') {
                // 弹出栈顶元素
                int repeatTimes = numStack.pop();
                StringBuilder temp = currentStr;
                currentStr = strStack.pop();
                // 将字符串重复 repeatTimes 次
                for(int i = 0; i < repeatTimes; i++) {
                    currentStr.append(temp);
                }
            } else {
                // 单个字母直接加上
                currentStr.append(c);
            }
        } 
        return currentStr.toString();
    }
}
