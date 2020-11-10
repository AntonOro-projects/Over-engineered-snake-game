import javafx.geometry.Point2D;

public class Apple extends Fruit {

    public Apple(int x, int y) {
        pos = new Point2D(x, y);
    }

    @Override
    public int getScore() {
        return score;
    }

}
