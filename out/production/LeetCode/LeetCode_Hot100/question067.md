# 19. 删除链表的倒数第 N 个结点

**难度: 中等**

## 题目描述
给你一个链表，删除链表的倒数第 `n` 个结点，并且返回链表的头结点。

---

## 示例说明
### 示例 1：

![remove_ex1](../../readFile/image/remove_ex1.jpg)

输入：head = [1,2,3,4,5], n = 2  
输出：[1,2,3,5]  
解释：删除倒数第 2 个结点（值为 4）后，链表变为 1->2->3->5

### 示例 2：
输入：head = [1], n = 1  
输出：[]  
解释：链表只有一个结点，删除后为空

### 示例 3：
输入：head = [1,2], n = 1  
输出：[1]  
解释：删除倒数第 1 个结点（值为 2）后，链表变为 [1]

---

## 提示：
- 链表中结点的数目为 sz
- 1 ≤ sz ≤ 30
- 0 ≤ Node.val ≤ 100
- 1 ≤ n ≤ sz

进阶：你能尝试使用一趟扫描实现吗？

---

## 解题思路

### 核心思想
使用**双指针（快慢指针）**技巧，只需一趟扫描即可找到倒数第 n 个结点。快指针先移动 n 步，然后快慢指针同时移动，当快指针到达链表末尾时，慢指针恰好指向待删除结点的前一个结点。

### 关键观察
- 要删除倒数第 n 个结点，需要找到它的前一个结点
- 使用虚拟头节点可以简化头结点被删除的情况
- 快慢指针的间距为 n，当快指针到达 null 时，慢指针就在待删除结点的前一个位置

### 算法步骤
1. 创建虚拟头节点 `dummy`，指向原链表的头节点
2. 初始化快指针 `fast` 和慢指针 `slow` 都指向 `dummy`
3. 快指针先向前移动 n+1 步（这样当快指针到达末尾时，慢指针刚好在待删除结点的前一个位置）
4. 然后快慢指针同时向前移动，直到快指针到达 null
5. 此时慢指针的下一个结点就是待删除的结点，执行删除操作：`slow.next = slow.next.next`
6. 返回 `dummy.next`

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
    def removeNthFromEnd(self, head: Optional[ListNode], n: int) -> Optional[ListNode]:
        # 创建虚拟头节点
        dummy = ListNode(0)
        dummy.next = head
        
        # 初始化快慢指针
        fast = dummy
        slow = dummy
        
        # 快指针先移动 n+1 步
        for _ in range(n + 1):
            fast = fast.next
        
        # 快慢指针同时移动，直到快指针到达末尾
        while fast:
            fast = fast.next
            slow = slow.next
        
        # 删除倒数第 n 个节点
        slow.next = slow.next.next
        
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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // 创建虚拟头节点
        ListNode dummy = new ListNode(0);
        dummy.next = head;
        
        // 初始化快慢指针
        ListNode fast = dummy;
        ListNode slow = dummy;
        
        // 快指针先移动 n+1 步
        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }
        
        // 快慢指针同时移动，直到快指针到达末尾
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        // 删除倒数第 n 个节点
        slow.next = slow.next.next;
        
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

struct ListNode* removeNthFromEnd(struct ListNode* head, int n) {
    // 创建虚拟头节点
    struct ListNode* dummy = (struct ListNode*)malloc(sizeof(struct ListNode));
    dummy->val = 0;
    dummy->next = head;
    
    // 初始化快慢指针
    struct ListNode* fast = dummy;
    struct ListNode* slow = dummy;
    
    // 快指针先移动 n+1 步
    for (int i = 0; i <= n; i++) {
        fast = fast->next;
    }
    
    // 快慢指针同时移动，直到快指针到达末尾
    while (fast != NULL) {
        fast = fast->next;
        slow = slow->next;
    }
    
    // 删除倒数第 n 个节点
    struct ListNode* temp = slow->next;
    slow->next = temp->next;
    free(temp);
    
    struct ListNode* result = dummy->next;
    free(dummy);
    return result;
}
```

---