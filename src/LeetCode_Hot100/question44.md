# 297. 二叉树的序列化与反序列化

**难度: 困难**

## 题目描述
序列化是将一个数据结构或者对象转换为连续的比特位的操作，进而可以将转换后的数据存储在一个文件或者内存中，
同时也可以通过网络传输到另一个计算机环境，采取相反方式重构得到原数据。

请设计一个算法来实现二叉树的序列化与反序列化。这里不限定你的序列/反序列化算法执行逻辑，
你只需要保证一个二叉树可以被序列化为一个字符串并且将这个字符串反序列化为原始的树结构。

**提示**: 输入输出格式与 LeetCode 目前使用的方案一致。你并非必须采用这种方式，你也可以采用其他的方法解决这个问题。

---

## 示例说明
### 示例 1：

输入: root = [1,2,3,null,null,4,5]

![serdeser](../../readFile/image/serdeser.jpg)

输出: [1,2,3,null,null,4,5]

- 序列化：将二叉树转换为字符串表示
- 反序列化：从字符串重建相同的二叉树

---

### 示例 2：
```
输入: root = []
输出: []
```

---

### 示例 3：
```
输入: root = [1]
输出: [1]
```

---

### 示例 4：
```
输入: root = [1,2]
    1
   /
  2
输出: [1,2]
```

---

## 提示：
- 树中结点数在范围 [0, 10⁴] 内
- -1000 ≤ Node.val ≤ 1000

---

## 解题思路

### 核心思想
设计一个可以唯一表示二叉树的字符串格式，并能从该字符串重建原始二叉树。

### 关键观察
1. 需要一种方式表示空节点（如使用 "null" 或 "#"）
2. 需要一种遍历顺序来序列化二叉树，常见的有：
   - 前序遍历（根左右）
   - 层次遍历（BFS）
3. 序列化和反序列化需要使用相同的遍历顺序

### 算法步骤（前序遍历方法）
**序列化（serialize）:**
1. 使用前序遍历：根节点 -> 左子树 -> 右子树
2. 遇到空节点时，用特殊标记（如 "null"）表示
3. 节点值之间用分隔符（如 ","）连接

**反序列化（deserialize）:**
1. 将字符串按分隔符分割成数组
2. 使用索引指针按前序遍历顺序重建二叉树
3. 遇到 "null" 时，返回空节点

### 算法步骤（层次遍历方法）
**序列化（serialize）:**
1. 使用队列进行层次遍历
2. 将节点值（或 "null"）加入结果列表
3. 即使遇到空节点，也继续处理其左右子节点（都记为 "null"）

**反序列化（deserialize）:**
1. 将字符串分割成数组
2. 使用队列重建二叉树
3. 按层次顺序为节点添加左右子节点

---

## 代码参考(python, java, c)

### Python 代码实现

