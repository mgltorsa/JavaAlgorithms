/**
 * 
 */
package conecctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Miguel
 *
 */
public class ConnectionTest {


	
	public static void main(String[] args) throws InterruptedException {
		
		Server server = new Server();
		server.start();	

	}

}
