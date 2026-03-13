package LeetCode_Hot100;

/*
2. 两数相加

给你两个 **非空** 的链表，表示两个非负的整数。它们每位数字都是按照 **逆序** 的方式存储的，并且每个节点只能存储一位数字。

请你将两个数相加，并以相同形式返回一个表示和的链表。

你可以假设除了数字 0 之外，这两个数都不会以 0 开头。

## 提示：
    -- 每个链表中的节点数在范围 [1, 100] 内
    -- 0 ≤ Node.val ≤ 9
    -- 题目数据保证列表表示的数字不含前导零
*/
public class question075 {
    public static void main(String[] args) {
        ListNode l1 = new ListNode(2, new ListNode(4, new ListNode(3)));
        ListNode l2 = new ListNode(5, new ListNode(6, new ListNode(4)));

        question075 sl75 = new question075();
        ListNode resNode = sl75.addTwoNumbers(l1, l2);

        ListNode p = resNode;
        while (p != null) {
            System.out.print(p.val + " ");
            p = p.next;
        }

        System.out.println(resNode);

    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode cur = dummy;
        int carry = 0;      // 进位

        // 只要 l1、l2 没走完，或者还有余下的进位，就继续
        while (l1 != null || l2 != null || carry != 0) {
            // 如果链表走完了，取 0
            int x = (l1 != null) ? l1.val : 0;
            int y = (l2 != null) ? l2.val : 0;

            // 计算总和及新的进位
            int sum = x + y + carry;
            carry = sum / 10;

            // 创建新节点存放个位数
            cur.next = new ListNode(sum % 10);
            cur = cur.next;

            // 指针继续
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }

        }
        return dummy.next;
    }
}
