# 67. 二进制求和

**难度: 简单**

## 题目描述
给你两个二进制字符串 `a` 和 `b`，以二进制字符串的形式返回它们的和。

---

## 示例说明
### 示例 1：
输入：a = "11", b = "1"  
输出："100"

### 示例 2：
输入：a = "1010", b = "1011"  
输出："10101"

---

## 提示：
- 1 ≤ a.length, b.length ≤ 10^4  
- `a` 和 `b` 仅由字符 `'0'` 或 `'1'` 组成  
- 字符串如果不是 `"0"`，就不含前导零

---

## 解题思路

### 核心思想
模拟竖式加法，从两个字符串的最低位（末尾）开始逐位相加，并处理进位。

### 关键观察
- 二进制加法规则：0+0=0，0+1=1，1+1=0（进位1）。
- 两个字符串长度可能不同，需要将较短的字符串高位视为0。
- 最终结果需要反转，因为我们是从低位开始计算。

### 算法步骤
1. 初始化两个指针 `i = len(a)-1`，`j = len(b)-1`，进位 `carry = 0`，结果列表 `res = []`。
2. 当 `i >= 0` 或 `j >= 0` 或 `carry != 0` 时循环：
   - 获取当前位的数字：`x = int(a[i]) if i >= 0 else 0`，`y = int(b[j]) if j >= 0 else 0`。
   - 计算和：`total = x + y + carry`。
   - 当前位结果：`total % 2`，进位：`carry = total // 2`。
   - 将当前位结果转换为字符添加到 `res`。
   - 移动指针：`i--`，`j--`。
3. 将 `res` 反转并连接成字符串。

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def addBinary(self, a: str, b: str) -> str:
        i, j = len(a) - 1, len(b) - 1
        carry = 0
        res = []
        
        while i >= 0 or j >= 0 or carry:
            x = int(a[i]) if i >= 0 else 0
            y = int(b[j]) if j >= 0 else 0
            total = x + y + carry
            res.append(str(total % 2))
            carry = total // 2
            i -= 1
            j -= 1
        
        return ''.join(res[::-1])
```

### Java 代码实现
```java
class Solution {
    public String addBinary(String a, String b) {
        StringBuilder res = new StringBuilder();
        int i = a.length() - 1;
        int j = b.length() - 1;
        int carry = 0;
        
        while (i >= 0 || j >= 0 || carry != 0) {
            int x = i >= 0 ? a.charAt(i) - '0' : 0;
            int y = j >= 0 ? b.charAt(j) - '0' : 0;
            int total = x + y + carry;
            res.append(total % 2);
            carry = total / 2;
            i--;
            j--;
        }
        
        return res.reverse().toString();
    }
}
```

### C 代码实现
```c
#include <stdlib.h>
#include <string.h>

char* addBinary(char* a, char* b) {
    int len_a = strlen(a);
    int len_b = strlen(b);
    int max_len = (len_a > len_b ? len_a : len_b) + 2; // +2 for possible carry and '\0'
    char* res = (char*)malloc(max_len * sizeof(char));
    int idx = 0;
    int i = len_a - 1;
    int j = len_b - 1;
    int carry = 0;
    
    while (i >= 0 || j >= 0 || carry) {
        int x = i >= 0 ? a[i] - '0' : 0;
        int y = j >= 0 ? b[j] - '0' : 0;
        int total = x + y + carry;
        res[idx++] = (total % 2) + '0';
        carry = total / 2;
        i--;
        j--;
    }
    
    // 反转结果
    for (int k = 0; k < idx / 2; k++) {
        char temp = res[k];
        res[k] = res[idx - 1 - k];
        res[idx - 1 - k] = temp;
    }
    
    res[idx] = '\0';
    return res;
}
```

---