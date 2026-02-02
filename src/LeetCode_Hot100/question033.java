package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
/*
406. 根据身高重建队列
假设有一群人被打乱顺序地站成一个队列，数组 `people` 表示队列中一些人的属性（不一定按顺序）。  
每个 `people[i] = [h_i, k_i]` 表示第 `i` 个人的身高为 `h_i`，前面正好有 `k_i` 个身高大于或等于 `h_i` 的人。

请你重新构建并返回输入数组 `people` 所表示的队列。  
返回的队列应该格式化为数组 `queue`，其中 `queue[j] = [h_j, k_j]` 是队列中第 `j` 个人的属性（`queue[0]` 是排在队列前面的人）。
*/

public class question033 {
    public static void main(String[] args) {
        int[][] nums = {{7,0},{4,4},{7,1},{5,0},{6,1},{5,2}};
        int[][] res = reconstructQueue(nums);
        for (int i = 0; i < res.length; i++) {
            System.out.println("[" + res[i][0] + " " +res[i][1] + "]");
        }
    }

    public static int[][] reconstructQueue(int[][] people) {
        // !!!核心逻辑
        // 1. 高的先入场: 处理升高为 h 的人时，队列里已经存在的其他人，升高全部 ≥h 。
        // 2. k即索引: k 值表示这个人前面有 k 个比他高或一样高的人。既然现在队伍里全都是比他高的人，只需要将其插入 index=k 的这个位置，他的前面就正好有 k 个比他高的人 。
        // 3. 矮的后入场: 插入一个更矮的人时，无论他插在哪里，对于已经在队列里的“高人”来说，多一个矮子并不会影响他们的 k 值统计 。
        Arrays.sort(people, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                if (a[0] != b[0]) {
                    return b[0] - a[0];
                }
                return a[1] - b[1];
            }
            
        });
        // 与上面的等价, 都是匿名内部类的写法, 下面的是缩写版本
        // Arrays.sort(people, (a, b) -> {
        //     if (a[0] != b[0]) {
        //             return b[0] - a[0];
        //         }
        //         return a[1] - b[1];
        // });

        List<int[]> res = new ArrayList<>();
        for (int[] person : people) {
            res.add(person[1], person);
        }

        return res.toArray(new int[res.size()][]);

    }
}
