package LeetCode_Hot100;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-01-20:33
 **/
/*
208. 实现 Trie (前缀树)
Trie（发音类似 "try"）或者说 前缀树 是一种树形数据结构，用于高效地存储和检索字符串数据集中的键。
这一数据结构有相当多的应用情景，例如自动补全和拼写检查。
请你实现 Trie 类：
    Trie() 初始化前缀树对象。
    void insert(String word) 向前缀树中插入字符串 word 。
    boolean search(String word) 如果字符串 word 在前缀树中，返回 true（即，在检索之前已经插入）；否则，返回 false 。
    boolean startsWith(String prefix) 如果之前已经插入的字符串 word 的前缀之一为 prefix ，返回 true ；否则，返回 false 。
*/
public class question08 {
    public static void main(String[] args) {
        Tire obj = new Tire();
        obj.insert("apple");
        System.out.println(obj.search("apple"));
        System.out.println(obj.startsWith("app"));
    }

}

class Tire {
    private class TireNode {
        TireNode[] children = new TireNode[26];
        boolean isEnd = false;
    }

    private TireNode root = new TireNode();

    // 构造函数
    public Tire() {

    }

    public void insert(String word) {
        if (word == null) return;
        TireNode cur = root;
        for (char c : word.toCharArray()) {
            // 将字符变为索引
            int index = c - 'a';
            if (cur.children[index] == null) {
                cur.children[index] = new TireNode();
            }
            cur = cur.children[index];
        }
        cur.isEnd = true;

    }

    public boolean search(String word) {
        TireNode node = findNode(word);
        return node != null && node.isEnd;
    }

    public boolean startsWith(String prefix) {
        return findNode(prefix) != null;
    }

    // 辅助: 找到 word 对应的最后一个节点，如果存在的话
    private TireNode findNode(String word) {
        if (word == null) return null;
        TireNode cur = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (cur.children[index] == null) {
                return null;
            }
            cur = cur.children[index];
        }
        return cur;
    }

}
