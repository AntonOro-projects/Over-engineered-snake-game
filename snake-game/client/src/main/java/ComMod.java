import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class ComMod {

    public ComMod() {

    }

    public boolean sendSignUp(String username, String password){
        System.out.println("tried to send data");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        return true;
    }

    public boolean sendSignIn(String username, String password){
        System.out.println("tried to send data");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        return true;
    }

    public boolean sendConnect() {
        System.out.println("Tried to connect to a game");
        return true;
    }

    // Log out



    public Data update() {
        Board testBoard = new Board(50, 50, 1);
        ArrayList<Integer> scores = new ArrayList<Integer>() {
            {
                add(10);
                add(20);
                add(1337);
            }
        };
        ArrayList<Fruit> fruits = new ArrayList<Fruit>() {
            {
                add(new Apple(20, 20));
                add(new Apple(0, 0));
                add(new Apple(49, 49));
            }
        };
        ArrayList<Snake> snakes = new ArrayList<Snake>() {
            {
                add(new Snake(1));
            }
        };

        Data data = new Data(testBoard, scores, fruits, snakes);
        return data;
    }

    public static void sendDirection(String dir) {
        System.out.println(dir);
    }
}
