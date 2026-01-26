# 3314. 构造最小位运算数组 I

**难度：简单**

## 题目描述

给你一个长度为 `n` 的**质数数组** `nums`。你的任务是返回一个长度为 `n` 的数组 `ans`，对于每个下标 `i`，以下条件均成立：

- `ans[i] OR (ans[i] + 1) == nums[i]`

除此之外，你需要 **最小化结果数组里每一个 `ans[i]`**。

如果没法找到符合条件的 `ans[i]`，那么 `ans[i] = -1`。

> ✅ 质数指的是一个大于 1 的自然数，且它只有 1 和自己两个因数。

---

## 示例说明

**示例 1：**  
输入：`nums = [3, 5, 7]`  
输出：`[1, 2, 3]`  
解释：
- `1 OR 2 = 3` → 满足条件，且 `1` 是最小可能值。
- `2 OR 3 = 3 ≠ 5` ❌，但 `2 OR 3 = 3` 不对，重新计算：
    - 实际上：`2 OR 3 = 3`（二进制：`10 | 11 = 11`）→ 错误。
    - 正确：`4 OR 5 = 5`？不满足。
    - 正确解法：`2 OR 3 = 3` 不行，尝试 `4 OR 5 = 5` → `4 | 5 = 5` ✔️，所以 `ans[1] = 4`？

等等，我们来仔细分析。

实际上，`2 OR 3 = 3`（因为 `10 | 11 = 11`），但我们需要的是 `ans[i] OR (ans[i]+1) == nums[i]`。

例如：
- 对于 `nums[i] = 3`：
    - 尝试 `ans[i] = 1`：`1 OR 2 = 3` ✔️ → 最小值是 1。
- 对于 `nums[i] = 5`：
    - `2 OR 3 = 3` ❌
    - `3 OR 4 = 7` ❌
    - `4 OR 5 = 5` ✔️ → 所以 `ans[i] = 4`
- 对于 `nums[i] = 7`：
    - `3 OR 4 = 7` ✔️ → `ans[i] = 3`

所以输出应为 `[1, 4, 3]`？

但注意：我们要找的是**最小的** `ans[i]`。

继续验证：
- `3 OR 4 = 7` ✔️ → `ans[i] = 3`
- `2 OR 3 = 3` → 不能得到 5
- `4 OR 5 = 5` → 可行，且 `4` 是最小吗？

是否存在更小的？
- `0 OR 1 = 1` ❌
- `1 OR 2 = 3` ❌
- `2 OR 3 = 3` ❌
- `3 OR 4 = 7` ❌
- `4 OR 5 = 5` ✔️

所以 `ans[1] = 4` 是唯一可行解。

因此输出：`[1, 4, 3]`

---

## 解题思路

### 核心观察：
要使 `x OR (x+1) == target`，必须满足什么条件？

考虑二进制：
- `x` 和 `x+1` 在最低位总是不同（`x` 的末位是 0，`x+1` 是 1，或反之）
- 所以 `x OR (x+1)` 的结果是将 `x` 的最低位及其之后的所有位都置为 1

例如：
- `x = 1` → `1 | 2 = 3` → 二进制 `01 | 10 = 11`
- `x = 2` → `2 | 3 = 3` → `10 | 11 = 11`
- `x = 4` → `4 | 5 = 5` → `100 | 101 = 101`
- `x = 6` → `6 | 7 = 7` → `110 | 111 = 111`

👉 发现规律：`x OR (x+1)` 的结果是 **将 x 的二进制中从最低位开始连续的 0 和 1 全部置为 1**，直到遇到第一个 0。

换句话说，`x OR (x+1)` 等于 `(x & (x+1)) == 0` 时的最大值，但这不是直接帮助。

### 更好的方法：
枚举所有可能的 `x`，从小到大尝试，直到找到满足 `x OR (x+1) == nums[i]` 的最小 `x`。

由于 `x OR (x+1)` 至少为 `x+1`，而 `nums[i]` 是质数，我们可以限制搜索范围。

#### 观察：
- 若 `x OR (x+1) == n`，则 `x <= n`
- 因为 `x OR (x+1) >= x+1`，所以 `x <= n-1`

所以我们只需在 `[0, n-1]` 范围内查找即可。

---

## 算法步骤

1. 对每个 `nums[i]`，遍历 `x` 从 0 到 `nums[i]-1`。
2. 检查是否 `x | (x+1) == nums[i]`。
3. 第一个满足条件的 `x` 即为答案。
4. 如果没有找到，返回 `-1`。

---

## 复杂度分析

- **时间复杂度**：O(n × max(nums))，最坏情况下每个数都要遍历到其自身。
- **空间复杂度**：O(1)，仅使用常量额外空间。

---

## 参考代码（Python、Java、C）

### Python 实现

```python
def constructArray(nums):
    ans = []
    for num in nums:
        found = False
        for x in range(num):
            if (x | (x + 1)) == num:
                ans.append(x)
                found = True
                break
        if not found:
            ans.append(-1)
    return ans
```

### Java 实现

```Java
public int[] constructArray(int[] nums) {
    int[] ans = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
        int num = nums[i];
        boolean found = false;
        for (int x = 0; x < num; x++) {
            if ((x | (x + 1)) == num) {
                ans[i] = x;
                found = true;
                break;
            }
        }
        if (!found) {
            ans[i] = -1;
        }
    }
    return ans;
}
```

### C 实现

```C
public int[] constructArray(int[] nums) {
    int[] ans = new int[nums.length];
    for (int i = 0; i < nums.length; i++) {
        int num = nums[i];
        boolean found = false;
        for (int x = 0; x < num; x++) {
            if ((x | (x + 1)) == num) {
                ans[i] = x;
                found = true;
                break;
            }
        }
        if (!found) {
            ans[i] = -1;
        }
    }
    return ans;
}
```

