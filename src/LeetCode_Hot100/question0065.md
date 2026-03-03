# 21. 合并两个有序链表

**难度: 简单**

## 题目描述
将两个升序链表合并为一个新的 **升序** 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。

---

## 示例说明
### 示例 1：

![merge_ex1](../../readFile/image/merge_ex1.jpg)

输入：l1 = [1,2,4], l2 = [1,3,4]  
输出：[1,1,2,3,4,4]

### 示例 2：
输入：l1 = [], l2 = []  
输出：[]

### 示例 3：
输入：l1 = [], l2 = [0]  
输出：[0]

---

## 提示：
- 两个链表的节点数目范围是 [0, 50]
- -100 <= Node.val <= 100
- l1 和 l2 均按 **非递减顺序** 排列

---

## 解题思路

### 核心思想
使用**双指针法**遍历两个链表，每次比较两个指针所指节点的值，将较小的节点接入结果链表，然后移动对应指针。可以使用迭代或递归两种方式实现。

### 关键观察
- 两个链表都是有序的，所以可以像归并排序中的合并步骤一样处理
- 使用虚拟头节点（dummy node）可以简化边界情况的处理
- 当一个链表遍历完后，直接将另一个链表的剩余部分接上即可

### 算法步骤

#### 方法一：迭代法
1. 创建虚拟头节点 `dummy` 和当前指针 `curr` 指向 `dummy`
2. 当两个链表都不为空时：
   - 比较 `l1.val` 和 `l2.val`
   - 将较小的节点接到 `curr.next`，并移动对应链表的指针
   - 移动 `curr` 到下一个位置
3. 将剩余的非空链表接到 `curr.next`
4. 返回 `dummy.next`

#### 方法二：递归法
1. 递归终止条件：如果其中一个链表为空，返回另一个链表
2. 比较两个头节点的值：
   - 如果 `l1.val < l2.val`，则 `l1.next = mergeTwoLists(l1.next, l2)`，返回 `l1`
   - 否则，`l2.next = mergeTwoLists(l1, l2.next)`，返回 `l2`

---

## 代码参考(python, java, c)

### Python 代码实现
```python
# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

# 方法一：迭代法
class Solution:
    def mergeTwoLists(self, list1: Optional[ListNode], list2: Optional[ListNode]) -> Optional[ListNode]:
        dummy = ListNode(0)
        curr = dummy
        
        while list1 and list2:
            if list1.val < list2.val:
                curr.next = list1
                list1 = list1.next
            else:
                curr.next = list2
                list2 = list2.next
            curr = curr.next
        
        # 连接剩余部分
        curr.next = list1 if list1 else list2
        
        return dummy.next

# 方法二：递归法
class Solution:
    def mergeTwoLists(self, list1: Optional[ListNode], list2: Optional[ListNode]) -> Optional[ListNode]:
        # 递归终止条件
        if not list1:
            return list2
        if not list2:
            return list1
        
        # 递归合并
        if list1.val < list2.val:
            list1.next = self.mergeTwoLists(list1.next, list2)
            return list1
        else:
            list2.next = self.mergeTwoLists(list1, list2.next)
            return list2
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

// 方法一：迭代法
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }
        
        curr.next = list1 != null ? list1 : list2;
        return dummy.next;
    }
}

// 方法二：递归法
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) return list2;
        if (list2 == null) return list1;
        
        if (list1.val < list2.val) {
            list1.next = mergeTwoLists(list1.next, list2);
            return list1;
        } else {
            list2.next = mergeTwoLists(list1, list2.next);
            return list2;
        }
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

// 方法一：迭代法
struct ListNode* mergeTwoLists(struct ListNode* list1, struct ListNode* list2) {
    struct ListNode dummy;
    struct ListNode* curr = &dummy;
    dummy.next = NULL;
    
    while (list1 && list2) {
        if (list1->val < list2->val) {
            curr->next = list1;
            list1 = list1->next;
        } else {
            curr->next = list2;
            list2 = list2->next;
        }
        curr = curr->next;
    }
    
    curr->next = list1 ? list1 : list2;
    return dummy.next;
}

// 方法二：递归法
struct ListNode* mergeTwoLists(struct ListNode* list1, struct ListNode* list2) {
    if (!list1) return list2;
    if (!list2) return list1;
    
    if (list1->val < list2->val) {
        list1->next = mergeTwoLists(list1->next, list2);
        return list1;
    } else {
        list2->next = mergeTwoLists(list1, list2->next);
        return list2;
    }
}
```

---