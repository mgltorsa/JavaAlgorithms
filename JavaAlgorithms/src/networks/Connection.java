package networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection extends Thread {

	Socket socket;
	Server server;
	volatile BufferedReader in;
	volatile PrintWriter out;
	int code;
	String address;
	

	public Connection(Server server, Socket socket, int code) throws IOException {
		this.server = server;
		this.socket = socket;
		address= socket.getInetAddress().getHostAddress();
		this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		this.out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
		this.code = code;
	}

	@Override
	public void run() {

		write(server.compute("newwake", this));
		while (true) {

			String input = read();
			String response = server.compute(input, this);
			write(response);
		}
	}

	public synchronized void write(String response) {
		if( !(response.isEmpty() && response.equals("") )) {
			out.println(response);
		}

	}

	private String read() {
		String response="";
		try {
			while (!in.ready()) {
				Thread.sleep(500);
			}
			while(in.ready()) {
				response+=in.readLine();
			}
		} catch (IOException | InterruptedException e) {
			
			e.printStackTrace();
		}
		return response;
	}

	public int getCode() {
		return code;
	}
	
	public void closeConnection() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setCode(int code) {
		this.code=code;
		
	}

	public String getAddress() {
		return address;
	}
}
