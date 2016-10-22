import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int N;
    private final WeightedQuickUnionUF uf;
    private final int virtualTopSite;
    private final int virtualBottomSite;
    private final boolean[][] grid;

    public Percolation(int n){
        N = n;
        grid = new boolean[N][N];
        uf = new WeightedQuickUnionUF(N * N + 2);
        virtualTopSite = N * N;
        virtualBottomSite = N * N + 1;
        for(int i = 1; i <= N; i++){
            uf.union(toIndex(1, i), virtualTopSite);
            uf.union(toIndex(N, i), virtualBottomSite);
        }
    }
    public void open(int row, int col){

    }
    public boolean isOpen(int row, int col){
        return grid[row-1][col-1];
    }

    public boolean isFull(int row, int col){
        return false;
    }
    public boolean percolates(){
        return false;
    }

    /*utility function*/
    // return the index in the 1-d array given
    // index i, j in the 2d array
    private int toIndex(int i, int j){
        return N * (i-1) + i -1;
    }
}
