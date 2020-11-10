package data;

public class Response {
    public String type;
    public String response;
    public String jsonData;

    public Response(String type, String response, String jsonData) {
        this.type = type;
        this.response = response;
        this.jsonData = jsonData;
    }
}
