package module3;

import java.util.*;
import javafx.geometry.Point2D;

public class Board {
    int height, width, id;
    ArrayList<Fruit> fruits = new ArrayList<Fruit>();
    ArrayList<Snake> snakes = new ArrayList<Snake>();
    Random rand = new Random();
    int highestScore = 0;
    int highscoreID = 0;
    Map<Integer, String> inputs = new HashMap<Integer, String>();
    boolean gameOver = false;

    public Board(int width, int height, int id) {
        this.id = id;
        this.width = width;
        this.height = height;
    }

    public void init() {
        spawnApples(10);
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
            newApple = new Apple(x, y);
            if (!fruits.contains(newApple)) {
                fruits.add(newApple);
            }
        }
    }

    public void createSnake(int id) {
        Snake newSnake = new Snake(id);
        snakes.add(newSnake);
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
        for (Snake snake : getSnakes()) {
            for (Fruit apple : fruits) {
                if (snake.getHead() == apple.getPos()) {
                    spawnApples(1);
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
            System.out.println("loop");
            for (Snake snake2 : getSnakes()) {
                if (snake1 == snake2) {
                    ArrayList<Point2D> body = snake1.getBody();
                    body.remove(0); // remove head to make sure you don't always kill yourself :)
                    if (body.contains(snake1.getHead())) {
                        snake1.setIsAlive(false);
                        // gameOver(snake1);
                        // send score to DB
                        System.out.println("Snake collision with self detected");
                    }
                } else if (snake2.getBody().contains(snake1.getHead())) {
                    snake1.setIsAlive(false);
                    // gameOver(snake1);
                    // send score to DB
                    System.out.println("Snake collision with other snake detected");
                }
            }
        }
    }

    public void getInputs(Map<Integer, String> userInputs) {
        for (Snake snake : snakes) {
            inputs.put(snake.getID(), userInputs.get(snake.getID()));
        }
    }

    public void updateSnakes() {
        for (Snake snake : snakes) {
            snake.updateDirection(inputs.get(snake.getID()));
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