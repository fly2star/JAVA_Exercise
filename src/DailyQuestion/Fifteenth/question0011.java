package DailyQuestion.Fifteenth;

/*
2946. 循环移位后的矩阵相似检查

给你一个下标从 0 开始且大小为 `m x n` 的整数矩阵 mat 和一个整数 k。请你将矩阵中的 **奇数** 行循环右移 k 次，偶数行循环左移 k 次。

如果初始矩阵和最终矩阵完全相同，则返回 `true`，否则返回 `false`。


## 提示：
-- 1 ≤ mat.length ≤ 25
-- 1 ≤ mat[i].length ≤ 25
-- 1 ≤ mat[i][j] ≤ 25
-- 1 ≤ k ≤ 50
*/
public class question0011 {

    public static void main(String[] args) {
        
        int[][] mat = new int[][]{{1,2,1,2}, {5,5,5,5}, {6,3,6,3}};
        int k = 2;

        question0011 sl11 = new question0011();
        System.out.println(sl11.areSimilar(mat, k));
    }

    public boolean areSimilar(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;
        
        int shift = k % n;

        for (int i = 0; i < m; i++) {
            if (i % 2 == 0) {   // 偶数行, 左移
                for (int j = 0; j < n; j++) {
                    int newVal = mat[i][(j + shift) % n];
                    if (newVal != mat[i][j]) {
                        return false;
                    }
                }
            } else {    // 奇数行, 右移
                for (int j = 0; j < n; j++) {
                    int newVal = mat[i][(j - shift + n) % n];
                    if (newVal != mat[i][j]) {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    
}
