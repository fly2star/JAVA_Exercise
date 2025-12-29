package Eighth;

import java.util.*;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-29-18:30
 **/
/*
756. 金字塔转换矩阵
你正在把积木堆成金字塔。每个块都有一个颜色，用一个字母表示。每一行的块比它下面的行 少一个块 ，并且居中。

为了使金字塔美观，只有特定的 三角形图案 是允许的。一个三角形的图案由 两个块 和叠在上面的 单个块 组成。
模式是以三个字母字符串的列表形式 allowed 给出的，其中模式的前两个字符分别表示左右底部块，第三个字符表示顶部块。
    例如，"ABC" 表示一个三角形图案，其中一个 “C” 块堆叠在一个 'A' 块(左)和一个 'B' 块(右)之上。
    请注意，这与 "BAC" 不同，"B" 在左下角，"A" 在右下角。
你从作为单个字符串给出的底部的一排积木 bottom 开始，必须 将其作为金字塔的底部。

在给定 bottom 和 allowed 的情况下，如果你能一直构建到金字塔顶部，
使金字塔中的 每个三角形图案 都是在 allowed 中的，则返回 true ，否则返回 false 。
*/
public class Question01 {
    public static void main(String[] args) {
        String bottom = "BCD";
        List<String> allowed = List.of("BCC", "CDE", "CEA", "FFF");
//        List<String> allowed = new ArrayList<>();
//        allowed.add("BCC");
//        allowed.add("CDE");
//        allowed.add("CEA");
//        allowed.add("FFF");
        boolean res = pyramidTransition(bottom, allowed);
        System.out.println(res);
    }

    // 方法1:回溯法
    public static boolean pyramidTransition(String bottom, List<String> allowed) {
        // 三角形底部两个字母 -> [三角形顶部字母]
        List<Character>[][] groups = new ArrayList[6][6];
        for (List<Character>[] row : groups) {
            // Arrays.setAll(row, _ -> new ArrayList<>()); _ 不再是合法的变量名
            Arrays.setAll(row, ignored -> new ArrayList<>());
        }
        for (String Str : allowed) {
            char[] s = Str.toCharArray();
            groups[s[0] - 'A'][s[1] - 'A'].add(s[2]);
        }

        int n = bottom.length();
        char[][] pyramid = new char[n][n];
        for (int i = 0; i < n - 1; i++) {
            pyramid[i] = new char[i + 1];
        }
        pyramid[n-1] = bottom.toCharArray();

        // 从倒数第二行开始填
        return dfs1(n - 2, 0, pyramid, groups);


    }

    // 现在准备填 (i, j) 这个格子
    // 返回继续填能否填完所有格子（从下往上填，每行从左到右填）
    private static boolean dfs1(int i, int j, char[][] pyramid, List<Character>[][] groups) {
        if (i < 0) { // 所有格子都已填完
            return true;
        }

        if (j == i + 1) { // i 行已填完
            return dfs1(i - 1, 0, pyramid, groups); // 开始填 i-1 行
        }

        // 枚举 (i, j) 填什么字母
        // 这取决于 (i+1, j) 和 (i+1, j+1) 填的字母
        for (char top : groups[pyramid[i + 1][j] - 'A'][pyramid[i + 1][j + 1] - 'A']) {
            pyramid[i][j] = top;
            if (dfs1(i, j + 1, pyramid, groups)) {
                return true;
            }
        }
        return false;
    }

    // 方法2:对方法1进行优化, 减少重复搜索
    public static boolean pyramidTransition2(String bottom, List<String> allowed) {
        // 三角形底部两个字母 -> [三角形顶部字母]
        List<Character>[][] groups = new ArrayList[6][6];
        for (List<Character>[] row : groups) {
            // Arrays.setAll(row, _ -> new ArrayList<>()); _ 不再是合法的变量名
            Arrays.setAll(row, ignored -> new ArrayList<>());
        }
        for (String Str : allowed) {
            char[] s = Str.toCharArray();
            groups[s[0] - 'A'][s[1] - 'A'].add(s[2]);
        }

        int n = bottom.length();
        char[][] pyramid = new char[n][n];
        for (int i = 0; i < n - 1; i++) {
            pyramid[i] = new char[i + 1];
        }
        pyramid[n-1] = bottom.toCharArray();

        Set<String> vis = new HashSet<>();  // 访问标记

        return dfs2(n - 2, 0, pyramid, vis, groups);
    }

    private static boolean dfs2(int i, int j, char[][] pyramid, Set<String> vis, List<Character>[][] groups) {
        if (i < 0) {
            return true;
        }

        if (j == i + 1) {
            String row = new String(pyramid[i]);
            if (!vis.add(row)) { // 这一行之前填过一模一样的，继续填，没能填到塔顶
                return false; // 直接返回
            }
            return dfs2(i - 1, 0, pyramid, vis, groups);
        }

        for (char top : groups[pyramid[i + 1][j] - 'A'][pyramid[i + 1][j + 1] - 'A']) {
            pyramid[i][j] = top;
            if (dfs2(i, j + 1, pyramid, vis, groups)) {
                return true;
            }
        }
        return false;
    }

    // 方法3: 进一步优化
    public static boolean pyramidTransition3(String bottom, List<String> allowed) {
        // 三角形底部两个字母 -> [三角形顶部字母]
        List<Character>[][] groups = new ArrayList[6][6];
        for (List<Character>[] row : groups) {
            // Arrays.setAll(row, _ -> new ArrayList<>()); _ 不再是合法的变量名
            Arrays.setAll(row, ignored -> new ArrayList<>());
        }
        for (String Str : allowed) {
            char[] s = Str.toCharArray();
            groups[s[0] - 'A'][s[1] - 'A'].add(s[2]);
        }

        int n = bottom.length();
        char[][] pyramid = new char[n][n];
        for (int i = 0; i < n - 1; i++) {
            pyramid[i] = new char[i + 1];
        }
        pyramid[n-1] = bottom.toCharArray();

        Set<String> vis = new HashSet<>();  // 访问标记

        return dfs3(n - 2, 0, pyramid, vis, groups);
    }

    private static boolean dfs3(int i, int j, char[][] pyramid, Set<String> vis, List<Character>[][] groups) {
        if (i < 0) {
            return true;
        }

        String row = new String(pyramid[i], 0, j);
        if (vis.contains(row)) { // 之前填过一模一样的，这个局部的金字塔无法填完
            return false; // 继续递归也无法填完，直接返回
        }

        if (j == i + 1) {
            vis.add(row);
            return dfs3(i - 1, 0, pyramid, vis, groups);
        }

        for (char top : groups[pyramid[i + 1][j] - 'A'][pyramid[i + 1][j + 1] - 'A']) {
            pyramid[i][j] = top;
            if (dfs3(i, j + 1, pyramid, vis, groups)) {
                return true;
            }
        }
        return false;
    }



}
