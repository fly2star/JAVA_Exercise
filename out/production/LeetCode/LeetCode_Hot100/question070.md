# 11. 盛最多水的容器

**难度: 中等**

## 题目描述
给定一个长度为 `n` 的整数数组 `height`。有 `n` 条垂线，第 `i` 条线的两个端点是 `(i, 0)` 和 `(i, height[i])`。

找出其中的两条线，使得它们与 `x` 轴共同构成的容器可以容纳最多的水。

返回容器可以储存的最大水量。

**说明：** 你不能倾斜容器。

---

## 示例说明
### 示例 1：

![question_11](../../readFile/image/question_11.jpg)

输入：height = [1,8,6,2,5,4,8,3,7]  
输出：49  
解释：在此情况下，容器能够容纳的水的最大值为 49。

### 示例 2：
输入：height = [1,1]  
输出：1

---

## 提示：
- n = height.length
- 2 ≤ n ≤ 10^5
- 0 ≤ height[i] ≤ 10^4

---

## 解题思路

### 核心思想
使用**双指针法**，从数组的两端开始向中间移动。容器的盛水量由两个因素决定：**两条线的距离（宽度）**和**较短的那条线的高度**。我们每次移动较短的那条线的指针，因为移动较长的线不会增加盛水量（受限于较短的线）。

### 关键观察
- 盛水量 = 宽度 × 高度，其中高度是两条线中较矮的那条
- 初始时，宽度最大，但高度可能很小
- 移动指针时，宽度减小，但有机会遇到更高的线
- 总是移动较矮的那条线的指针，因为如果移动较高的线，高度不会增加（甚至可能减小），而宽度一定减小，盛水量只会减少

### 算法步骤
1. 初始化左指针 `left = 0`，右指针 `right = n - 1`
2. 初始化最大水量 `max_area = 0`
3. 当 `left < right` 时循环：
   - 计算当前水量：`width = right - left`，`height = min(height[left], height[right])`，`area = width * height`
   - 更新 `max_area = max(max_area, area)`
   - 移动较矮的那条线的指针：
     - 如果 `height[left] < height[right]`，则 `left++`
     - 否则 `right--`
4. 返回 `max_area`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def maxArea(self, height: List[int]) -> int:
        left, right = 0, len(height) - 1
        max_area = 0
        
        while left < right:
            # 计算当前面积
            width = right - left
            h = min(height[left], height[right])
            area = width * h
            max_area = max(max_area, area)
            
            # 移动较矮的那条线的指针
            if height[left] < height[right]:
                left += 1
            else:
                right -= 1
        
        return max_area
```

### Java 代码实现
```java
class Solution {
    public int maxArea(int[] height) {
        int left = 0;
        int right = height.length - 1;
        int maxArea = 0;
        
        while (left < right) {
            // 计算当前面积
            int width = right - left;
            int h = Math.min(height[left], height[right]);
            int area = width * h;
            maxArea = Math.max(maxArea, area);
            
            // 移动较矮的那条线的指针
            if (height[left] < height[right]) {
                left++;
            } else {
                right--;
            }
        }
        
        return maxArea;
    }
}
```

### C 代码实现
```c
int maxArea(int* height, int heightSize) {
    int left = 0;
    int right = heightSize - 1;
    int maxArea = 0;
    
    while (left < right) {
        // 计算当前面积
        int width = right - left;
        int h = height[left] < height[right] ? height[left] : height[right];
        int area = width * h;
        
        if (area > maxArea) {
            maxArea = area;
        }
        
        // 移动较矮的那条线的指针
        if (height[left] < height[right]) {
            left++;
        } else {
            right--;
        }
    }
    
    return maxArea;
}
```

---