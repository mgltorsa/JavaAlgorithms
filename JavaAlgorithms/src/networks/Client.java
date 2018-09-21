package networks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

	Socket socket;
	int port;
	String host;

	public Client(String host, int port) {
		setHost(host);
		setPort(port);
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void start() {
		try {
			socket = new Socket(host, port);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader inClient = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			InitReadThread(in);
			InitWriteThread(out, inClient);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void InitWriteThread(PrintWriter out,BufferedReader inClient) throws IOException {
		while (true) {
			
			
			String toServer = "";
			String inputClient = inClient.readLine();

			toServer += inputClient;

			out.println(toServer);
			
		}
	}

	private void InitReadThread(BufferedReader in) {
		Thread readT = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					try {
						while (!in.ready()) {								
							Thread.sleep(500);
						}
						String input = "";
						while (in.ready()) {
							input += in.readLine();
						}

						System.out.println(input);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
					
				}
				
			}
		});
		readT.start();
		
	}

	public static void main(String[] args) {
//		Client client = new Client("172.30.175.248",1234);
		Client client = new Client("localhost", 1234);
		client.start();
	}
}
