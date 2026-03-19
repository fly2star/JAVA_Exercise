# 79. 单词搜索

**难度: 中等**

## 题目描述
给定一个 `m x n` 二维字符网格 `board` 和一个字符串单词 `word`。如果 `word` 存在于网格中，返回 `true`；否则，返回 `false`。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。同一个单元格内的字母不允许被重复使用。

---

## 示例说明
### 示例 1：

![word2](../../readFile/image/word2.jpg)

输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCCED"  
输出：true

### 示例 2：

![word-1](../../readFile/image/word-1.jpg)

输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "SEE"  
输出：true

### 示例 3：

![word3](../../readFile/image/word3.jpg)

输入：board = [["A","B","C","E"],["S","F","C","S"],["A","D","E","E"]], word = "ABCB"  
输出：false

---

## 提示：
- m == board.length
- n = board[i].length
- 1 ≤ m, n ≤ 6
- 1 ≤ word.length ≤ 15
- board 和 word 仅由大小写英文字母组成

---

## 解题思路

### 核心思想
使用**回溯法（深度优先搜索）**遍历网格，从每个可能的起点开始，尝试匹配单词的每个字符。由于同一个单元格不能重复使用，需要标记已访问的单元格。

### 关键观察
- 可以从网格中的任何位置开始搜索
- 搜索过程中需要向四个方向（上、下、左、右）扩展
- 需要使用一个标记数组或原地修改来避免重复使用单元格
- 如果某条路径不匹配，需要回溯（撤销标记）

### 算法步骤
1. 遍历网格中的每个单元格，以每个单元格为起点进行深度优先搜索
2. 定义 DFS 函数 `dfs(i, j, index)`：
   - 如果 `index == len(word)`，说明已经找到完整单词，返回 true
   - 如果越界或当前字符不匹配或单元格已访问，返回 false
   - 标记当前单元格为已访问
   - 向四个方向递归搜索
   - 回溯：取消标记
3. 如果从任何起点开始的 DFS 返回 true，则整个函数返回 true
4. 否则返回 false

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def exist(self, board: List[List[str]], word: str) -> bool:
        if not board or not board[0]:
            return False
        
        m, n = len(board), len(board[0])
        
        def dfs(i: int, j: int, index: int) -> bool:
            # 找到完整单词
            if index == len(word):
                return True
            
            # 边界检查
            if i < 0 or i >= m or j < 0 or j >= n:
                return False
            
            # 字符不匹配
            if board[i][j] != word[index]:
                return False
            
            # 标记已访问
            temp = board[i][j]
            board[i][j] = '#'
            
            # 四个方向搜索
            found = (dfs(i + 1, j, index + 1) or
                    dfs(i - 1, j, index + 1) or
                    dfs(i, j + 1, index + 1) or
                    dfs(i, j - 1, index + 1))
            
            # 回溯
            board[i][j] = temp
            
            return found
        
        # 从每个单元格开始搜索
        for i in range(m):
            for j in range(n):
                if dfs(i, j, 0):
                    return True
        
        return False
```

### Java 代码实现
```java
class Solution {
    private int m, n;
    private char[][] board;
    private String word;
    
    public boolean exist(char[][] board, String word) {
        this.board = board;
        this.word = word;
        this.m = board.length;
        this.n = board[0].length;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }
    
    private boolean dfs(int i, int j, int index) {
        // 找到完整单词
        if (index == word.length()) {
            return true;
        }
        
        // 边界检查
        if (i < 0 || i >= m || j < 0 || j >= n) {
            return false;
        }
        
        // 字符不匹配
        if (board[i][j] != word.charAt(index)) {
            return false;
        }
        
        // 标记已访问
        char temp = board[i][j];
        board[i][j] = '#';
        
        // 四个方向搜索
        boolean found = dfs(i + 1, j, index + 1) ||
                       dfs(i - 1, j, index + 1) ||
                       dfs(i, j + 1, index + 1) ||
                       dfs(i, j - 1, index + 1);
        
        // 回溯
        board[i][j] = temp;
        
        return found;
    }
}
```

### C 代码实现
```c
bool dfs(char** board, int m, int n, int i, int j, char* word, int index) {
    // 找到完整单词
    if (word[index] == '\0') {
        return true;
    }
    
    // 边界检查
    if (i < 0 || i >= m || j < 0 || j >= n) {
        return false;
    }
    
    // 字符不匹配
    if (board[i][j] != word[index]) {
        return false;
    }
    
    // 标记已访问
    char temp = board[i][j];
    board[i][j] = '#';
    
    // 四个方向搜索
    bool found = dfs(board, m, n, i + 1, j, word, index + 1) ||
                 dfs(board, m, n, i - 1, j, word, index + 1) ||
                 dfs(board, m, n, i, j + 1, word, index + 1) ||
                 dfs(board, m, n, i, j - 1, word, index + 1);
    
    // 回溯
    board[i][j] = temp;
    
    return found;
}

bool exist(char** board, int boardSize, int* boardColSize, char* word) {
    int m = boardSize;
    int n = boardColSize[0];
    
    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (dfs(board, m, n, i, j, word, 0)) {
                return true;
            }
        }
    }
    
    return false;
}
```

---