package LeetCode_Hot100;

/*
4. 寻找两个正序数组的中位数

给定两个大小分别为 `m` 和 `n` 的正序（从小到大）数组 `nums1` 和 `nums2`。
请你找出并返回这两个正序数组的中位数。

算法的时间复杂度应该为 `O(log (m+n))`。

## 提示：
    -- nums1.length == m
    -- nums2.length == n
    -- 0 ≤ m ≤ 1000
    -- 0 ≤ n ≤ 1000
    -- 1 ≤ m + n ≤ 2000
    -- -10^6 ≤ nums1[i], nums2[i] ≤ 10^6
*/
public class question73 {
    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 2};
        int[] nums2 = new int[]{3, 4};

        question73 sl73 = new question73();
        System.out.println(sl73.findMedianSortedArrays(nums1, nums2));
    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;

        int total = m + n;

        // 如果总长度是奇数，中位数就是第 (total+1)//2 小的数
        // 如果总长度是偶数，中位数是第 total//2 和 total//2+1 小的数的平均值
        int left = (total + 1) / 2;
        int right = (total + 2) / 2;
        
        return (findKth(nums1, 0, m - 1, nums2, 0, n - 1, left) + 
                findKth(nums1, 0, m - 1, nums2, 0, n - 1, right)) / 2.0;
    }

    private int findKth(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2, int k) {
            
        int len1 = end1 - start1 + 1;
        int len2 = end2 - start2 + 1;

        // 确保 nums1 是较短的数组
        if (len1 > len2) {
            return findKth(nums2, start2, end2, nums1, start1, end1, k);
        }

        // 如果 nums1 为空
        if (len1 == 0) {
            return nums2[start2 + k - 1];
        }

        // 如果 k == 1
        if (k == 1) {
            return Math.min(nums1[start1], nums2[start2]);
        }

        // 比较两个数组的第 k/2 个元素
        int i = start1 + Math.min(len1, k / 2) - 1;
        int j = start2 + Math.min(len2, k / 2) - 1;

        if (nums1[i] > nums2[j]) {
            return findKth(nums1, start1, end1, nums2, j + 1, end2,  k - (j - start2 + 1));
        } else {
            return findKth(nums1, i + 1, end1, nums2, start2, end2, k - (i - start1 + 1));
        }

    }

}
