package networks;

import java.util.ArrayList;

public class Chat {

	volatile String line;
	volatile ArrayList<Connection> connections;
	volatile int code;

	public Chat(int code) {
		connections = new ArrayList<>();
		this.code = code;
		line = "";

	}

	public synchronized void write(String input, Connection conn) {
		line = conn.getAddress() + ":" + input;
		for (Connection con : connections) {
			if(con!=conn) {
				con.write(line);
			}
		}
	}

	public synchronized void addConnection(Connection connection) {
		connections.add(connection);
		connection.setCode(this.code);
	}

	public synchronized void removeConnection(Connection connection) {
		connections.remove(connection);

	}

}
