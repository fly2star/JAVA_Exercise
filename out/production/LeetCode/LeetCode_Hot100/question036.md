# 347. 前 K 个高频元素

**难度: 中等**

## 题目描述
给你一个整数数组 `nums` 和一个整数 `k`，请你返回其中出现频率前 `k` 高的元素。你可以按任意顺序返回答案。

---

## 示例说明
### 示例 1：
**输入：** nums = [1,1,1,2,2,3], k = 2  
**输出：** [1,2]  
**解释：** 
- 元素 1 出现 3 次
- 元素 2 出现 2 次
- 元素 3 出现 1 次
- 前 2 个高频元素是 [1, 2]

---

### 示例 2：
**输入：** nums = [1], k = 1  
**输出：** [1]  
**解释：**
- 元素 1 出现 1 次
- 前 1 个高频元素是 [1]

---

### 示例 3：
**输入：** nums = [1,2,1,2,1,2,3,1,3,2], k = 2  
**输出：** [1,2]  
**解释：**
- 元素 1 出现 4 次
- 元素 2 出现 4 次
- 元素 3 出现 2 次
- 前 2 个高频元素是 [1, 2]

---

## 提示：
- 1 ≤ nums.length ≤ 10^5
- -10^4 ≤ nums[i] ≤ 10^4
- k 的取值范围是 [1, 数组中不相同的元素的个数]
- 题目数据保证答案唯一，换句话说，数组中前 k 个高频元素的集合是唯一的

---

## 解题思路

### 核心思想
统计每个元素的出现频率，然后找出频率最高的 k 个元素。这是一个典型的 Top K 问题。

### 关键观察
1. 需要先统计每个数字出现的频率，可以使用哈希表
2. 然后需要从频率统计中找出前 k 个最大值
3. 有多种方法可以解决：
   - 最小堆（优先队列）：维护大小为 k 的最小堆
   - 桶排序：由于频率最多为 n，可以使用桶来统计
   - 快速选择算法

### 算法步骤（最小堆方法）
1. 使用哈希表统计每个数字的出现频率
2. 维护一个大小为 k 的最小堆（优先队列）
3. 遍历频率哈希表：
   - 将元素和频率放入堆中
   - 如果堆大小超过 k，弹出频率最小的元素
4. 堆中剩下的就是前 k 个高频元素

---

## 代码参考(python, java, c)

### Python 代码实现

```python
from typing import List
import heapq
from collections import Counter

class Solution:
    def topKFrequent(self, nums: List[int], k: int) -> List[int]:
        # 统计频率
        freq = Counter(nums)
        
        # 使用最小堆
        heap = []
        for num, count in freq.items():
            heapq.heappush(heap, (count, num))
            # 如果堆的大小超过k，弹出频率最小的元素
            if len(heap) > k:
                heapq.heappop(heap)
        
        # 提取结果
        result = [num for count, num in heap]
        return result
    
    # 方法二：使用桶排序
    def topKFrequent_bucket(self, nums: List[int], k: int) -> List[int]:
        # 统计频率
        freq = Counter(nums)
        
        # 创建桶，索引表示频率，值是该频率的所有元素
        n = len(nums)
        bucket = [[] for _ in range(n + 1)]
        
        for num, count in freq.items():
            bucket[count].append(num)
        
        # 从高频率到低频率收集结果
        result = []
        for i in range(n, 0, -1):
            if bucket[i]:
                result.extend(bucket[i])
            if len(result) >= k:
                break
        
        return result[:k]
```

---

### Java 代码实现

```java
import java.util.*;

class Solution {
    // 方法一：最小堆
    public int[] topKFrequent(int[] nums, int k) {
        // 统计频率
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        // 使用最小堆（按频率排序）
        PriorityQueue<Map.Entry<Integer, Integer>> heap = 
            new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            heap.offer(entry);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        
        // 提取结果
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = heap.poll().getKey();
        }
        return result;
    }
    
    // 方法二：桶排序
    public int[] topKFrequentBucket(int[] nums, int k) {
        // 统计频率
        Map<Integer, Integer> freq = new HashMap<>();
        for (int num : nums) {
            freq.put(num, freq.getOrDefault(num, 0) + 1);
        }
        
        // 创建桶
        List<Integer>[] bucket = new List[nums.length + 1];
        for (int i = 0; i < bucket.length; i++) {
            bucket[i] = new ArrayList<>();
        }
        
        for (Map.Entry<Integer, Integer> entry : freq.entrySet()) {
            bucket[entry.getValue()].add(entry.getKey());
        }
        
        // 从高频率到低频率收集结果
        List<Integer> resultList = new ArrayList<>();
        for (int i = bucket.length - 1; i >= 0 && resultList.size() < k; i--) {
            if (!bucket[i].isEmpty()) {
                resultList.addAll(bucket[i]);
            }
        }
        
        // 转换为数组
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = resultList.get(i);
        }
        return result;
    }
}
```

---

### C 代码实现

