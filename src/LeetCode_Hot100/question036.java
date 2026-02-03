package LeetCode_Hot100;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

/*
347. 前 K 个高频元素

给你一个整数数组 `nums` 和一个整数 `k`，请你返回其中出现频率前 `k` 高的元素。你可以按任意顺序返回答案。
*/
public class question036 {
    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3};
        int K = 2;
        int[] res = topKFrequent(nums, K);
        for (int i : res) {
            System.out.print(i + " ");
        }
    }

    public static int[] topKFrequent(int[] nums, int K) {
        // 使用 Map 统计频率
        Map<Integer, Integer> freqMap = new HashMap<>();
        for (int num : nums) {
            freqMap.put(num, freqMap.getOrDefault(num, 0) + 1);
        }

        // 使用最小推 (按频率排序)
        // new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue)) 谁的 value 值最小, 谁就在堆顶
        // 在 Java 的 PriorityQueue 中，默认的排序逻辑是自然升序（从小到大）, 当比较器判定 A<B 时，A 会被放在堆顶。
        // 写法 1
        // PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));
        // 写法 2
        PriorityQueue<Map.Entry<Integer, Integer>> heap = new PriorityQueue<>((a , b) -> a.getValue() - b.getValue());
        for (Map.Entry<Integer,Integer> entry : freqMap.entrySet()) {
            heap.offer(entry);
            if (heap.size() > K) {
                heap.poll();
            }
        }

        // 提取结果
        int[] result = new int[K];
        for (int i = 0; i < K; i++) {
            result[i] = heap.poll().getKey();
        }

        return result;
    }
    
}
