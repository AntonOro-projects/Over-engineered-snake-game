package module3;

import java.util.*;

import data.RequestSuccess;
import data.Response;
import data.SnakeAction;
import server.GameServer;
import server.MailBox;
import com.google.gson.Gson;

public class Logic implements Runnable {
    int length;
    ArrayList<Board> boards = new ArrayList<Board>();
    ArrayList<SnakeAction> userInputs = new ArrayList<SnakeAction>();
    private MailBox<SnakeAction> snakeMoveMailBox;
    private GameServer gameServer;
    private ArrayList<String> snakes;
    Gson gson = new Gson();
    // 10 boards per instance kanske?

    // GameServer kommer instansiera Logic med en referens till en mailbox som
    // möjliggör metoden nedan. I samma mailbox kommer kommunikationsmodulen
    // placera alla SnakeActíons från klienter.
    public Logic(MailBox<SnakeAction> box, GameServer gs,ArrayList<String> snakeList) {
        snakeMoveMailBox = box;
        gameServer = gs;
        snakes = snakeList;

        System.out.println("before create board");
        Board b = createBoard(50,50,1);
        System.out.println("before for loop board");
        for(String snakeId : snakeList){
            System.out.println("before create snake");
            System.out.println("snake id :" + snakeId);
            b.createSnake(snakeId);
            System.out.println("after create snake");
            Response res = new Response("gameConnect","", gson.toJson(new RequestSuccess(true)));
            System.out.println("before loop send");
            gameServer.sendMessage(snakeId,res);
            System.out.println("after loop send");
        }
        System.out.println("before add board");
    }

    // Denna metod hämtar alla SnakeActions som finns i brevlådan och tömmer den och
    // returnerar sedan alla actions i en arraylist.
    private ArrayList<SnakeAction> getActions() {
        return snakeMoveMailBox.fetchAll();
    }

    public Board getBoard(int id) {
        return boards.get(id);
    }

    public Board createBoard(int width, int height, int id) {
        Board board = new Board(width, height, id);
        board.init();
        boards.add(board);
        return board;
    }

    public void sendBoard(Board board) {
        Response response = new Response("gameState", "", gson.toJson(board));
        for (Snake s : board.getSnakes()) {
            gameServer.sendMessage(s.getID(), response);
        }
    }

    public void run() {
        System.out.println("Logic is running!");
        double ns = 1000000.0 / 1.0;
        double delta = 0;

        long lastTime = System.nanoTime();

        while (true) {
            long now = System.nanoTime();
            delta = (now - lastTime) / ns;
            lastTime = now;
            //if (delta < 1) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            //}
            System.out.println(now);
            userInputs = getActions();
            System.out.println("Number of boards: " + boards.size());
            for (Board board : boards) {
                board.getInputs(userInputs);
                board.tick();
                sendBoard(board);
                if(board.gameOver){
                    String playerId = snakes.get(0);
                    gameServer.endGame(gameServer.userNameToCounter.get(playerId));
                    System.out.println(gson.toJson(board));
                    return;
                }
            }
            delta--;
        }
    }
}
