# 394. 字符串解码

**难度: 中等**

## 题目描述
给定一个经过编码的字符串，返回它解码后的字符串。

编码规则为：`k[encoded_string]`，表示其中方括号内部的 `encoded_string` 正好重复 k 次。注意 k 保证为正整数。

你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。

此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k，例如不会出现像 `3a` 或 `2[4]` 的输入。

测试用例保证输出的长度不会超过 10^5。

---

## 示例说明
### 示例 1：
**输入：** s = "3[a]2[bc]"  
**输出：** "aaabcc"  
**解释：** 
- `3[a]` 解码为 `aaa`
- `2[bc]` 解码为 `bcbc`
- 合并得到 `aaabcbc`

---

### 示例 2：
**输入：** s = "3[a2[c]]"  
**输出：** "accaccacc"  
**解释：**
- `a2[c]` 解码为 `acc`
- `3[acc]` 解码为 `accaccacc`

---

### 示例 3：
**输入：** s = "2[abc]3[cd]ef"  
**输出：** "abcabc cdcdcd ef"  
**解释：**
- `2[abc]` 解码为 `abcabc`
- `3[cd]` 解码为 `cdcdcd`
- `ef` 保持不变

---

### 示例 4：
**输入：** s = "abc3[cd]xyz"  
**输出：** "abccdcdcdxyz"  
**解释：**
- `abc` 保持不变
- `3[cd]` 解码为 `cdcdcd`
- `xyz` 保持不变

---

## 提示：
- 1 ≤ s.length ≤ 30
- s 由小写英文字母、数字和方括号 `[]` 组成
- s 保证是一个 **有效的** 输入
- s 中所有整数的取值范围为 [1, 300]
- 输出字符串长度不会超过 10^5

---

## 解题思路

### 核心思想
使用栈来处理嵌套的解码操作。遇到数字、字母和括号时分别处理，遇到右括号时弹出栈中的元素进行解码。

### 关键观察
1. 字符串可能包含多层嵌套，如 `3[a2[c]]`
2. 数字可能有多位，需要完整解析
3. 解码时需要从内向外处理

### 算法步骤
1. 使用两个栈：一个存储数字（重复次数），一个存储字符串
2. 遍历输入字符串：
   - 如果是数字：解析完整的数字（可能有多位）
   - 如果是字母：追加到当前字符串
   - 如果是 `[`：将当前数字和字符串分别入栈，并重置
   - 如果是 `]`：弹出数字和字符串，将当前字符串重复指定次数后与弹出的字符串拼接
3. 返回最终解码的字符串

---

## 代码参考(python, java, c)

### Python 代码实现

```python
class Solution:
    def decodeString(self, s: str) -> str:
        stack = []
        current_str = ""
        current_num = 0
        
        for char in s:
            if char.isdigit():
                current_num = current_num * 10 + int(char)
            elif char == '[':
                # 将当前数字和字符串入栈
                stack.append((current_num, current_str))
                current_num = 0
                current_str = ""
            elif char == ']':
                # 弹出栈顶元素
                num, prev_str = stack.pop()
                # 将当前字符串重复num次
                current_str = prev_str + current_str * num
            else:
                # 字母直接追加
                current_str += char
        
        return current_str
```

---

### Java 代码实现

```java
import java.util.Stack;

class Solution {
    public String decodeString(String s) {
        Stack<Integer> numStack = new Stack<>();
        Stack<StringBuilder> strStack = new Stack<>();
        StringBuilder currentStr = new StringBuilder();
        int currentNum = 0;
        
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                currentNum = currentNum * 10 + (c - '0');
            } else if (c == '[') {
                // 将当前数字和字符串入栈
                numStack.push(currentNum);
                strStack.push(currentStr);
                currentNum = 0;
                currentStr = new StringBuilder();
            } else if (c == ']') {
                // 弹出栈顶元素
                int repeatTimes = numStack.pop();
                StringBuilder temp = currentStr;
                currentStr = strStack.pop();
                // 将字符串重复repeatTimes次
                for (int i = 0; i < repeatTimes; i++) {
                    currentStr.append(temp);
                }
            } else {
                // 字母直接追加
                currentStr.append(c);
            }
        }
        
        return currentStr.toString();
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

char* decodeString(char* s) {
    // 分配足够大的空间存储结果
    char* result = (char*)malloc(100000 * sizeof(char));
    result[0] = '\0';
    
    // 使用数组模拟栈
    int numStack[100];
    char* strStack[100];
    int top = -1;
    
    int currentNum = 0;
    char* currentStr = (char*)malloc(100000 * sizeof(char));
    currentStr[0] = '\0';
    
    for (int i = 0; s[i] != '\0'; i++) {
        if (isdigit(s[i])) {
            currentNum = currentNum * 10 + (s[i] - '0');
        } else if (s[i] == '[') {
            // 将当前数字和字符串入栈
            numStack[++top] = currentNum;
            strStack[top] = strdup(currentStr);
            currentNum = 0;
            currentStr[0] = '\0';
        } else if (s[i] == ']') {
            // 弹出栈顶元素
            int repeatTimes = numStack[top];
            char* prevStr = strStack[top--];
            
            // 将当前字符串重复repeatTimes次
            char* temp = strdup(currentStr);
            strcpy(currentStr, prevStr);
            for (int j = 0; j < repeatTimes; j++) {
                strcat(currentStr, temp);
            }
            
            free(prevStr);
            free(temp);
        } else {
            // 字母直接追加
            int len = strlen(currentStr);
            currentStr[len] = s[i];
            currentStr[len + 1] = '\0';
        }
    }
    
    strcpy(result, currentStr);
    free(currentStr);
    return result;
}

// 测试代码
int main() {
    char s1[] = "3[a]2[bc]";
    char* result1 = decodeString(s1);
    printf("Input: %s\nOutput: %s\n\n", s1, result1);
    free(result1);
    
    char s2[] = "3[a2[c]]";
    char* result2 = decodeString(s2);
    printf("Input: %s\nOutput: %s\n", s2, result2);
    free(result2);
    
    return 0;
}
```

---