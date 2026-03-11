package LeetCode_Hot100;

/*
21. 合并两个有序链表

将两个升序链表合并为一个新的 **升序** 链表并返回。新链表是通过拼接给定的两个链表的所有节点组成的。
*/
public class question0065 {
    public static void main(String[] args) {
        ListNode lt0 = new ListNode(1, new ListNode(4, new ListNode(5, null)));
        ListNode lt1 = new ListNode(1, new ListNode(3, new ListNode(4, null)));
        
        question0065 sl65 = new question0065();
        ListNode mergeList = sl65.mergeTwoList(lt0, lt1);

        ListNode point = mergeList;
        while (point != null) {
            System.out.print(point.val + " ");
            point = point.next;
        }
    }

    public ListNode mergeTwoList(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
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
