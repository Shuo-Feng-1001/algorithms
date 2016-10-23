import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int n;
    private final WeightedQuickUnionUF uf;
    private final int virtualTopSite;
    private final int virtualBottomSite;
    private final boolean[][] grid;
    private boolean hasOpen;

    public Percolation(int n) {
        this.n = n;
        grid = new boolean[n][n];
        uf = new WeightedQuickUnionUF(n * n + 2);
        hasOpen = false;
        virtualTopSite = n * n;
        virtualBottomSite = n * n + 1;
        for (int i = 1; i <= n; i++) {
            uf.union(toIndex(1, i), virtualTopSite);
            uf.union(toIndex(n, i), virtualBottomSite);
        }
    }

    public void open(int row, int col) {
        outOfBoundCheck(row, col);
        if (!isOpen(row, col)) {
            grid[row-1][col-1] = true;
            hasOpen = true;
            int[][] coordinates = {{-1, 0}, {1, 0}, {0, 1}, {0, -1}};
            for (int[] point : coordinates) {
                if (isValidPoint(row + point[0], col + point[1]) && isOpen(row + point[0], col + point[1])) {
                    uf.union(toIndex(row + point[0], col + point[1]), toIndex(row, col));
                }
            }
        }
    }

    public boolean isOpen(int row, int col) {
        outOfBoundCheck(row, col);
        return grid[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {
        outOfBoundCheck(row, col);
        if (!isOpen(row, col)) {
            return false;
        }
        return uf.connected(toIndex(row, col), virtualTopSite);
    }
    public boolean percolates() {
        return hasOpen && uf.connected(virtualTopSite, virtualBottomSite);
    }

    /* utility function */
    // return the index in the 1-d array given
    // index i, j in the 2d array
    private int toIndex(int row, int col) {
        return n * (row-1) + col -1;
    }

    private boolean isInBound(int i) {
        return i >= 1 && i <= n;
    }

    private boolean isValidPoint(int row, int col) {
        return isInBound(col) && isInBound(row);
    }

    private void outOfBoundCheck(int row, int col) {
        if (!isValidPoint(row, col)) {
            throw new IndexOutOfBoundsException(String.format("%d or %d out of the bound 1-N (%d)",
                    row, col, n));
        }
    }

    public static void main(String[] args) {
        Percolation percolationTest = new Percolation(4);
        StdOut.println("should be false: " + percolationTest.percolates());
        percolationTest.open(1, 2);
        percolationTest.open(2, 2);
        percolationTest.open(3, 2);
        percolationTest.open(4, 2);
        StdOut.println("should be false: " + percolationTest.percolates());
        Percolation percolationTest2 = new Percolation(1);
        StdOut.println("should be false: " + percolationTest2.percolates());
    }
}
