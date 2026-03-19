# 55. 跳跃游戏

**难度: 中等**

## 题目描述
给你一个非负整数数组 `nums`，你最初位于数组的第一个下标。数组中的每个元素代表你在该位置可以跳跃的最大长度。

判断你是否能够到达最后一个下标，如果可以，返回 `true`；否则，返回 `false`。

---

## 示例说明
### 示例 1：
输入：nums = [2,3,1,1,4]  
输出：true  
解释：可以先跳 1 步，从下标 0 到达下标 1，然后再从下标 1 跳 3 步到达最后一个下标。

### 示例 2：
输入：nums = [3,2,1,0,4]  
输出：false  
解释：无论怎样，总会到达下标为 3 的位置。但该下标的最大跳跃长度是 0，所以永远不可能到达最后一个下标。

---

## 提示：
- 1 ≤ nums.length ≤ 10^4
- 0 ≤ nums[i] ≤ 10^5

---

## 解题思路

### 核心思想
使用**贪心算法**，维护一个变量 `maxReach` 表示当前能够到达的最远位置。遍历数组，如果当前位置超过了 `maxReach`，说明无法到达，返回 false；否则更新 `maxReach` 为当前位置能到达的最远位置。如果 `maxReach` 已经大于等于最后一个下标，返回 true。

### 关键观察
- 不需要关心具体怎么跳，只需要关心能跳到的最远位置
- 如果当前位置 i 在可达范围内（i ≤ maxReach），则更新 maxReach = max(maxReach, i + nums[i])
- 如果 maxReach 已经覆盖最后一个位置，可以提前返回 true

### 算法步骤
1. 初始化 `maxReach = 0`
2. 遍历数组的每个位置 i：
   - 如果 i > maxReach，说明无法到达当前位置，返回 false
   - 更新 `maxReach = max(maxReach, i + nums[i])`
   - 如果 `maxReach >= len(nums) - 1`，返回 true
3. 遍历结束后，返回 true（实际上不会执行到这里）

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def canJump(self, nums: List[int]) -> bool:
        n = len(nums)
        max_reach = 0
        
        for i in range(n):
            if i > max_reach:
                return False
            max_reach = max(max_reach, i + nums[i])
            if max_reach >= n - 1:
                return True
        
        return True
```

### Java 代码实现
```java
class Solution {
    public boolean canJump(int[] nums) {
        int n = nums.length;
        int maxReach = 0;
        
        for (int i = 0; i < n; i++) {
            if (i > maxReach) {
                return false;
            }
            maxReach = Math.max(maxReach, i + nums[i]);
            if (maxReach >= n - 1) {
                return true;
            }
        }
        
        return true;
    }
}
```

### C 代码实现
```c
bool canJump(int* nums, int numsSize) {
    int maxReach = 0;
    
    for (int i = 0; i < numsSize; i++) {
        if (i > maxReach) {
            return false;
        }
        
        int reach = i + nums[i];
        if (reach > maxReach) {
            maxReach = reach;
        }
        
        if (maxReach >= numsSize - 1) {
            return true;
        }
    }
    
    return true;
}
```

---