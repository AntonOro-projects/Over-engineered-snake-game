package data;

/*
Request format (maybe we should use http format?)
 */
public class Request {
    public String type;
    public String jsonData;
    public int id;
    public static final String[] TYPES = { "login", "register", "startGame", "getHighScore" };

    public Request(String type, String jsonData) {
        this.type = type;
        this.jsonData = jsonData;
    }
}