```c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    int value;
    int count;
} Element;

// 哈希表节点
typedef struct HashNode {
    int key;
    int count;
    struct HashNode* next;
} HashNode;

// 哈希表
typedef struct {
    HashNode** table;
    int size;
} HashMap;

// 创建哈希表
HashMap* createHashMap(int size) {
    HashMap* map = (HashMap*)malloc(sizeof(HashMap));
    map->size = size;
    map->table = (HashNode**)calloc(size, sizeof(HashNode*));
    return map;
}

// 哈希函数
int hash(int key, int size) {
    return abs(key) % size;
}

// 插入或更新哈希表
void put(HashMap* map, int key) {
    int index = hash(key, map->size);
    HashNode* node = map->table[index];
    
    // 查找是否已存在
    while (node != NULL) {
        if (node->key == key) {
            node->count++;
            return;
        }
        node = node->next;
    }
    
    // 不存在，创建新节点
    HashNode* newNode = (HashNode*)malloc(sizeof(HashNode));
    newNode->key = key;
    newNode->count = 1;
    newNode->next = map->table[index];
    map->table[index] = newNode;
}

// 获取所有元素及其频率
Element* getAllElements(HashMap* map, int* total) {
    // 先统计元素数量
    int count = 0;
    for (int i = 0; i < map->size; i++) {
        HashNode* node = map->table[i];
        while (node != NULL) {
            count++;
            node = node->next;
        }
    }
    
    // 创建数组
    Element* elements = (Element*)malloc(count * sizeof(Element));
    int index = 0;
    for (int i = 0; i < map->size; i++) {
        HashNode* node = map->table[i];
        while (node != NULL) {
            elements[index].value = node->key;
            elements[index].count = node->count;
            index++;
            node = node->next;
        }
    }
    
    *total = count;
    return elements;
}

// 快速选择分区函数
int partition(Element* arr, int left, int right) {
    int pivot = arr[right].count;
    int i = left - 1;
    
    for (int j = left; j < right; j++) {
        if (arr[j].count > pivot) {  // 降序排列
            i++;
            Element temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }
    
    Element temp = arr[i + 1];
    arr[i + 1] = arr[right];
    arr[right] = temp;
    
    return i + 1;
}

// 快速选择算法
void quickSelect(Element* arr, int left, int right, int k) {
    if (left < right) {
        int pivotIndex = partition(arr, left, right);
        
        if (pivotIndex == k - 1) {
            return;
        } else if (pivotIndex > k - 1) {
            quickSelect(arr, left, pivotIndex - 1, k);
        } else {
            quickSelect(arr, pivotIndex + 1, right, k);
        }
    }
}

// 主函数
int* topKFrequent(int* nums, int numsSize, int k, int* returnSize) {
    // 创建哈希表统计频率
    HashMap* map = createHashMap(1009);  // 质数作为哈希表大小
    
    for (int i = 0; i < numsSize; i++) {
        put(map, nums[i]);
    }
    
    // 获取所有元素及其频率
    int totalElements;
    Element* elements = getAllElements(map, &totalElements);
    
    // 使用快速选择找到前k个高频元素
    quickSelect(elements, 0, totalElements - 1, k);
    
    // 创建结果数组
    int* result = (int*)malloc(k * sizeof(int));
    for (int i = 0; i < k; i++) {
        result[i] = elements[i].value;
    }
    
    *returnSize = k;
    
    // 释放内存
    free(elements);
    for (int i = 0; i < map->size; i++) {
        HashNode* node = map->table[i];
        while (node != NULL) {
            HashNode* temp = node;
            node = node->next;
            free(temp);
        }
    }
    free(map->table);
    free(map);
    
    return result;
}

// 测试代码
int main() {
    // 测试示例1
    int nums1[] = {1, 1, 1, 2, 2, 3};
    int k1 = 2;
    int returnSize1;
    int* result1 = topKFrequent(nums1, 6, k1, &returnSize1);
    
    printf("测试1:\n输入: [1,1,1,2,2,3], k=2\n输出: [");
    for (int i = 0; i < returnSize1; i++) {
        printf("%d", result1[i]);
        if (i < returnSize1 - 1) printf(", ");
    }
    printf("]\n\n");
    free(result1);
    
    // 测试示例2
    int nums2[] = {1};
    int k2 = 1;
    int returnSize2;
    int* result2 = topKFrequent(nums2, 1, k2, &returnSize2);
    
    printf("测试2:\n输入: [1], k=1\n输出: [");
    for (int i = 0; i < returnSize2; i++) {
        printf("%d", result2[i]);
        if (i < returnSize2 - 1) printf(", ");
    }
    printf("]\n\n");
    free(result2);
    
    // 测试示例3
    int nums3[] = {1, 2, 1, 2, 1, 2, 3, 1, 3, 2};
    int k3 = 2;
    int returnSize3;
    int* result3 = topKFrequent(nums3, 10, k3, &returnSize3);
    
    printf("测试3:\n输入: [1,2,1,2,1,2,3,1,3,2], k=2\n输出: [");
    for (int i = 0; i < returnSize3; i++) {
        printf("%d", result3[i]);
        if (i < returnSize3 - 1) printf(", ");
    }
    printf("]\n");
    free(result3);
    
    return 0;
}
```

---