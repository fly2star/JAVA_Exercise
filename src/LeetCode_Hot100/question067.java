package LeetCode_Hot100;


/*
19. 删除链表的倒数第 N 个结点

给你一个链表，删除链表的倒数第 `n` 个结点，并且返回链表的头结点。

## 提示：
    -- 链表中结点的数目为 sz
    -- 1 ≤ sz ≤ 30
    -- 0 ≤ Node.val ≤ 100
    -- 1 ≤ n ≤ sz
*/
public class question067 {
    public static void main(String[] args) {
        ListNode p1 = new ListNode(1);
        ListNode p2 = new ListNode(2);
        ListNode p3 = new ListNode(3);
        ListNode p4 = new ListNode(4);
        ListNode p5 = new ListNode(5);
        p1.next = p2;
        p2.next = p3;
        p3.next = p4;
        p4.next = p5;
        p5.next = null;

        question067 sl67 = new question067();
        ListNode node = sl67.removeNthFromEnd(p1, 2);

        ListNode p = node;
        while (p != null) {
            System.out.print(p.val + " ");
            p = p.next;
        }
    }

    // 方法1: 快慢指针找到前一个节点    hand
    public ListNode removeNthFromEnd(ListNode head, int n) {

        // 用快慢指针找到倒数第 n+1 个节点
        ListNode fast = head;
        ListNode slow = head;
        int k = n;
        while (k!=0) {
            fast = fast.next;
            k--;
        }
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        
        slow.next = slow.next.next;

        return head;
    }

    // 方法2: AI
    public ListNode removeNthFromEnd2(ListNode head, int n) {
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
