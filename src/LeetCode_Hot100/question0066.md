# 20. 有效的括号

**难度: 简单**

## 题目描述
给定一个只包括 `'('`，`')'`，`'{'`，`'}'`，`'['`，`']'` 的字符串 s，判断字符串是否有效。

有效字符串需满足：
1. 左括号必须用相同类型的右括号闭合。
2. 左括号必须以正确的顺序闭合。
3. 每个右括号都有一个对应的相同类型的左括号。

---

## 示例说明
### 示例 1：
输入：s = "()"  
输出：true

### 示例 2：
输入：s = "()[]{}"  
输出：true

### 示例 3：
输入：s = "(]"  
输出：false

### 示例 4：
输入：s = "([)]"  
输出：false

### 示例 5：
输入：s = "{[]}"  
输出：true

---

## 提示：
- 1 ≤ s.length ≤ 10^4
- s 仅由括号 `'()[]{}'` 组成

---

## 解题思路

### 核心思想
使用**栈**这种数据结构来匹配括号。遍历字符串，遇到左括号就压入栈，遇到右括号就检查栈顶是否是对应的左括号，如果是则弹出，否则说明无效。

### 关键观察
- 括号匹配问题经典解法就是使用栈
- 需要建立括号之间的映射关系，方便快速判断
- 遍历结束后，栈应该为空，否则说明有未匹配的左括号

### 算法步骤
1. 创建一个空栈
2. 创建括号映射：右括号为键，对应的左括号为值（例如 `')'` → `'('`）
3. 遍历字符串中的每个字符：
   - 如果是左括号，将其压入栈
   - 如果是右括号：
     - 如果栈为空，返回 false（没有对应的左括号）
     - 如果栈顶元素与当前右括号不匹配，返回 false
     - 否则，弹出栈顶元素
4. 遍历结束后，返回栈是否为空（空则所有括号匹配成功）

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def isValid(self, s: str) -> bool:
        # 括号映射
        bracket_map = {')': '(', '}': '{', ']': '['}
        stack = []
        
        for char in s:
            # 如果是右括号
            if char in bracket_map:
                # 如果栈为空，用空字符代替栈顶元素，确保不会出错
                top = stack.pop() if stack else '#'
                # 检查是否匹配
                if bracket_map[char] != top:
                    return false
            else:
                # 左括号入栈
                stack.append(char)
        
        # 最后栈应该为空
        return not stack
```

### Java 代码实现
```java
class Solution {
    public boolean isValid(String s) {
        // 使用 Deque 作为栈
        Deque<Character> stack = new ArrayDeque<>();
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            
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
```

### C 代码实现
```c
#include <stdbool.h>
#include <string.h>

bool isValid(char* s) {
    int len = strlen(s);
    // 用数组模拟栈
    char* stack = (char*)malloc(len * sizeof(char));
    int top = -1;
    
    for (int i = 0; i < len; i++) {
        char c = s[i];
        
        // 左括号入栈
        if (c == '(' || c == '[' || c == '{') {
            stack[++top] = c;
        }
        // 右括号检查
        else {
            if (top == -1) {
                free(stack);
                return false;
            }
            
            char topChar = stack[top--];
            // 检查是否匹配
            if ((c == ')' && topChar != '(') ||
                (c == ']' && topChar != '[') ||
                (c == '}' && topChar != '{')) {
                free(stack);
                return false;
            }
        }
    }
    
    free(stack);
    return top == -1;
}
```

---