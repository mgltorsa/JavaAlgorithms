/**
 * 
 */
package conecctions;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Miguel
 *
 */
public abstract class Connection {

	ReentrantLock lock;
	private int port;
	private ConnectionType type;
	private String host;
	String serverMessage;
	Socket socket;
	ServerSocket serverSocket;
	DataOutputStream clientOutput, serverOutput;

	/**
	 * 
	 */
	public Connection(ConnectionType type) {
		this.type = type;
		port = 1234;
		lock = new ReentrantLock();
		switch (type) {
		case Server:
			setPort(port);
			break;
		case Client:
			setHost("localhost");
			setPort(port);
			break;
		default:
			throw new IllegalArgumentException();
		}

	}



	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}

	/**
	 * @return the type
	 */
	public ConnectionType getType() {
		return type;
	}

}
