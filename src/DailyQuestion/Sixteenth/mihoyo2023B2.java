package DailyQuestion.Sixteenth;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

/*
米小游定义一个字符串的 权值 为该字符串的 字母种类数量 。  
例如：
- `"hmh"` 的权值为 2（含 `'h'`, `'m'`），
- `"yyy"` 的权值为 1（仅 `'y'`）。

现在米小游给了你三个非负整数 x, y, z，她希望你构造一个长度为 x + y + z + 2 的字符串，该字符串 仅由字符 'm'、'h'、'y' 组成，并满足：

> 在该字符串包含的所有长度为 3 的子串中：
> - 恰好有 $x$ 个子串的权值为 1，
> - 恰好有 $y$ 个子串的权值为 2，
> - 恰好有 $z$ 个子串的权值为 3。

你能帮帮她吗？

*/
public class mihoyo2023B2 {
    public static void main(String[] args) throws IOException{
        // 使用快速 I/O 提升读取和输出速度
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(System.out);

        String line = br.readLine();
        if (line == null) {
            return;
        }
        int t = Integer.parseInt(line.trim());
        
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            // 步骤 1 : 判断无法过渡的死局情况
            if (x > 0 && z > 0 && y == 0) {
                out.println("-1");
                continue;
            }

            // 使用 StringBuilder 高效拼接字符串
            StringBuilder sb = new StringBuilder();
            
            // 步骤 2 : 如果需要构造全职为 1 的子串
            if (x > 0) {
                sb.append("mm");    // 放好前置基础
                // 追加 x 个 'm'，产生 x 个权值为 1 的子串
                for (int i = 0; i < x; i++) {
                    sb.append('m');
                }

                // 步骤 3：如果还需要构造权值为 2 的子串
                if (y > 0) {
                    sb.append('h');     // 第一次打破相同字符，消耗掉 1 个 y
                    y--;

                    // 剩余的 y 个，通过交替 'm' 和 'h' 来稳定生成权值 2 的子串
                    for (int i = 0; i < y; i++) {
                        if (sb.charAt(sb.length() - 1) == 'h') {
                            sb.append('m');
                        } else {
                            sb.append('h');
                        }
                    }
                }
            }
            // 如果压根不需要权值为 1 的子串，直接从权值 2 甚至权值 3 开始
            else {
                sb.append("mh");

                if (y > 0) {
                    // 对于 y 个权值为 2 的要求，同理进行交替追加
                    for (int i = 0; i < y; i++) {
                        if (sb.charAt(sb.length() - 1) == 'h') {
                            sb.append('m');
                        } else {
                            sb.append('h');
                        }
                    }
                }
            }

            // 步骤 4：无论前面的基础如何，只要需要构造权值为 3 的子串，就在最后操作
            if (z > 0) {
                for (int i = 0; i < z; i++) {
                    // 获取与末尾两个字符都不相同的那个字符
                    char c = getDiffChar(sb.charAt(sb.length() - 1), sb.charAt(sb.length() - 2));
                    sb.append(c);
                }
            }

            // 打印该次询问的结果
            out.println(sb.toString());
        }

        // 刷新缓冲区，输出结果
        out.flush();
        out.close();
        br.close();

    }

    /**
     * 辅助方法：返回一个不同于 c1 和 c2 的新字符 (仅在 'm', 'h', 'y' 中选择)
     * 因为我们保证在调用此方法时，c1 和 c2 一定不相同，所以只需判断它们占用了哪两个即可。
     */
    private static char getDiffChar(char c1, char c2) {
        if ((c1 == 'm' && c2 == 'h') || (c1 == 'h' && c2 == 'm')) return 'y';
        if ((c1 == 'm' && c2 == 'y') || (c1 == 'y' && c2 == 'm')) return 'h';
        return 'm'; // 如果占用了 'h' 和 'y'，那剩下的只能是 'm'
    }
}
