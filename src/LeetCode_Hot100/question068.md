# 17. 电话号码的字母组合

**难度: 中等**

## 题目描述
给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。答案可以按任意顺序返回。

1[1752723054-mfIHZs-image](../../readFile/image/1752723054-mfIHZs-image.png)

给出数字到字母的映射如下（与电话按键相同）：
- 2: abc
- 3: def
- 4: ghi
- 5: jkl
- 6: mno
- 7: pqrs
- 8: tuv
- 9: wxyz

注意：1 不对应任何字母。

---

## 示例说明
### 示例 1：
输入：digits = "23"  
输出：["ad","ae","af","bd","be","bf","cd","ce","cf"]

### 示例 2：
输入：digits = "2"  
输出：["a","b","c"]

### 示例 3：
输入：digits = ""  
输出：[]

---

## 提示：
- 0 ≤ digits.length ≤ 4
- digits[i] 是范围 ['2', '9'] 的一个数字

---

## 解题思路

### 核心思想
使用**回溯法**（深度优先搜索）来生成所有可能的字母组合。每个数字对应一组字母，我们需要从每个数字对应的字母集合中选出一个字母，组成所有可能的组合。

### 关键观察
- 这是一个典型的组合问题，可以用回溯模板解决
- 数字到字母的映射可以用哈希表或数组存储
- 当 digits 为空时，返回空列表

### 算法步骤
1. 建立数字到字母的映射表
2. 定义回溯函数 `backtrack(combination, next_digits)`：
   - 如果没有更多数字要处理，将当前组合加入结果集
   - 否则，取出第一个数字，获取其对应的字母集合
   - 遍历每个字母：
     - 将当前字母附加到组合后面
     - 递归处理剩余的数字
3. 调用回溯函数，从空组合和完整 digits 开始
4. 返回结果集

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def letterCombinations(self, digits: str) -> List[str]:
        if not digits:
            return []
        
        # 数字到字母的映射
        phone_map = {
            '2': 'abc',
            '3': 'def',
            '4': 'ghi',
            '5': 'jkl',
            '6': 'mno',
            '7': 'pqrs',
            '8': 'tuv',
            '9': 'wxyz'
        }
        
        result = []
        
        def backtrack(combination: str, next_digits: str):
            # 如果没有更多数字，将当前组合加入结果
            if not next_digits:
                result.append(combination)
                return
            
            # 获取当前数字对应的所有字母
            letters = phone_map[next_digits[0]]
            
            # 遍历每个字母，进行回溯
            for letter in letters:
                backtrack(combination + letter, next_digits[1:])
        
        backtrack("", digits)
        return result
```

### Java 代码实现
```java
class Solution {
    private List<String> result = new ArrayList<>();
    private Map<Character, String> phoneMap = new HashMap<>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};
    
    public List<String> letterCombinations(String digits) {
        if (digits == null || digits.length() == 0) {
            return result;
        }
        backtrack("", digits);
        return result;
    }
    
    private void backtrack(String combination, String nextDigits) {
        // 如果没有更多数字，将当前组合加入结果
        if (nextDigits.length() == 0) {
            result.add(combination);
            return;
        }
        
        // 获取当前数字对应的所有字母
        char digit = nextDigits.charAt(0);
        String letters = phoneMap.get(digit);
        
        // 遍历每个字母，进行回溯
        for (int i = 0; i < letters.length(); i++) {
            char letter = letters.charAt(i);
            backtrack(combination + letter, nextDigits.substring(1));
        }
    }
}
```

### C 代码实现
```c
/**
 * Note: The returned array must be malloced, assume caller calls free().
 */

// 数字到字母的映射
char* phoneMap[] = {
    "",     // 0
    "",     // 1
    "abc",  // 2
    "def",  // 3
    "ghi",  // 4
    "jkl",  // 5
    "mno",  // 6
    "pqrs", // 7
    "tuv",  // 8
    "wxyz"  // 9
};

void backtrack(char* digits, int index, char* current, int currentSize, 
               char*** result, int* returnSize) {
    // 如果没有更多数字，将当前组合加入结果
    if (digits[index] == '\0') {
        (*result)[*returnSize] = (char*)malloc((currentSize + 1) * sizeof(char));
        strcpy((*result)[*returnSize], current);
        (*returnSize)++;
        return;
    }
    
    // 获取当前数字对应的所有字母
    int digit = digits[index] - '0';
    char* letters = phoneMap[digit];
    int len = strlen(letters);
    
    // 遍历每个字母，进行回溯
    for (int i = 0; i < len; i++) {
        current[currentSize] = letters[i];
        current[currentSize + 1] = '\0';
        backtrack(digits, index + 1, current, currentSize + 1, result, returnSize);
    }
}

char** letterCombinations(char* digits, int* returnSize) {
    *returnSize = 0;
    
    if (digits == NULL || digits[0] == '\0') {
        return NULL;
    }
    
    // 计算可能的结果数量
    int total = 1;
    for (int i = 0; digits[i] != '\0'; i++) {
        int digit = digits[i] - '0';
        total *= strlen(phoneMap[digit]);
    }
    
    // 分配结果数组
    char** result = (char**)malloc(total * sizeof(char*));
    
    // 当前组合
    char* current = (char*)malloc((strlen(digits) + 1) * sizeof(char));
    current[0] = '\0';
    
    // 开始回溯
    backtrack(digits, 0, current, 0, &result, returnSize);
    
    free(current);
    return result;
}
```

---