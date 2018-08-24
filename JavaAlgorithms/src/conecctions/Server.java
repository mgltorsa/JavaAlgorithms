/**
 * 
 */
package conecctions;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Miguel
 *
 */
public class Server extends Connection implements IServer {

	private boolean isOnline = false;
	private ServerSocket serverSocket;
	private String response;

	/**
	 * @param type
	 */
	public Server() {

		super(Type.Server);
		try {
			serverSocket = new ServerSocket(getPort());
			isOnline = false;
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
	public synchronized boolean start() {
		lock.lock();
		boolean start = true;
		try {
			
			isOnline=true;
			Socket clientSocket = serverSocket.accept();
//			while(true) {
			PrintWriter bw = new PrintWriter(clientSocket.getOutputStream(),true);
			while(bw==null) {
				lock.newCondition().await();
			}
			setResponseMessage("Mensaje recibido desde: " + clientSocket.getLocalAddress());

			bw.println(getResponseMessage());
			BufferedReader br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			bw.println("Respuestas: ");
			String line;
			while (br != null && (line = br.readLine()) != null & !line.equals("")) {				
				System.out.println(line);
				String[] info = line.split(":");
				bw.println(getResponse(info));
			}

//			}
			clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
			start = false;
		} catch (InterruptedException e) {
			start=false;
			e.printStackTrace();
		}

		return start;
	}

	/**
	 * @param line
	 * @return
	 */
	private String getResponse(String[] line) {
		String response = "Invalid format";
		try {
			if (line[0].trim().toLowerCase().equals("suma")) {
				String[] sum = line[1].trim().split(" ");
				int a = Integer.parseInt(sum[0].trim());
				int b = Integer.parseInt(sum[1].trim());
				response = "La suma de " + a + " y " + b + " es: " + suma(a, b);
			} else if (line[0].trim().toLowerCase().equals("resta")) {
				String[] rest = line[1].trim().split(" ");
				int a = Integer.parseInt(rest[0].trim());
				int b = Integer.parseInt(rest[1].trim());
				response = "La resta de " + a + " y " + b + " es: " + rest(a, b);
				;
			}
		} catch (NumberFormatException e) {
			response = "Verifique que los datos sean números";
		} catch (Exception e) {
			response = "A problem has occurred";
		}
		return response;
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

}
