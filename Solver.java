/* *****************************************************************************
 *  Name: Sumitesh Naithani
 *  Date: 23/11/18
 *  Description: Java class solver
 *****************************************************************************/
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class Solver {
    private SearchNode finalNode;
    private boolean solvable;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board current;
        private final int steps;
        private final SearchNode previous;

        public SearchNode(Board c) {
            this.current = c;
            this.steps = 0;
            this.previous = null;
        }

        public SearchNode(Board c, int s, SearchNode prev) {
            this.current = c;
            this.steps = s;
            this.previous = prev;
        }

        public Board getCurrent() { return this.current; }
        public int getSteps() { return this.steps; }
        public SearchNode getPrevious() { return this.previous; }
        public int getPriority() { return this.current.manhattan() + this.steps; }

        public int compareTo(SearchNode that) {
            return this.getPriority() - that.getPriority();
        }
    }

    public Solver(Board initial) {
        if (initial == null) {
            throw new java.lang.IllegalArgumentException();
        }

        SearchNode initialNode = new SearchNode(initial);
        MinPQ<SearchNode> pq;
        pq = new MinPQ<>();
        pq.insert(initialNode);

        SearchNode twinSolution = new SearchNode(initial.twin());
        MinPQ<SearchNode> pqTwin = new MinPQ<>();
        pqTwin.insert(twinSolution);

        while (true) {
            SearchNode temp, twinTemp;

            temp = pq.delMin();
            twinTemp = pqTwin.delMin();

            if (temp.getCurrent().isGoal()) {
                this.finalNode = temp;
                this.solvable = true;
                break;
            }
            else if (twinTemp.getCurrent().isGoal()) {
                this.solvable = false;
                break;
            }
            else {
                for (Board n : temp.getCurrent().neighbors()) {
                    if (temp.getPrevious() == null) {
                        pq.insert(new SearchNode(n, temp.getSteps() + 1, temp));
                    }
                    else {
                        if (!n.equals(temp.getPrevious().getCurrent())) {
                            pq.insert(new SearchNode(n, temp.getSteps() + 1, temp));
                        }
                    }
                }
                for (Board n : twinTemp.getCurrent().neighbors()) {
                    if (twinTemp.getPrevious() == null) {
                        pqTwin.insert(new SearchNode(n, twinTemp.getSteps() + 1, twinTemp));
                    }
                    else {
                        if (!n.equals(twinTemp.getPrevious().getCurrent())) {
                            pqTwin.insert(new SearchNode(n, twinTemp.getSteps() + 1, twinTemp));
                        }
                    }
                }
            }
        }
    }
    // find a solution to the initial board (using the A* algorithm)

    public boolean isSolvable() {
        return this.solvable;
    }
    // is the initial board solvable?

    public int moves() {
        if (this.isSolvable()) {
            return this.finalNode.getSteps();
        }
        else {
            return -1;
        }
    }
    // min number of moves to solve initial board; -1 if unsolvable

    public Iterable<Board> solution() {
        ArrayList<Board> seq = new ArrayList<>();

        if (!this.isSolvable()) {
            return null;
        }
        else {
            SearchNode temp = this.finalNode;

            while (temp != null) {
                seq.add(0, temp.getCurrent());
                temp = temp.getPrevious();
            }
        }

        return seq;
    }
    // sequence of boards in a shortest solution; null if unsolvable

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}

