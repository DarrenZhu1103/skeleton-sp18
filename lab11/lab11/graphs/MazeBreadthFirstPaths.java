package lab11.graphs;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int start, end;
    private boolean isEndFound;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);

        start = m.xyTo1D(sourceX, sourceY);
        end = m.xyTo1D(targetX, targetY);
        distTo[start] = 0;
        edgeTo[start] = start;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        marked[start] = true;
        while (!isEndFound && !queue.isEmpty()){
            int position = queue.remove();
            for (int p : maze.adj(position)){
                if (!marked[p]){
                    edgeTo[p] = position;
                    announce();
                    marked[p] = true;
                    distTo[p] = distTo[position] + 1;
                    queue.add(p);
                    announce();
                    if (p == end)
                        isEndFound = true;
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

