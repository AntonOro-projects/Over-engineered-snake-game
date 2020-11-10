public class DatabaseTest {
    private static Database dbase;

    @org.junit.jupiter.api.BeforeAll
    static void setUp() {
        dbase = new Database("snekdb_test");
        dbase.createUser("anton", "orö");
        dbase.createUser("anton1", "orö1");
        dbase.createUser("anton2", "orö2");
        dbase.createUser("anton3", "orö3");
        dbase.createUser("anton4", "orö4");
    }

    @org.junit.jupiter.api.AfterAll
    static void tearDown() {
        dbase.emptyDatabase();
    }

    @org.junit.jupiter.api.Test
    void stressTest() {
        Database db = new Database("snekdb_test");
        System.out.println("First test started");
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            db.setUserScore("anton", i*1000);
            db.setUserScore("anton1", i*1000);
            db.setUserScore("anton2", i*1000);
            db.setUserScore("anton3", i*1000);
            db.setUserScore("anton4", i*1000);
            db.getUserHighscore("anton", 5);
            db.getUserHighscore("anton1", 5);
            db.getUserHighscore("anton2", 5);
            db.getUserHighscore("anton3", 5);
            db.getUserHighscore("anton4", 5);
            db.getTopHighscores(1,5);
            db.getTopHighscores(7,5);
            db.getTopHighscores(30,5);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime-startTime) + " milliseconds to simulate 1000 games being completed");
    }
    @org.junit.jupiter.api.Test
    void stressTest1() {
        Database db = new Database("snekdb_test");
        System.out.println("Second test started");
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            db.setUserScore("anton", i*1001);
            db.setUserScore("anton1", i*1001);
            db.setUserScore("anton2", i*1001);
            db.setUserScore("anton3", i*1001);
            db.setUserScore("anton4", i*1001);
            db.getUserHighscore("anton", 5);
            db.getUserHighscore("anton1", 5);
            db.getUserHighscore("anton2", 5);
            db.getUserHighscore("anton3", 5);
            db.getUserHighscore("anton4", 5);
            db.getTopHighscores(1,5);
            db.getTopHighscores(7,5);
            db.getTopHighscores(30,5);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime-startTime) + " milliseconds to simulate 1000 games being completed");
    }
    @org.junit.jupiter.api.Test
    void stressTest2() {
        Database db = new Database("snekdb_test");
        System.out.println("Third test started");
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            db.setUserScore("anton", i*1002);
            db.setUserScore("anton1", i*1002);
            db.setUserScore("anton2", i*1002);
            db.setUserScore("anton3", i*1002);
            db.setUserScore("anton4", i*1002);
            db.getUserHighscore("anton", 5);
            db.getUserHighscore("anton1", 5);
            db.getUserHighscore("anton2", 5);
            db.getUserHighscore("anton3", 5);
            db.getUserHighscore("anton4", 5);
            db.getTopHighscores(1,5);
            db.getTopHighscores(7,5);
            db.getTopHighscores(30,5);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime-startTime) + " milliseconds to simulate 1000 games being completed");
    }
    @org.junit.jupiter.api.Test
    void stressTest3() {
        Database db = new Database("snekdb_test");
        System.out.println("Fourth test started");
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            db.setUserScore("anton", i*1003);
            db.setUserScore("anton1", i*1003);
            db.setUserScore("anton2", i*1003);
            db.setUserScore("anton3", i*1003);
            db.setUserScore("anton4", i*1003);
            db.getUserHighscore("anton", 5);
            db.getUserHighscore("anton1", 5);
            db.getUserHighscore("anton2", 5);
            db.getUserHighscore("anton3", 5);
            db.getUserHighscore("anton4", 5);
            db.getTopHighscores(1,5);
            db.getTopHighscores(7,5);
            db.getTopHighscores(30,5);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime-startTime) + " milliseconds to simulate 1000 games being completed");
    }
    @org.junit.jupiter.api.Test
    void stressTest4() {
        Database db = new Database("snekdb_test");
        System.out.println("Fifth test started");
        long startTime = System.currentTimeMillis();
        for(int i = 0; i < 1000; i++) {
            db.setUserScore("anton", i*1004);
            db.setUserScore("anton1", i*1004);
            db.setUserScore("anton2", i*1004);
            db.setUserScore("anton3", i*1004);
            db.setUserScore("anton4", i*1004);
            db.getUserHighscore("anton", 5);
            db.getUserHighscore("anton1", 5);
            db.getUserHighscore("anton2", 5);
            db.getUserHighscore("anton3", 5);
            db.getUserHighscore("anton4", 5);
            db.getTopHighscores(1,5);
            db.getTopHighscores(7,5);
            db.getTopHighscores(30,5);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("It took " + (endTime-startTime) + " milliseconds to simulate 1000 games being completed");
    }


}