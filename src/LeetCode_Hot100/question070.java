package LeetCode_Hot100;

/*
11. 盛最多水的容器

给定一个长度为 `n` 的整数数组 `height`。有 `n` 条垂线，第 `i` 条线的两个端点是 `(i, 0)` 和 `(i, height[i])`。

找出其中的两条线，使得它们与 `x` 轴共同构成的容器可以容纳最多的水。

返回容器可以储存的最大水量。

**说明：** 你不能倾斜容器。

## 提示：
-- n = height.length
-- 2 ≤ n ≤ 10^5
-- 0 ≤ height[i] ≤ 10^4
*/
public class question070 {
    public static void main(String[] args) {
        int[] height = new int[]{1,8,6,2,5,4,8,3,7};
        question070 sl70 = new question070();
        System.out.println(sl70.maxArea(height));
    }

    // 方法1: 双指针法
    public int maxArea(int[] height) {
        int n = height.length;
        int left = 0;
        int right = n - 1;
        int maxArea = 0;

        while (left < right) {
            // 计算当前面积
            int width = right - left;
            int h = Math.min(height[left], height[right]);
            int area = width * h;
            maxArea = Math.max(maxArea, area);
            
            // 移动高度较矮的柱子对应的指针
            if (height[left] < height[right]) {
                left++                ;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
