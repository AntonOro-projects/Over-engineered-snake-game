package module3;

import javafx.geometry.Point2D;

public abstract class Fruit {
    int score;
    Point2D pos;

    public Point2D getPos() {
        return pos;
    }

    public abstract int getScore();

}