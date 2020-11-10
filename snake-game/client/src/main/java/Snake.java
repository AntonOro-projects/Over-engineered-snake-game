import java.awt.Point;
import java.util.ArrayList;

public class Snake {
    // [[board size], [apples], snake: [int id, boolean dead, int score, [body]]]
    int id, score;
    boolean isAlive;
    ArrayList<Point> body; // [(1,2),(1,3),(1,4),(1,5),(1,6)]
    Point head, tail;
    String direction = "up";

    public Snake(int id) {
        this.id = id;
        this.score = 0;
        this.isAlive = true;
        this.body = new ArrayList<Point>();
        body.add(new Point(1, 2));
        body.add(new Point(1, 3));
        body.add(new Point(1, 4));
        this.head = body.get(0);
        this.tail = body.get(body.size() - 1);
    }

    public void setIsAlive(boolean state) {
        isAlive = state;
    }

    public ArrayList<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return head;
    }

    public int getScore() {
        return score;
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

    public void updatePos() {
        switch (direction) {
            case "up":
                body.add(0, new Point((int) head.getX(), (int) head.getY() + 1));
                body.remove(body.size() - 1);
                break;
            case "right":
                body.add(0, new Point((int) head.getX() + 1, (int) head.getY()));
                body.remove(body.size() - 1);
                break;
            case "down":
                body.add(0, new Point((int) head.getX(), (int) head.getY() - 1));
                body.remove(body.size() - 1);
                break;
            case "left":
                body.add(0, new Point((int) head.getX() - 1, (int) head.getY()));
                body.remove(body.size() - 1);
                break;

            default:
                break;
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
                // code block
        }
    }
}
