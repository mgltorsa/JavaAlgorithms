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
public class Client extends Connection implements IClient{

	private String host;
	private Socket socket;
	private String message;
	/**
	 * @param type
	 */
	public Client() {
		super(Type.Client);
		socket = new Socket();
		setRequestMessage("Suma:5 7\nSuma:3 4");
		host="localhost";
	}
	
	public Client(Type type, String host) {
		super(type);
		socket = new Socket();
		setRequestMessage("Suma:5 7\nSuma:3 4");
		this.host=host;
	}
	
	public Client(Type type, int port) {
		super(type);
		host="localhost";
		setPort(port);

	}
	
	public Client(Type type, String host, int port) {
		super(type);
		socket = new Socket();
		this.host=host;
		setPort(port);		
	}


	/* (non-Javadoc)
	 * @see conecctions.IConnection#start()
	 */
	@Override
	public boolean start() {
		boolean start = true;
		try {
			socket = new Socket(getHost(), getPort());
			
			PrintWriter bw = new PrintWriter(socket.getOutputStream(),true);
			bw.println(message);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
			String line;
			while( br!=null && (line=br.readLine())!=null && !line.equals("") )  {
				String response =line;
				System.out.println(response);
			}
			br.close();
			
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
			start=false;
		} catch (IOException e) {
			e.printStackTrace();
			start=false;
		}
		return start;
	}

	public void setRequestMessage(String message) {
		this.message=message;
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


	
	
	
	
}
