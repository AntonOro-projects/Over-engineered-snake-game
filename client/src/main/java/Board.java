

import java.util.*;

import data.SnakeAction;
import javafx.geometry.Point2D;

public class Board {
    int height, width, id;
    ArrayList<Fruit> fruits = new ArrayList<Fruit>();
    ArrayList<Snake> snakes = new ArrayList<Snake>();
    Random rand = new Random();
    int highestScore = 0;
    String highscoreID = "";
    private ArrayList<SnakeAction> inputs;
    boolean gameOver = false;

    public Board(int width, int height, int id) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public void init() {
        spawnApples(1);
    }

    public int getWidth() {
        return width;
    }

    public int getID() {
        return id;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<Snake> getSnakes() {
        return snakes;
    }

    public ArrayList<Fruit> getFruit() {
        return fruits;
    }

    public void spawnApples(int numberOfApples) {
        int x, y;
        Apple newApple;
        for (int i = 0; i < numberOfApples; ++i) {
            // Obtain a number between [0 - 49].
            x = rand.nextInt(50);
            y = rand.nextInt(50);
            newApple = new Apple(26, 26);
            if (!fruits.contains(newApple)) {
                fruits.add(newApple);
            }
        }
    }

    public ArrayList<Point2D> getStartingPos() {
        int x = rand.nextInt(50);
        int y = rand.nextInt(47);
        Point2D pos1 = new Point2D(x, y + 2);
        Point2D pos2 = new Point2D(x, y + 1);
        Point2D pos3 = new Point2D(x, y);
        ArrayList<Point2D> body = new ArrayList<Point2D>();
        body.add(pos1);
        body.add(pos2);
        body.add(pos3);
        return body;
    }

    public void createSnake(String id) {
        ArrayList<Point2D> takenPoints = new ArrayList<Point2D>();
        ArrayList<Point2D> body = new ArrayList<Point2D>();
        for (Snake snake : snakes) {
            for (Point2D point2d : snake.getBody()) {
                takenPoints.add(point2d);
            }
        }
        body = getStartingPos();
        System.out.println("before while in CreateSnake");
        while (takenPoints.contains(body.get(0)) && takenPoints.contains(body.get(1))
                && takenPoints.contains(body.get(2))) {
            body = getStartingPos();
        }
        System.out.println("after chreate snake");
        System.out.println("adding snake " + id);
        snakes.add(new Snake(id, body));
    }

    public void updateScoreBoard() {
        for (Snake snake : getSnakes()) {
            if (snake.getScore() > highestScore) {
                highestScore = snake.getScore();
                highscoreID = snake.getID();
            }
        }
    }

    // public void gameOver(Snake snake) {
    // // if (snake.getScore() > db.getHighscore(snake.getID())) {
    // // // send to db
    // // }
    // }

    public void checkAppleCol() {
        System.out.println(fruits.size());
        for (Snake snake : getSnakes()) {
            System.out.println("snake pos: " + snake.getHead().toString());
            for (Fruit apple : fruits) {
                System.out.println("apple pos: " + apple.getPos().toString());
                if (snake.getHead().equals(apple.getPos())) {
                    spawnApples(1);
                    System.out.println("Collision with an apple");
                    snake.updateScore();
                    snake.setAddLength(true);
                    fruits.remove(apple);
                }
            }
        }
    }

    public void checkColWall() {
        for (Snake snake : getSnakes()) {
            if (snake.getHead().getX() >= getWidth() || snake.getHead().getX() < 0
                    || snake.getHead().getY() >= getHeight() || snake.getHead().getY() < 0) {
                snake.setIsAlive(false);
                // gameOver(snake);
                // send score to DB
            }
        }
    }

    public void checkColSnake() {
        for (Snake snake1 : getSnakes()) {
            for (Snake snake2 : getSnakes()) {
                if (snake1.isAlive() && snake2.isAlive()) {
                    if (snake1 == snake2) {
                        ArrayList<Point2D> body = snake1.getBody();
                        Point2D savePos = body.remove(0); // remove head to make sure you don't always kill yourself :)
                        if (body.contains(snake1.getHead())) {
                            snake1.setIsAlive(false);
                            // gameOver(snake1);
                            // send score to DB
                            System.out.println("Snake collision with self detected");
                        }
                        body.add(0, savePos);
                    } else if (snake2.getBody().contains(snake1.getHead())) {
                        snake1.setIsAlive(false);
                        // snakes.remove(snake1);
                        // gameOver(snake1);
                        // send score to DB
                        System.out.println("Snake collision with other snake detected");
                    }
                }
            }
        }
    }

    public void getInputs(ArrayList<SnakeAction> userInputs) {
        inputs = userInputs;
    }

    public void updateSnakes() {
        for (Snake snake : snakes) {
            for (SnakeAction action : inputs) {
                if (action.id == snake.getID()) {
                    snake.updateDirection(action.action);
                }
            }
            snake.updatePos();
        }
    }

    public void checkGameOver() {
        int alive = 0;
        for (Snake snake : snakes) {
            if (snake.isAlive()) {
                alive++;
            }
        }
        if (alive <= 0) {
            gameOver = true;
            System.out.println("Game Over");
        }
    }

    public void tick() {
        updateSnakes();
        checkAppleCol();
        checkColWall();
        checkColSnake();
        updateScoreBoard();
        checkGameOver();
    }

}
