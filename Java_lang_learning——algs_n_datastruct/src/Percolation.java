import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
//  包algs4.jar要重新下载，放好存好，方便到时调用
    private int n;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;
    private int virtualTop;
    private int virtualBottom;
    private int openSites;

    //构造函数：初始节点全闭合
    public Percolation(int n) {
        // Initialize the grid with all sites blocked
        // Use a 2D array or similar structure to represent the grid
        // Ensure that n is a positive integer
        if (n <= 0)  throw new IllegalArgumentException("Grid size must be greater than 0");
        
        // Initialize the grid here
        this.n = n; //this相当于python中的self
        this.grid = new boolean[n][n];
        this.uf = new WeightedQuickUnionUF(n * n + 2); //创建虚拟顶节点与底节点
        this.virtualTop = n * n;  //原二维数组内共有n*n节点，末节点【n * n - 1】
        this.virtualBottom = n * n + 1;
        this.openSites = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false; //represents blocked sites
            }
        }
    }

    //检查输入坐标是否超出限定范围
    private void validate(int row, int col){
        if(row<1 || row>n || col<1 || col>n)
            throw new IllegalArgumentException("row/col out of bounds");
    }

    //将二维数组坐标映射到一维数组
    private int xyTo1D(int row, int col){
        return row * n + col;
    }

    //先检测周边是否有连通点，无则以上方点为父节点
    // opens the sites (row col) if it is not open already
    public void open(int row, int col){
        validate(row, col);
        int i = row-1, j = col-1; // Convert to 0-based index
        if (grid[i][j]) return;  // 已开
        grid[i][j] = true;
        openSites++;
        int index = xyTo1D(i, j);

        // 与虚拟顶部/底部相连通
        if (row == 1) uf.union(index, virtualTop);
        if (row == n) uf.union(index, virtualBottom);

        // 与四周已开放格点连通
        // 连接相邻开放格子
        int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
        for (int[] d : dirs){
            int ni = i + d[0], nj = j + d[1];
            if (ni >= 0 && ni < n && nj >= 0 && nj < n && grid[ni][nj]){
                uf.union(index, xyTo1D(ni, nj));
            }
        }
    }

    //节点(row col)是否开放?
    public boolean isOpen(int row, int col) {
        // Check if the site at (row, col) is open
        // Return true if it is open, false otherwise
        // Ensure that row and col are within bounds
        //return false; // Placeholder return value
        validate(row, col);
        return grid[row-1][col-1]; 
    }

    //判断格点是否与顶部连通
    public boolean isFull(int row, int col){
        validate(row, col);
        int index = xyTo1D(row-1, col-1);
        return uf.find(index) == uf.find(virtualTop);
    }

    //返回当前开放格点的数量
    public int numberOfOpenSites(){
        return openSites;
    }

    //判断系统是否渗流
    public boolean percolates(){
        return uf.find(virtualTop) == uf.find(virtualBottom);
    }

    //test client (optional)
    public static void main(String[] args) {
        // You can test the Percolation class here
        // Create an instance of Percolation and perform operations
        Percolation perc = new Percolation(5);
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(5, 1);
        perc.open(3, 1);
        perc.open(4, 1);
        
        System.out.println("Percolates: " + perc.percolates()); // Should print true
        System.out.println("Number of open sites: " + perc.numberOfOpenSites()); // Should print 5
    }
}
