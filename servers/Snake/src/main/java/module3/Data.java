package module3;

import java.util.ArrayList;

public class Data {
    Board board;
    ArrayList<Integer> scorelList;
    ArrayList<Fruit> fruits = new ArrayList<Fruit>();
    ArrayList<Snake> snakes = new ArrayList<Snake>();

    // WIP
    public Data(Board board, ArrayList<Integer> scorelList, ArrayList<Fruit> fruits, ArrayList<Snake> snakes) {
        this.board = board;
        this.scorelList = scorelList;
        this.snakes = snakes;
        this.fruits = fruits;
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }

    public ArrayList<Fruit> getFruit() {
        return fruits;
    }
}