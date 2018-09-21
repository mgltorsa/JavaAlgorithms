package networks;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class Server {

	public static int group = 100;

	ServerSocket serverSocket;
	int port;
	ArrayList<Connection> connections;
	HashMap<Integer, Chat> groups;

	public Server() throws IOException {
		this.port = 1234;
		groups = new HashMap<>();
		connections = new ArrayList<>();
		serverSocket=new ServerSocket(port);
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void start() {
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				Connection conn = new Connection(this, socket, -1);
				connections.add(conn);
				conn.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getWelcomeResponse(Connection conn) {
		if(conn.getCode()==-1) {
			return "No estas en ningun chat";
		}
		return "Conectado al chat: " + conn.getCode();
	}

	public synchronized String compute(String input, Connection connection) {
		String response = "";
		if (input.isEmpty()) {
			return "";
		}
		if (input.equals("newwake")) {
			response = getWelcomeResponse(connection);
		} else {
			response = computeInternal(input, connection);
		}
		return response;
	}

	private synchronized String computeInternal(String input, Connection conn) {
		if (input.equalsIgnoreCase("create")) {
			Chat chat = new Chat(group);
			chat.addConnection(conn);
			groups.put(group, chat);
			return "Grupo creado con codigo: "+(group++);
		} else if (input.contains("cme:")) {
			String[] info = input.trim().split("cme:");
			int code = Integer.parseInt(info[1]);
			int oldCode = conn.getCode();
			if (oldCode != -1 && groups.get(oldCode) != null) {
				groups.get(oldCode).removeConnection(conn);
			}
			if (groups.get(code) != null) {
				conn.setCode(code);
				groups.get(code).addConnection(conn);
				return this.getWelcomeResponse(conn);
			} else {
				return "codigoInvalido";
			}
		} else {
			int code = conn.getCode();
			Chat group = groups.get(code);
			if (group != null) {
				group.write(input, conn);
			} else {
				return "no estas en un grupo";
			}
		}

		return "";
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}

}
