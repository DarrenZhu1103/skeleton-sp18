package byog.lab5;
import javafx.geometry.Pos;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final Random randomNumGenerator = new Random();
    private static final int WIDTH = 40;
    private static final int HEIGHT = 40;

    private static class Position{
        public int x;
        public int y;

        Position(int x, int y){
            this.x = x;
            this.y = y;
        }
    }

    private static void drawNumOfHex(TETile[][] tiles, Position p, int size, int n){
        for (int i = 0; i < n; i++){
            drawHex(tiles, p, size);
            p = new Position(p.x, p.y + 2 * size);
        }
    }

    private static void drawHex(TETile[][] tiles, Position p, int size){
        TETile t = randomTile();
        for (int i = 0; i < 2 * size; i++){
            int length = lengthOf(size, i);
            addRow(tiles, p, length, t);
            p = nextStart(p, size, i);
        }
    }

    private static void addRow(TETile[][] tiles, Position p, int length, TETile t){
        for (int i = p.x, j = p.y; i < p.x + length; i++){
            tiles[i][j] = t;
        }
    }

    private static Position nextStart(Position p, int size, int n){
        if (n >= size){
            return new Position(p.x + 1, p.y + 1);
        }
        else if (n == size - 1){
            return new Position(p.x, p.y + 1);
        }
        return new Position(p.x - 1, p.y + 1);
    }

    private static int lengthOf(int size, int n){
        if (n >= size){
            n = 2 * size - 1 - n;
        }
        return size + 2 * n;
    }

    private static TETile randomTile(){
        int tileNum = randomNumGenerator.nextInt(8);
        switch (tileNum){
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.FLOOR;
            case 3: return Tileset.GRASS;
            case 4: return Tileset.MOUNTAIN;
            case 5: return Tileset.SAND;
            case 6: return Tileset.TREE;
            case 7: return Tileset.WATER;
            default: return Tileset.WALL;
        }
    }

    public static void main(String[] args){
        int size = 3;
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
        Position p = new Position(2, 12);
        drawNumOfHex(tiles, p, size, 3);
        p = new Position(p.x + 2 * size - 1, p.y - size);
        drawNumOfHex(tiles, p, size, 4);
        p = new Position(p.x + 2 * size - 1, p.y - size);
        drawNumOfHex(tiles, p, size, 5);
        p = new Position(p.x + 2 * size - 1, p.y + size);
        drawNumOfHex(tiles, p, size, 4);
        p = new Position(p.x + 2 * size - 1, p.y + size);
        drawNumOfHex(tiles, p, size, 3);
        ter.renderFrame(tiles);
    }
}
