package module3;

public class test {
    public static void main(String[] args) throws CloneNotSupportedException {
        Logic logic1 = new Logic();
        Logic logic2 = new Logic();

        // INIT BOARD
        logic1.createBoard(50, 50, 1);
        logic1.createBoard(50, 50, 2);
        logic1.createBoard(50, 50, 3);
        logic1.createBoard(50, 50, 4);

        logic1.run();
        // PLAYER JOINS

        logic2.createBoard(50, 50, 1);
        logic2.createBoard(50, 50, 2);
        logic2.createBoard(50, 50, 3);
        logic2.createBoard(50, 50, 4);
        logic2.run();
        // Sends data
        // {....}

        // Recieves data

    }
}