# 301. 删除无效的括号

**难度: 困难**

## 题目描述
给你一个由若干括号和字母组成的字符串 `s`，删除最小数量的无效括号，使得输入的字符串有效。  
返回所有可能的结果。答案可以按任意顺序返回。

---

## 示例说明
### 示例 1：
**输入：** s = "()())()"  
**输出：** ["(())()", "()()()"]  
**解释：**
- 删除第一个右括号：`"()()()"`
- 删除第二个右括号：`"(())()"`

---

### 示例 2：
**输入：** s = "(a)())()"  
**输出：** ["(a())()", "(a)()()"]  
**解释：**
- 删除第一个右括号：`"(a)()()"`
- 删除第二个右括号：`"(a())()"`

---

### 示例 3：
**输入：** s = ")("  
**输出：** [""]  
**解释：**
- 需要删除所有括号，只保留空字符串

---

### 示例 4：
**输入：** s = "n"  
**输出：** ["n"]  
**解释：**
- 没有括号，本身就是有效的

---

## 提示：
- 1 ≤ s.length ≤ 25
- s 由小写英文字母以及括号 `(` 和 `)` 组成
- s 中至多含 20 个括号

---

## 解题思路

### 核心思想
使用BFS（广度优先搜索）或DFS（深度优先搜索）来尝试删除不同位置的括号，直到找到有效字符串。由于要求删除最小数量的括号，BFS更合适。

### 关键观察
1. 首先需要计算需要删除的最少左括号和右括号数量
2. 括号有效的条件：
   - 遍历过程中，左括号数量始终 ≥ 右括号数量
   - 最终左括号数量 = 右括号数量
3. 使用BFS可以保证找到删除最少括号的解决方案

### 算法步骤（BFS方法）
1. 计算需要删除的最少左括号和右括号数量：
   - 遍历字符串，统计左右括号数量
   - 当右括号多于左括号时，需要删除一个右括号
   - 最后剩余的左括号也需要删除
2. 使用队列进行BFS搜索：
   - 初始状态：原始字符串
   - 每次从队列中取出一个字符串，检查是否有效
   - 如果有效，加入结果集
   - 如果无效，尝试删除每个括号生成新的字符串，加入队列
3. 去重和优化：
   - 使用集合避免重复处理相同字符串
   - 一旦找到有效字符串，当前层就是最小删除数量的解

### 算法步骤（DFS回溯方法）
1. 计算需要删除的最少左括号和右括号数量
2. 使用DFS回溯：
   - 从字符串开始位置遍历
   - 如果遇到可以删除的括号，尝试删除
   - 递归处理剩余字符串
   - 当删除数量达到最小，且字符串有效时，加入结果集
3. 剪枝优化：
   - 跳过连续相同的括号，避免重复结果
   - 记录已经处理过的字符串，避免重复计算

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List
from collections import deque

class Solution:
    def removeInvalidParentheses(self, s: str) -> List[str]:
        """BFS方法"""
        def is_valid(string):
            """检查字符串是否有效"""
            count = 0
            for ch in string:
                if ch == '(':
                    count += 1
                elif ch == ')':
                    count -= 1
                    if count < 0:
                        return False
            return count == 0
        
        # BFS初始化
        queue = deque([s])
        visited = set([s])
        found = False
        result = []
        
        while queue:
            current = queue.popleft()
            
            # 如果当前字符串有效
            if is_valid(current):
                result.append(current)
                found = True  # 标记已找到最小删除数量的解
            
            # 如果已找到有效字符串，不再继续删除字符
            if found:
                continue
            
            # 尝试删除每个字符
            for i in range(len(current)):
                # 只删除括号
                if current[i] not in '()':
                    continue
                
                # 生成新字符串
                new_str = current[:i] + current[i+1:]
                
                # 如果没访问过，加入队列
                if new_str not in visited:
                    visited.add(new_str)
                    queue.append(new_str)
        
        return result
    
    def removeInvalidParentheses_dfs(self, s: str) -> List[str]:
        """DFS回溯方法"""
        # 计算需要删除的最少左右括号数量
        left_rem, right_rem = 0, 0
        for ch in s:
            if ch == '(':
                left_rem += 1
            elif ch == ')':
                if left_rem > 0:
                    left_rem -= 1
                else:
                    right_rem += 1
        
        result = set()
        
        def dfs(index, left_count, right_count, left_rem, right_rem, expr):
            """深度优先搜索"""
            if index == len(s):
                # 到达字符串末尾
                if left_rem == 0 and right_rem == 0:
                    result.add(''.join(expr))
                return
            
            ch = s[index]
            
            # 情况1：删除当前字符（如果是括号）
            if (ch == '(' and left_rem > 0) or (ch == ')' and right_rem > 0):
                dfs(index + 1, left_count, right_count, 
                    left_rem - (1 if ch == '(' else 0), 
                    right_rem - (1 if ch == ')' else 0), 
                    expr)
            
            # 情况2：保留当前字符
            expr.append(ch)
            
            if ch not in '()':
                # 普通字符
                dfs(index + 1, left_count, right_count, left_rem, right_rem, expr)
            elif ch == '(':
                # 左括号
                dfs(index + 1, left_count + 1, right_count, left_rem, right_rem, expr)
            elif ch == ')':
                # 右括号，只有在左括号多于右括号时才有效
                if left_count > right_count:
                    dfs(index + 1, left_count, right_count + 1, left_rem, right_rem, expr)
            
            # 回溯
            expr.pop()
        
        dfs(0, 0, 0, left_rem, right_rem, [])
        return list(result)
