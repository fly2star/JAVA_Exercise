# 32. 最长有效括号

**难度: 困难**

## 题目描述
给你一个只包含 `'('` 和 `')'` 的字符串，找出最长有效（格式正确且连续）括号子串的长度。

左右括号匹配，即每个左括号都有对应的右括号将其闭合的字符串是格式正确的，比如 `"((()))"`。

---

## 示例说明
### 示例 1：
输入：s = "(()"  
输出：2  
解释：最长有效括号子串是 "()"

### 示例 2：
输入：s = ")()())"  
输出：4  
解释：最长有效括号子串是 "()()"

### 示例 3：
输入：s = ""  
输出：0

---

## 提示：
- 0 ≤ s.length ≤ 3 * 10^4
- s[i] 为 '(' 或 ')'

---

## 解题思路

### 核心思想
求最长有效括号子串的长度，可以使用**栈**、**动态规划**或**双指针计数法**。栈的方法直观易懂，动态规划需要仔细推导状态转移，双指针计数法空间复杂度最优。

### 关键观察
- 有效括号子串的特点是：在遍历过程中，右括号数量不会超过左括号数量，且最终左右括号数量相等
- 栈可以记录每个无法匹配的括号位置，从而计算有效长度
- 动态规划中，dp[i] 表示以第 i 个字符结尾的最长有效括号子串长度

### 算法步骤

#### 方法一：栈
1. 初始化一个栈，压入 -1 作为起始标记
2. 遍历每个字符：
   - 如果当前字符是 '('，将其下标压入栈
   - 如果是 ')'：
     - 弹出栈顶元素（表示匹配一个左括号）
     - 如果栈为空，说明当前右括号无法匹配，将当前下标压入栈作为新的起始标记
     - 如果栈不为空，计算当前有效长度 = 当前下标 - 栈顶元素，更新最大值
3. 返回最大长度

#### 方法二：动态规划
1. 创建 dp 数组，dp[i] 表示以 s[i] 结尾的最长有效括号长度
2. 遍历字符串：
   - 如果 s[i] == ')' 且 s[i-1] == '('，则 dp[i] = (i >= 2 ? dp[i-2] : 0) + 2
   - 如果 s[i] == ')' 且 s[i-1] == ')' 且 s[i-dp[i-1]-1] == '('，则 dp[i] = dp[i-1] + 2 + (i-dp[i-1]-2 >= 0 ? dp[i-dp[i-1]-2] : 0)
3. 返回 dp 数组中的最大值

#### 方法三：双指针计数法（左右各遍历一次）
1. 从左到右遍历，用 left 和 right 分别计数左括号和右括号
2. 当 left == right 时，更新最大长度为 2 * left
3. 当 right > left 时，重置 left 和 right 为 0
4. 再从右到左遍历一次，处理类似情况（当 left > right 时重置）

---

## 代码参考(python, java, c)

### Python 代码实现
```python
# 方法一：栈
class Solution:
    def longestValidParentheses(self, s: str) -> int:
        stack = [-1]
        max_len = 0
        
        for i, char in enumerate(s):
            if char == '(':
                stack.append(i)
            else:
                stack.pop()
                if not stack:
                    stack.append(i)
                else:
                    max_len = max(max_len, i - stack[-1])
        
        return max_len

# 方法二：动态规划
class Solution:
    def longestValidParentheses(self, s: str) -> int:
        n = len(s)
        if n < 2:
            return 0
        
        dp = [0] * n
        max_len = 0
        
        for i in range(1, n):
            if s[i] == ')':
                if s[i-1] == '(':
                    dp[i] = (dp[i-2] if i >= 2 else 0) + 2
                elif i - dp[i-1] > 0 and s[i - dp[i-1] - 1] == '(':
                    dp[i] = dp[i-1] + 2 + (dp[i - dp[i-1] - 2] if i - dp[i-1] >= 2 else 0)
                max_len = max(max_len, dp[i])
        
        return max_len

# 方法三：双指针计数法
class Solution:
    def longestValidParentheses(self, s: str) -> int:
        left = right = max_len = 0
        
        # 从左到右遍历
        for char in s:
            if char == '(':
                left += 1
            else:
                right += 1
            
            if left == right:
                max_len = max(max_len, 2 * right)
            elif right > left:
                left = right = 0
        
        left = right = 0
        # 从右到左遍历
        for char in reversed(s):
            if char == '(':
                left += 1
            else:
                right += 1
            
            if left == right:
                max_len = max(max_len, 2 * left)
            elif left > right:
                left = right = 0
        
        return max_len
```

### Java 代码实现
```java
// 方法一：栈
class Solution {
    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        stack.push(-1);
        int maxLen = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else {
                stack.pop();
                if (stack.isEmpty()) {
                    stack.push(i);
                } else {
                    maxLen = Math.max(maxLen, i - stack.peek());
                }
            }
        }
        
        return maxLen;
    }
}

// 方法二：动态规划
class Solution {
    public int longestValidParentheses(String s) {
        int n = s.length();
        if (n < 2) return 0;
        
        int[] dp = new int[n];
        int maxLen = 0;
        
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i-1) == '(') {
                    dp[i] = (i >= 2 ? dp[i-2] : 0) + 2;
                } else if (i - dp[i-1] > 0 && s.charAt(i - dp[i-1] - 1) == '(') {
                    dp[i] = dp[i-1] + 2 + (i - dp[i-1] >= 2 ? dp[i - dp[i-1] - 2] : 0);
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        }
        
        return maxLen;
    }
}
```

### C 代码实现
```c
// 方法一：栈
int longestValidParentheses(char* s) {
    int len = strlen(s);
    int* stack = (int*)malloc((len + 1) * sizeof(int));
    int top = 0;
    stack[top++] = -1;
    int maxLen = 0;
    
    for (int i = 0; i < len; i++) {
        if (s[i] == '(') {
            stack[top++] = i;
        } else {
            top--;
            if (top == 0) {
                stack[top++] = i;
            } else {
                int currentLen = i - stack[top - 1];
                if (currentLen > maxLen) {
                    maxLen = currentLen;
                }
            }
        }
    }
    
    free(stack);
    return maxLen;
}

// 方法三：双指针计数法（空间复杂度最优）
int longestValidParentheses(char* s) {
    int left = 0, right = 0, maxLen = 0;
    int len = strlen(s);
    
    // 从左到右遍历
    for (int i = 0; i < len; i++) {
        if (s[i] == '(') {
            left++;
        } else {
            right++;
        }
        
        if (left == right) {
            int currentLen = 2 * right;
            if (currentLen > maxLen) {
                maxLen = currentLen;
            }
        } else if (right > left) {
            left = right = 0;
        }
    }
    
    left = right = 0;
    // 从右到左遍历
    for (int i = len - 1; i >= 0; i--) {
        if (s[i] == '(') {
            left++;
        } else {
            right++;
        }
        
        if (left == right) {
            int currentLen = 2 * left;
            if (currentLen > maxLen) {
                maxLen = currentLen;
            }
        } else if (left > right) {
            left = right = 0;
        }
    }
    
    return maxLen;
}
```

---