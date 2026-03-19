# 1404. 将二进制表示减到 1 的步骤数

**难度: 中等**

## 题目描述
给你一个以二进制形式表示的数字 `s`。请你返回按下述规则将其减少到 1 所需要的步骤数：
- 如果当前数字为偶数，则将其除以 2。
- 如果当前数字为奇数，则将其加上 1。

题目保证总是可以按上述规则将测试用例变为 1。

---

## 示例说明
### 示例 1：
输入：s = "1101"  
输出：6  
解释："1101" 表示十进制数 13。  
Step 1) 13 是奇数，加 1 得到 14  
Step 2) 14 是偶数，除 2 得到 7  
Step 3) 7 是奇数，加 1 得到 8  
Step 4) 8 是偶数，除 2 得到 4  
Step 5) 4 是偶数，除 2 得到 2  
Step 6) 2 是偶数，除 2 得到 1

### 示例 2：
输入：s = "10"  
输出：1  
解释："10" 表示十进制数 2。  
Step 1) 2 是偶数，除 2 得到 1

### 示例 3：
输入：s = "1"  
输出：0

---

## 提示：
- 1 <= s.length <= 500
- s 由字符 '0' 或 '1' 组成
- s[0] == '1'

---

## 解题思路

### 核心思想
直接在二进制字符串上模拟操作，避免将整个字符串转换为整数（可能溢出）。关键是要理解二进制下的除2和加1操作：

- **除以2（偶数）**：相当于去掉二进制表示的最后一位（右移一位）
- **加1（奇数）**：二进制末尾是1，加1会导致进位，类似于二进制加法

### 关键观察
- 从低位到高位处理更直观
- 遇到0（偶数）：直接移除最后一位（步骤+1）
- 遇到1（奇数）：需要加1，这会引起进位，处理完进位后相当于移除了最后一位（步骤+1），但进位可能影响前面的位
- 最后剩下"1"时，步骤数为0

### 算法步骤
1. 初始化步骤数 steps = 0，从字符串最后一位向前遍历
2. 当字符串长度大于1时：
   - 如果最后一位是'0'（偶数）：直接移除最后一位，steps++
   - 如果最后一位是'1'（奇数）：需要加1，这会导致进位
     - 从当前位置向前找第一个0，将其变为1，中间经过的1都变成0
     - 如果所有位都是1（如"111"），需要在最前面加一个1
     - 每处理一次奇数操作，steps++
3. 当字符串变为"1"时，返回steps

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def numSteps(self, s: str) -> int:
        steps = 0
        carry = 0
        # 从最后一位向前处理，直到第一位
        for i in range(len(s) - 1, 0, -1):
            # 当前位加上进位后的值
            digit = int(s[i]) + carry
            if digit == 1:  # 奇数
                steps += 2  # 加1和除以2两步
                carry = 1   # 加1会产生进位
            elif digit == 2:  # 原来是1且有进位
                steps += 1   # 只需要除以2
                carry = 1    # 进位保持
            else:  # digit == 0
                steps += 1   # 只需要除以2
                carry = 0    # 无进位
        
        # 处理最高位
        return steps + carry
```

### Java 代码实现
```java
class Solution {
    public int numSteps(String s) {
        int steps = 0;
        int carry = 0;
        
        for (int i = s.length() - 1; i > 0; i--) {
            int digit = (s.charAt(i) - '0') + carry;
            if (digit == 1) {
                steps += 2;  // 加1和除以2
                carry = 1;
            } else if (digit == 2) {
                steps += 1;  // 只需除以2
                carry = 1;
            } else {
                steps += 1;  // 只需除以2
                carry = 0;
            }
        }
        
        // 处理最高位
        return steps + carry;
    }
}
```

### C 代码实现
```c
int numSteps(char* s) {
    int steps = 0;
    int carry = 0;
    int len = strlen(s);
    
    for (int i = len - 1; i > 0; i--) {
        int digit = (s[i] - '0') + carry;
        if (digit == 1) {
            steps += 2;
            carry = 1;
        } else if (digit == 2) {
            steps += 1;
            carry = 1;
        } else {
            steps += 1;
            carry = 0;
        }
    }
    
    return steps + carry;
}
```

---