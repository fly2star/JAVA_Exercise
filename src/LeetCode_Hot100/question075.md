# 2. 两数相加

**难度: 中等**

## 题目描述
给你两个 **非空** 的链表，表示两个非负的整数。它们每位数字都是按照 **逆序** 的方式存储的，并且每个节点只能存储一位数字。

请你将两个数相加，并以相同形式返回一个表示和的链表。

你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

---

## 示例说明
### 示例 1：

![addtwonumber1](../../readFile/image/addtwonumber1.jpg)

输入：l1 = [2,4,3], l2 = [5,6,4]  
输出：[7,0,8]  
解释：342 + 465 = 807.

### 示例 2：
输入：l1 = [0], l2 = [0]  
输出：[0]

### 示例 3：
输入：l1 = [9,9,9,9,9,9,9], l2 = [9,9,9,9]  
输出：[8,9,9,9,0,0,0,1]  
解释：9999999 + 9999 = 10009998，逆序表示为 [8,9,9,9,0,0,0,1]

---

## 提示：
- 每个链表中的节点数在范围 [1, 100] 内
- 0 ≤ Node.val ≤ 9
- 题目数据保证列表表示的数字不含前导零

---

## 解题思路

### 核心思想
模拟**竖式加法**的过程，从最低位（链表头部）开始相加，处理进位。由于链表是逆序存储的，正好符合我们从低位到高位的计算顺序。

### 关键观察
- 两个链表长度可能不同，需要处理较长的链表剩余部分
- 进位可能一直存在到最高位之后，需要在最后额外添加一个节点
- 使用虚拟头节点可以简化链表的构建过程

### 算法步骤
1. 创建虚拟头节点 `dummy` 和当前指针 `curr` 指向 `dummy`
2. 初始化进位 `carry = 0`
3. 当 `l1 != null` 或 `l2 != null` 或 `carry != 0` 时循环：
   - 获取两个链表当前节点的值，如果链表已空则取 0
   - 计算和：`sum = val1 + val2 + carry`
   - 更新进位：`carry = sum / 10`
   - 创建新节点：`new ListNode(sum % 10)`，接到 `curr.next`
   - 移动指针：`curr = curr.next`，`l1 = l1?.next`，`l2 = l2?.next`
4. 返回 `dummy.next`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

class Solution:
    def addTwoNumbers(self, l1: Optional[ListNode], l2: Optional[ListNode]) -> Optional[ListNode]:
        dummy = ListNode(0)
        curr = dummy
        carry = 0
        
        while l1 or l2 or carry:
            # 获取当前位的值
            val1 = l1.val if l1 else 0
            val2 = l2.val if l2 else 0
            
            # 计算和与进位
            total = val1 + val2 + carry
            carry = total // 10
            curr.next = ListNode(total % 10)
            
            # 移动指针
            curr = curr.next
            if l1:
                l1 = l1.next
            if l2:
                l2 = l2.next
        
        return dummy.next
```

### Java 代码实现
```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        int carry = 0;
        
        while (l1 != null || l2 != null || carry != 0) {
            // 获取当前位的值
            int val1 = l1 != null ? l1.val : 0;
            int val2 = l2 != null ? l2.val : 0;
            
            // 计算和与进位
            int total = val1 + val2 + carry;
            carry = total / 10;
            curr.next = new ListNode(total % 10);
            
            // 移动指针
            curr = curr.next;
            if (l1 != null) l1 = l1.next;
            if (l2 != null) l2 = l2.next;
        }
        
        return dummy.next;
    }
}
```

### C 代码实现
```c
/**
 * Definition for singly-linked list.
 * struct ListNode {
 *     int val;
 *     struct ListNode *next;
 * };
 */

struct ListNode* addTwoNumbers(struct ListNode* l1, struct ListNode* l2) {
    struct ListNode* dummy = (struct ListNode*)malloc(sizeof(struct ListNode));
    dummy->val = 0;
    dummy->next = NULL;
    struct ListNode* curr = dummy;
    int carry = 0;
    
    while (l1 != NULL || l2 != NULL || carry != 0) {
        // 获取当前位的值
        int val1 = l1 != NULL ? l1->val : 0;
        int val2 = l2 != NULL ? l2->val : 0;
        
        // 计算和与进位
        int total = val1 + val2 + carry;
        carry = total / 10;
        
        // 创建新节点
        struct ListNode* newNode = (struct ListNode*)malloc(sizeof(struct ListNode));
        newNode->val = total % 10;
        newNode->next = NULL;
        curr->next = newNode;
        curr = newNode;
        
        // 移动指针
        if (l1 != NULL) l1 = l1->next;
        if (l2 != NULL) l2 = l2->next;
    }
    
    struct ListNode* result = dummy->next;
    free(dummy);
    return result;
}
```

---