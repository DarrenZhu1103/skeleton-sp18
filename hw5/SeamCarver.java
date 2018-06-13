import java.awt.Color;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private Picture picture;

    private static class Node {
        double energy;
        double totalEnergy;
        int x, y;
        Node p;

        Node(double energy, int i, int j){
            this.energy = energy;
            this.totalEnergy = Double.MAX_VALUE;
            this.x = i;
            this.y = j;
        }

        public void set(Node p){
            if (p == null) {
                totalEnergy = energy;
                return;
            }
            this.p = p;
            this.totalEnergy = p.totalEnergy + energy;
        }

        public void swap(){
            int temp = this.x;
            this.x = this.y;
            this.y = temp;
        }
    }

    public SeamCarver(Picture picture) {
        this.picture = picture;
    }

    public Picture picture() {
        return picture;
    }

    public int width() {
        return picture.width();
    }

    public int height() {
        return picture.height();
    }

    public double energy(int x, int y) {
        if (x < 0 || x >= width() || y < 0 || y >= height())
            throw new java.lang.IndexOutOfBoundsException();
        Color up = picture.get(x, Math.floorMod(y - 1, height()));
        Color down = picture.get(x, (y + 1) % height());
        Color left = picture.get(Math.floorMod(x - 1, width()) ,y);
        Color right = picture.get((x + 1) % width(), y);
        double enX = Math.pow(left.getRed() - right.getRed(), 2) + Math.pow(left.getBlue() - right.getBlue(), 2)
                + Math.pow(left.getGreen() - right.getGreen(), 2);
        double enY = Math.pow(up.getRed() - down.getRed(), 2) + Math.pow(up.getBlue() - down.getBlue(), 2)
                + Math.pow(up.getGreen() - down.getGreen(), 2);
        return enX + enY;
    }

    public int[] findHorizontalSeam() {
        Node[][] mat = getMatrix();
        updateMat(mat);
        return getSeam(mat);
    }

    public int[] findVerticalSeam()  {
        Node[][] mat = getMatrix();
        mat = transpose(mat);
        updateMat(mat);
        return getSeam(mat);
    }

    private Node[][] getMatrix(){
        Node[][] mat = new Node[picture.height()][picture.width()];
        for (int i = 0; i < mat[0].length; i++) {
             for (int j = 0; j < mat.length; j++) {
                mat[j][i] = new Node(energy(i, j), i, j);
            }
        }
        return mat;
    }

    private void updateMat(Node[][] mat){
        for (int i = 0; i < mat[0].length; i++)
            for (int j = 0; j < mat.length; j++) {
                if (i > 0)
                    mat[j][i].set(getNode(mat, i, j));
                else
                    mat[j][i].set(null);
            }
    }

    private Node getNode(Node[][] mat, int i, int j){
        Node p = mat[j][i - 1];
        if (j > 0 && mat[j - 1][i - 1].totalEnergy < p.totalEnergy)
            p = mat[j - 1][i - 1];
        if (j < mat.length - 1 && mat[j + 1][i - 1].totalEnergy < p.totalEnergy)
            p = mat[j + 1][i - 1];
        return p;
    }

    private int[] getSeam(Node[][] mat) {
        double m = Double.MAX_VALUE;
        Node p = null;
        for (int i = 0; i < mat.length; i++){
            if (mat[i][mat[0].length - 1].totalEnergy < m) {
                p = mat[i][mat[0].length - 1];
                m = mat[i][mat[0].length - 1].totalEnergy;
            }
        }
        int[] result = new int[mat[0].length];
        for (int i = result.length - 1; i >= 0; i--){
            result[i] = p.y;
            p = p.p;
        }
        return result;
    }

    private Node[][] transpose(Node[][] mat){
        Node[][] result = new Node[mat[0].length][mat.length];
        for (int i = 0; i < mat.length; i++)
            for (int j = 0; j < mat[0].length; j++) {
                result[j][i] = mat[i][j];
                result[j][i].swap();
            }
        return result;
    }

    public void removeHorizontalSeam(int[] seam) {
        if (seam.length != picture.width())
            throw new java.lang.IllegalArgumentException();
        SeamRemover.removeHorizontalSeam(picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        if (seam.length != picture.height())
            throw new java.lang.IllegalArgumentException();
        SeamRemover.removeVerticalSeam(picture, seam);
    }
}
