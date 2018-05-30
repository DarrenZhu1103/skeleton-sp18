package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    private GameMap lastMap;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        int width = WIDTH, height = HEIGHT + 2;
        ter.initialize(width, height);
        while(true){
            drawStartFrame();
            char command = getCommand();

            switch (command){
                case 'n': playGame(drawGetSeedFrame(), null); break;
                case 'l': playGame(0, lastMap); break;
                default: System.exit(0); break;
            }
        }
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        input = input.toLowerCase();

        int i = 0;
        GameMap map = null;
        if (input.charAt(i) == 'l'){
            map = lastMap;
            i++;
        }
        if (input.charAt(i) == 'n') {
            int seed = readSeed(input);
            map = new GameMap(seed, WIDTH, HEIGHT);
            i = input.indexOf("s") + 1;
        }
        while (i < input.length()){
            char command = input.charAt(i);
            switch (command){
                case 'a': map.move(-1, 0); break;
                case 's': map.move(0, -1); break;
                case 'd': map.move(1, 0); break;
                case 'w': map.move(0, 1); break;
            }
            i++;
        }

        if (input.endsWith(":q")){
            lastMap = map;
        }

        try {
            return map.tiles;
        } catch (Exception e){
            throw new RuntimeException("Invalid Command");
        }
    }

    private int readSeed(String command){
        try {
            int sPosition = command.indexOf("s");
            String result = command.substring(1, sPosition);
            return Integer.parseInt(result);
        } catch (Exception e){
            throw new RuntimeException("invalid command");
        }
    }

    private void drawStartFrame(){
        StdDraw.clear(Color.black);

        Font title = new Font("Monaco", Font.BOLD, 40);
        Font smallTitle = new Font("Monaco", Font.BOLD, 20);

        StdDraw.setPenColor(Color.white);

        StdDraw.setFont(title);
        StdDraw.text(WIDTH / 2, HEIGHT * 4 / 5, "A STUPID GAME");

        StdDraw.setFont(smallTitle);
        StdDraw.text(WIDTH / 2, HEIGHT * 5 / 10, "NEW GAME(N)");
        StdDraw.text(WIDTH / 2, HEIGHT * 4 / 10, "LOAD GAME(L)");
        StdDraw.text(WIDTH / 2, HEIGHT * 3 / 10, "QUIT(Q)");

        StdDraw.show();
    }

    private char getCommand(){
        while (true){
            if (StdDraw.hasNextKeyTyped()){
                char command = StdDraw.nextKeyTyped();
                if (command == 'l' && lastMap != null || command == 'n' || command == 'q')
                    return command;
            }
        }
    }

    private int drawGetSeedFrame(){
        String s = "0";
        drawGetSeedFrameRefresh(Integer.parseInt(s));
        while(true){
            if (StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                if (Character.isDigit(key)){
                    s += String.valueOf(key);
                    drawGetSeedFrameRefresh(Integer.parseInt(s));
                }
                if (key == '\n')
                    break;
            }
        }
        return Integer.parseInt(s);
    }


    private void drawGetSeedFrameRefresh(int s){
        StdDraw.clear(Color.black);

        Font title = new Font("Monaco", Font.BOLD, 40);

        StdDraw.setPenColor(Color.white);

        StdDraw.setFont(title);
        StdDraw.text(WIDTH / 2, HEIGHT * 2 / 3, "GIVE A SEED");
        StdDraw.text(WIDTH / 2, HEIGHT * 1 / 3, String.valueOf(s));

        StdDraw.show();
    }

    private void playGame(int seed, GameMap oddMap){
        GameMap map;
        if (oddMap == null){
            map = new GameMap(seed, WIDTH, HEIGHT);
        }
        else{
            map = oddMap;
        }
        char lastKey = '0';
        while(true){
            if (map.gameOver){
                drawBye();
                return;
            }
            drawPosition((int)StdDraw.mouseX(), (int)StdDraw.mouseY(), map.tiles);
            if (StdDraw.hasNextKeyTyped()){
                char key = StdDraw.nextKeyTyped();
                if (lastKey == ':' && key == 'q'){
                    lastMap = map;
                    return;
                }

                lastKey = key;
            }
        }
    }

    private void drawBye(){
        StdDraw.clear(Color.black);

        Font title = new Font("Monaco", Font.BOLD, 40);

        StdDraw.setPenColor(Color.white);

        StdDraw.setFont(title);
        StdDraw.text(WIDTH / 2, HEIGHT / 2, "CONGRATULATIONS!");

        StdDraw.show();

        StdDraw.pause(2000);
    }

    private void drawPosition(int x, int y, TETile[][] tiles){
        ter.renderFrame(tiles);

        Font title = new Font("Monaco", Font.BOLD, 13);

        StdDraw.setPenColor(Color.white);

        StdDraw.setFont(title);
        StdDraw.textRight(WIDTH - 1, HEIGHT + 1, x + ", " + y);
        StdDraw.line(0, HEIGHT, WIDTH, HEIGHT);


        StdDraw.show();

    }

}
