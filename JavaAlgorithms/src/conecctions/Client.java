/**
 * 
 */
package conecctions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



/**
 * @author Miguel
 *
 */
public class Client extends Connection{

	/**
	 * @param type
	 */
	public Client() {
		super(ConnectionType.Client);
		try {
			socket= new Socket();
		} catch (Exception e) {
			System.out.println("From client constructor");
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 
	 */
	public synchronized void startClient(String message) {
		try {
			socket = new Socket(getHost(), getPort());
			
			PrintWriter output = new PrintWriter(socket.getOutputStream(),true);
			output.println(message);
			
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while( br!=null && (serverMessage=br.readLine())!=null & !serverMessage.equals("") ) {
				System.out.println(serverMessage);
			}
			br.close();
			
			
		    
		} catch (Exception e) {
			System.out.println("from start client");
			e.printStackTrace();
		}
		

	}
	
	/**
	 * 
	 */
	public void startClient() {
		Scanner sc= new Scanner(System.in);
		System.out.println("Ingrese un mensaje");
		String message = sc.nextLine();
		sc.close();
		startClient(message);
	}

	
}
