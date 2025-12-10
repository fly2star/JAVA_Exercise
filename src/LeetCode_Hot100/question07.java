package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-01-16:10
 **/
/*
215. 数组中的第K个最大元素
给定整数数组 nums 和整数 k，请返回数组中第 k 个最大的元素。
请注意，你需要找的是数组排序后的第 k 个最大的元素，而不是第 k 个不同的元素。
你必须设计并实现时间复杂度为 O(n) 的算法解决此问题。
*/
public class question07 {

    public static final Random RANDOM = new Random();

    public static void main(String[] args) {
        int[] arr = {3, 2, 1, 5, 6, 4};
//        System.out.println(findKthLargest(arr, 2));
//        System.out.println(findKthLargest4(arr, 2));
        System.out.println(findKthLargest6(arr, 2));
        int[] arr2 = {3,2,3,1,2,4,5,5,6};
//        System.out.println(findKthLargest2(arr2, 4));
        System.out.println(findKthLargest5(arr2, 4));
    }
    // 方法1: 先使用带随机 pivot 的快速排序对数组进行排序，然后返回第 k 大的元素
    // 超时
    public static int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        quickSort(nums);
        return nums[n - k];
    }

    // 方法2: 尝试使用三路快排
    // 成功
    public static int findKthLargest2(int[] nums, int k) {
        int n = nums.length;
        quickSort3way(nums);
        return nums[n - k];
    }

    // 方法3: 基于array自带的排序
    public static int findKthLargest3(int[] nums, int k) {
        int n = nums.length;
        Arrays.sort(nums);
        return nums[n - k];
    }

    // 方法4: 基于快速排序的选择方法
    public static int findKthLargest4(int[] nums, int k) {
        int n = nums.length;
        return quickSelect(nums, 0, n - 1, n - k);
    }

    // 方法5: 基于堆的选择方法
    public static int findKthLargest5(int[] nums, int k) {
        int heapSize = nums.length;
        buildMaxHeap(nums, heapSize);
        for (int i = nums.length - 1; i >= nums.length - k + 1; i--) {
            swap(nums, 0, i);
            --heapSize;
            maxHeapify(nums, 0, heapSize);
        }
        return nums[0];
    }

    // 方法6: 另一种形式的快速选择
    public static int findKthLargest6(int[] nums, int k) {
        List<Integer> numList = new ArrayList<>();
        for (int num : nums) {
            numList.add(num);
        }
        return quickSelect(numList, k);

    }

    //===================================方法区======================================
    public static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static int partition(int [] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            // 随机选择 pivot 并移动到末尾
            int randomIndex = low + RANDOM.nextInt(high - low + 1);
            swap(arr, randomIndex, high);

            int pivotIndex = partition(arr, low, high);
            quickSort(arr, low, pivotIndex - 1);
            quickSort(arr, pivotIndex + 1, high);
        }
    }
    public static void quickSort(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSort(arr, 0, arr.length - 1);
    }

    public static void quickSort3way(int[] arr, int low, int high) {
        if (low >= high) return;

        int pivot = arr[low];
        int lt = low;      // arr[low..lt-1] < pivot
        int gt = high;     // arr[gt+1..high] > pivot
        int i = low + 1;   // arr[lt..i-1] == pivot

        // 三路划分
        while (i <= gt) {
            if (arr[i] < pivot) {
                swap(arr, lt++, i++);
            } else if (arr[i] > pivot) {
                swap(arr, i, gt--);
            } else {
                i++;
            }
        }

        // 递归调用
        quickSort3way(arr, low, lt - 1);
        quickSort3way(arr, gt + 1, high);
    }
    public static void quickSort3way(int[] arr) {
        if (arr == null || arr.length <= 1) return;
        quickSort3way(arr, 0, arr.length - 1);
    }

    // 快速选择
    public static int quickSelect(int[] arr, int l, int r, int k) {
        if (l == r) return arr[k];
        int x = arr[l], i = l - 1, j = r + 1;
        while (i < j) {
            do i++; while (arr[i] < x);
            do j--; while (arr[j] > x);
            if (i < j) swap(arr, i, j);
        }
        if (k <= j) return quickSelect(arr, l, j, k);
        else return quickSelect(arr, j + 1, r, k);
    }

    // 建堆
    public static void buildMaxHeap(int[] arr, int heapSize) {
        for (int i = heapSize / 2 - 1; i >= 0; i--) {
            maxHeapify(arr, i, heapSize);
        }
    }

    // 大顶堆
    public static void maxHeapify(int[] arr, int i, int heapSize) {
        int l = 2 * i + 1, r = 2 * i + 2;
        int largest = i;
        if (l < heapSize && arr[l] > arr[largest]) largest = l;
        if (r < heapSize && arr[r] > arr[largest]) largest = r;
        if (largest != i) {
            swap(arr, i, largest);
            maxHeapify(arr, largest, heapSize);
        }
    }

    public static int quickSelect(List<Integer> nums, int k) {
        // 随机选择基准数
        Random rand = new Random();
        int pivot = nums.get(rand.nextInt(nums.size()));
        // 将大于、小于、等于 pivot 的元素划分至 big, small, equal 中
        List<Integer> big = new ArrayList<>();
        List<Integer> equal = new ArrayList<>();
        List<Integer> small = new ArrayList<>();
        for (int num : nums) {
            if (num > pivot)
                big.add(num);
            else if (num < pivot)
                small.add(num);
            else
                equal.add(num);
        }
        // 第 k 大元素在 big 中，递归划分
        if (k <= big.size())
            return quickSelect(big, k);
        // 第 k 大元素在 small 中，递归划分
        if (nums.size() - small.size() < k)
        // if (big.size() + equal.size())
            return quickSelect(small, k - nums.size() + small.size());
            //return quickSelect(small, k-(big.size()+equal.size()));
        // 第 k 大元素在 equal 中，直接返回 pivot
        return pivot;
    }

}
