# 23. 合并 K 个升序链表

**难度: 困难**

## 题目描述
给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表。

---

## 示例说明
### 示例 1：
输入：lists = [[1,4,5],[1,3,4],[2,6]]  
输出：[1,1,2,3,4,4,5,6]  
解释：链表数组如下：
[
  1->4->5,
  1->3->4,
  2->6
]
将它们合并到一个有序链表中得到：1->1->2->3->4->4->5->6

### 示例 2：
输入：lists = []  
输出：[]

### 示例 3：
输入：lists = [[]]  
输出：[]

---

## 提示：
- k = lists.length
- 0 <= k <= 10^4
- 0 <= lists[i].length <= 500
- -10^4 <= lists[i][j] <= 10^4
- lists[i] 按升序排列
- lists[i].length 的总和不超过 10^4

---

## 解题思路

### 核心思想
合并 k 个有序链表，可以采用多种方法：
1. **顺序合并**：逐一合并两个链表，时间复杂度 O(k²n)
2. **分治合并**：两两配对合并，类似归并排序，时间复杂度 O(kn log k)
3. **优先队列（最小堆）**：每次取所有链表头部的最小节点，时间复杂度 O(kn log k)

### 关键观察
- 每个链表都是有序的，所以每次合并时只需要比较各个链表的头节点
- 使用最小堆可以高效地获取当前最小的节点
- 分治合并的空间复杂度更低（O(1) 额外空间）

### 算法步骤

#### 方法一：顺序合并
1. 初始化结果链表为 null
2. 遍历所有链表，逐个与结果链表合并（复用合并两个有序链表的函数）
3. 返回最终结果

#### 方法二：分治合并
1. 将 k 个链表两两配对合并
2. 重复上述过程，直到只剩一个链表
3. 类似归并排序的思想

#### 方法三：优先队列（最小堆）
1. 创建最小堆，将所有链表的头节点加入堆中（非空链表）
2. 创建虚拟头节点，用于构建结果链表
3. 当堆不为空时：
   - 弹出最小节点
   - 将该节点接到结果链表后面
   - 如果该节点有下一个节点，将其加入堆中
4. 返回虚拟头节点的下一个节点

---

## 代码参考(python, java, c)

### Python 代码实现
```python
# Definition for singly-linked list.
class ListNode:
    def __init__(self, val=0, next=None):
        self.val = val
        self.next = next

# 方法一：顺序合并
class Solution:
    def mergeKLists(self, lists: List[Optional[ListNode]]) -> Optional[ListNode]:
        def mergeTwoLists(l1, l2):
            dummy = ListNode(0)
            curr = dummy
            
            while l1 and l2:
                if l1.val < l2.val:
                    curr.next = l1
                    l1 = l1.next
                else:
                    curr.next = l2
                    l2 = l2.next
                curr = curr.next
            
            curr.next = l1 if l1 else l2
            return dummy.next
        
        if not lists:
            return None
        
        result = None
        for lst in lists:
            result = mergeTwoLists(result, lst)
        
        return result

# 方法二：分治合并
class Solution:
    def mergeKLists(self, lists: List[Optional[ListNode]]) -> Optional[ListNode]:
        def mergeTwoLists(l1, l2):
            dummy = ListNode(0)
            curr = dummy
            
            while l1 and l2:
                if l1.val < l2.val:
                    curr.next = l1
                    l1 = l1.next
                else:
                    curr.next = l2
                    l2 = l2.next
                curr = curr.next
            
            curr.next = l1 if l1 else l2
            return dummy.next
        
        def merge(lists, left, right):
            if left == right:
                return lists[left]
            if left > right:
                return None
            
            mid = (left + right) // 2
            l1 = merge(lists, left, mid)
            l2 = merge(lists, mid + 1, right)
            return mergeTwoLists(l1, l2)
        
        if not lists:
            return None
        return merge(lists, 0, len(lists) - 1)

# 方法三：优先队列
class Solution:
    def mergeKLists(self, lists: List[Optional[ListNode]]) -> Optional[ListNode]:
        import heapq
        
        # 创建虚拟头节点
        dummy = ListNode(0)
        curr = dummy
        
        # 最小堆，存储 (节点值, 索引, 节点)
        heap = []
        for i, node in enumerate(lists):
            if node:
                heapq.heappush(heap, (node.val, i, node))
        
        while heap:
            val, i, node = heapq.heappop(heap)
            curr.next = node
            curr = curr.next
            
            if node.next:
                heapq.heappush(heap, (node.next.val, i, node.next))
        
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

// 方法一：顺序合并
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        ListNode result = null;
        for (ListNode list : lists) {
            result = mergeTwoLists(result, list);
        }
        return result;
    }
    
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        
        curr.next = l1 != null ? l1 : l2;
        return dummy.next;
    }
}

// 方法二：分治合并
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        return merge(lists, 0, lists.length - 1);
    }
    
    private ListNode merge(ListNode[] lists, int left, int right) {
        if (left == right) {
            return lists[left];
        }
        if (left > right) {
            return null;
        }
        
        int mid = left + (right - left) / 2;
        ListNode l1 = merge(lists, left, mid);
        ListNode l2 = merge(lists, mid + 1, right);
        return mergeTwoLists(l1, l2);
    }
    
    private ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        
        curr.next = l1 != null ? l1 : l2;
        return dummy.next;
    }
}

// 方法三：优先队列
class Solution {
    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }
        
        // 创建最小堆，比较节点值
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a, b) -> a.val - b.val);
        
        // 将所有链表的头节点加入堆
        for (ListNode node : lists) {
            if (node != null) {
                heap.offer(node);
            }
        }
        
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        
        while (!heap.isEmpty()) {
            ListNode node = heap.poll();
            curr.next = node;
            curr = curr.next;
            
            if (node.next != null) {
                heap.offer(node.next);
            }
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

// 合并两个有序链表
struct ListNode* mergeTwoLists(struct ListNode* l1, struct ListNode* l2) {
    struct ListNode dummy;
    struct ListNode* curr = &dummy;
    dummy.next = NULL;
    
    while (l1 && l2) {
        if (l1->val < l2->val) {
            curr->next = l1;
            l1 = l1->next;
        } else {
            curr->next = l2;
            l2 = l2->next;
        }
        curr = curr->next;
    }
    
    curr->next = l1 ? l1 : l2;
    return dummy.next;
}

// 方法一：顺序合并
struct ListNode* mergeKLists(struct ListNode** lists, int listsSize) {
    if (listsSize == 0) {
        return NULL;
    }
    
    struct ListNode* result = NULL;
    for (int i = 0; i < listsSize; i++) {
        result = mergeTwoLists(result, lists[i]);
    }
    return result;
}

// 方法二：分治合并
struct ListNode* merge(struct ListNode** lists, int left, int right) {
    if (left == right) {
        return lists[left];
    }
    if (left > right) {
        return NULL;
    }
    
    int mid = left + (right - left) / 2;
    struct ListNode* l1 = merge(lists, left, mid);
    struct ListNode* l2 = merge(lists, mid + 1, right);
    return mergeTwoLists(l1, l2);
}

struct ListNode* mergeKLists(struct ListNode** lists, int listsSize) {
    if (listsSize == 0) {
        return NULL;
    }
    return merge(lists, 0, listsSize - 1);
}
```

---