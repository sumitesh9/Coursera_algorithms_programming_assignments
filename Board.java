/* *****************************************************************************
 *  Name: Sumitesh Naithani
 *  Date: 23/11/18
 *  Description: API for implementing 8*8 puzzle
 *****************************************************************************/

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdRandom;

public class Board {
    private final int[][] board;
    private final int size;
    private int zi;
    private int zj;
    private Board aTwin;
    private int h;
    private int m;

    /* construct a board from an n-by-n array of blocks (where blocks[i][j] = block in row i, column j) */
    public Board(int[][] blocks) {
        this.size = blocks[0].length;
        this.board = new int[this.size][this.size];
        this.aTwin = null;

        for (int i = 0; i < blocks[0].length; i++) {
            for (int j = 0; j < blocks[0].length; j++) {
                if (blocks[i][j] == 0) {
                    this.zi = i;
                    this.zj = j;
                }
                this.board[i][j] = blocks[i][j];
            }
        }
    }

    private void deepCopy(int[][] from, int[][] to) {
        for (int i = 0; i < from[0].length; i++) {
            for (int j = 0; j < from[0].length; j++) {
                to[i][j] = from[i][j];
            }
        }
    }

    private void swap(int i1, int j1, int i2, int j2, int[][] a) {
        int temp = a[i1][j1];
        a[i1][j1] = a[i2][j2];
        a[i2][j2] = temp;
    }

    // board dimension n
    public int dimension() {
        return this.size;
    }

    // number of blocks out of place
    public int hamming() {
        int no = 0;

        for (int i = 0; i < this.size; i++)
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] == 0) {
                    continue;
                }
                else {
                    if (this.board[i][j] != i * size + j + 1) {
                        no++;
                    }
                }
            }
        return no;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int sum = 0;

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] != 0) {
                    int r, c;
                    r = (this.board[i][j] - 1) / this.size;
                    c = this.board[i][j] - 1 - r * size;

                    sum += (Math.abs(i - r) + Math.abs(j - c));
                }
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        for (int i = 0; i < this.size; i++) {
            if (i == size - 1) {
                for (int j = 0; j < this.size - 1; j++) {
                    if (this.board[i][j] != i * size + j + 1) {
                        return false;
                    }
                }
            }
            else {
                for (int j = 0; j < this.size; j++) {
                    if (this.board[i][j] != i * size + j + 1) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        if (this.aTwin == null) {
            int[][] twinBoard = new int[this.size][this.size];
            deepCopy(this.board, twinBoard);
            int i1, j1, i2, j2;

            i1 = 0;
            j1 = 0;

            while (i1 == this.zi && j1 == this.zj) {
                if (j1 == this.size - 1) {
                    j1 = 0;
                    i1++;
                } else {
                    j1++;
                }
            }

            i2 = 0;
            j2 = 1;

            while ((i2 == this.zi && j2 == this.zj) || (i2 == i1 && j2 == j1)) {
                if (j2 == this.size - 1) {
                    j2 = 0;
                    i2++;
                } else {
                    j2++;
                }
            }

            swap(i1, j1, i2, j2, twinBoard);
            this.aTwin = new Board(twinBoard);
        }

        return this.aTwin;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == this) {
            return true;
        }

        if (y == null) {
            return false;
        }

        if (y.getClass() != this.getClass()) {
            return false;
        }

        Board that = (Board) y;

        if (this.dimension() != that.dimension()) {
            return false;
        }

        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                if (this.board[i][j] != that.board[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        Queue<Board> ls = new Queue<>();
        int[][] tmp = new int[this.size][this.size];

        if (this.zj - 1 >= 0) {
            deepCopy(this.board, tmp);
            swap(this.zi, this.zj, this.zi, this.zj - 1, tmp);
            ls.enqueue(new Board(tmp));
        }

        if (this.zj + 1 < this.size) {
            deepCopy(this.board, tmp);
            swap(this.zi, this.zj, this.zi, this.zj + 1, tmp);
            ls.enqueue(new Board(tmp));
        }

        if (this.zi - 1 >= 0) {
            deepCopy(this.board, tmp);
            swap(this.zi, this.zj, this.zi - 1, this.zj, tmp);
            ls.enqueue(new Board(tmp));
        }

        if (this.zi + 1 < this.size) {
            deepCopy(this.board, tmp);
            swap(this.zi, this.zj, this.zi + 1, this.zj, tmp);
            ls.enqueue(new Board(tmp));
        }

        if (ls.isEmpty()) {
            return null;
        }
        else {
            return ls;
        }
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(this.size + "\n");
        for (int i = 0; i < this.size; i++) {
            for (int j = 0; j < this.size; j++) {
                s.append(String.format("%2d ", this.board[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public static void main(String[] args) {
        // Test here
        int[][] testBoard = new int[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                testBoard[i][j] = StdRandom.uniform(10) + 1;
            }
        }

        testBoard[StdRandom.uniform(3)][StdRandom.uniform(3)] = 0;
        Board test = new Board(testBoard);
        System.out.println("Zero at position (" + Integer.toString(test.zi) + ", " + Integer.toString(test.zj) + ")");
        System.out.println("Neighbors:");
        for (Board b : test.neighbors()) {
            System.out.println(b);
        }

        System.out.println("Twins:");

        System.out.println(test);
        System.out.println(test.twin());
    }
}