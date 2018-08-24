/**
 * 
 */
package conecctions;

import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Miguel
 *
 */
public abstract class Connection implements IConnection {

	ReentrantLock lock;
	private int port;
	private Type type;
	DataOutputStream output;

	/**
	 * 
	 */
//	@SuppressWarnings("resource")
	public Connection(Type type) {
		this.type = type;
		this.port=1234;
		lock = new ReentrantLock();
//		if(!isPortAvailable(port) && type!=Type.Client) {
//			try {
//				port = new ServerSocket(port).getLocalPort();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}


	public boolean setPort(int port) {
		boolean settedPort = isPortAvailable(port);
		if(settedPort) {
			this.port=port;
		}
		return settedPort;
	}
	

	
	/**
	 * @param port2
	 * @return
	 */
	private boolean isPortAvailable(int port) {
		boolean isAvailable = true;
		try {
			ServerSocket socket = new ServerSocket(port);
			socket.close();
		}catch(Exception e) {
			isAvailable=false;
		}
		return isAvailable;
	}


	/**
	 * @return the port
	 */
	public int getPort() {
		
		return port;
	}

	public DataOutputStream getOutputStream() {
		return output;
	}
	
	public void setOutputStream(DataOutputStream stream) {
		this.output=stream;
	}	
	
	/**
	 * @return the type
	 */
	public Type getConnectionType() {
		return type;
	}

}
