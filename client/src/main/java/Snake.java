

import javafx.geometry.Point2D;
import java.util.ArrayList;

public class Snake {
    int score, eatenFruit;
    boolean alive;
    ArrayList<Point2D> body; // [(1,2),(1,3),(1,4),(1,5),(1,6)]
    Point2D head, tail;
    String id;
    String direction = "up";
    boolean addLength = false;

    public Snake(String id, ArrayList<Point2D> body) {
        this.id = id;
        this.score = 0;
        this.alive = true;
        this.body = body;
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

    public String getID() {
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

    public void updateHeadAndTail() {
        head = body.get(0);
        tail = body.get(body.size() - 1);
    }

    public void updatePos() {
        if (addLength) {
            switch (direction) {
                case "up":
                    body.add(0, new Point2D((int) head.getX(), (int) head.getY() + 1));
                    updateHeadAndTail();
                    break;
                case "right":
                    body.add(0, new Point2D((int) head.getX() + 1, (int) head.getY()));
                    updateHeadAndTail();
                    break;
                case "down":
                    body.add(0, new Point2D((int) head.getX(), (int) head.getY() - 1));
                    updateHeadAndTail();
                    break;
                case "left":
                    body.add(0, new Point2D((int) head.getX() - 1, (int) head.getY()));
                    updateHeadAndTail();
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
                    updateHeadAndTail();
                    break;
                case "right":
                    body.add(0, new Point2D((int) head.getX() + 1, (int) head.getY()));
                    body.remove(body.size() - 1);
                    updateHeadAndTail();
                    break;
                case "down":
                    body.add(0, new Point2D((int) head.getX(), (int) head.getY() - 1));
                    body.remove(body.size() - 1);
                    updateHeadAndTail();
                    break;
                case "left":
                    body.add(0, new Point2D((int) head.getX() - 1, (int) head.getY()));
                    body.remove(body.size() - 1);
                    updateHeadAndTail();
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
