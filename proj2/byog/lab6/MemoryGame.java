package byog.lab6;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;


public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        String result = "";
        for (int i = 0; i < n; i++){
            result += CHARACTERS[rand.nextInt(26)];
        }
        return result;
    }

    public void drawFrame(String s) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(Color.WHITE);

        if (!gameOver){
            Font font = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(font);
            StdDraw.textLeft(1, this.height - 1, "Round: " + round);
            StdDraw.text(this.width / 2, this.height - 1, playerTurn ? "Type!" : "Watch!");
            StdDraw.textRight(this.width, this.height - 1, ENCOURAGEMENT[rand.nextInt(ENCOURAGEMENT.length)]);
            StdDraw.line(0, height - 2, width, height - 2);
        }


        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.text(this.width / 2, this.height / 2, s);
        StdDraw.show();
    }

    public void flashSequence(String letters) {
        for (int i = 0; i < letters.length(); i++){
            drawFrame(String.valueOf(letters.charAt(i)));
            StdDraw.pause(1000);
            drawFrame(" ");
            StdDraw.pause(500);
        }
    }

    public String solicitNCharsInput(int n) {
        String result = "";
        drawFrame(result);
        int i = 0;
        while (i < n){
            if (StdDraw.hasNextKeyTyped()){
                result += String.valueOf(StdDraw.nextKeyTyped());
                drawFrame(result);
                i++;
            }
        }
        StdDraw.pause(1000);
        return result;
    }

    public void startGame() {
        round = 1;
        gameOver = false;
        playerTurn = false;

        while(!gameOver){
            drawFrame("Round: " + round);
            StdDraw.pause(1000);

            String question = generateRandomString(round);
            flashSequence(question);

            playerTurn = true;
            String answer = solicitNCharsInput(round);

            if (!answer.equals(question)){
                drawFrame("Game Over! Final level: " + round);
                gameOver = true;
            }
            else{
                playerTurn = false;
                drawFrame("Correct, well done!");
                StdDraw.pause(1500);
                round++;
            }

        }
    }

}
