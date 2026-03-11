package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
17. 电话号码的字母组合

给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按任意顺序返回。

给出数字到字母的映射如下（与电话按键相同）：
- 2: abc
- 3: def
- 4: ghi
- 5: jkl
- 6: mno
- 7: pqrs
- 8: tuv
- 9: wxyz

## 提示：
    -- 0 ≤ digits.length ≤ 4
    -- digits[i] 是范围 ['2', '9'] 的一个数字
*/
public class question068 {
    private List<String> res = new ArrayList<>();
    private Map<Character, String> phoneMap = new HashMap<>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jki");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
        

    }};
    public static void main(String[] args) {
        String digits = "23";
        question068 sl68 = new question068();
        sl68.letterCombinations(digits);
        for (String string : sl68.res) {
            System.out.print(string + " ");
        }
    }

    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return res;
        }
        backtrack(digits, "");
        return res;
    }

    private void backtrack(String nextDigits, String combination) {
        // 终止条件：路径长度等于输入长度
        if (nextDigits.length() == 0) {
            res.add(combination);
            return;
        }

        // 找到当前数字对应的所有字母
        char digit = nextDigits.charAt(0);
        String letters = phoneMap.get(digit);

        // 遍历每个字母, 进行回溯
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            backtrack(nextDigits.substring(1), combination + letter);
        }


    }
}
