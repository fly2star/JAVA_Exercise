package LeetCode_Hot100;

/*
240. 搜索二维矩阵 II

编写一个高效的算法来搜索 `m × n` 矩阵 `matrix` 中的一个目标值 `target`。该矩阵具有以下特性：

-- 每行的元素从左到右升序排列。
-- 每列的元素从上到下升序排列。

提示:
    -- m = matrix.length
    -- n = matrix[i].length
    -- 1 ≤ n, m ≤ 300
    -- -10⁹ ≤ matrix[i][j] ≤ 10⁹
    -- 每行的所有元素从左到右升序排列
    -- 每列的所有元素从上到下升序排列
    -- -10⁹ ≤ target ≤ 10⁹
*/
public class question049 {
    public static void main(String[] args) {
        int[][] matrix = {{3}, {5}, {7}};
        System.out.println(searchMatrix(matrix, 7));
    }

    // 方法1: 从右上角开始搜索
    public static boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length; 
        int n = matrix[0].length;

        if (matrix == null || m == 0 || n == 0) {
            return false;
        }

        // 从右上角开始
        int row = 0, col = n - 1;
        while (row < m && col >= 0) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                // 当前元素太大, 向左移动 (消去当前列)
                col--;
            } else {
                // 当前元素太小, 向下移动 (消去当前行)
                row++;
            }
        }

        return false;
    }

    // 方法2: 从左下角开始搜索
    public static boolean searchMatrixLeftBottom(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        
        int m = matrix.length;
        int n = matrix[0].length;
        int row = m - 1, col = 0;  // 从左下角开始
        
        while (row >= 0 && col < n) {
            if (matrix[row][col] == target) {
                return true;
            } else if (matrix[row][col] > target) {
                // 当前元素太大, 向上移动 (排除当前行)
                row--;
            } else {
                // 当前元素太小, 向右移动 (排除当前列)
                col++;
            }
        }
        
        return false;
    }

    // 方法3: 逐行二分查找
    public static boolean searchMatrixBinarySearch(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        for(int[] row : matrix) {
            if (binarySearch(row, target)) {
                return true;
            }
        }

        return false;
    }

    private static boolean binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return true;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }

}
