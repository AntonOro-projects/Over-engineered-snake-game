package module3;

import javafx.geometry.Point2D;
import java.util.ArrayList;

public class Snake {
    int id, score, eatenFruit;
    boolean alive;
    ArrayList<Point2D> body; // [(1,2),(1,3),(1,4),(1,5),(1,6)]
    Point2D head, tail;
    String direction = "up";
    boolean addLength = false;

    public Snake(int id) {
        this.id = id;
        this.score = 0;
        this.alive = true;
        this.body = new ArrayList<Point2D>();
        body.add(new Point2D(1, 2));
        body.add(new Point2D(1, 3));
        body.add(new Point2D(1, 4));
        this.head = body.get(0);
        this.tail = body.get(body.size() - 1);
    }

    public void setIsAlive(boolean state) {
        alive = state;
    }

    public boolean isAlive() {
        return alive;
    }

    public ArrayList<Point2D> getBody() {
        return body;
    }

    public Point2D getHead() {
        return head;
    }

    public int getScore() {
        return score;
    }

    public int getEaten() {
        return eatenFruit;
    }

    public int getID() {
        return id;
    }

    public void updateScore() {
        score++;
    }

    public void setDirection(String dir) {
        direction = dir;
    }

    public void setAddLength(boolean val) {
        addLength = val;
    }

    public void updatePos() {
        if (addLength) {
            switch (direction) {
                case "up":
                    body.add(0, new Point2D((int) head.getX(), (int) head.getY() + 1));
                    break;
                case "right":
                    body.add(0, new Point2D((int) head.getX() + 1, (int) head.getY()));
                    break;
                case "down":
                    body.add(0, new Point2D((int) head.getX(), (int) head.getY() - 1));
                    break;
                case "left":
                    body.add(0, new Point2D((int) head.getX() - 1, (int) head.getY()));
                    break;
                default:
                    break;
            }
            addLength = false;
        } else {
            switch (direction) {
                case "up":
                    body.add(0, new Point2D((int) head.getX(), (int) head.getY() + 1));
                    body.remove(body.size() - 1);
                    break;
                case "right":
                    body.add(0, new Point2D((int) head.getX() + 1, (int) head.getY()));
                    body.remove(body.size() - 1);
                    break;
                case "down":
                    body.add(0, new Point2D((int) head.getX(), (int) head.getY() - 1));
                    body.remove(body.size() - 1);
                    break;
                case "left":
                    body.add(0, new Point2D((int) head.getX() - 1, (int) head.getY()));
                    body.remove(body.size() - 1);
                    break;
                default:
                    break;
            }
        }
    }

    public void updateDirection(String input) {
        switch (input) {
            case "left":
                if (direction != "right") {
                    direction = "left";
                }
                break;
            case "right":
                if (direction != "left") {
                    direction = "right";
                }
                break;
            case "up":
                if (direction != "down") {
                    direction = "up";
                }
                break;
            case "down":
                if (direction != "up") {
                    direction = "down";
                }
                break;
            default:
                break;
        }
    }
}