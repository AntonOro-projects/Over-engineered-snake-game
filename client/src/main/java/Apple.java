

import javafx.geometry.Point2D;

public class Apple extends Fruit {

    public Apple(int x, int y) {
        pos = new Point2D(x, y);
    }

    //Lade till denna i superklass istället
//    @Override
//    public int getScore() {
//        return score;
//    }

}