```

---

### Java 代码实现

```java
import java.util.*;

class Solution {
    // BFS方法
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null) return result;
        
        // BFS队列
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
            if (found) continue;
            
            // 尝试删除每个字符
            for (int i = 0; i < current.length(); i++) {
                char ch = current.charAt(i);
                // 只删除括号
                if (ch != '(' && ch != ')') continue;
                
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
    
    // DFS回溯方法
    public List<String> removeInvalidParenthesesDFS(String s) {
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
    
    private void dfs(String s, int index, int leftCount, int rightCount,
                    int leftRem, int rightRem, StringBuilder expr, Set<String> result) {
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
                leftRem - (ch == '(' ? 1 : 0),
                rightRem - (ch == ')' ? 1 : 0),
                expr, result);
        }
        
        // 情况2：保留当前字符
        expr.append(ch);
        
        if (ch == '(') {
            dfs(s, index + 1, leftCount + 1, rightCount, leftRem, rightRem, expr, result);
        } else if (ch == ')') {
            // 只有在左括号多于右括号时才有效
            if (leftCount > rightCount) {
                dfs(s, index + 1, leftCount, rightCount + 1, leftRem, rightRem, expr, result);
            }
        } else {
            // 普通字符
            dfs(s, index + 1, leftCount, rightCount, leftRem, rightRem, expr, result);
        }
        
        // 回溯
        expr.deleteCharAt(expr.length() - 1);
    }
    
    // 检查字符串是否有效
    private boolean isValid(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(') {
                count++;
            } else if (ch == ')') {
                count--;
                if (count < 0) return false;
            }
        }
        return count == 0;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdbool.h>

// 字符串链表节点
typedef struct StringNode {
    char* str;
    struct StringNode* next;
} StringNode;

// 字符串链表
typedef struct {
    StringNode* head;
    StringNode* tail;
    int size;
} StringList;

// 初始化链表
StringList* createStringList() {
    StringList* list = (StringList*)malloc(sizeof(StringList));
    list->head = NULL;
    list->tail = NULL;
    list->size = 0;
    return list;
}

// 添加字符串到链表
void addString(StringList* list, const char* str) {
    StringNode* node = (StringNode*)malloc(sizeof(StringNode));
    node->str = strdup(str);
    node->next = NULL;
    
    if (list->head == NULL) {
        list->head = node;
        list->tail = node;
    } else {
        list->tail->next = node;
        list->tail = node;
    }
    list->size++;
}

// 释放链表
void freeStringList(StringList* list) {
    StringNode* curr = list->head;
    while (curr != NULL) {
        StringNode* next = curr->next;
        free(curr->str);
        free(curr);
        curr = next;
    }
    free(list);
}

// 检查字符串是否有效
bool isValid(const char* s) {
    int count = 0;
    for (int i = 0; s[i] != '\0'; i++) {
        if (s[i] == '(') {
            count++;
        } else if (s[i] == ')') {
            count--;
            if (count < 0) {
                return false;
            }
        }
    }
    return count == 0;
}

