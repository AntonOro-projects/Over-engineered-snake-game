package data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    public Socket socket;
    public DataInputStream inputStream;
    public DataOutputStream outputStream;

    public Connection(Socket socket) {
        this.socket = socket;
        try {
			this.inputStream = new DataInputStream(socket.getInputStream());
			this.outputStream = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }

	public Connection() {
		socket = null;
		inputStream = null;
		outputStream = null;
	}
}
