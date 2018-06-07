package lab11.graphs;

import edu.princeton.cs.algs4.In;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        marked[s] = true;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked(int s) {
        int minLength = Integer.MAX_VALUE;
        int result = -1;
        for (int i : maze.adj(s)){
            int length = h(i);
            if (length < minLength && !marked[i]){
                minLength = length;
                result = i;
            }
        }
        return result;

        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        if (s == t) return;
        int nextNode = findMinimumUnmarked(s);
        if (nextNode == -1) {
            astar(edgeTo[s]);
            return;
        }
        distTo[nextNode] = distTo[s] + 1;
        edgeTo[nextNode] = s;
        marked[nextNode] = true;
        announce();
        astar(nextNode);
    }

    @Override
    public void solve() {
        astar(s);
    }

}

