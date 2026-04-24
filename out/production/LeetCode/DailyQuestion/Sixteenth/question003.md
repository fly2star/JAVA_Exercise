# 2515. 到目标字符串的最短距离

**难度: 简单**

## 题目描述
给你一个下标从 0 开始的 **环形** 字符串数组 `words` 和一个字符串 `target`。**环形数组** 意味着数组首尾相连。

- 形式上，`words[i]` 的下一个元素是 `words[(i + 1) % n]`，而 `words[i]` 的前一个元素是 `words[(i - 1 + n) % n]`，其中 \( n \) 是 `words` 的长度。

从 `startIndex` 开始，你一次可以用 1 步移动到下一个或者前一个单词。

返回到目标字符串 `target` 所需的最短距离。如果 `words` 中不存在字符串 `target`，返回 \(-1\)。

---

## 示例说明
### 示例 1：
输入：words = ["hello", "i", "am", "leetcode", "hello"], target = "hello", startIndex = 1  
输出：1  
解释：从下标 1 开始，可以向左移动 1 步到达下标 0（"hello"），距离为 1。向右移动需要更多步。

### 示例 2：
输入：words = ["a", "b", "leetcode"], target = "leetcode", startIndex = 0  
输出：1  
解释：从下标 0 开始，可以向右移动 2 步或向左移动 1 步到达下标 2，最短距离为 1。

### 示例 3：
输入：words = ["i", "eat", "leetcode"], target = "ate", startIndex = 0  
输出：-1  
解释：`words` 中不存在 "ate"，因此返回 -1。

---

## 提示：
- 1 ≤ words.length ≤ 100
- 1 ≤ words[i].length ≤ 100
- words[i] 和 target 仅由小写英文字母组成
- 0 ≤ startIndex < words.length

---

## 解题思路

### 核心思想
在环形数组中，从 `startIndex` 到某个目标索引 `i` 的最短距离为：
```
min( |i - startIndex|, n - |i - startIndex| )
```
其中 `n` 为数组长度。因为可以顺时针或逆时针移动。

因此，只需遍历所有等于 `target` 的索引，计算上述距离，取最小值。若没有找到任何匹配，返回 -1。

### 关键观察
- 环形特性允许我们通过反向绕行缩短距离。
- 数组长度 ≤ 100，可直接遍历。

### 算法步骤
1. 初始化 `ans = n`（最大值）。
2. 遍历 `words` 的所有下标 `i`：
   - 若 `words[i] == target`：
     - 计算顺时针距离 `d1 = abs(i - startIndex)`
     - 计算逆时针距离 `d2 = n - d1`
     - 取 `dist = min(d1, d2)`
     - 更新 `ans = min(ans, dist)`
3. 若 `ans` 仍为初始值，返回 -1；否则返回 `ans`。

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def closetTarget(self, words: List[str], target: str, startIndex: int) -> int:
        n = len(words)
        ans = n
        for i, w in enumerate(words):
            if w == target:
                d = abs(i - startIndex)
                dist = min(d, n - d)
                ans = min(ans, dist)
        return -1 if ans == n else ans
```

### Java 代码实现
```java
class Solution {
    public int closetTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        int ans = n;
        for (int i = 0; i < n; i++) {
            if (words[i].equals(target)) {
                int d = Math.abs(i - startIndex);
                int dist = Math.min(d, n - d);
                ans = Math.min(ans, dist);
            }
        }
        return ans == n ? -1 : ans;
    }
}
```

### C 代码实现
```c
#include <string.h>
#include <stdlib.h>

int closetTarget(char** words, int wordsSize, char* target, int startIndex) {
    int n = wordsSize;
    int ans = n;
    for (int i = 0; i < n; i++) {
        if (strcmp(words[i], target) == 0) {
            int d = abs(i - startIndex);
            int dist = d < (n - d) ? d : (n - d);
            if (dist < ans) ans = dist;
        }
    }
    return ans == n ? -1 : ans;
}
```

---