// BFS方法
char** removeInvalidParentheses(char* s, int* returnSize) {
    StringList* resultList = createStringList();
    
    // 如果输入为空，返回空数组
    if (s == NULL || strlen(s) == 0) {
        *returnSize = 0;
        char** result = (char**)malloc(sizeof(char*));
        return result;
    }
    
    // 创建队列和访问集合
    char** queue = (char**)malloc(10000 * sizeof(char*));
    bool* visited = (bool*)calloc(10000, sizeof(bool));
    char** visitedStrs = (char**)malloc(10000 * sizeof(char*));
    
    int front = 0, rear = 0;
    bool found = false;
    
    // 初始字符串入队
    queue[rear] = strdup(s);
    visitedStrs[rear] = strdup(s);
    visited[rear] = true;
    rear++;
    
    while (front < rear && !found) {
        int levelSize = rear - front;
        
        for (int i = 0; i < levelSize; i++) {
            char* current = queue[front];
            front++;
            
            // 检查是否有效
            if (isValid(current)) {
                addString(resultList, current);
                found = true;
            }
            
            // 如果已找到有效字符串，不再继续删除字符
            if (found) {
                free(current);
                continue;
            }
            
            // 尝试删除每个字符
            int len = strlen(current);
            for (int j = 0; j < len; j++) {
                char ch = current[j];
                // 只删除括号
                if (ch != '(' && ch != ')') {
                    continue;
                }
                
                // 生成新字符串
                char* newStr = (char*)malloc(len * sizeof(char));
                int idx = 0;
                for (int k = 0; k < len; k++) {
                    if (k != j) {
                        newStr[idx++] = current[k];
                    }
                }
                newStr[idx] = '\0';
                
                // 检查是否已访问
                bool alreadyVisited = false;
                for (int k = 0; k < rear; k++) {
                    if (strcmp(newStr, visitedStrs[k]) == 0) {
                        alreadyVisited = true;
                        break;
                    }
                }
                
                // 如果没访问过，加入队列
                if (!alreadyVisited) {
                    queue[rear] = newStr;
                    visitedStrs[rear] = strdup(newStr);
                    visited[rear] = true;
                    rear++;
                } else {
                    free(newStr);
                }
            }
            
            free(current);
        }
    }
    
    // 准备返回结果
    *returnSize = resultList->size;
    char** result = (char**)malloc(resultList->size * sizeof(char*));
    
    StringNode* curr = resultList->head;
    for (int i = 0; i < resultList->size; i++) {
        result[i] = strdup(curr->str);
        curr = curr->next;
    }
    
    // 释放内存
    for (int i = 0; i < rear; i++) {
        free(visitedStrs[i]);
    }
    free(queue);
    free(visited);
    free(visitedStrs);
    freeStringList(resultList);
    
    return result;
}

// 测试代码
int main() {
    // 测试示例1
    char s1[] = "()())()";
    int returnSize1;
    char** result1 = removeInvalidParentheses(s1, &returnSize1);
    
    printf("测试1:\n输入: \"()())()\"\n输出: [");
    for (int i = 0; i < returnSize1; i++) {
        printf("\"%s\"", result1[i]);
        if (i < returnSize1 - 1) printf(", ");
        free(result1[i]);
    }
    printf("]\n期望: [\"(())()\", \"()()()\"]\n\n");
    free(result1);
    
    // 测试示例2
    char s2[] = "(a)())()";
    int returnSize2;
    char** result2 = removeInvalidParentheses(s2, &returnSize2);
    
    printf("测试2:\n输入: \"(a)())()\"\n输出: [");
    for (int i = 0; i < returnSize2; i++) {
        printf("\"%s\"", result2[i]);
        if (i < returnSize2 - 1) printf(", ");
        free(result2[i]);
    }
    printf("]\n期望: [\"(a())()\", \"(a)()()\"]\n\n");
    free(result2);
    
    // 测试示例3
    char s3[] = ")(";
    int returnSize3;
    char** result3 = removeInvalidParentheses(s3, &returnSize3);
    
    printf("测试3:\n输入: \")(\"\n输出: [");
    for (int i = 0; i < returnSize3; i++) {
        printf("\"%s\"", result3[i]);
        if (i < returnSize3 - 1) printf(", ");
        free(result3[i]);
    }
    printf("]\n期望: [\"\"]\n\n");
    free(result3);
    
    return 0;
}
```

---

### 复杂度分析
- **时间复杂度：** 
  - BFS：最坏情况 O(n × 2ⁿ)，n 为字符串长度
  - DFS：O(2ⁿ)，但实际有剪枝优化
- **空间复杂度：** O(2ⁿ)，需要存储中间结果和访问集合

### 算法特点
1. **BFS保证最优解**：由于BFS按层搜索，先找到的一定是删除最少括号的解
2. **去重优化**：使用集合避免重复处理相同字符串
3. **剪枝策略**：跳过非括号字符，减少搜索空间
4. **多种实现**：BFS和DFS都可以解决问题，BFS更直观，DFS在剪枝良好时更高效