package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-23-17:26
 **/
/*
146. LRU 缓存
请你设计并实现一个满足  LRU (最近最少使用) 缓存 约束的数据结构。
实现 LRUCache 类：
    LRUCache(int capacity) 以 正整数 作为容量 capacity 初始化 LRU 缓存
    int get(int key) 如果关键字 key 存在于缓存中，则返回关键字的值，否则返回 -1 。
    void put(int key, int value) 如果关键字 key 已经存在，则变更其数据值 value ；
        如果不存在，则向缓存中插入该组 key-value 。
        如果插入操作导致关键字数量超过 capacity ，则应该 逐出 最久未使用的关键字。
函数 get 和 put 必须以 O(1) 的平均时间复杂度运行。
*/
public class question018 {
    public static void main(String[] args) {

    }
}


class LRUCache {
    private int capacity;   // 缓存最大容量
    private Map<Integer, Node> cache;   // 哈希表: key -> Node
    private Node head;      // 链表虚拟头结点(dummy head)
    private Node tail;      // 链表虚拟尾结点(dummy tail)
    private int size;       // 缓存已使用的容量

    // 使用虚拟头尾节点, 避免处理空链表或单节点时的边界条件
    // 真实的头部节点为 head.next=最近使用, 尾部节点 为tail.prev=最久未使用
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.head = new Node(0, 0);
        this.tail = new Node(0, 0);
        this.head.next = this.tail;
        this.tail.prev = this.head;
        this.size = 0;
    }

    public int get(int key) {
        Node node = cache.get(key);     // (Integer, Node), 通过key 获取 Node
        if (node == null) return -1;    // 未找到，返回 -1
        moveToHead(node);               // 找到，代表使用，将 node 移动到链表头部
        return node.value;
    }

    public void put(int key, int value) {
        Node node = cache.get(key);
        if (node == null) {
            // 如果不存在，则创建新节点
            node = new Node(key, value);
            cache.put(key, node);
            addToHead(node);
            size++;
            if (size > capacity) {
                Node tail = removeTail();   // 删除链表尾部节点
                cache.remove(tail.key);
                size--;
            }
        } else {
            // 如果存在，则更新节点值，并移动到链表头部
            node.value = value;
            moveToHead(node);
        }

    }

    // 辅助方法
    private void addToHead(Node node) {
        // 把 node 插入到 head 和 head.next 之间
        node.prev = head;
        node.next = head.next;
        head.next.prev = node;
        head.next = node;
    }

    private void removeNode(Node node) {
        // 把 node 从链表中移除,(不释放内存, 只是断开链接)
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private void moveToHead(Node node) {
        // 组合操作: 先删除 node, 再插入到头部 -> 实现"提升为最近使用"
        removeNode(node);
        addToHead(node);
    }

    private Node removeTail() {
        // 返回被删除的节点, 以便从 cache 中移除对应的 key
        Node node = tail.prev;
        removeNode(node);
        return node;
    }
}

class Node {
    int key;
    int value;
    Node prev;
    Node next;

    public Node(int key, int value) {
        this.key = key;
        this.value = value;
    }
}