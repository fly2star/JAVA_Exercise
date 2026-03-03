# 42. 接雨水

**难度: 困难**

## 题目描述
给定 `n` 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。

---

## 示例说明
### 示例 1：
![rainwatertrap](../../readFile/image/rainwatertrap.png)

输入：height = [0,1,0,2,1,0,1,3,2,1,2,1]  
输出：6  
解释：上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水。

### 示例 2：
输入：height = [4,2,0,3,2,5]  
输出：9

---

## 提示：
- n = height.length
- 1 < n < 2 * 10^4
- 0 <= height[i] <= 10^5

---

## 解题思路

### 核心思想
对于每个位置能接多少雨水，取决于它**左边最高的柱子**和**右边最高的柱子**中的较小值，减去当前位置的高度。即：
```
water[i] = min(left_max[i], right_max[i]) - height[i]
```
如果这个值为负，则取 0（实际上不会发生，因为 left_max 和 right_max 都 >= height[i]）。

### 关键观察
- 雨水的存储取决于"木桶效应"：能接多少水取决于左右两边较矮的那个最高柱子
- 如果从左到右扫描，我们只知道左边的最高，不知道右边的最高
- 如果从右到左扫描，我们只知道右边的最高，不知道左边的最高
- 可以使用双指针同时从两端向中间移动，维护左右两边的最高值

### 算法步骤

#### 方法一：动态规划（三次遍历）
1. 第一次从左到右遍历，记录每个位置左边的最大值 `left_max[i]`
2. 第二次从右到左遍历，记录每个位置右边的最大值 `right_max[i]`
3. 第三次遍历计算每个位置的雨水量：`min(left_max[i], right_max[i]) - height[i]`

#### 方法二：双指针（一次遍历）
1. 初始化左指针 `left = 0`，右指针 `right = n-1`
2. 维护左边最大值 `left_max` 和右边最大值 `right_max`
3. 当 `left < right` 时循环：
   - 如果 `height[left] < height[right]`：
     - 如果 `height[left] >= left_max`，更新 `left_max`
     - 否则累加雨水量：`ans += left_max - height[left]`
     - `left++`
   - 否则：
     - 如果 `height[right] >= right_max`，更新 `right_max`
     - 否则累加雨水量：`ans += right_max - height[right]`
     - `right--`

#### 方法三：单调栈
1. 维护一个单调递减栈（栈底到栈顶递减），存储柱子的下标
2. 遍历每个柱子：
   - 如果当前柱子高度大于栈顶柱子高度，说明形成了凹槽
   - 弹出栈顶作为底部，新的栈顶作为左边界，当前柱子作为右边界
   - 计算宽度和高度差，累加雨水量
   - 重复直到栈空或栈顶高度 >= 当前高度
   - 将当前柱子下标入栈

---

## 代码参考(python, java, c)

### Python 代码实现

```python
# 方法一：动态规划
class Solution:
    def trap(self, height: List[int]) -> int:
        if not height:
            return 0
        
        n = len(height)
        left_max = [0] * n
        right_max = [0] * n
        
        # 计算左边最大值
        left_max[0] = height[0]
        for i in range(1, n):
            left_max[i] = max(left_max[i-1], height[i])
        
        # 计算右边最大值
        right_max[n-1] = height[n-1]
        for i in range(n-2, -1, -1):
            right_max[i] = max(right_max[i+1], height[i])
        
        # 计算雨水量
        ans = 0
        for i in range(n):
            ans += min(left_max[i], right_max[i]) - height[i]
        
        return ans

# 方法二：双指针
class Solution:
    def trap(self, height: List[int]) -> int:
        if not height:
            return 0
        
        left, right = 0, len(height) - 1
        left_max = right_max = 0
        ans = 0
        
        while left < right:
            if height[left] < height[right]:
                if height[left] >= left_max:
                    left_max = height[left]
                else:
                    ans += left_max - height[left]
                left += 1
            else:
                if height[right] >= right_max:
                    right_max = height[right]
                else:
                    ans += right_max - height[right]
                right -= 1
        
        return ans

# 方法三：单调栈
class Solution:
    def trap(self, height: List[int]) -> int:
        stack = []
        ans = 0
        
        for i in range(len(height)):
            while stack and height[i] > height[stack[-1]]:
                bottom = stack.pop()  # 凹槽底部
                if not stack:
                    break
                left = stack[-1]  # 左边界
                width = i - left - 1
                height_diff = min(height[left], height[i]) - height[bottom]
                ans += width * height_diff
            stack.append(i)
        
        return ans
```

### Java 代码实现

```java
// 方法一：动态规划
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        int n = height.length;
        int[] leftMax = new int[n];
        int[] rightMax = new int[n];
        
        leftMax[0] = height[0];
        for (int i = 1; i < n; i++) {
            leftMax[i] = Math.max(leftMax[i-1], height[i]);
        }
        
        rightMax[n-1] = height[n-1];
        for (int i = n-2; i >= 0; i--) {
            rightMax[i] = Math.max(rightMax[i+1], height[i]);
        }
        
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans += Math.min(leftMax[i], rightMax[i]) - height[i];
        }
        
        return ans;
    }
}

// 方法二：双指针
class Solution {
    public int trap(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }
        
        int left = 0, right = height.length - 1;
        int leftMax = 0, rightMax = 0;
        int ans = 0;
        
        while (left < right) {
            if (height[left] < height[right]) {
                if (height[left] >= leftMax) {
                    leftMax = height[left];
                } else {
                    ans += leftMax - height[left];
                }
                left++;
            } else {
                if (height[right] >= rightMax) {
                    rightMax = height[right];
                } else {
                    ans += rightMax - height[right];
                }
                right--;
            }
        }
        
        return ans;
    }
}

// 方法三：单调栈
class Solution {
    public int trap(int[] height) {
        Stack<Integer> stack = new Stack<>();
        int ans = 0;
        
        for (int i = 0; i < height.length; i++) {
            while (!stack.isEmpty() && height[i] > height[stack.peek()]) {
                int bottom = stack.pop();
                if (stack.isEmpty()) {
                    break;
                }
                int left = stack.peek();
                int width = i - left - 1;
                int heightDiff = Math.min(height[left], height[i]) - height[bottom];
                ans += width * heightDiff;
            }
            stack.push(i);
        }
        
        return ans;
    }
}
```

### C 代码实现

```c
// 方法二：双指针（最简洁高效）
int trap(int* height, int heightSize) {
    if (heightSize == 0) return 0;
    
    int left = 0, right = heightSize - 1;
    int left_max = 0, right_max = 0;
    int ans = 0;
    
    while (left < right) {
        if (height[left] < height[right]) {
            if (height[left] >= left_max) {
                left_max = height[left];
            } else {
                ans += left_max - height[left];
            }
            left++;
        } else {
            if (height[right] >= right_max) {
                right_max = height[right];
            } else {
                ans += right_max - height[right];
            }
            right--;
        }
    }
    
    return ans;
}

// 方法三：单调栈
int trap(int* height, int heightSize) {
    int* stack = (int*)malloc(heightSize * sizeof(int));
    int top = -1;
    int ans = 0;
    
    for (int i = 0; i < heightSize; i++) {
        while (top >= 0 && height[i] > height[stack[top]]) {
            int bottom = stack[top--];
            if (top < 0) {
                break;
            }
            int left = stack[top];
            int width = i - left - 1;
            int heightDiff = (height[left] < height[i] ? height[left] : height[i]) - height[bottom];
            ans += width * heightDiff;
        }
        stack[++top] = i;
    }
    
    free(stack);
    return ans;
}
```

---