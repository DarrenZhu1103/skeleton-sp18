package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.LinkedList;
import java.util.Random;

class GameMap {
    private Position user, door;
    public TETile[][] tiles;
    private int WIDTH, HEIGHT;
    private Random randomNumGenerator;
    public boolean gameOver = false;

    private static class Position{
        public int x;
        public int y;

        Position(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            return x == ((Position) obj).x && y == ((Position) obj).y;
        }
    }

    private static class Area extends Position{
        public int width;
        public int height;

        Area(int x, int y, int height, int width){
            super(x, y);
            this.width = width;
            this.height = height;
        }
    }

    GameMap(int seed, int WIDTH, int HEIGHT){
        this.randomNumGenerator = new Random(seed);
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        tiles = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < WIDTH; i++)
            for (int j = 0; j < HEIGHT; j++)
                tiles[i][j] = Tileset.NOTHING;
        mapGenerator();
    }

    private void mapGenerator(){
        LinkedList<Area> linked = new LinkedList<>();
        LinkedList<Area> unlinked = new LinkedList<>();
        addRoom(unlinked);
        linked.add(unlinked.removeFirst());
        while (!unlinked.isEmpty()){
            LinkedList<Area> pair = findPair(linked, unlinked);
            addWay(pair.getFirst(), pair.getLast());
            unlinked.remove(pair.getLast());
            linked.add(pair.getLast());
        }
        addWall();
        addGoldenDoor();
        addUser();
    }

    /** 创建所有的Room 进行数据填充 */
    private void addRoom(LinkedList<Area> unlinked){
        int height = HEIGHT, width = WIDTH, s = height * width, total = 0;
        while ((double) total / s <= 0.3){
            Area newRoom = createRoom(unlinked, width, height);
            total += newRoom.height * newRoom.width;
            addOneRoom(newRoom);
        }
    }

    /** 创建合法的Room */
    private Area createRoom(LinkedList<Area> unlinked, int totalWidth, int totalHeight){
        loop:
        while (true){
            int xPosition = randomNumGenerator.nextInt(totalWidth - 2) + 1;
            int yPosition = randomNumGenerator.nextInt(totalHeight - 2) + 1;
            int width = randomNumGenerator.nextInt(totalWidth / 5) + 1;
            int height = randomNumGenerator.nextInt(totalHeight / 5) + 1;
            Area area = new Area(xPosition, yPosition, height, width);
            if (xPosition + width > totalWidth - 1 || yPosition + height > totalHeight - 1)
                continue;
            for (Area other: unlinked){
                boolean x = (other.x > xPosition + width) || (xPosition > other.x + other.width);
                boolean y = (other.y > yPosition + height) || (yPosition > other.y + other.height);
                if(!x && !y)
                    continue loop;
            }
            unlinked.add(area);
            return area;
        }
    }

    /** 给数据数组加入Floor的值 */
    private void addOneRoom(Area p){
        for (int i = p.x; i < p.x + p.width; i++)
            for (int j = p.y; j < p.y + p.height; j++)
                tiles[i][j] = Tileset.FLOOR;
    }

    /** 找连接的一对room */
    private LinkedList<Area> findPair(LinkedList<Area> linked, LinkedList<Area> unlinked){
        return doSomeLoop(linked, unlinked);
    }

    private void addWay(Area pStart, Area pEnd){
        LinkedList<Position> startDoor = getDoor(pStart), endDoor = getDoor(pEnd);
        LinkedList<Position> pair = doSomeLoop(startDoor, endDoor);
        Position point = pair.getFirst(), last = pair.getLast();
        if (point.x > last.x){
            Position temp = point;
            point = last;
            last = temp;
        }
        int x = point.x, y = point.y;
//        System.out.println(TETile.toString(tiles));
        while (x < last.x){
            tiles[x][y] = Tileset.FLOOR;
            x++;
        }

        while (y > last.y){
            tiles[x][y] = Tileset.FLOOR;
            y--;
        }

        while (y < last.y){
            tiles[x][y] = Tileset.FLOOR;
            y++;
        }
        tiles[x][y] = Tileset.FLOOR;
//        System.out.println(TETile.toString(tiles));
    }

    private <T extends Position> LinkedList<T> doSomeLoop(LinkedList<T> fList, LinkedList<T> lList){
        int distance = HEIGHT + WIDTH;
        T first = null, last = null;
        LinkedList<T> pair = new LinkedList<>();
        for (T tFirst : fList) {
            for (T tLast : lList) {
                int newDistance = positionDistance(tFirst, tLast);
                if (newDistance < distance) {
                    first = tFirst;
                    last = tLast;
                    distance = newDistance;
                }
            }
        }
        pair.add(first);
        pair.add(last);
        return pair;
    }

    private LinkedList<Position> getDoor(Area p){
        LinkedList<Position> door = new LinkedList<>();
        addOneDoor(new Position(p.x - 1, p.y + p.height / 2), door);
        addOneDoor(new Position(p.x + p.width / 2, p.y - 1), door);
        addOneDoor(new Position(p.x + p.width / 2, p.y + p.height), door);
        addOneDoor(new Position(p.x + p.width, p.y + p.height / 2), door);
        return door;
    }

    private void addOneDoor(Position p, LinkedList<Position> door){
        if (validDoor(p))
            door.add(p);
    }

    private boolean validDoor(Position p){
        if (p.x > 0 && p.x < WIDTH - 1 && p.y > 0 && p.y < HEIGHT - 1)
            return true;
        return false;
    }

    private void addWall(){
        for (int i = 0; i < WIDTH; i++){
            for (int j = 0; j < HEIGHT; j++){
                if (tiles[i][j] == Tileset.FLOOR){
                    for (int m = -1; m <= 1; m++) {
                        for (int n = -1; n <= 1; n++) {
                            if (tiles[i + m][j + n] == Tileset.NOTHING)
                                tiles[i + m][j + n] = Tileset.WALL;
                        }
                    }
                }
            }
        }
    }

    private int positionDistance(Position pStart, Position pEnd){
        return Math.abs(pStart.x - pEnd.x) + Math.abs(pStart.y - pEnd.y);
    }

    private void addGoldenDoor(){
        while (true){
            int x = randomNumGenerator.nextInt(WIDTH - 4) + 2;
            int y = randomNumGenerator.nextInt(HEIGHT - 4) + 2;
            double d0 = randomNumGenerator.nextDouble();
            int[] arr = {-1, 1};
            int d = arr[randomNumGenerator.nextInt(2)];
            if (d0 < 0.5 && tiles[x][y] == Tileset.FLOOR && tiles[x][y + d] == Tileset.WALL && tiles[x][y + 2 * d] == Tileset.NOTHING){
                tiles[x][y + d] = Tileset.LOCKED_DOOR;
                door = new Position(x, y + d);
                return;
            }
            else if (d0 >= 0.5 && tiles[x][y] == Tileset.FLOOR && tiles[x + d][y] == Tileset.WALL && tiles[x + 2 * d][y] == Tileset.NOTHING){
                tiles[x + d][y] = Tileset.LOCKED_DOOR;
                door = new Position(x + d, y);
                return;
            }

        }
    }

    private void addUser(){
        while (true) {
            int x = randomNumGenerator.nextInt(WIDTH);
            int y = randomNumGenerator.nextInt(HEIGHT);
            if (tiles[x][y] == Tileset.FLOOR){
                tiles[x][y] = Tileset.PLAYER;
                user = new Position(x, y);
                return;
            }
        }
    }

    public TETile[][] move(int deltaX, int deltaY){
        int x = user.x + deltaX, y = user.y + deltaY;
        if (tiles[x][y] == Tileset.FLOOR || tiles[x][y] == Tileset.LOCKED_DOOR){
            tiles[x][y] = Tileset.PLAYER;
            tiles[user.x][user.y] = Tileset.FLOOR;
            user.x = x;
            user.y = y;
        }
        if (user.equals(door)){
            gameOver = true;
        }
        return tiles;
    }
}
