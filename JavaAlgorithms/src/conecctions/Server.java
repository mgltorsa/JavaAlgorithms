/**
 * 
 */
package conecctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Miguel
 *
 */
public class Server extends Connection {

	private boolean isOnline = false;

	/**
	 * @param type
	 */
	public Server() {

		super(ConnectionType.Server);
		try {
			serverSocket = new ServerSocket(getPort());
		} catch (IOException e) {
			System.out.println("From server constructor");
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public synchronized void startServer() {
		lock.lock();
		try {
			isOnline = true;
			socket = serverSocket.accept();
			PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
			while (output == null) {
				lock.newCondition().await();
			}
			output.println("¿Que dice perro? (respuesta desde): " + socket.getLocalAddress().toString());

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (br != null && (serverMessage = br.readLine()) != null & !serverMessage.equals("")) {
				System.out.println(serverMessage);
			}

			br.close();

			System.out.println("connection ended");

			serverSocket.close();

		} catch (Exception e) {
			System.out.println("From server");
			e.printStackTrace();
		}

		lock.unlock();

	}

	/**
	 * @return
	 */
	public boolean isOnline() {
		// TODO Auto-generated method stub
		return isOnline;
	}

}
