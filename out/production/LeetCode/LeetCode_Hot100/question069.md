# 15. 三数之和

**难度: 中等**

## 题目描述
给你一个整数数组 `nums`，判断是否存在三元组 `[nums[i], nums[j], nums[k]]` 
满足 `i != j`、`i != k` 且 `j != k`，同时还满足 `nums[i] + nums[j] + nums[k] == 0`。
请你返回所有和为 0 且不重复的三元组。

**注意：答案中不可以包含重复的三元组。**

---

## 示例说明
### 示例 1：
输入：nums = [-1,0,1,2,-1,-4]  
输出：[[-1,-1,2],[-1,0,1]]  
解释：
- nums[0] + nums[1] + nums[2] = (-1) + 0 + 1 = 0
- nums[1] + nums[2] + nums[4] = 0 + 1 + (-1) = 0
- nums[0] + nums[3] + nums[4] = (-1) + 2 + (-1) = 0
不同的三元组是 [-1,0,1] 和 [-1,-1,2]。

### 示例 2：
输入：nums = [0,1,1]  
输出：[]  
解释：唯一可能的三元和不为 0。

### 示例 3：
输入：nums = [0,0,0]  
输出：[[0,0,0]]

---

## 提示：
- 3 ≤ nums.length ≤ 3000
- -10^5 ≤ nums[i] ≤ 10^5

---

## 解题思路

### 核心思想
使用**排序 + 双指针**的方法，将三数之和问题转化为两数之和问题。先固定一个数，然后在剩余数组中使用双指针寻找两数之和等于目标值的组合。

### 关键观察
- 需要去除重复的三元组，因此排序后可以方便地跳过重复元素
- 固定第一个数 `nums[i]`，然后在 `[i+1, n-1]` 范围内寻找两个数使它们的和为 `-nums[i]`
- 使用双指针 `left` 和 `right` 分别指向子数组的两端，根据和与目标值的大小关系移动指针

### 算法步骤
1. 对数组进行排序
2. 遍历数组，固定第一个数 `nums[i]`：
   - 如果 `nums[i] > 0`，由于数组已排序，后面的数都大于 0，不可能和为 0，直接跳出循环
   - 如果 `i > 0` 且 `nums[i] == nums[i-1]`，跳过当前元素（去重）
   - 初始化左指针 `left = i + 1`，右指针 `right = n - 1`
   - 当 `left < right` 时循环：
     - 计算当前和 `sum = nums[i] + nums[left] + nums[right]`
     - 如果 `sum == 0`：
       - 将三元组加入结果
       - 跳过重复的左指针元素：`while left < right and nums[left] == nums[left+1]: left++`
       - 跳过重复的右指针元素：`while left < right and nums[right] == nums[right-1]: right--`
       - 同时移动左右指针：`left++`，`right--`
     - 如果 `sum < 0`，说明和太小，左指针右移
     - 如果 `sum > 0`，说明和太大，右指针左移
3. 返回结果集

---

## 代码参考(python, java, c)

### Python 代码实现
```python
class Solution:
    def threeSum(self, nums: List[int]) -> List[List[int]]:
        n = len(nums)
        result = []
        
        # 先排序
        nums.sort()
        
        for i in range(n - 2):
            # 如果当前数大于0，后面的数都大于0，不可能和为0
            if nums[i] > 0:
                break
            
            # 跳过重复的第一个数
            if i > 0 and nums[i] == nums[i - 1]:
                continue
            
            left, right = i + 1, n - 1
            target = -nums[i]
            
            while left < right:
                current_sum = nums[left] + nums[right]
                
                if current_sum == target:
                    result.append([nums[i], nums[left], nums[right]])
                    
                    # 跳过重复的左指针
                    while left < right and nums[left] == nums[left + 1]:
                        left += 1
                    # 跳过重复的右指针
                    while left < right and nums[right] == nums[right - 1]:
                        right -= 1
                    
                    left += 1
                    right -= 1
                elif current_sum < target:
                    left += 1
                else:
                    right -= 1
        
        return result
```

### Java 代码实现
```java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        int n = nums.length;
        
        // 先排序
        Arrays.sort(nums);
        
        for (int i = 0; i < n - 2; i++) {
            // 如果当前数大于0，后面的数都大于0，不可能和为0
            if (nums[i] > 0) {
                break;
            }
            
            // 跳过重复的第一个数
            if (i > 0 && nums[i] == nums[i - 1]) {
                continue;
            }
            
            int left = i + 1;
            int right = n - 1;
            int target = -nums[i];
            
            while (left < right) {
                int sum = nums[left] + nums[right];
                
                if (sum == target) {
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));
                    
                    // 跳过重复的左指针
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }
                    // 跳过重复的右指针
                    while (left < right && nums[right] == nums[right - 1]) {
                        right--;
                    }
                    
                    left++;
                    right--;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return result;
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

int cmp(const void* a, const void* b) {
    return *(int*)a - *(int*)b;
}

int** threeSum(int* nums, int numsSize, int* returnSize, int** returnColumnSizes) {
    *returnSize = 0;
    if (numsSize < 3) return NULL;
    
    // 先排序
    qsort(nums, numsSize, sizeof(int), cmp);
    
    // 分配最大可能的结果空间
    int** result = (int**)malloc(numsSize * numsSize * sizeof(int*));
    *returnColumnSizes = (int*)malloc(numsSize * numsSize * sizeof(int));
    
    for (int i = 0; i < numsSize - 2; i++) {
        // 如果当前数大于0，后面的数都大于0，不可能和为0
        if (nums[i] > 0) {
            break;
        }
        
        // 跳过重复的第一个数
        if (i > 0 && nums[i] == nums[i - 1]) {
            continue;
        }
        
        int left = i + 1;
        int right = numsSize - 1;
        int target = -nums[i];
        
        while (left < right) {
            int sum = nums[left] + nums[right];
            
            if (sum == target) {
                result[*returnSize] = (int*)malloc(3 * sizeof(int));
                result[*returnSize][0] = nums[i];
                result[*returnSize][1] = nums[left];
                result[*returnSize][2] = nums[right];
                (*returnColumnSizes)[*returnSize] = 3;
                (*returnSize)++;
                
                // 跳过重复的左指针
                while (left < right && nums[left] == nums[left + 1]) {
                    left++;
                }
                // 跳过重复的右指针
                while (left < right && nums[right] == nums[right - 1]) {
                    right--;
                }
                
                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
    }
    
    return result;
}
```

---