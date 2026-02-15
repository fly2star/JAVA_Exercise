package DailyQuestion.Fourteenth;

public class question001 {
    public static void main(String[] args) {
        String a = "11", b = "1";
        System.out.println(addBinary(a, b));
        System.out.println(addBinary2(a, b));
    }


    // 方法1: 简单方法, 测试案例可能超过长度
    public static String addBinary(String a, String b) {
        int numA = Integer.parseInt(a, 2);
        int numB = Integer.parseInt(b, 2);
        String res = Integer.toString( numA + numB, 2);
        return res;
    }

    // 方法2: 双指针模拟法
    public static String addBinary2(String a, String b) {
        StringBuilder sb = new StringBuilder();
        int i = a.length() - 1;     // a的末尾
        int j = b.length() - 1;     // b的末尾
        int carry = 0;              // 进位

        while (i >= 0||j >= 0 || carry > 0) {
            int sum =carry;     // 先加上上一轮的进位

            if (i >= 0) {
                // 加上 a 的当前位
                sum += a.charAt(i--) - '0';
            }

            if (j >= 0) {
                // 加上 b 的当前位
                sum += b.charAt(j--) - '0';
            }

            sb.append(sum % 2);     // 当前位的结果（sum 为 2 或 3 时，当前位是 0 或 1）
            carry = sum / 2;        // 计算新的进位（sum >= 2 则进位为 1）
        }
        
        // 从后往前加的，最后需要翻转
        return sb.reverse().toString();
    }
}
