/**
 * 
 */
package conecctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.net.ssl.SSLSocketFactory;

/**
 * @author Miguel
 *
 */
public class Client implements IListener {
	public static final String TRUSTTORE_LOCATION = "C:/Users/Asus/Desktop/Desktop/clientTrusted.jks";

	private int port;
	private String host;
	private Socket socket;
	private IListener listener;
	boolean ssl;

	public Client() {
		this(1234, "localhost", false);
	}
	
	public Client(String host) {
		this(1234,host);
	}
	
	public Client(boolean sslActivated) {
		this(1234,sslActivated);
	}
	
	public Client(int port, String host) {
		this(port,host,false);
	}
	public Client(int port, boolean sslActivated) {
		this(port,"localhost",sslActivated);
	}
	
	public Client(String host, boolean sslActivated) {
		this(1234,host,sslActivated);
	}

	public Client(int port, String host, boolean sslActivated) {
		System.setProperty("javax.net.ssl.trustStore", TRUSTTORE_LOCATION);
		this.port = port;
		this.ssl = sslActivated;
		this.host = host;
	}

	/**
	 * @param i
	 */
	public Client(int port) {
		this(port,"localhost");
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setSSLSecurity(boolean sslActivated) {
		this.ssl = sslActivated;
	}

	public void start() {
		try {
			openSocket();
			this.listener = new Conection(socket, this);
			((Thread) listener).start();
			initInputClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @return
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	private void openSocket() throws UnknownHostException, IOException {
		if(ssl) {
			SSLSocketFactory sf = (SSLSocketFactory) SSLSocketFactory.getDefault();
			socket = sf.createSocket(host, port);
		}else {
			socket = new Socket(host, port);
		}
	}

	/**
	 * @throws IOException
	 * 
	 */
	private void initInputClient() throws IOException {
		BufferedReader inClient = new BufferedReader(
				new InputStreamReader(System.in));
		while (true) {
			onSendMessageData(inClient.readLine());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IListener#onMessageData(java.lang.String,
	 * conecctions.IListener)
	 */
	@Override
	public void onSendMessageData(String message) {
		listener.onSendMessageData(message);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IListener#onInputMessageData(java.lang.String,
	 * conecctions.IListener)
	 */
	@Override
	public void onInputMessageData(String message) {
		System.out.println("recibido en cliente: " + message);
		if(message.equals("200 OK")) {
			Conection conection = (Conection) listener;
			try {
				conection.initDownload();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		Client client = new Client(8000,false);
		client.start();
	}
}
