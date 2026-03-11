package LeetCode_Hot100;

import java.util.PriorityQueue;

/*
23. 合并 K 个升序链表

给你一个链表数组，每个链表都已经按升序排列。请你将所有链表合并到一个升序链表中，返回合并后的链表。
*/
public class question63 {
    public static void main(String[] args) {
        ListNode[] lists = new ListNode[3];
        ListNode lt0 = new ListNode(1, new ListNode(4, new ListNode(5, null)));
        ListNode lt1 = new ListNode(1, new ListNode(3, new ListNode(4, null)));
        ListNode lt2 = new ListNode(2, new ListNode(6, null));
        lists[0] = lt0;
        lists[1] = lt1;
        lists[2] = lt2;
        
    }

    // 方法1: 分治合并
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
        int mid = left + (right - left) / 2;
        // 分: 左边合好, 右边合好
        ListNode l1 = merge(lists, left, mid);
        ListNode l2 = merge(lists, mid + 1, right);
        // 治: 合并了两个有序链表
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

    // 方法2: 顺序合并
    public ListNode mergeKList2(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        ListNode res = null;
        for (ListNode list : lists) {
            res = mergeTwoLists2(res, list);
        }
        return res;
        
    }

    private ListNode mergeTwoLists2(ListNode l1, ListNode l2) {
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


    // 方法3: 优先队列
    public ListNode mergeKLists3(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        // 创建最小堆, 比较节点值
        PriorityQueue<ListNode> heap = new PriorityQueue<>((a,b) -> a.val - b.val);

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
