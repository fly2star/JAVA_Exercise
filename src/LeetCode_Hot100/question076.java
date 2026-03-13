package LeetCode_Hot100;

/*
79. 单词搜索

给定一个 `m x n` 二维字符网格 `board` 和一个字符串单词 `word`。
如果 `word` 存在于网格中，返回 `true`；否则，返回 `false`。

单词必须按照字母顺序，通过相邻的单元格内的字母构成，其中“相邻”单元格是那些水平相邻或垂直相邻的单元格。
同一个单元格内的字母不允许被重复使用。

## 提示：
    -- m == board.length
    -- n = board[i].length
    -- 1 ≤ m, n ≤ 6
    -- 1 ≤ word.length ≤ 15
    -- board 和 word 仅由大小写英文字母组成
*/
public class question076 {
    public static void main(String[] args) {
        
    }

    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board.length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                // 每一个格子都有可能是单词的起点
                if (backtrack(board, word, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    // 深度优先搜索
    public boolean backtrack(char[][] board, String word, int r, int c, int index) {
        // 成功匹配：索引到了单词末尾
        if (index == word.length()) {
            return true;
        }

        // 边界检查
        if (r < 0 || r >= board.length || c < 0 || c > board[0].length) {
            return false;
        }

        // 字符不匹配
        if (board[r][c] != word.charAt(index)) {
            return false;
        }

        // 做出选择: 标记当前格子为已访问
        char temp = board[r][c];
        board[r][c] = '#';

        // 递归: 向四个方向搜索
        boolean found = backtrack(board, word, r + 1, c, index + 1) ||
                        backtrack(board, word, r - 1, c, index - 1) ||
                        backtrack(board, word, r, c + 1, index + 1) ||
                        backtrack(board, word, r, c - 1, index + 1);

        // 撤销选择: 恢复现场
        board[r][c] = temp;

        return found;
        

    }
}


// 将变量作为类的成员变量
class Solution076 {
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
