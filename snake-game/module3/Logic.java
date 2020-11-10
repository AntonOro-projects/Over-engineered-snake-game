package module3;

import java.util.*;

public class Logic {
    int length;
    ArrayList<Board> boards = new ArrayList<Board>();

    // 10 boards per instance kanske?

    public Board getBoard(int id) {
        return boards.get(id);
    }

    public Board createBoard(int width, int height, int id) {
        Board board = new Board(width, height, id);
        board.init();
        boards.add(board);
        return board;
    }

    public void getInputs(Map<Integer, String> userInputs) {
        for (Board board : boards) {
            board.getInputs(userInputs);
        }
    }

    public void run() {
        double ns = 1000000000.0 / 1.0;
        double delta = 0;

        long lastTime = System.nanoTime();

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                System.out.println(now);
                // getInputs(....);
                for (Board board : boards) {
                    System.out.println(board.getID());
                    board.tick();
                }
                delta--;
            }
        }
    }

    // getinputs()

    // public Board checkBoardCapacity() {
    // for (Board board : boards) {
    // if (board.getSnakes().size() < 5) {
    // return board;
    // }
    // }
    // return createBoard(50, 50, boards.size() + 1); // fix - give it a good id
    // }
}
