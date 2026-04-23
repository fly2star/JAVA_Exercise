# 45. 跳跃游戏 II

**难度: 中等**

## 题目描述
给定一个长度为 `n` 的 **0** 索引整数数组 `nums`。初始位置在下标 0。

每个元素 `nums[i]` 表示从索引 `i` 向后跳转的最大长度。换句话说，如果你在索引 `i` 处，你可以跳到任意 `(i + j)` 处：
- \( 0 \leq j \leq nums[i] \) 且
- \( i + j < n \)

返回到达 `n - 1` 的最小跳跃次数。测试用例保证可以到达 `n - 1`。

---

## 示例说明
### 示例 1：
输入：nums = [2,3,1,1,4]  
输出：2  
解释：从下标 0 跳到下标 1，然后跳 3 步到达最后一个位置。

### 示例 2：
输入：nums = [2,3,0,1,4]  
输出：2

---

## 提示：
- 1 ≤ nums.length ≤ 10^4
- 0 ≤ nums[i] ≤ 1000
- 题目保证可以到达 n-1

---

## 解题思路

### 核心思想
使用**贪心算法**，在每一步中，我们选择在当前可到达范围内能够跳到最远位置的点，并更新下一次的跳跃边界。这种方法只需一次遍历，时间复杂度 O(n)。

### 关键观察
- 维护当前跳跃的结束位置 `end` 和最远可到达位置 `maxPos`。
- 遍历数组，当到达 `end` 时，必须进行一次新的跳跃，并将 `end` 更新为当前能跳到的最远位置。
- 最后一次跳跃不需要计数（当 `end >= n-1` 时停止）。

### 算法步骤
1. 初始化 `steps = 0`, `end = 0`, `maxPos = 0`。
2. 遍历 `i` 从 `0` 到 `n-2`（因为到达最后一个位置后无需继续）：
   - 更新 `maxPos = max(maxPos, i + nums[i])`
   - 如果 `i == end`，则必须再跳一次：`steps++`, `end = maxPos`
3. 返回 `steps`。

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def jump(self, nums: List[int]) -> int:
        n = len(nums)
        steps = 0
        end = 0
        maxPos = 0
        for i in range(n - 1):
            maxPos = max(maxPos, i + nums[i])
            if i == end:
                steps += 1
                end = maxPos
        return steps
```

### Java 代码实现
```java
class Solution {
    public int jump(int[] nums) {
        int n = nums.length;
        int steps = 0;
        int end = 0;
        int maxPos = 0;
        for (int i = 0; i < n - 1; i++) {
            maxPos = Math.max(maxPos, i + nums[i]);
            if (i == end) {
                steps++;
                end = maxPos;
            }
        }
        return steps;
    }
}
```

### C 代码实现
```c
int jump(int* nums, int numsSize) {
    int steps = 0;
    int end = 0;
    int maxPos = 0;
    for (int i = 0; i < numsSize - 1; i++) {
        if (i + nums[i] > maxPos) maxPos = i + nums[i];
        if (i == end) {
            steps++;
            end = maxPos;
        }
    }
    return steps;
}
```

---