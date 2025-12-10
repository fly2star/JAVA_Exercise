package SecondWeek;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-11-13-21:38
 **/
/*
852. 山脉数组的峰顶索引
给定一个长度为 n 的整数 山脉 数组 arr ，其中的值递增到一个 峰值元素 然后递减。
返回峰值元素的下标。
你必须设计并实现时间复杂度为 O(log(n)) 的解决方案。
*/
public class Question09 {
    public static void main(String[] args) {
        int[] arr = {0,1,0};
        System.out.println(peakIndexInMountainArray(arr));
        int[] arr2 = {0,2,1,0};
        System.out.println(peakIndexInMountainArray2(arr2));

    }
    public static int peakIndexInMountainArray(int[] arr) {
        int left = 0;
        int right = arr.length - 1;
        while (left <  right){
            int mid = left + (right - left + 1) / 2;
            if(arr[mid] > arr[mid - 1]) left = mid;
            else if(arr[mid] < arr[mid - 1])right = mid - 1;
        }
        return left;
    }

    public static int peakIndexInMountainArray2(int[] arr) {
        int n = arr.length;
        int ans = -1;
        for (int i = 1; i < n - 1; ++i) {
            if (arr[i] > arr[i + 1]) {
                ans = i;
                break;
            }
        }
        return ans;
    }
}
