package Fifth;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-09-09:39
 **/
/*
3583. 统计特殊三元组
给你一个整数数组 nums。
特殊三元组 定义为满足以下条件的下标三元组 (i, j, k)：
    0 <= i < j < k < n，其中 n = nums.length
    nums[i] == nums[j] * 2
    nums[k] == nums[j] * 2
返回数组中 特殊三元组 的总数。
由于答案可能非常大，请返回结果对 109 + 7 取余数后的值。
*/
public class Question02 {
    public static void main(String[] args) {
        int[] nums = {0, 1, 0, 0};
        System.out.println(specialTriplets(nums));
    }

    // 暴力解法
    public static int specialTriplets(int[] nums) {
        int n = nums.length;
        int ans = 0;
        int big = 1_000_000_000 + 7;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                for (int k = j + 1; k < n; k++) {
                    if (nums[i]==nums[j]*2 && nums[j]==nums[k]*2) {
                        ans++;
                    }
                }
            }
        }
        return ans % big;
    }


    // 枚举中间
    public static int specialTriplets2(int[] nums) {
        int MOD = 1000_000_007;
        int ans = 0;
        int n = nums.length;

        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // map.merge(key, value, remappingFUnction)
            // 1. 检查键:如果 map 中不存在 key, 则将 key 和 value 添加到 map 中;
            // 2. 存在键:如果 key 已经存在，则将 key 的 value 更新为 remappingFunction(key, oldValue)
            // Interger::sum, 是一个静态方法引用，引用了 Integer 类中对的静态方法 sum(int a, int b);
            map.merge(nums[i], 1, Integer::sum);
            // 如果数字 nums[i] 首次出现，将其作为键，并将计数 1 作为值放入 map;
            // 如果数字 nums[i] 已经存在，则将其旧的计数 (oldValue) 与新出现的次数 1 相加 (Integer::sum)，然后用新的总和更新该数字的计数。
        }

        Map<Integer, Integer> prev = new HashMap<>();
        for (int i = 0; i < n; i++) {
            // 遍历每个位置 i. 将其视为(i,j,k)中的中间元素 j
            // prev 记录了 [0,i-1] 中所有数字的计数,
            // map 记录了 [i+1,n-1] 中所有数字的计数, 之前统计了整个数据的，前去目前遍历的prev，剩下的就是右侧的数数据
            map.merge(nums[i], -1, Integer::sum);

            // V getOrDefault(Object key, V defaultValue)
            // 尝试获取指定键 (key) 对应的值；如果该键不存在于 Map 中，则返回一个默认值 (defaultValue)，而不是返回 null。
            // a = 左边（prev）中值为 2 * nums[i] 的元素个数; b = 右边（map）中值为 2 * nums[i] 的元素个数
            long a = prev.getOrDefault(nums[i] * 2, 0);
            long b = map.getOrDefault(nums[i] * 2, 0);

            // 以 i 为中间的三元组数量是 a * b, 排列组合: 左边右 a 个选择, 右边右 b 个选择。
            ans = (int)((ans + (a * b) % MOD) % MOD);
            // 将当前元素 nums[i] 加入 prev 中
            prev.merge(nums[i], 1, Integer::sum);
        }

        return ans;

    }

    // 枚举中间，同法2
    public static int specialTriplets3(int[] nums) {
        final int MOD = 1_000_000_007;
        Map<Integer, Integer> suf = new HashMap<>();
        for (int x : nums) {
            suf.merge(x, 1, Integer::sum); // suf[x]++
        }

        long ans = 0;
        Map<Integer, Integer> pre = new HashMap<>();
        for (int x : nums) { // x = nums[j]
            suf.merge(x, -1, Integer::sum); // suf[x]-- // 撤销
            // 现在 pre 中的是 [0,j-1]，suf 中的是 [j+1,n-1]
            ans += (long) pre.getOrDefault(x * 2, 0) * suf.getOrDefault(x * 2, 0);
            pre.merge(x, 1, Integer::sum); // pre[x]++
        }
        return (int) (ans % MOD);
    }

    // 枚举右，维护左
    // 枚举 k，设 x=nums[k]，问题变成:
    // 有多少个二元组 (i,j)，满足 i<j<k 且 nums[i]=x 且 nums[j]= x/2 。用哈希表 cnt2 记录这样的二元组个数。
    public static int specialTriplets4(int[] nums) {
        final int MOD = 1_000_000_007;
        // cnt1 记录 nums[i] 的频次 , 可当作左端点
        Map<Integer, Integer> cnt1 = new HashMap<>();
        // cnt12 存储的是满足 (nums[i] = nums[j] * 2) 的二元组 (i, j) 的数量
        Map<Integer, Long> cnt12 = new HashMap<>();
        // cnt123 存储最终三元组的数量
        long cnt123 = 0;
        for (int x : nums) {
            // 1. 把当前 x 当作 nums[k], 我们要求 nums[k] = nums[j] * 2，即 nums[j] = x / 2
            if (x % 2 == 0) {
                // cnt12 存储着所有在 x 之前(即i < j < k)满足num[i]=num[j] * 2的(i, j)对的数量。
                // 因此，我们查找 cnt12 中 键为 x/2 的值，并将其累加到最终结果 cnt123 上。
                cnt123 += cnt12.getOrDefault(x / 2, 0L); // 把 x 当作 nums[k]
                // 为什么是 x/2, 因为此时 num[j]=x/2
                // 在接下来 cnt12 的存储中, key=x 对应是 x=num[j]
            }
            // 2. 此时 x 扮演 nums[j]。我们要求 nums[i] = nums[j] * 2，即 nums[i] = x * 2
            cnt12.merge(x, (long) cnt1.getOrDefault(x * 2, 0), Long::sum); // 把 x 当作 nums[j]

            // 3. 此时 x 扮演 nums[i]
            cnt1.merge(x, 1, Integer::sum); // 把 x 当作 nums[i]
        }
        return (int) (cnt123 % MOD);

    }
}
