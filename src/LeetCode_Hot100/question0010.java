package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-03-19:15
 **/
/*
206. 反转链表
给你单链表的头节点 head ，请你反转链表，并返回反转后的链表。
*/
public class question0010 {
    public static void main(String[] args) {
        ListNode head = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, null))));
        ListNode inverse = reverseList(head);
        while (inverse != null) {
            System.out.print(inverse.val + " ");
            inverse = inverse.next;
        }
    }

    public static ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode next;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;

        }
        return pre;
    }
}
