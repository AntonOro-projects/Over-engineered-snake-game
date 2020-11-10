import java.util.*;
import java.awt.Point;

public class Board {
    int height, width, id;

    // TEMPORARY, ASK GROUP 3
    boolean gameOver = false;

    ArrayList<Point> apples = new ArrayList<Point>() {
        {
            add(new Point(1, 20));
            add(new Point(32, 4));
            add(new Point(44, 22));
            add(new Point(33, 33));
        }
    };
    ArrayList<Snake> snakes = new ArrayList<Snake>() {
        {
            add(new Snake(1));
        }
    };
    Random rand = new Random();
    int highestScore = 0;
    int highscoreID = 0;
    Map<Snake, String> inputs = new HashMap<Snake, String>();

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

    public ArrayList<Point> getApples() {
        return apples;
    }

    public void spawnApples(int numberOfApples) {
        int x, y;
        Point newApple;
        for (int i = 0; i < numberOfApples; ++i) {
            // Obtain a number between [0 - 49].
            x = rand.nextInt(50);
            y = rand.nextInt(50);
            newApple = new Point(x, y);
            if (!apples.contains(newApple)) {
                apples.add(newApple);
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

    public void gameOver(Snake snake) {
        // if (snake.getScore() > db.getHighscore(snake.getID())) {
        // // send to db
        // }
    }

    public void checkAppleCol() {
        for (Snake snake : getSnakes()) {
            for (Point apple : getApples()) {
                if (snake.getHead() == apple) {
                    spawnApples(1);
                    snake.updateScore();
                }
            }
        }
    }

    public void checkColWall() {
        for (Snake snake : getSnakes()) {
            if (snake.getHead().getX() >= getWidth() || snake.getHead().getX() < 0
                    || snake.getHead().getY() >= getHeight() || snake.getHead().getY() < 0) {
                snake.setIsAlive(false);
                gameOver(snake);
                // send score to DB
            }
        }
    }

    public void checkColSnake() {
        for (Snake snake1 : getSnakes()) {
            System.out.println("loop");
            for (Snake snake2 : getSnakes()) {
                if (snake1 == snake2) {
                    ArrayList<Point> body = snake1.getBody();
                    body.remove(0); // remove head to make sure you don't always kill yourself :)
                    if (body.contains(snake1.getHead())) {
                        snake1.setIsAlive(false);
                        gameOver(snake1);
                        // send score to DB
                        System.out.println("Snake collision with self detected");
                    }
                } else if (snake2.getBody().contains(snake1.getHead())) {
                    snake1.setIsAlive(false);
                    gameOver(snake1);
                    // send score to DB
                    System.out.println("Snake collision with other snake detected");
                }
            }
        }
    }

    public void getInputs(Map<Snake, String> userInputs) {
        for (Snake snake : snakes) {
            inputs.put(snake, userInputs.get(snake));
        }
    }

    public void updateSnakeDirection() {
        for (Snake snake : snakes) {
            snake.updateDirection(inputs.get(snake));
        }
    }

    public void tick() {
        updateSnakeDirection();
        checkAppleCol();
        checkColWall();
        checkColSnake();
        updateScoreBoard();
        updateSnakeDirection();
    }

}

