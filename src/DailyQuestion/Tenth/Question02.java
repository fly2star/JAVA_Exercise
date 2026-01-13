package DailyQuestion.Tenth;

import java.util.*;
import java.util.List;

/**
 * @author ccwwll
 * @version 1.0
 * create 2026-01-13-11:01
 **/
public class Question02 {
    public static void main(String[] args) {
        int[][] squares = {{0, 0, 2}, {1, 1, 1}};
        System.out.println(separateSquares2(squares));
    }

    // 方法1: 结果只能是自然数
    public static double separateSquares(int[][] squares) {
        int n = squares.length;
        double ans = 0d;
        // 提取所有的y坐标
        Set<Integer> ySet = new HashSet<>();
        for (int[] square : squares) {
            int y = square[1];
            int l = square[2];
            ySet.add(y);
            ySet.add(y + l);
        }

        // 将y坐标排序
        List<Integer> yCoords = new ArrayList<>(ySet);
        Collections.sort(yCoords);

        // 枚举每个候选 y 坐标
        for (int y : yCoords) {
            double aboveArea = 0.0d;
            double belowArea = 0.0d;
            for (int[] square : squares) {
                int yi = square[1];
                int li = square[2];
                int top = yi + li;

                if (yi >= y) {  // 完全在 y 上方
                    aboveArea += (double)li * li;
                } else if (top <= y) {  // 完全在 y 下方
                    belowArea += (double)li * li;
                } else {
                    aboveArea += (double)(top - y) * li;
                    belowArea += (double)(y - yi) * li;
                }
            }

            if (Math.abs(aboveArea - belowArea) < 1e-7) {
                return (double) y;
            }

        }

        return yCoords.get(yCoords.size() - 1);

    }

    // 方法2: 参考二分法
    public static double separateSquares2(int[][] squares) {
        double low = Double.MAX_VALUE;
        double high = Double.MIN_VALUE;

        // 确定二分的边界
        for (int[] square : squares) {
            low = Math.min(low, (double)square[1]);
            high = Math.max(high, (double)square[1] + square[2]);
        }

        // 二分查找 y 坐标, 先试着循环 100 次
        for (int i = 0; i < 100; i++) {
            double mid = (low + high) / 2.0d;
            if (calAboveArea(squares, mid) > calBelowArea(squares, mid)) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return low;

    }

    public static double calAboveArea(int[][] squares, double y) {
    /**
     * 计算 y 上方区域面积
     * */
        double area = 0.0d;
        for (int[] square : squares) {
            double yi = square[1];
            double li = square[2];
            double top = yi + li;
            if (top <= y ) continue;  // 完全在 y 下方
            if (yi >= y) {
                area += li * li;    // 完全在 y 上方
            } else {
                area += (top - y) * li;
            }
        }

        return area;

    }

    public static double calBelowArea(int[][] squares, double y) {
        /**
         * 计算 y 下方区域面积
         * */
        double area = 0.0d;
        for (int[] square : squares) {
            double yi = square[1];
            double li = square[2];
            double top = yi + li;
            if (yi >= y ) continue;  // 完全在 y 上方
            if (top <= y) {
                area += li * li;    // 完全在 y 上方
            } else {
                area += (y - yi) * li;
            }
        }

        return area;

    }
}
