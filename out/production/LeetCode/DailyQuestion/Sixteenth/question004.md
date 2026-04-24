# 2078. 两栋颜色不同且距离最远的房子

**难度: 简单**

## 题目描述
街上有 n 栋房子整齐地排成一列，每栋房子都粉刷上了漂亮的颜色。给你一个下标从 0 开始且长度为 n 的整数数组 colors，其中 colors[i] 表示第 i 栋房子的颜色。

返回两栋颜色不同且房子之间的 **最大距离**。

第 i 栋房子和第 j 栋房子之间的距离是 `abs(i - j)`。

---

## 示例说明
### 示例 1：
输入：colors = [1,1,1,6,1,1,1]

![eg1](../../../readFile/image/2078-eg1.png)

输出：3  
解释：颜色不同的两栋最远房子是 0 和 3（或 3 和 6），距离为 3。

### 示例 2：
输入：colors = [1,8,3,8,3]

![eg2](../../../readFile/image/2078-eg2.png)

输出：4  
解释：颜色不同的两栋最远房子是 0 和 4，距离为 4。

### 示例 3：
输入：colors = [0,1]  
输出：1

---

## 提示：
- n = colors.length
- 2 ≤ n ≤ 100
- 0 ≤ colors[i] ≤ 100
- 至少存在 2 栋颜色不同的房子

---

## 解题思路

### 核心思想
由于我们要最大化距离 `abs(i - j)`，最远的房子对一定是其中一个在数组的某一端（最左或最右）。因此，只需检查：
- 第一个房子与右侧最后一个颜色不同的房子之间的距离
- 最后一个房子与左侧第一个颜色不同的房子之间的距离
取两者最大值即可。时间复杂度 O(n)，空间 O(1)。

### 关键观察
- 如果两栋房子颜色不同且距离最大，那么其中一栋必然是边界房子（索引 0 或 n-1）。
- 否则，如果两个房子都在内部，我们可以向外扩展得到更远的距离。

### 算法步骤
1. 初始化 `ans = 0`
2. 从右向左找到第一个与 `colors[0]` 颜色不同的房子，记录距离
3. 从左向右找到第一个与 `colors[n-1]` 颜色不同的房子，记录距离
4. 返回两者最大值

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def maxDistance(self, colors: List[int]) -> int:
        n = len(colors)
        # 最左端与最右端颜色不同的最远距离
        left = 0
        while left < n and colors[left] == colors[-1]:
            left += 1
        # 最右端与最左端颜色不同的最远距离
        right = n - 1
        while right >= 0 and colors[right] == colors[0]:
            right -= 1
        return max(n - 1 - left, right)
```

### Java 代码实现
```java
class Solution {
    public int maxDistance(int[] colors) {
        int n = colors.length;
        int left = 0;
        while (left < n && colors[left] == colors[n - 1]) left++;
        int right = n - 1;
        while (right >= 0 && colors[right] == colors[0]) right--;
        return Math.max(n - 1 - left, right);
    }
}
```

### C 代码实现
```c
int maxDistance(int* colors, int colorsSize) {
    int n = colorsSize;
    int left = 0;
    while (left < n && colors[left] == colors[n - 1]) left++;
    int right = n - 1;
    while (right >= 0 && colors[right] == colors[0]) right--;
    int d1 = n - 1 - left;
    int d2 = right;
    return d1 > d2 ? d1 : d2;
}
```

---