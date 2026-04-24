# 2946. 循环移位后的矩阵相似检查

**难度: 简单**

## 题目描述
给你一个下标从 0 开始且大小为 `m x n` 的整数矩阵 mat 和一个整数 k。请你将矩阵中的 **奇数** 行循环右移 k 次，偶数行循环左移 k 次。

如果初始矩阵和最终矩阵完全相同，则返回 `true`，否则返回 `false`。

---

## 示例说明
### 示例 1：
输入：mat = [[1,2,1,2],[5,5,5,5],[6,3,6,3]], k = 2  

![similarmatrix](../../../readFile/image/similarmatrix.png)

输出：true  
解释：初始矩阵如图一所示。经过两次循环移位后的最终矩阵状态与初始矩阵相同。

### 示例 2：
输入：mat = [[2,2],[2,2]], k = 3  
输出：true  
解释：由于矩阵中的所有值都相等，即使进行循环移位，矩阵仍然保持不变。

### 示例 3：
输入：mat = [[1,2,1],[1,2,1]], k = 1  
输出：false  
解释：循环移位一次后，mat = [[2,1,1],[1,2,1]]，与初始矩阵不相等。

---

## 提示：
- 1 ≤ mat.length ≤ 25
- 1 ≤ mat[i].length ≤ 25
- 1 ≤ mat[i][j] ≤ 25
- 1 ≤ k ≤ 50

---

## 解题思路

### 核心思想
直接模拟循环移位操作，然后比较变换后的矩阵与原矩阵是否相同。由于矩阵尺寸最大为 25×25，k 最大为 50，直接模拟是可行的。

### 关键观察
- 行索引从 0 开始，所以偶数行（索引 0,2,4,...）左移，奇数行（索引 1,3,5,...）右移
- 循环左移 k 次相当于将前 k 个元素移到末尾
- 循环右移 k 次相当于将后 k 个元素移到开头
- 由于 k 可能大于 n，可以先对 k 取模 `k % n`

### 算法步骤
1. 获取矩阵的行数 `m` 和列数 `n`
2. 计算实际移位次数 `shift = k % n`
3. 创建一个新矩阵 `newMat` 用于存储变换后的结果
4. 遍历每一行：
   - 如果行索引为偶数（0-based），循环左移 `shift` 次
   - 如果行索引为奇数，循环右移 `shift` 次
5. 比较 `newMat` 与原矩阵是否完全相同

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def areSimilar(self, mat: List[List[int]], k: int) -> bool:
        m, n = len(mat), len(mat[0])
        shift = k % n
        
        for i in range(m):
            if i % 2 == 0:  # 偶数行，左移
                # 左移 shift 位：取从 shift 到末尾，再取前 shift 个
                new_row = mat[i][shift:] + mat[i][:shift]
            else:  # 奇数行，右移
                # 右移 shift 位：取后 shift 个，再取从开头到 n-shift
                new_row = mat[i][-shift:] + mat[i][:-shift]
            
            if new_row != mat[i]:
                return False
        
        return True
```

### Java 代码实现
```java
class Solution {
    public boolean areSimilar(int[][] mat, int k) {
        int m = mat.length;
        int n = mat[0].length;
        int shift = k % n;
        
        for (int i = 0; i < m; i++) {
            if (i % 2 == 0) {  // 偶数行，左移
                for (int j = 0; j < n; j++) {
                    int newVal = mat[i][(j + shift) % n];
                    if (newVal != mat[i][j]) {
                        return false;
                    }
                }
            } else {  // 奇数行，右移
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
```

### C 代码实现
```c
bool areSimilar(int** mat, int matSize, int* matColSize, int k) {
    int m = matSize;
    int n = matColSize[0];
    int shift = k % n;
    
    for (int i = 0; i < m; i++) {
        if (i % 2 == 0) {  // 偶数行，左移
            for (int j = 0; j < n; j++) {
                int newVal = mat[i][(j + shift) % n];
                if (newVal != mat[i][j]) {
                    return false;
                }
            }
        } else {  // 奇数行，右移
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
```

---