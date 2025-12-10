package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-27-17:26
 **/
/*
160. 相交链表
给你两个单链表的头节点 headA 和 headB ，请你找出并返回两个单链表相交的起始节点。如果两个链表没有交点，返回 null 。
*/
public class Question001 {
    public static void main(String[] args) {

    }
    public static ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        ListNode p1 = headA;
        ListNode p2 = headB;
        while (p1 != p2) {
            p1 = p1 == null ? headB : p1.next;
            p2 = p2 == null ? headA : p2.next;
        }
        return p1;
    }
}

