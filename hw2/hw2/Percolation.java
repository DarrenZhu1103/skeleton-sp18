package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] data;
    private int numOfOpen = 0;
    private int size;
    private WeightedQuickUnionUF connectUnion, otherUnion;


    public Percolation(int N){
        if (N <= 0)
            throw new java.lang.IllegalArgumentException();
        size = N;
        data = new boolean[N][N];
        connectUnion = new WeightedQuickUnionUF(N * N + 2);
        otherUnion = new WeightedQuickUnionUF(N * N + 2);
        for (int i = 0; i < N; i++){
            connectUnion.union(0, numCaculation(0, i));
            otherUnion.union(0, numCaculation(0, i));
            connectUnion.union(N * N + 1, numCaculation(N - 1, i));
        }
    }

    private int numCaculation(int row, int col){
        return row * size + col + 1;
    }

    public void open(int row, int col){
        if (row >= 0 && row < size && col >= 0 && col < size) {
            if (!isOpen(row, col)) {
                numOfOpen++;
                data[row][col] = true;
                connectNeighbour(row, col);
            }
        }
        else
            throw new java.lang.IllegalArgumentException();
    }

    private void connectNeighbour(int row, int col){
        int num = numCaculation(row, col);
        if (row != 0 && isOpen(row - 1, col))
            connectTwo(num, num - size);
        if (col != 0 && isOpen(row, col - 1))
            connectTwo(num, num - 1);
        if (row != size - 1 && isOpen(row + 1, col))
            connectTwo(num, num + size);
        if (col != size - 1 && isOpen(row, col + 1))
            connectTwo(num, num + 1);
    }

    private void connectTwo(int i, int j){
        connectUnion.union(i, j);
        otherUnion.union(i, j);
    }

    public boolean isOpen(int row, int col){
        if (row >= 0 && row < size && col >= 0 && col < size)
            return data[row][col];
        else
            throw new java.lang.IllegalArgumentException();
    }

    public boolean isFull(int row, int col){
        if (row >= 0 && row < size && col >= 0 && col < size)
            return isOpen(row, col) && otherUnion.connected(0, numCaculation(row, col));
        else
            throw new java.lang.IllegalArgumentException();
    }

    public int numberOfOpenSites(){
        return numOfOpen;
    }

    public boolean percolates(){
        return connectUnion.connected(0, size * size + 1);
    }
}
