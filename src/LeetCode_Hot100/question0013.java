package LeetCode_Hot100;

import java.util.*;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-03-21:17
 **/
/*
169. 多数元素
给定一个大小为 n 的数组 nums ，返回其中的多数元素。多数元素是指在数组中出现次数 大于 ⌊ n/2 ⌋ 的元素。
你可以假设数组是非空的，并且给定的数组总是存在多数元素。
*/
public class question0013 {
    public static void main(String[] args) {
        int[] nums = {3, 2, 3};
        System.out.println(majorityElement(nums));
        System.out.println(majorityElement2(nums));
        System.out.println(majorityElement3(nums));
        System.out.println(majorityElement4(nums));
        System.out.println(majorityElement5(nums));
        System.out.println(majorityElement6(nums));
    }


    // 方法1: 排序，返回中间元素
    public static int majorityElement(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        return nums[n / 2];
    }

    // 方法2: 用栈模拟投票，遇到不同的投票者，则将栈顶的投票者出栈
    public static int majorityElement2(int[] nums) {
        int n = nums.length;
        Deque<Integer> stack = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            if ( !stack.isEmpty() && stack.peek() != nums[i]) {
                stack.pop();
            } else {
                stack.push(nums[i]);
            }
        }
        return stack.isEmpty() ? Integer.MIN_VALUE : stack.peek();
    }

    // 方法3: 随机化
    // 因为超过 n/2 的数组下标被众数占据了，这样我们随机挑选一个下标对应的元素并验证，有很大的概率能找到众数。
    public static int majorityElement3(int[] nums) {
        Random rand = new Random();
        // 众数在数组中的数量，应该大于等于 n/2
        int majorityCount = nums.length / 2;

        while (true) { // 无限循环
            // 1. 随机选一个索引 范围在 [0, nums.length)
            int candidate = nums[randomRange(rand, 0, nums.length)];
            // 2. 统计 candidate 在数组中出现的次数
            if (count0ccurences(nums, candidate) > majorityCount) {
                return candidate;
            }
            // 没找到众数，就开始新一轮的循环
        }
    }

    // 生成 [min,max) 范围内的随机整数
    public static int randomRange(Random random, int min, int max) {
        return random.nextInt(max - min) + min;
    }

    // 统计 num 在 nums 中出现的次数
    public static int count0ccurences(int[] nums, int num) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }

    // 方法4: 分治
    // 如果数 a 是数组 nums 的众数，如果我们将 nums 分成两部分，那么 a 必定是至少一部分的众数。
    //      我们使用经典的分治算法递归求解，直到所有的子问题都是长度为 1 的数组。长度为 1 的子数组中唯一的数显然是众数，直接返回即可。
    //      如果回溯后某区间的长度大于 1，我们必须将左右子区间的值合并。如果它们的众数相同，那么显然这一段区间的众数是它们相同的值。
    //      否则，我们需要比较两个众数在整个区间内出现的次数来决定该区间的众数。

    public static int majorityElement4(int[] nums) {
        return majorityElementRec(nums, 0, nums.length - 1);
    }

    public static int majorityElementRec(int[] nums, int lo, int hi) {
        // base case; the only element in an array of size 1 is the majority element.
        // 子数组中只有一个元素时，它本身肯定时众数
        if (lo == hi) {
            return nums[lo];
        }

        // recurse on left and right halves of this slice.
        // 计算中心点
        int mid = (hi - lo) / 2 + lo;  // 防止溢出的写法，等价于 (lo+hi)/2
        // 递归求左右两半的“候选众数”
        int left = majorityElementRec(nums, lo, mid);           // 递归求左半边众数
        int right = majorityElementRec(nums, mid + 1, hi);   // 递归求右半边众数

        // if the two halves agree on the majority element, return it.
        // 合并结果，如果左右候选相同，那么它一定是当前这个大区间（left-mid-right）的众数
        if (left == right) {
            return left;
        }

        // otherwise, count each element and return the "winner".
        // 如果不同，就数一数谁在[low,hi)区间中出现的次数多
        int leftCount = countInRange(nums, left, lo, hi);
        int rightCount = countInRange(nums, right, lo, hi);

        return leftCount > rightCount ? left : right;
    }

    // 在子区间计数，返回子区间 nums[]  中 [lo, hi) 数字 num 的数量
    public static int countInRange(int[] nums, int num, int lo, int hi) {
        int count = 0;
        for (int i = lo; i < hi; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }

    // 方法5: Boyer-Moore 投票算法
    // 如果我们把众数记为 +1，把其他数记为 −1，将它们全部加起来，显然和大于 0，从结果本身我们可以看出众数比其他数多。
    /**
     * Boyer-Moore 算法的本质和方法四中的分治十分类似。我们首先给出 Boyer-Moore 算法的详细步骤：
     *      我们维护一个候选众数 candidate 和它出现的次数 count。初始时 candidate 可以为任意值，count 为 0；
     * <br>
     *      我们遍历数组 nums 中的所有元素，对于每个元素 x，在判断 x 之前，如果 count 的值为 0，我们先将 x 的值赋予 candidate，
     *      随后我们判断 x：
     *                  如果 x 与 candidate 相等，那么计数器 count 的值增加 1；
     *                  如果 x 与 candidate 不等，那么计数器 count 的值减少 1。
     * <br>
     * 在遍历完成后，candidate 即为整个数组的众数。
     * */
    public static int majorityElement5(int[] nums) {
        int count = 0;
        // 用 Integer 而不是 int, 方便表示“无候选人”
        Integer candidate = null;

        for (int num : nums) {
            if (count == 0) {
                // 没有人票数为正，当前元素为候选人
                candidate = num;
            }
            // 如果当前元素 === 候选人 --> 票数加1,否则票数减1
            count += (num == candidate) ? 1 : -1;
        }
        // 题目规定众数存在，所以结果一定是众数，candidate 一定是答案
        return candidate;
    }

    // 方法6: 哈希表
    public static int majorityElement6(int[] nums) {
        // 统计每个数字出现的次数
        Map<Integer, Integer> counts = countNums(nums);
        // key 表示数字，value 表示出现的次数
        Map.Entry<Integer, Integer> majorityEntry = null;
        for (Map.Entry<Integer, Integer> entry : counts.entrySet()) {
            // 如果是第一个元素，或者当前 entry 的 value 更大
            if (majorityEntry == null || entry.getValue() > majorityEntry.getValue()) {
                // 更新最大value 的 entry 为众数对应的 entry
                majorityEntry = entry;
            }
        }
        return majorityEntry.getKey();
    }
    // 统计 nums 中每个数字出现的次数
    public static Map<Integer, Integer> countNums(int[] nums) {
        Map<Integer, Integer> counts = new HashMap<Integer, Integer>();
        for (int num : nums) {
            if (!counts.containsKey(num)) {
                // 第一次出现，记为1
                counts.put(num, 1);
            } else {
                // 已存在，次数 +1
                counts.put(num, counts.get(num) + 1);
            }
        }
        return counts;
    }


}