```python
# Definition for a binary tree node.
class TreeNode(object):
    def __init__(self, x):
        self.val = x
        self.left = None
        self.right = None

class Codec:
    # ========== 方法1：前序遍历 ==========
    def serialize_preorder(self, root):
        """Encodes a tree to a single string using preorder traversal."""
        def dfs(node):
            if not node:
                return ["null"]
            return [str(node.val)] + dfs(node.left) + dfs(node.right)
        
        return ",".join(dfs(root))
    
    def deserialize_preorder(self, data):
        """Decodes your encoded data to tree using preorder traversal."""
        if not data:
            return None
        
        nodes = data.split(",")
        self.idx = 0
        
        def dfs():
            if self.idx >= len(nodes) or nodes[self.idx] == "null":
                self.idx += 1
                return None
            
            val = int(nodes[self.idx])
            self.idx += 1
            node = TreeNode(val)
            node.left = dfs()
            node.right = dfs()
            return node
        
        return dfs()
    
    # ========== 方法2：层次遍历（BFS）==========
    def serialize(self, root):
        """Encodes a tree to a single string using BFS."""
        if not root:
            return ""
        
        result = []
        queue = [root]
        
        while queue:
            node = queue.pop(0)
            if node:
                result.append(str(node.val))
                queue.append(node.left)
                queue.append(node.right)
            else:
                result.append("null")
        
        # 去除末尾多余的 "null"
        while result and result[-1] == "null":
            result.pop()
        
        return ",".join(result)
    
    def deserialize(self, data):
        """Decodes your encoded data to tree using BFS."""
        if not data:
            return None
        
        values = data.split(",")
        root = TreeNode(int(values[0]))
        queue = [root]
        i = 1
        
        while queue and i < len(values):
            node = queue.pop(0)
            
            # 左子节点
            if i < len(values) and values[i] != "null":
                left_node = TreeNode(int(values[i]))
                node.left = left_node
                queue.append(left_node)
            i += 1
            
            # 右子节点
            if i < len(values) and values[i] != "null":
                right_node = TreeNode(int(values[i]))
                node.right = right_node
                queue.append(right_node)
            i += 1
        
        return root

# ========== 测试代码 ==========
if __name__ == "__main__":
    # 测试示例1
    codec = Codec()
    
    # 构建树: [1,2,3,null,null,4,5]
    root = TreeNode(1)
    root.left = TreeNode(2)
    root.right = TreeNode(3)
    root.right.left = TreeNode(4)
    root.right.right = TreeNode(5)
    
    print("原始树结构:")
    print("    1")
    print("   / \\")
    print("  2   3")
    print("     / \\")
    print("    4   5")
    
    # 序列化
    serialized = codec.serialize(root)
    print(f"序列化结果: {serialized}")
    
    # 反序列化
    deserialized = codec.deserialize(serialized)
    print("反序列化成功!" if deserialized else "反序列化失败!")
    
    # 再次序列化验证
    re_serialized = codec.serialize(deserialized)
    print(f"再次序列化: {re_serialized}")
    print(f"两次序列化是否相同: {serialized == re_serialized}")
```

---

### Java 代码实现

