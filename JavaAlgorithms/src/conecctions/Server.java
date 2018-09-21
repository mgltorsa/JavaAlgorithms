/**
 * 
 */
package conecctions;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Miguel
 *
 */
public class Server extends Source implements IServer {

	private boolean isOnline = true;
	private ServerSocket serverSocket;
	private String response;
	private int ones;
	private int zeros;
	private int sum = 2;
	private int quantity = 1;

	/**
	 * @param type
	 */
	public Server() {

		super(Type.Server);
		try {
			serverSocket = new ServerSocket(getPort());
		} catch (IOException e) {
			System.out.println("From server constructor");
			e.printStackTrace();
		}

	}

	/**
	 * @return
	 */
	public boolean isOnline() {
		return isOnline;
	}

	public ServerSocket getSocket() {
		return serverSocket;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IConnection#getOutputStream()
	 */
	@Override
	public DataOutputStream getOutputStream() {
		return super.getOutputStream();
	}

	/*
	 * (non-Javadoc) *
	 * 
	 * @see conecctions.IConnection#start()
	 */
	@Override
	public synchronized void start() {

		while (isOnline) {
			try {
				Socket connection = serverSocket.accept();
				Thread thread = new Thread(new ConnectionThread(this, connection));
				thread.start();
			} catch (Exception e) {
				e.printStackTrace();
				isOnline = false;
			}
		}
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	private int rest(int a, int b) {
		return a - b;
	}

	/**
	 * @param a
	 * @param b
	 * @return
	 */
	private int suma(int a, int b) {
		return a + b;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IServer#setResponseMessage(java.lang.String)
	 */
	@Override
	public void setResponseMessage(String message) {
		this.response = message;
	}

	public String getResponseMessage() {
		return response;
	}

	public int bet(int q, int value) {

		int res = value;

		if (ones == 0 && zeros == 0) {
			betTo(q);
			return res;
		}
		if (q == 1) {
			if (ones > zeros) {
				ones--;
				res = value;
			} else {
				ones++;
				res = -value;
			}

		} else {
			if (ones > zeros) {
				res = -value;
			} else {
				zeros++;
				res = value;
			}
		}
		return res;

	}

	private void betTo(int q) {
		if (q == 1) {
			ones++;
		} else {
			zeros++;
		}
	}

	public void canBet(int cash, AtomicBoolean canBet) {

		boolean response = true;
		if (cash < (sum/quantity)) {
			
			response = false;
		}
		sum += cash;
		quantity++;
		canBet.set(response);
	}

}
