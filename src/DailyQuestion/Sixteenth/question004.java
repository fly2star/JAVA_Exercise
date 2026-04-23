package DailyQuestion.Sixteenth;

/*
2078. 两栋颜色不同且距离最远的房子

街上有 n 栋房子整齐地排成一列，每栋房子都粉刷上了漂亮的颜色。给你一个下标从 0 开始且长度为 n 的整数数组 colors，其中 colors[i] 表示第 i 栋房子的颜色。

返回两栋颜色不同且房子之间的 **最大距离**。

第 i 栋房子和第 j 栋房子之间的距离是 `abs(i - j)`。

    -- n = colors.length
    -- 2 ≤ n ≤ 100
    -- 0 ≤ colors[i] ≤ 100
    -- 至少存在 2 栋颜色不同的房子
*/
public class question004 {
    public static void main(String[] args) {

        int[] colors = {1,1,1,6,1,1,1};

        question004 sl04 = new question004();
        System.out.println(sl04.maxDistance(colors));
        
    }

    public int maxDistance(int[] colors) {
        int n = colors.length;
        int left = 0;
        while (left < n && colors[left] == colors[n - 1]) {
            left++;
        }
        int right = n - 1;
        while (right >= 0 && colors[right] == colors[0]) {
            right--;
        }
        return Math.max(n - 1 - left, right);
    }
}
