/**
 * 
 */
package conecctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Miguel
 *
 */
public class Client extends Source implements IClient {

	private String host;
	private Socket socket;
	private String message;

	/**
	 * @param type
	 */
	public Client() {
		super(Type.Client);
		socket = new Socket();
		host = "localhost";
	}

	public Client(Type type, String host) {
		super(type);
		socket = new Socket();
		setRequestMessage("Suma:5 7\nSuma:3 4");
		this.host = host;
	}

	public Client(Type type, int port) {
		super(type);
		host = "localhost";
		setPort(port);

	}

	public Client(Type type, String host, int port) {
		super(type);
		socket = new Socket();
		this.host = host;
		setPort(port);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IConnection#start()
	 */
	@Override
	public synchronized void start() {
		try {
			socket = new Socket(getHost(), getPort());

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter bw = new PrintWriter(socket.getOutputStream(), true);

			String lineIn = null;
			while (true) {

				while (!br.ready()) {					
					Thread.sleep(300);
				}
				while (br.ready()) {
					System.out.println(br.readLine());
				}

				System.out.println("Empiece a escribir papa");
				lineIn = in.readLine();
				if (lineIn.equals("x")) {
					break;
				}
				bw.println(lineIn);
			}
			socket.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void setRequestMessage(String message) {
		this.message = message;
	}

	/**
	 * @return
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
	}

	public String getRequestMessage() {
		return message;
	}

	public static void main(String[] args) {
		Client client = new Client();
		
		client.start();
	}
}
