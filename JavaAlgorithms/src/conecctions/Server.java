/**
 * 
 */
package conecctions;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.net.ssl.SSLServerSocketFactory;

/**
 * @author Miguel
 *
 */
public class Server implements IListener {

	public static final String KEYSTORE_LOCATION = "C:/Program Files (x86)/Java/jre1.8.0_181/bin/keystore.jks";
	public static final String KEYSTORE_PASSWORD = "123456";

	private int port;
	private boolean ssl;
	private ServerSocket serverSocket;
	private ArrayList<IListener> connections;

	public Server(int port, boolean sslActivated) {
		System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
		System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);
		this.port = port;
		ssl = sslActivated;
		connections = new ArrayList<>();
	}
	

	public Server(int port) {
		this(port, false);
	}

	public Server(boolean sslActivated) {
		this(1234,sslActivated);
	}
	
	public Server() {
		this(1234, false);
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setSSL(boolean sslActivated) {
		this.ssl = sslActivated;
	}
	public void start() {
		try {
			if (serverSocket == null) {
				openServerSocket();
			}
			while (true) {
				Socket socket = serverSocket.accept();
				Connection connection = new Connection(socket, this);
				connections.add(connection);
				connection.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @throws IOException
	 * 
	 */
	private void openServerSocket() throws IOException {

		if (ssl) {
			SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory
					.getDefault();
			serverSocket = ssf.createServerSocket(port);

		} else {
			serverSocket = new ServerSocket(port);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IListener#onMessageData(java.lang.String,
	 * conecctions.IListener)
	 */
	@Override
	public void onSendMessageData(String message) {
		connections.forEach(x -> x.onSendMessageData(message));

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IListener#onInputMessageData(java.lang.String,
	 * conecctions.IListener)
	 */
	@Override
	public void onInputMessageData(String message) {
		if (message.equals("newinstance")) {
			onSendMessageData("Welcome to the jungle");
		}
		
		System.out.println("recibido en servidor"+message);

	}

	public static void main(String[] args) {
		Server server = new Server();
		server.start();
	}
}
