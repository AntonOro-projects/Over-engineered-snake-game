package data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Connection {
    public Socket socket;
    public DataInputStream inputStream;
    public DataOutputStream outputStream;

    public Connection(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
}
