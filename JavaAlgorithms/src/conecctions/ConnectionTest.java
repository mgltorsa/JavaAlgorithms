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

	public static Server server;
	
	public static class S{
		ServerSocket ss;
		
		public S() throws IOException {
			ss=new ServerSocket(1234);
		}
		
		public void start() throws IOException {
			Socket cs = ss.accept();
			PrintWriter bw =new PrintWriter( cs.getOutputStream(),true);
			bw.println("recibido en: "+InetAddress.getLocalHost());
			String line;
			BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			while( br!=null && (line=br.readLine())!=null && !line.equals("") )  {
				String response =line;
				System.out.println(response);
			}
			br.close();
			
		}
	}
	
	public static class C{
		Socket cs;
		public C() {
			cs =null;
		}
		public void start() throws UnknownHostException, IOException {
			cs= new Socket("localhost", 1234);
			PrintWriter bw =new PrintWriter( cs.getOutputStream(),true);
			bw.println("Solicitud desde: "+cs.getLocalAddress());
			BufferedReader br = new BufferedReader(new InputStreamReader(cs.getInputStream()));
			String line;
			while( br!=null && (line=br.readLine())!=null && !line.equals("") )  {
				String response =line;
				System.out.println(response);
			}
			br.close();
			
			
		}
	}
	public static void main(String[] args) throws InterruptedException {
		
		Server server = new Server();
		server.start();

	}

}
