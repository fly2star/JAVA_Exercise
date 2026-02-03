package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
399. 除法求值

给你一个变量对数组 `equations` 和一个实数值数组 `values` 作为已知条件，其中 `equations[i] = [Ai, Bi]` 和 `values[i]` 共同表示等式 $A_i / B_i = values[i]$ 。
每个 `Ai` 或 `Bi` 是一个表示单个变量的字符串。

另有一些以数组 queries 表示的问题，其中 queries[j] = [Cj, Dj] 表示第 j 个问题，请你根据已知条件找出 Cj / Dj = ? 的结果作为答案。

返回所有问题的答案。如果存在某个无法确定的答案，则用 -1.0 替代这个答案。如果问题中出现了给定的已知条件中没有出现的字符串，也需要用 -1.0 替代这个答案。

注意：输入总是有效的。你可以假设除法运算中不会出现除数为 0 的情况，且不存在任何矛盾的结果。
*/

public class question034 {
    // 配合方法 1 的成员变量
    public static Map<String, String> parent = new HashMap<>();
    public static Map<String, Double> weight = new HashMap<>();

    public static void main(String[] args) {
        List<List<String>> eq = new ArrayList<>();
        eq.add(List.of("a", "b"));
        eq.add(List.of("b", "c"));
        double[] values = {2.0, 3.0};
        List<List<String>> qu = new ArrayList<>();
        qu.add(List.of("a", "c"));
        qu.add(List.of("b", "a"));
        qu.add(List.of("a", "e"));
        qu.add(List.of("a", "a"));
        qu.add(List.of("x", "x"));
        double[] results = calcEquation(eq, values, qu);
        System.out.println(Arrays.toString(results));

    }

    // 方法 1: 只能处理查询种字符串为单个字符的情况
    public static double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // 初始化并查集
        for (int i = 0; i < equations.size(); i++) {
            String a = equations.get(i).get(0);
            String b = equations.get(i).get(1);
            if (!parent.containsKey(a)) {
                parent.put(a, a);
                weight.put(a, 1.0);
            }
            if (!parent.containsKey(b)) {
                parent.put(b, b);
                weight.put(b, 1.0);
            }
            union(a, b, values[i]);
        }
        
        // 处理查询
        double[] res = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            String a = queries.get(i).get(0);
            String b = queries.get(i).get(1);
            if (!parent.containsKey(a) || !parent.containsKey(b)) {
                res[i] = -1.0;
            } else {
                String rootA = find(a);
                String rootB = find(b);
                if (!rootA.equals(rootB)) {
                    res[i] = -1.0;
                } else {
                    res[i] = weight.get(a) / weight.get(b);
                }
            }

        }
        return res;    
    }

    public static String find(String x) {
        if (!parent.get(x).equals(x)) {
            String originParent = parent.get(x);
            String root = find(originParent);
            weight.put(x, weight.get(x) * weight.get(originParent));
            parent.put(x, root);
        }
        return parent.get(x);
    }

    public static void union(String x, String y, double values) {
        String rootX = find(x);
        String rootY = find(y);
        if (!rootX.equals(rootY)) {
            parent.put(rootX, rootY);
            weight.put(rootX, weight.get(y) * values / weight.get(x));
        }
    }


    // 方法 2 : 定义了一个并查集的类, 来解决问题
    public static double[] calcEquation2(List<List<String>> equations, double[] values, List<List<String>> queries) {
        // 把不同字符串映射为不同的数字，方便使用并查集
        Map<String, Integer> variableToId = new HashMap<>();
        for (List<String> equation : equations) {
            for (String s : equation) {
                variableToId.putIfAbsent(s, variableToId.size());
            }
        }

        // 初始化并查集
        UnionFind uf = new UnionFind(variableToId.size());
        for (int i = 0; i < equations.size(); i++) {
            List<String> equation = equations.get(i);
            uf.merge(variableToId.get(equation.get(1)), variableToId.get(equation.get(0)), values[i]);
        }

        // 回答询问
        double[] ans = new double[queries.size()];
        for (int i = 0; i < queries.size(); i++) {
            List<String> query = queries.get(i);
            Integer c = variableToId.get(query.get(0));
            Integer d = variableToId.get(query.get(1));
            if (c != null && d != null && uf.same(c, d)) {
                //    c * mul[c] = d * mul[d] = 代表元的值
                // => c / d = mul[d] / mul[c]
                ans[i] = uf.mul[d] / uf.mul[c];
            } else {
                ans[i] = -1;
            }
        }
        return ans;

    }


    
}



class UnionFind {
    private final int[] fa; // 代表元
    public final double[] mul; // x 的值 * mul[x] = x 的代表元的值

    public UnionFind(int n) {
        fa = new int[n];
        // 一开始有 n 个集合 {0}, {1}, ..., {n-1}
        // 集合 i 的代表元是自己，自己 * 1 = 自己
        for (int i = 0; i < n; i++) {
            fa[i] = i;
        }

        mul = new double[n];
        Arrays.fill(mul, 1);
    }

    // 返回 x 所在集合的代表元
    // 同时做路径压缩，也就是把 x 所在集合中的所有元素的 fa 都改成代表元
    private int find(int x) {
        if (fa[x] != x) {
            int root = find(fa[x]);
            mul[x] *= mul[fa[x]]; // 更新 x 到其代表元的 mul 值
            fa[x] = root;
        }
        return fa[x];
    }

    // 判断 x 和 y 是否在同一个集合
    public boolean same(int x, int y) {
        // 如果 x 的代表元和 y 的代表元相同，那么 x 和 y 就在同一个集合
        // 这就是代表元的作用：用来快速判断两个元素是否在同一个集合
        return find(x) == find(y);
    }

    // 合并 from 和 to，新增信息 to / from = value
    // 其中 to 和 from 表示未知量，下文的 x 和 y 也表示未知量
    public void merge(int from, int to, double value) {
        int x = find(from);
        int y = find(to);
        if (x == y) { // from 和 to 在同一个集合，不做合并
            return;
        }
        //    x --------- y
        //   /           /
        // from ------- to
        // 已知 x/from = mul[from] 和 y/to = mul[to]，现在合并 from 和 to，新增信息 to/from = value
        // 由于 y/from = (y/x) * (x/from) = (y/to) * (to/from)
        // 所以 y/x = (y/to) * (to/from) / (x/from) = mul[to] * value / mul[from]
        mul[x] = mul[to] * value / mul[from];
        fa[x] = y;
    }
}
