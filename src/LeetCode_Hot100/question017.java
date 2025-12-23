package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-16-17:15
 **/
public class question017 {
    public static void main(String[] args) {
        ListNode head = new ListNode(4, new ListNode(2, new ListNode(1, new ListNode(3, null))));
        ListNode cur = head;
        while (cur != null) {
            System.out.print(cur.val + " ");
            cur = cur.next;
        }
        System.out.println();
        ListNode newHead = sortList(head);
        ListNode cur2 = newHead;
        while (cur2 != null) {
            System.out.print(cur2.val + " ");
            cur2 = cur2.next;
        }
    }

    public static ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;
        ListNode newHead = head;
        ListNode p1 = head.next;
        newHead.next = null;
        while (p1 != null) {
            ListNode next = p1.next;
            if (p1.val < newHead.val) {
                p1.next = newHead;
                newHead = p1;
            } else {
                ListNode cur = newHead;
                while (cur.next != null && cur.next.val < p1.val) {
                    cur = cur.next;
                }
                p1.next = cur.next;
                cur.next = p1;
            }
            p1 = next;
        }
        return newHead;
    }

    public ListNode sortList2(ListNode head) {
        return sortList(head, null);
    }

    public ListNode sortList(ListNode head, ListNode tail) {
        if (head == null) {
            return head;
        }
        if (head.next == tail) {
            head.next = null;
            return head;
        }
        ListNode slow = head, fast = head;
        while (fast != tail) {
            slow = slow.next;
            fast = fast.next;
            if (fast != tail) {
                fast = fast.next;
            }
        }
        ListNode mid = slow;
        ListNode list1 = sortList(head, mid);
        ListNode list2 = sortList(mid, tail);
        ListNode sorted = merge(list1, list2);
        return sorted;
    }

    public ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummyHead = new ListNode(0);
        ListNode temp = dummyHead, temp1 = head1, temp2 = head2;
        while (temp1 != null && temp2 != null) {
            if (temp1.val <= temp2.val) {
                temp.next = temp1;
                temp1 = temp1.next;
            } else {
                temp.next = temp2;
                temp2 = temp2.next;
            }
            temp = temp.next;
        }
        if (temp1 != null) {
            temp.next = temp1;
        } else if (temp2 != null) {
            temp.next = temp2;
        }
        return dummyHead.next;
    }

}
