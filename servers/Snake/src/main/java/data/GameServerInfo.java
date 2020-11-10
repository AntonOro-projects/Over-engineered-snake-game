package data;

public class GameServerInfo {
	
	public GameServerInfo(String address, int port, String key) {
		this.address = address;
		this.port = port;
		this.key = key;
	}
	
    public String address;
    public int port;
    public String key;
}
