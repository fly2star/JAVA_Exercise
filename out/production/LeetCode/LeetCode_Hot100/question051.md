# 22. 括号生成

**难度: 中等**

## 题目描述
数字 `n` 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 **有效的** 括号组合。

---

## 示例说明
### 示例 1：
输入：n = 3  
输出：["((()))", "(()())", "(())()", "()(())", "()()()"]

### 示例 2：
输入：n = 1  
输出：["()"]

---

## 提示：
- 1 <= n <= 8

---

## 解题思路

### 核心思想
使用**回溯法**（深度优先搜索）生成所有可能的括号组合，并在生成过程中通过剪枝确保只生成有效的括号序列。

### 关键观察
- 有效括号序列的特点是：**在任何前缀中，左括号的数量都不少于右括号的数量**
- 当字符串长度达到 2n 时，就生成了一个完整的有效括号组合
- 在递归过程中，需要记录当前已使用的左括号数和右括号数

### 算法步骤
1. 定义回溯函数 `backtrack(current, left, right)`：
   - `current`: 当前已构建的括号字符串
   - `left`: 已使用的左括号数
   - `right`: 已使用的右括号数
2. 终止条件：当 `current` 长度等于 2n 时，将其加入结果集
3. 剪枝条件：
   - 如果 `left < n`，可以添加左括号
   - 如果 `right < left`，可以添加右括号（保证右括号数不超过左括号数）
4. 递归地进行选择与撤销（回溯）

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def generateParenthesis(self, n: int) -> List[str]:
        result = []
        
        def backtrack(current: str, left: int, right: int):
            # 如果当前字符串长度达到 2n，说明找到了一个有效组合
            if len(current) == 2 * n:
                result.append(current)
                return
            
            # 可以添加左括号的条件：左括号数量小于 n
            if left < n:
                backtrack(current + '(', left + 1, right)
            
            # 可以添加右括号的条件：右括号数量小于左括号数量
            if right < left:
                backtrack(current + ')', left, right + 1)
        
        backtrack("", 0, 0)
        return result
```

### Java 代码实现
```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, "", 0, 0, n);
        return result;
    }
    
    private void backtrack(List<String> result, String current, int left, int right, int n) {
        // 如果当前字符串长度达到 2n，说明找到了一个有效组合
        if (current.length() == 2 * n) {
            result.add(current);
            return;
        }
        
        // 可以添加左括号的条件：左括号数量小于 n
        if (left < n) {
            backtrack(result, current + "(", left + 1, right, n);
        }
        
        // 可以添加右括号的条件：右括号数量小于左括号数量
        if (right < left) {
            backtrack(result, current + ")", left, right + 1, n);
        }
    }
}
```

### C 代码实现
```c
/**
 * Note: The returned array must be malloced, assume caller calls free().
 */

void backtrack(char** result, char* current, int index, int left, int right, int n, int* returnSize) {
    // 如果当前字符串长度达到 2n，说明找到了一个有效组合
    if (index == 2 * n) {
        current[index] = '\0';
        result[*returnSize] = (char*)malloc((2 * n + 1) * sizeof(char));
        strcpy(result[*returnSize], current);
        (*returnSize)++;
        return;
    }
    
    // 可以添加左括号的条件：左括号数量小于 n
    if (left < n) {
        current[index] = '(';
        backtrack(result, current, index + 1, left + 1, right, n, returnSize);
    }
    
    // 可以添加右括号的条件：右括号数量小于左括号数量
    if (right < left) {
        current[index] = ')';
        backtrack(result, current, index + 1, left, right + 1, n, returnSize);
    }
}

char** generateParenthesis(int n, int* returnSize) {
    *returnSize = 0;
    // 卡特兰数计算最大可能结果数
    int maxSize = 1;
    for (int i = 0; i < n; i++) {
        maxSize = maxSize * (4 * i + 2) / (i + 2);
    }
    
    char** result = (char**)malloc(maxSize * sizeof(char*));
    char* current = (char*)malloc((2 * n + 1) * sizeof(char));
    
    backtrack(result, current, 0, 0, 0, n, returnSize);
    
    free(current);
    return result;
}
```

---