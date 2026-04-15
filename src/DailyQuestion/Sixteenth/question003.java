package DailyQuestion.Sixteenth;

/*
2515. 到目标字符串的最短距离

给你一个下标从 0 开始的 **环形** 字符串数组 `words` 和一个字符串 `target`。**环形数组** 意味着数组首尾相连。

        形式上，`words[i]` 的下一个元素是 `words[(i + 1) % n]`，而 `words[i]` 的前一个元素是 `words[(i - 1 + n) % n]`，其中 n 是 `words` 的长度。

从 `startIndex` 开始，你一次可以用 1 步移动到下一个或者前一个单词。

返回到目标字符串 `target` 所需的最短距离。如果 `words` 中不存在字符串 `target`，返回 -1 。

*/
public class question003 {
    
    public static void main(String[] args) {
        String[] words = {"hello", "i", "am", "leetcode", "hello"};
        String target = "hello";
        int startIndex = 1;

        question003 sl003 = new question003();
        System.out.println(sl003.closestTarget(words, target, startIndex));
        
    }

    public int closestTarget(String[] words, String target, int startIndex) {
        
        int n = words.length;
        int ans = n;
        for (int i = 0; i < n; i++) {
            if (words[i].equals(target)) {
                int d = Math.abs(i - startIndex);
                int dist = Math.min(d, n - d);
                ans = Math.min(ans, dist);
            }
        }
        return ans == n ? -1 : ans;
    }
}
