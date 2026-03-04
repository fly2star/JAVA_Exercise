package LeetCode_Hot100;

/*
48. 旋转图像

给定一个 $ n \times n $ 的二维矩阵 matrix 表示一个图像。请你将图像顺时针旋转 90 度。
你必须在原地旋转图像，这意味着你需要直接修改输入的二维矩阵。请不要使用另一个矩阵来旋转图像。
*/
public class question053 {
    public static void main(String[] args) {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        for (int[] is : matrix) {
            for (int x : is) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
        System.out.println("======================");
        rotate(matrix);
        for (int[] is : matrix) {
            for (int x : is) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
        System.out.println("======================");
        rotate2(matrix);
        for (int[] is : matrix) {
            for (int x : is) {
                System.out.print(x + " ");
            }
            System.out.println();
        }
    }

    // 方法1: 借用一个数组来存储元素
    public static void rotate(int[][] matrix) {
        int n = matrix.length;

        int[] nums = new int[n * n];
        int current = 0;
        for (int i = 0; i < n; i++) {
            for (int j = n - 1; j >= 0; j--) {
                nums[current++] = matrix[j][i];
            }
        }
        
        current = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = nums[current++];
            }
        }

    }

    // 方法2: 转置 + 反转
    public static void rotate2(int[][] matrix) {
        int n = matrix.length;

        // 转置矩阵
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        // 反转每一行
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int temp = matrix[i][j];
                matrix[i][j] = matrix[i][n - 1 -j];
                matrix[i][n - 1 - j] = temp;
            }
        }
    }

    // 方法3: 直接旋转 (四元素交换)
    public static void rotate3(int[][] matrix) {
        int n = matrix.length;

        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < n - 1 - j; j++) {
                // 保存左上角元素
                int temp = matrix[i][j];
                // 左上角 = 左下角
                matrix[i][j] = matrix[n - 1 - j][i];
                // 左下角 = 右下角
                matrix[n - 1 - j][i] = matrix[n - 1 - i][n - 1 - j];
                // 右下角 = 右上角
                matrix[n - 1 - i][n - 1 - j] = matrix[j][n - 1 - i];
                // 右上角 = 左上角（保存的temp）
                matrix[j][n - 1 - i] = temp;
            }
        }

    }
}
