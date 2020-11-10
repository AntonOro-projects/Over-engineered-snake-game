package module3;

import javafx.geometry.Point2D;

//Gjorde denna klass till att inte längre vara abstrakt för att kunna instansieras i klienten
public  class Fruit {
    int score;
    Point2D pos;

    public Point2D getPos() {
        return pos;
    }

    public  int getScore() {
    	return score;
    }

}