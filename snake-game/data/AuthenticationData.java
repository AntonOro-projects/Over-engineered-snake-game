package data;

public class AuthenticationData {
	private String userName;
	private String pwHash;

	public AuthenticationData(String userName, String pw) {
		this.userName = userName;
		this.pwHash = pw; // TODO: Hasha istället i framtiden
	}

	public String getUserName() {
		return userName;
	}

	public String getPwHash() {
		return pwHash;
	}

}
