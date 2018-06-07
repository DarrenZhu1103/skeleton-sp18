package lab11.graphs;

import java.util.Stack;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean cycleFound;
    private int[] hideEdge;

    public MazeCycles(Maze m) {
        super(m);
    }

    @Override
    public void solve() {
        hideEdge = new int[maze.V()];
        hideEdge[0] = 0;
        dfs(0);
    }

    private void dfs(int start){
        marked[start] = true;
        announce();
        for (int i : maze.adj(start)){
            if (cycleFound) return;
            if (marked[i] && hideEdge[start] != i){
                drawRoute(i, start);
                cycleFound = true;
                return;
            }
            else if (!marked[i]) {
                hideEdge[i] = start;
                dfs(i);
            }
        }
    }

    // Helper methods go here
    private void drawRoute(int start, int end){
        edgeTo[start] = end;
        while (start != end){
            edgeTo[end] = hideEdge[end];
            end = edgeTo[end];
        }
        announce();
    }
}

