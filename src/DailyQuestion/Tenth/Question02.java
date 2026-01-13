package DailyQuestion.Tenth;

import java.awt.*;
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
        System.out.println(f3453(squares));
    }

    public static double f3453(int[][] squares) {
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
}
