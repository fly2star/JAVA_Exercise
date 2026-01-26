# 461. 汉明距离

**难度：简单**

## 题目描述

两个整数之间的 **汉明距离** 指的是这两个数字对应二进制位不同的位置的数目。

给你两个整数 `x` 和 `y`，计算并返回它们之间的汉明距离。

---

## 示例说明

**示例 1：**  
输入：`x = 1`, `y = 4`  
输出：`2`  
解释：
- `1` 的二进制：`0001`
- `4` 的二进制：`0100`
- 不同的位置：第 2 位和第 4 位（从右往左，从 1 开始）→ 共 2 个不同位 → 汉明距离为 2。

**示例 2：**  
输入：`x = 3`, `y = 1`  
输出：`1`  
解释：
- `3` 的二进制：`0011`
- `1` 的二进制：`0001`
- 不同的位置：第 2 位 → 距离为 1。

---

## 解题思路

### 核心思想：
- 汉明距离 = 两个数异或（XOR）结果中 **1 的个数**。
- 因为异或运算：相同为 0，不同为 1。
- 所以 `x ^ y` 的二进制中，1 的个数就是不同位的数目。

### 步骤：
1. 计算 `x ^ y`。
2. 统计该结果中二进制表示中 `1` 的个数。

---

## 方法一：逐位检查（Bit Counting）

遍历每一位，判断是否为 1。

### Python 实现
```python
def hammingDistance(x, y):
    xor = x ^ y
    count = 0
    while xor:
        count += xor & 1
        xor >>= 1
    return count
```

### Java 实现
```Java
public int hammingDistance(int x, int y) {
    int xor = x ^ y;
    int count = 0;
    while (xor != 0) {
        count += xor & 1;
        xor >>= 1;
    }
    return count;
}
```

### C 实现
```C
int hammingDistance(int x, int y) {
    int xor = x ^ y;
    int count = 0;
    while (xor) {
        count += xor & 1;
        xor >>= 1;
    }
    return count;
}
```

