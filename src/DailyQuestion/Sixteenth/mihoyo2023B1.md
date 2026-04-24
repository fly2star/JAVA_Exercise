# 米小游想知道，自己最少需要花费多少 mp？

## 输入描述

- 第一行输入两个正整数 `n` 和 `x`，分别代表深渊法师的数量和“高天之歌”的技能 mp 消耗。
- 第二行输入 `n` 个正整数 `a_i`，分别代表每个深渊法师的护盾值。
- 第三行输入一个长度为 `n` 的字符串，第 `i` 个字符为：
  - `'F'` 代表该深渊法师是火系，
  - `'I'` 代表冰系，
  - `'W'` 代表水系。

**约束条件：**
- $1 \le n \le 200000$
- $1 \le x, a_i \le 10^9$

## 输出描述

一个正整数，代表米小游需要消耗的最少 mp。

## 示例 1

### 输入
4 5
6 8 2 3
FFWI


### 输出
14


### 说明
- 对第一个深渊法师使用 6 次重击，消耗 $6$ mp。
- 对第二个和第三个深渊法师使用“高天之歌”，消耗 $5$ mp。
- 对第四个深渊法师使用 3 次重击，消耗 $3$ mp。  
  总消耗：$6 + 5 + 3 = 14$ mp。



### 代码
```java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) return;
        
        int n = scanner.nextInt();
        long x = scanner.nextLong(); // x 可能会很大
        
        long[] a = new long[n];
        long totalCost = 0; // 不使用技能的基础总消耗
        for (int i = 0; i < n; i++) {
            a[i] = scanner.nextLong();
            totalCost += a[i];
        }
        
        String s = scanner.next();
        
        // 动态规划求最大节省值
        // prev2 代表 dp[i-2], prev1 代表 dp[i-1]
        long prev2 = 0;
        long prev1 = 0;
        
        for (int i = 1; i < n; i++) {
            // 默认不配对
            long current = prev1;
            
            // 如果相邻法师属性不同，尝试配对
            if (s.charAt(i) != s.charAt(i - 1)) {
                // 计算通过配对能省下多少 mp
                long save = a[i] + a[i - 1] - x;
                
                // 只有配对能省 mp 时才考虑转移
                if (save > 0) {
                    current = Math.max(current, prev2 + save);
                }
            }
            
            // 状态滚动
            prev2 = prev1;
            prev1 = current;
        }
        
        // 最终结果 = 原始总消耗 - 最大能节省的消耗
        System.out.println(totalCost - prev1);
        scanner.close();
    }
}
```