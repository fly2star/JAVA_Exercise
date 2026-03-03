# 49. 字母异位词分组

**难度: 中等**

## 题目描述
给你一个字符串数组，请你将 **字母异位词** 组合在一起。可以按任意顺序返回结果列表。字母异位词指由相同的字母按照不同的顺序组成的单词。

---

## 示例说明
### 示例 1：
输入：strs = ["eat", "tea", "tan", "ate", "nat", "bat"]  
输出：[["bat"],["nat","tan"],["ate","eat","tea"]]  
解释：
- 在 strs 中没有字符串可以通过重新排列来形成 "bat"
- 字符串 "nat" 和 "tan" 是字母异位词，因为它们可以重新排列以形成彼此
- 字符串 "ate"、"eat" 和 "tea" 是字母异位词，因为它们可以重新排列以形成彼此

### 示例 2：
输入：strs = [""]  
输出：[[""]]

### 示例 3：
输入：strs = ["a"]  
输出：[["a"]]

---

## 提示：
- 1 ≤ strs.length ≤ 10^4
- 0 ≤ strs[i].length ≤ 100
- strs[i] 仅包含小写字母

---

## 解题思路

### 核心思想
字母异位词的本质是它们有相同的字母出现频率。因此，我们可以将每个字符串标准化（排序后的字符串或字符计数）作为哈希表的键，将原字符串作为值存入对应的列表中。

### 关键观察
- 两个字符串如果是字母异位词，对它们分别按字母排序后得到的结果相同
- 也可以使用字符计数作为键：因为只有小写字母，可以用一个长度为26的数组记录每个字母的出现次数，然后将其转化为字符串作为键

### 算法步骤
1. 创建一个哈希表，键是字符串的标准化表示，值是该异位词组的所有原字符串
2. 遍历每个字符串：
   - 方法一：将字符串排序后作为键
   - 方法二：统计每个字符出现次数，生成计数键
3. 将原字符串添加到对应键的列表中
4. 返回哈希表中所有值组成的列表

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def groupAnagrams(self, strs: List[str]) -> List[List[str]]:
        from collections import defaultdict
        
        # 使用字典存储结果，键为排序后的字符串
        anagram_dict = defaultdict(list)
        
        for s in strs:
            # 将字符串排序后作为键
            sorted_str = ''.join(sorted(s))
            anagram_dict[sorted_str].append(s)
        
        return list(anagram_dict.values())

# 方法二：使用字符计数作为键（更高效，避免排序）
class Solution:
    def groupAnagrams(self, strs: List[str]) -> List[List[str]]:
        from collections import defaultdict
        
        anagram_dict = defaultdict(list)
        
        for s in strs:
            # 创建长度为26的计数数组
            count = [0] * 26
            for c in s:
                count[ord(c) - ord('a')] += 1
            # 将计数数组转换为元组作为键（因为列表不可哈希）
            key = tuple(count)
            anagram_dict[key].append(s)
        
        return list(anagram_dict.values())
```

### Java 代码实现
```java
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) return new ArrayList<>();
        
        Map<String, List<String>> map = new HashMap<>();
        
        for (String s : strs) {
            // 将字符串转为字符数组并排序
            char[] chars = s.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            
            // 如果键不存在，创建新的列表
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        
        return new ArrayList<>(map.values());
    }
}

// 方法二：使用字符计数作为键
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        
        for (String s : strs) {
            // 创建计数数组
            int[] count = new int[26];
            for (char c : s.toCharArray()) {
                count[c - 'a']++;
            }
            
            // 将计数数组转换为字符串键
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 26; i++) {
                sb.append('#');
                sb.append(count[i]);
            }
            String key = sb.toString();
            
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(s);
        }
        
        return new ArrayList<>(map.values());
    }
}
```

### C 代码实现
```c
/**
 * Return an array of arrays of size *returnSize.
 * The sizes of the arrays are returned as *returnColumnSizes array.
 * Note: Both returned array and *columnSizes array must be malloced, assume caller calls free().
 */

#define MAX_STR_LEN 101
#define MAX_STR_COUNT 10000

// 字符串比较函数用于qsort
int cmp_char(const void* a, const void* b) {
    return *(char*)a - *(char*)b;
}

// 结构体用于存储字符串和它的排序后版本
typedef struct {
    char original[MAX_STR_LEN];
    char sorted[MAX_STR_LEN];
} StringInfo;

// 比较StringInfo结构体，用于排序分组
int cmp_stringinfo(const void* a, const void* b) {
    return strcmp(((StringInfo*)a)->sorted, ((StringInfo*)b)->sorted);
}

char*** groupAnagrams(char** strs, int strsSize, int* returnSize, int** returnColumnSizes) {
    if (strsSize == 0) {
        *returnSize = 0;
        *returnColumnSizes = NULL;
        return NULL;
    }
    
    // 创建StringInfo数组
    StringInfo* infos = (StringInfo*)malloc(strsSize * sizeof(StringInfo));
    for (int i = 0; i < strsSize; i++) {
        strcpy(infos[i].original, strs[i]);
        strcpy(infos[i].sorted, strs[i]);
        qsort(infos[i].sorted, strlen(infos[i].sorted), sizeof(char), cmp_char);
    }
    
    // 按排序后的字符串排序
    qsort(infos, strsSize, sizeof(StringInfo), cmp_stringinfo);
    
    // 统计分组数量
    *returnSize = 0;
    int* colSizes = (int*)malloc(strsSize * sizeof(int));
    
    // 第一遍遍历确定组数
    for (int i = 0; i < strsSize; ) {
        int j = i + 1;
        while (j < strsSize && strcmp(infos[i].sorted, infos[j].sorted) == 0) {
            j++;
        }
        colSizes[(*returnSize)++] = j - i;
        i = j;
    }
    
    // 分配结果数组
    char*** result = (char***)malloc(*returnSize * sizeof(char**));
    *returnColumnSizes = (int*)malloc(*returnSize * sizeof(int));
    
    // 第二遍遍历填充结果
    int index = 0;
    for (int i = 0; i < strsSize; ) {
        int j = i + 1;
        while (j < strsSize && strcmp(infos[i].sorted, infos[j].sorted) == 0) {
            j++;
        }
        
        int groupSize = j - i;
        (*returnColumnSizes)[index] = groupSize;
        result[index] = (char**)malloc(groupSize * sizeof(char*));
        
        for (int k = 0; k < groupSize; k++) {
            result[index][k] = (char*)malloc((strlen(infos[i + k].original) + 1) * sizeof(char));
            strcpy(result[index][k], infos[i + k].original);
        }
        
        index++;
        i = j;
    }
    
    free(infos);
    free(colSizes);
    return result;
}
```

---