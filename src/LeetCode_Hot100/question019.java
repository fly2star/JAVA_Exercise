package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-23-21:07
 **/
/*
142. 环形链表 II
给定一个链表的头节点  head ，返回链表开始入环的第一个节点。 如果链表无环，则返回 null。

如果链表中有某个节点，可以通过连续跟踪 next 指针再次到达，则链表中存在环。
为了表示给定链表中的环，评测系统内部使用整数 pos 来表示链表尾连接到链表中的位置（索引从 0 开始）。
如果 pos 是 -1，则在该链表中没有环。注意：pos 不作为参数进行传递，仅仅是为了标识链表的实际情况。

不允许修改 链表。

*/
public class question019 {
    public static void main(String[] args) {

    }

    public static ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        // Floyd 算法
        // 假设 从 head 到入口 = a; 从入口到相遇点 = c; 环长 = b
        // 慢指针总路程 = a + c; 快指针总路程 = a + c + k*b
        // 且快指针是慢指针的 2 倍，则 a + c + k*b = 2 * (a + c)
        // ∴ a + c = k*b
        // ∴ a = k*b - c = (k-1)*b + (b-c)
        // 而 b-c 就是从相遇点回到入口的距离
        // 结论: 从 head 走 a 步 = 从相遇点走 (b - c) 步, 都到达入口
        while (fast != null && fast.next != null) {
            slow = slow.next;        // 慢指针走 1 步
            fast = fast.next.next;   // 快指针走 2 步

            if (slow == fast) {      // 相遇 → 有环
                break;
            }
        }
        if (fast == null || fast.next == null) {
            return null;
        }
        
        ListNode meet = head;
        while (meet != slow) {
            meet = meet.next;
            slow = slow.next;
        }
        return meet;


    }
}
