package hw4.puzzle;

import java.util.LinkedList;

public class Board implements WorldState {
    private int[][] tiles;
    private int size;
    private boolean distanceGet;
    private int distance;

    public Board(int[][] tiles){
        size = tiles.length;
        this.tiles = tiles;
    }

    public int tileAt(int i, int j){
        if (i >= 0 && i < size && j >= 0 && j < size)
            return tiles[i][j];
        throw new IndexOutOfBoundsException();
    }

    public int size(){
        return size;
    }

    public Iterable<WorldState> neighbors() {
        int x = 0, y = 0;
        loop:
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tileAt(i, j) == 0) {
                    x = i;
                    y = j;
                    break loop;
                }
            }
        }
        LinkedList<WorldState> result = new LinkedList<>();
        if (x > 0){
            int[][] t = tilesCopy(tiles);
            t[x][y] = t[x - 1][y];
            t[x - 1][y] = 0;
            result.add(new Board(t));
        }
        if (y > 0){
            int[][] t = tilesCopy(tiles);
            t[x][y] = t[x][y - 1];
            t[x][y - 1] = 0;
            result.add(new Board(t));
        }
        if (x < size - 1){
            int[][] t = tilesCopy(tiles);
            t[x][y] = t[x + 1][y];
            t[x + 1][y] = 0;
            result.add(new Board(t));
        }
        if (y < size - 1){
            int[][] t = tilesCopy(tiles);
            t[x][y] = t[x][y + 1];
            t[x][y + 1] = 0;
            result.add(new Board(t));
        }
        return result;
    }

    private int[][] tilesCopy(int[][] tiles){
        int[][] result = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                result[i][j] = tiles[i][j];
            }
        }
        return result;
    }

    public int hamming(){
        int result = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (tileAt(i, j) != 0 && tileAt(i, j) != i + 3 * j + 1)
                    result += 1;
        return result;
    }

    public int manhattan(){
        int result = 0;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (tileAt(i, j) != 0)
                    result += Math.abs(j - toX(tileAt(i, j))) +  Math.abs(i - toY(tileAt(i, j)));
        return result;
    }

    private int toX(int n) {
        return (n - 1) % size;
    }

    private int toY(int n){
        return (n - 1) / size;
    }

    public int estimatedDistanceToGoal(){
        if (!distanceGet){
            distance = manhattan();
            distanceGet = true;
        }
        return distance;
    }

    public boolean equals(Object y){
        if (y == null || y.getClass() != this.getClass())
            return false;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                if (tileAt(i, j) != ((Board) y).tileAt(i, j))
                    return false;
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
