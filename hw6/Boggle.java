import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import edu.princeton.cs.algs4.In;
import java.util.PriorityQueue;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    private static class Word implements Comparable<Word> {
        String word;

        Word(String word){
            this.word = word;
        }

        @Override
        public boolean equals(Object o){
            if (o == null)
                return false;
            return ((Word) o).word.equals(this.word);
        }

        @Override
        public int compareTo(Word o) {
            if (word.length() > o.word.length())
                return 1;
            else if (word.length() < o.word.length())
                return -1;
            else
                return word.compareTo(o.word);
        }
    }

    private static class QueueWithCapacity {
        Queue<Word> queue;
        int capacity;

        QueueWithCapacity(int capacity){
            this.capacity = capacity;
            queue = new PriorityQueue<>();
        }

        public void insert(String s){
            if (queue.contains(new Word(s)))
                return;
            if(queue.size() < capacity)
                queue.add(new Word(s));
            else if (queue.peek().word.length() < s.length()){
                queue.poll();
                queue.add(new Word(s));
            }
        }

        public List<String> getResult(){
            List<String> result = new LinkedList<>();
            while (!queue.isEmpty())
                ((LinkedList<String>) result).addFirst((queue.poll().word));
            return result;
        }
    }

    private static class Position{
        private int x;
        private int y;
        private char c;

        Position(int x, int y, char[][] tile, int row, int col){
            this.x = (x + row) % row;
            this.y = (y + col) % col;
            this.c = tile[this.x][this.y];
        }
    }

    public static List<String> solve(int k, String boardFilePath) {
        char[][] tile = getChar(boardFilePath);
        Trie dict = new Trie(dictPath);
        QueueWithCapacity queue = new QueueWithCapacity(k);

        boolean[][] marked = new boolean[tile.length][tile[0].length];

        for (int i = 0; i < tile.length; i++)
            for (int j = 0; j < tile[0].length; j++){
                boolean[][] copy = clone(marked);
                search(dict, String.valueOf(tile[i][j]), tile, i, j, queue, copy);
            }
        return queue.getResult();
    }

    private static boolean[][] clone(boolean[][] marked){
        boolean[][] result = new boolean[marked.length][marked[0].length];
        for (int i = 0; i < marked.length; i++)
            for (int j = 0; j < marked[0].length; j++)
                result[i][j] = marked[i][j];
        return result;
    }

    private static void search(Trie dict, String s, char[][] tile, int i, int j, QueueWithCapacity queue, boolean[][] marked){
        marked[i][j] = true;
        if (s.length() > dict.length || !dict.hasPath(s))
            return;
        if (dict.contains(s))
            queue.insert(s);

        for (Position p : getNeighbor(tile, i, j))
            if (!marked[p.x][p.y]) {
                boolean[][] copy = clone(marked);
                search(dict, s + p.c, tile, p.x, p.y, queue, copy);
            }
    }

    private static Position[] getNeighbor(char[][] tile, int i, int j){
        Position[] result = new Position[8];
        int row = tile.length;
        int col = tile[0].length;

        result[0] = new Position(i - 1, j, tile, row, col);
        result[1] = new Position(i + 1, j, tile, row, col);
        result[2] = new Position(i - 1, j - 1, tile, row, col);
        result[3] = new Position(i + 1, j - 1, tile, row, col);
        result[4] = new Position(i - 1, j + 1, tile, row, col);
        result[5] = new Position(i + 1, j + 1, tile, row, col);
        result[6] = new Position(i, j - 1, tile, row, col);
        result[7] = new Position(i, j + 1, tile, row, col);

        return result;
    }



    private static char[][] getChar(String boardFilePath){
        In in = new In(boardFilePath);
        String[] lines = in.readAllLines();


        char[][] result = new char[lines.length][lines[0].length()];
        for (int i = 0; i < result.length; i++) {
            String line = lines[i];
            if (line.length() != result[0].length)
                throw new IllegalArgumentException("Wrong Input");
            for (int j = 0; j < line.length(); j++)
                result[i][j] = line.charAt(j);
        }
        return result;
    }

    public static void main(String[] args){
        System.out.println(solve(7, "exampleBoard.txt"));
    }
}
