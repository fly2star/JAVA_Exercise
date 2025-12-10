package LeetCode_Hot100;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-01-21:46
 **/
/*
207. 课程表
你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] ，
表示如果要学习课程 ai 则 必须 先学习课程  bi 。
    例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
*/
// 判断有无环
public class question09 {
    public static void main(String[] args) {
        int numCourses = 2;
        int[][] requisites = {{1,0}};
        System.out.println(canFinish(numCourses, requisites));

    }


    static List<List<Integer>> edges_1;
    static int[] visited;
    static boolean valid = true;
    // 方法1: 深度优先遍历
    public static boolean canFinish(int numCourses, int[][] requisites) {
        edges_1 = new ArrayList<List<Integer>>();
        for (int i = 0; i < numCourses; i++) {
            edges_1.add(new ArrayList<Integer>());
        }
        visited = new int[numCourses];
        for (int[] info : requisites) {
            edges_1.get(info[1]).add(info[0]);
        }
        for (int i = 0; i < numCourses && valid; i++) {
            if (visited[i] == 0) {
                dfs(i);
            }
        }
        return valid;

    }


    static List<List<Integer>> edges_2;
    static int[] indeg;
    // 方法2: 广度优先遍历
    public static boolean canFinish2(int numCourses, int[][] requisites) {
        edges_2 = new ArrayList<List<Integer>>();
        for (int i = 0; i < numCourses; i++) {
            edges_2.add(new ArrayList<Integer>());
        }
        indeg = new int[numCourses];
        for (int[] info : requisites) {
            edges_2.get(info[1]).add(info[0]);
            indeg[info[0]]++;
        }

        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < numCourses; i++) {
            if (indeg[i] == 0) {
                queue.offer(i);
            }
        }

        int visited = 0;
        while (!queue.isEmpty()) {
            visited++;
            int u = queue.poll();
            for (int v : edges_2.get(u)) {
                indeg[v]--;
                if (indeg[v] == 0) {
                    queue.offer(v);
                }
            }
        }
        return visited == numCourses;
    }

    public static boolean canFinish3(int numCourses, int[][] prerequisites) {
        // 入度数组，用于记录每门课程的入度
        int[] inDegree = new int[numCourses];
        // 邻接表，存储每门课程的后续课程
        List<List<Integer>> adjList = new ArrayList<>();
        for (int i = 0; i < numCourses; i++) {
            adjList.add(new ArrayList<>());
        }

        // 计算每门课程的入度，并构建邻接表
        for (int[] prerequisite : prerequisites) {
            int course = prerequisite[0];
            int preCourse = prerequisite[1];
            inDegree[course]++;
            adjList.get(preCourse).add(course);
        }

        // 存储入度为 0 的课程的队列
        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        // 记录已完成课程的数量
        int count = 0;
        while (!queue.isEmpty()) {
            int selectedCourse = queue.poll();
            count++;
            // 获取当前课程的后续课程列表
            List<Integer> nextCourses = adjList.get(selectedCourse);
            for (int nextCourse : nextCourses) {
                // 后续课程的入度减 1
                inDegree[nextCourse]--;
                if (inDegree[nextCourse] == 0) {
                    queue.offer(nextCourse);
                }
            }
        }

        // 如果已完成课程的数量等于总课程数，则可以完成所有课程
        return count == numCourses;
    }




    // ====================================方法区======================================
    public static void dfs(int u) {
        visited[u] = 1;
        for (int v : edges_1.get(u)) {
            if (visited[v] == 0) {
                dfs(v);
                if (!valid) return;
            } else if (visited[v] == 1) {
                valid = false;
                return;
            }
        }
        visited[u] = 2;
    }
}
