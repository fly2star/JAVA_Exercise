package LeetCode_Hot100;

/*
96. 不同的二叉搜索树

给你一个整数 `n`，求恰由 `n` 个节点组成且节点值从 1 到 `n` 互不相同的 **二叉搜索树** 有多少种？
返回满足题意的二叉搜索树的种数。
*/
public class question085 {
    public static void main(String[] args) {
        
        question085 sl85 = new question085();
        System.out.println(sl85.numTrees(1));
        System.out.println(sl85.numTrees2(3));
    }

    // 方法1: 动态规划
    public int numTrees(int n) {

        // dp[i] 表示 i 个节点能组成的 BST 数量
        int[] dp = new int[n + 1];
        dp[0] = 1;  // 空树
        dp[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                // 以 j 为根, 左子树有 j-1 个节点, 右子树有 i-j 个节点
                dp[i] += dp[j - 1] * dp[i - j];
            }
        }

        return dp[n];
    }

    // 方法2: 数学方法 (卡特兰数)
    /**
     * 设 `G(n)` 表示 `n` 个节点能组成的 BST 数量。
     * 则:
     *      G(n)=\sum_{i=1}^{n} G(i-1) \times G(n-i)
     *          G(0)=1  (空树算一种)
     *          G(1)=1
     * 这正是 卡特兰数 的递推公式!
     * 
     *      卡特兰数第 n 项:
     *                  C_{n}=\dfrac{1}{n+1} \binom{2n}{n}
     * 
     * 利用卡特兰数通项公式:
     *      C_{n} = \dfrac{(2n)!}{(n+1)!n!}=\prod_{i=0}^{n-1}\dfrac{i+2}{2(2i+1)}
     * 
    */
    public int numTrees2(int n) {
        long catalan = 1;
        for (int i = 0; i < n; i++) {
            catalan = catalan * 2 * (2 * i + 1) / (i + 2);
        }
        return (int)catalan;
    }


}
