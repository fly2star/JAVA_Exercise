package LeetCode_Hot100;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author ccwwll
 * @version 1.0
 * create 2025-12-03-19:33
 **/
/*
200. 岛屿数量
*/
public class question011 {
    public static void main(String[] args) {
        char[][] grid = {{'1', '1', '1', '1', '0'},
                        {'1', '1', '0', '1', '0'},
                        {'1', '1', '0', '0', '0'},
                        {'0', '0', '0', '0', '0'}};
        System.out.println(numIsland(grid));
    }

    // 方法一：深度优先遍历
    public static int numIsland(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int m = grid.length;
        int n = grid[0].length;
        int isLandNum = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    fire(grid, i, j);
                    isLandNum++;
                }
            }
        }
        return isLandNum;
    }

    // 方法二：广度优先遍历
    //为了求出岛屿的数量，我们可以扫描整个二维网格。如果一个位置为 1，则将其加入队列，开始进行广度优先搜索。
    // 在广度优先搜索的过程中，每个搜索到的 1 都会被重新标记为 0。直到队列为空，搜索结束。

    public static int numIsland2(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int nr = grid.length;
        int nc = grid[0].length;
        int numIslands = 0;
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (grid[i][j] == '1') {
                    numIslands++;
                    grid[i][j] = '0';
                    Queue<Integer> neighbors = new LinkedList<>();
                    neighbors.add(i * nc + j);
                    while (!neighbors.isEmpty()) {
                        int id = neighbors.remove();
                        int row = id / nc;
                        int col = id % nc;
                        if (row - 1 >= 0 && grid[row - 1][col] == '1') {
                            neighbors.add((row - 1) * nc + col);
                            grid[row - 1][col] = '0';
                        }
                        if (row + 1 < nr && grid[row + 1][col] == '1') {
                            neighbors.add((row + 1) * nc + col);
                            grid[row + 1][col] = '0';
                        }
                        if (col - 1 >= 0 && grid[row][col - 1] == '1') {
                            neighbors.add(row * nc + col - 1);
                            grid[row][col - 1] = '0';
                        }
                        if (col + 1 < nc && grid[row][col + 1] == '1') {
                            neighbors.add(row * nc + col + 1);
                            grid[row][col + 1] = '0';
                        }
                    }
                }
            }
        }
        return numIslands;
    }

    // 方法三：并查集
    // 为了求出岛屿的数量，我们可以扫描整个二维网格。如果一个位置为 1，则将其与相邻四个方向上的 1 在并查集中进行合并。
    //最终岛屿的数量就是并查集中连通分量的数目。
    public static int numIsland3(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int nr = grid.length;       // 行数
        int nc = grid[0].length;    // 列数

        // 创建并查集，自动统计初始化陆地数量
        UnionFind uf = new UnionFind(grid);
        // 遍历每个格子
        for (int i = 0; i < nr; i++) {
            for (int j = 0; j < nc; j++) {
                if (grid[i][j] == '1') {
                    // 将当前格子记为0，避免重复处理
                    grid[i][j] = '0';

                    // 检查四个方向的邻居
                    // 注意：由于我们是从左上到右下遍历，
                    // 实际上只需要检查「下」和「右」即可避免重复，
                    // 但这里检查全部四个方向 + 标记为 '0' 也能正确工作

                    // 上方邻居(r-1,c)
                    if (i - 1 >= 0 && grid[i - 1][j] == '1') {
                        uf.union(i * nc + j, (i - 1) * nc + j);
                    }
                    // 下方邻居 (r+1,c)
                    if (j - 1 >= 0 && grid[i][j - 1] == '1') {
                        uf.union(i * nc + j, i * nc + j - 1);
                    }
                    // 左方邻居 (r,c-1)
                    if (i + 1 < nr && grid[i + 1][j] == '1') {
                        uf.union(i * nc + j, (i + 1) * nc + j);
                    }
                    // 右方邻居 (r,c+1)
                    if (j + 1 < nc && grid[i][j + 1] == '1') {
                        uf.union(i * nc + j, i * nc + j + 1);
                    }
                }
            }
        }

        // 返回岛屿数量
        return uf.getCount();

    }



    // ======================================方法区======================================
    public static void fire(char[][] grid, int i, int j){
        if (i < 0 || i >= grid.length || j < 0 || j >= grid[0].length || grid[i][j] != '1') {
            return;
        }
        grid[i][j] = '2';
        fire(grid, i + 1, j);
        fire(grid, i - 1, j);
        fire(grid, i, j + 1);
        fire(grid, i, j - 1);
    }
}



class UnionFind {
    int count;      // 连通分量个数
    int[] parent;   // parent[i] 表示第 i 个元素所指向的父节点
    int[] rank;     // rank[i] 表示以 i 为根的集合所表示的树的高度

    // 构造函数: 根据 grid 初始化并查集
    public UnionFind(char[][] grid) {
        count = 0;
        int m = grid.length;
        int n = grid[0].length;
        // 总共 m * n 个元素, (i,j) -> i * n + j
        parent = new int[m * n];
        rank = new int[m * n];
        // 遍历每个格子
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    // 如果是陆地，初始化为自己的根(独立岛屿)
                    parent[i * n + j] = i * n + j;
                    count++;    // 岛屿数量加1
                }
                // 无论是否陆地，都初始化为 rank (确保安全)
                rank[i * n + j] = 0;
            }
        }
    }

    // 查找 x 的根，并对路劲进行压缩
    public int find(int x) {
        if (parent[x] != x) {
            // 路劲压缩: 让 x 直接指向根
            parent[x] = find(parent[x]);
        }
        return parent[x];
    }

    // 合并 x 和 y 所在的集合
    public void union(int x, int y) {
        int rootx = find(x);
        int rooty = find(y);
        if (rootx != rooty) {
            // 按 rank 合并，把 rank 更小的树合并到 rank 更高的树中
            if (rank[rootx] > rank[rooty]) {
                parent[rooty] = rootx;
            } else if (rank[rootx] < rank[rooty]) {
                parent[rootx] = rooty;
            } else {
                // rank 相等时，任意合并，被添加的那棵树的 rank 加 1
                parent[rooty] = rootx;
                rank[rootx]++;
            }
            // 合并后，连通分量(岛屿数量)减 1
            count--;
        }
    }

    // 获取连通分量个数(岛屿数量)
    public int getCount() {
        return count;
    }
}