```java
import java.util.*;

/**
 * Definition for a binary tree node.
 */
class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

public class Codec {
    // ========== 方法1：前序遍历 ==========
    public String serializePreorder(TreeNode root) {
        StringBuilder sb = new StringBuilder();
        serializeHelper(root, sb);
        return sb.toString();
    }
    
    private void serializeHelper(TreeNode node, StringBuilder sb) {
        if (node == null) {
            sb.append("null,");
            return;
        }
        sb.append(node.val).append(",");
        serializeHelper(node.left, sb);
        serializeHelper(node.right, sb);
    }
    
    public TreeNode deserializePreorder(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        String[] nodes = data.split(",");
        Queue<String> queue = new LinkedList<>(Arrays.asList(nodes));
        return deserializeHelper(queue);
    }
    
    private TreeNode deserializeHelper(Queue<String> queue) {
        String val = queue.poll();
        if (val == null || val.equals("null")) {
            return null;
        }
        TreeNode node = new TreeNode(Integer.parseInt(val));
        node.left = deserializeHelper(queue);
        node.right = deserializeHelper(queue);
        return node;
    }
    
    // ========== 方法2：层次遍历（BFS）==========
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node == null) {
                sb.append("null,");
            } else {
                sb.append(node.val).append(",");
                queue.offer(node.left);
                queue.offer(node.right);
            }
        }
        
        // 去除末尾多余的 "null,"
        String result = sb.toString();
        while (result.endsWith("null,")) {
            result = result.substring(0, result.length() - 5);
        }
        if (result.endsWith(",")) {
            result = result.substring(0, result.length() - 1);
        }
        
        return result;
    }
    
    public TreeNode deserialize(String data) {
        if (data == null || data.isEmpty()) {
            return null;
        }
        
        String[] values = data.split(",");
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        
        int i = 1;
        while (!queue.isEmpty() && i < values.length) {
            TreeNode node = queue.poll();
            
            // 左子节点
            if (i < values.length && !values[i].equals("null")) {
                TreeNode leftNode = new TreeNode(Integer.parseInt(values[i]));
                node.left = leftNode;
                queue.offer(leftNode);
            }
            i++;
            
            // 右子节点
            if (i < values.length && !values[i].equals("null")) {
                TreeNode rightNode = new TreeNode(Integer.parseInt(values[i]));
                node.right = rightNode;
                queue.offer(rightNode);
            }
            i++;
        }
        
        return root;
    }
    
    // ========== 测试代码 ==========
    public static void main(String[] args) {
        Codec codec = new Codec();
        
        // 构建树: [1,2,3,null,null,4,5]
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.right.left = new TreeNode(4);
        root.right.right = new TreeNode(5);
        
        System.out.println("原始树结构:");
        System.out.println("    1");
        System.out.println("   / \\");
        System.out.println("  2   3");
        System.out.println("     / \\");
        System.out.println("    4   5");
        
        // 序列化
        String serialized = codec.serialize(root);
        System.out.println("序列化结果: " + serialized);
        
        // 反序列化
        TreeNode deserialized = codec.deserialize(serialized);
        System.out.println("反序列化成功!");
        
        // 再次序列化验证
        String reSerialized = codec.serialize(deserialized);
        System.out.println("再次序列化: " + reSerialized);
        System.out.println("两次序列化是否相同: " + serialized.equals(reSerialized));
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// 二叉树节点定义
struct TreeNode {
    int val;
    struct TreeNode *left;
    struct TreeNode *right;
};

// 字符串构建器
typedef struct {
    char* data;
    int capacity;
    int length;
} StringBuilder;

StringBuilder* createStringBuilder() {
    StringBuilder* sb = (StringBuilder*)malloc(sizeof(StringBuilder));
    sb->capacity = 16;
    sb->length = 0;
    sb->data = (char*)malloc(sb->capacity * sizeof(char));
    sb->data[0] = '\0';
    return sb;
}

void appendString(StringBuilder* sb, const char* str) {
    int strLen = strlen(str);
    if (sb->length + strLen + 1 >= sb->capacity) {
        sb->capacity *= 2;
        sb->data = (char*)realloc(sb->data, sb->capacity * sizeof(char));
    }
    strcat(sb->data, str);
    sb->length += strLen;
}

void freeStringBuilder(StringBuilder* sb) {
    free(sb->data);
    free(sb);
}

// ========== 序列化（层次遍历）==========
char* serialize(struct TreeNode* root) {
    if (root == NULL) {
        char* result = (char*)malloc(1 * sizeof(char));
        result[0] = '\0';
        return result;
    }
    
    // 使用队列进行层次遍历
    struct TreeNode** queue = (struct TreeNode**)malloc(10000 * sizeof(struct TreeNode*));
    int front = 0, rear = 0;
    
    StringBuilder* sb = createStringBuilder();
    
    queue[rear++] = root;
    
    while (front < rear) {
        struct TreeNode* node = queue[front++];
        
        if (node == NULL) {
            appendString(sb, "null,");
        } else {
            // 将节点值转换为字符串
            char valStr[20];
            sprintf(valStr, "%d,", node->val);
            appendString(sb, valStr);
            
            queue[rear++] = node->left;
            queue[rear++] = node->right;
        }
    }
    
    // 去除末尾多余的 "null,"
    char* result = sb->data;
    int len = strlen(result);
    while (len > 0 && strcmp(result + len - 5, "null,") == 0) {
        result[len - 5] = '\0';
        len -= 5;
    }
    if (len > 0 && result[len - 1] == ',') {
        result[len - 1] = '\0';
    }
    
    free(queue);
    free(sb);  // 注意：这里只释放StringBuilder结构体，不释放data
    
    return result;
}

// ========== 反序列化 ==========
struct TreeNode* deserialize(char* data) {
    if (data == NULL || strlen(data) == 0) {
        return NULL;
    }
    
    // 分割字符串
    char* values[10000];
    int count = 0;
    
    char* token = strtok(data, ",");
    while (token != NULL) {
        values[count] = token;
        count++;
        token = strtok(NULL, ",");
    }
    
    if (count == 0) {
        return NULL;
    }
    
    // 创建根节点
    struct TreeNode* root = (struct TreeNode*)malloc(sizeof(struct TreeNode));
    root->val = atoi(values[0]);
    root->left = NULL;
    root->right = NULL;
    
    // 使用队列重建二叉树
    struct TreeNode** queue = (struct TreeNode**)malloc(10000 * sizeof(struct TreeNode*));
    int front = 0, rear = 0;
    queue[rear++] = root;
    
    int i = 1;
    while (front < rear && i < count) {
        struct TreeNode* node = queue[front++];
        
        // 左子节点
        if (i < count && strcmp(values[i], "null") != 0) {
            struct TreeNode* leftNode = (struct TreeNode*)malloc(sizeof(struct TreeNode));
            leftNode->val = atoi(values[i]);
            leftNode->left = NULL;
            leftNode->right = NULL;
            node->left = leftNode;
            queue[rear++] = leftNode;
        }
        i++;
        
        // 右子节点
        if (i < count && strcmp(values[i], "null") != 0) {
            struct TreeNode* rightNode = (struct TreeNode*)malloc(sizeof(struct TreeNode));
            rightNode->val = atoi(values[i]);
            rightNode->left = NULL;
            rightNode->right = NULL;
            node->right = rightNode;
            queue[rear++] = rightNode;
        }
        i++;
    }
    
    free(queue);
    return root;
}

// ========== 辅助函数 ==========
// 创建新节点
struct TreeNode* createNode(int val) {
    struct TreeNode* node = (struct TreeNode*)malloc(sizeof(struct TreeNode));
    node->val = val;
    node->left = NULL;
    node->right = NULL;
    return node;
}

// 释放二叉树
void freeTree(struct TreeNode* root) {
    if (root == NULL) return;
    freeTree(root->left);
    freeTree(root->right);
    free(root);
}

// ========== 测试代码 ==========
int main() {
    // 构建树: [1,2,3,null,null,4,5]
    struct TreeNode* root = createNode(1);
    root->left = createNode(2);
    root->right = createNode(3);
    root->right->left = createNode(4);
    root->right->right = createNode(5);
    
    printf("原始树结构:\n");
    printf("    1\n");
    printf("   / \\\n");
    printf("  2   3\n");
    printf("     / \\\n");
    printf("    4   5\n");
    
    // 序列化
    char* serialized = serialize(root);
    printf("序列化结果: %s\n", serialized);
    
    // 反序列化
    struct TreeNode* deserialized = deserialize(serialized);
    printf("反序列化成功!\n");
    
    // 再次序列化验证
    char* reSerialized = serialize(deserialized);
    printf("再次序列化: %s\n", reSerialized);
    printf("两次序列化是否相同: %s\n", strcmp(serialized, reSerialized) == 0 ? "是" : "否");
    
    // 释放内存
    freeTree(root);
    freeTree(deserialized);
    free(serialized);
    free(reSerialized);
    
    return 0;
}
```

---

### 复杂度分析
- **时间复杂度：**
  - 序列化：O(n)，需要遍历所有节点
  - 反序列化：O(n)，需要处理所有节点值
- **空间复杂度：**
  - 序列化：O(n)，存储序列化字符串
  - 反序列化：O(n)，存储重建的二叉树和队列

### 算法特点
1. **前序遍历方法**：
   - 实现简单，递归自然
   - 序列化字符串较短
   - 需要记录空节点

2. **层次遍历方法**：
   - 符合LeetCode的二叉树表示格式
   - 更直观，易于调试
   - 可能需要处理末尾多余的空节点
