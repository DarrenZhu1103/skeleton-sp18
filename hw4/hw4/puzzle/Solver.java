package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Solver {
    private SearchNode target;

    private class SearchNode implements Comparable {
        private WorldState state;
        private int moves;
        private SearchNode previousNode;

        SearchNode(WorldState state, SearchNode previousNode){
            this.state = state;
            if (previousNode != null)
                this.moves = previousNode.moves + 1;
            this.previousNode = previousNode;
        }

        public int compareTo(Object other){
            SearchNode otherNode = (SearchNode) other;
            return moves + state.estimatedDistanceToGoal() - otherNode.moves - otherNode.state.estimatedDistanceToGoal();
        }
    }


    public Solver(WorldState initial){
        MinPQ<SearchNode> queue = new MinPQ<>();
        queue.insert(new SearchNode(initial, null));
        SearchNode p = queue.delMin();
        while (!p.state.isGoal()){
            for (WorldState w : p.state.neighbors())
                if (p.previousNode == null || !p.previousNode.state.equals(w)) {
                    queue.insert(new SearchNode(w, p));
                }
            p = queue.delMin();
        }
        target = p;
    }

    public int moves(){
        return target.moves;
    }

    public Iterable<WorldState> solution(){
        LinkedList<WorldState> result = new LinkedList<>();
        SearchNode p = target;
        while (p.previousNode != null){
            result.add(p.state);
            p = p.previousNode;
        }
        return result;
    }
}
