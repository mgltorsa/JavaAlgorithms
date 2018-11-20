/**
 * 
 */
package conections;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import javax.net.ssl.SSLServerSocketFactory;

/**
 * @author Miguel
 *
 */
public class Server implements IListener {

    public static final String KEYSTORE_LOCATION = "C:/Users/Asus/Desktop/Desktop/key.jks";
    public static final String KEYSTORE_PASSWORD = "password";
    public static int ID = 0;

    private int port;
    private boolean ssl;
    private ServerSocket serverSocket;
    private HashMap<String, IListener> conections;

    public Server(int port, boolean sslActivated) {

	System.setProperty("javax.net.ssl.keyStore", KEYSTORE_LOCATION);
	System.setProperty("javax.net.ssl.keyStorePassword", KEYSTORE_PASSWORD);
	this.port = port;
	ssl = sslActivated;
	conections = new HashMap<>();
    }

    public Server(int port) {
	this(port, false);
    }

    public Server(boolean sslActivated) {
	this(1234, sslActivated);
    }

    public Server() {
	this(1234, false);
    }

    public void setPort(int port) {
	this.port = port;
    }

    public void setSSL(boolean sslActivated) {
	this.ssl = sslActivated;
    }

    public void start() {
	try {
	    if (serverSocket == null) {
		openServerSocket();
	    }
	    while (true) {
		Socket socket = serverSocket.accept();
		Conection connection = new Conection(socket, this);
		connection.SetId("" + ID);
		conections.put(connection.getConnectionId(), connection);

		ID++;
		connection.start();
		sendNewInstance(connection);
	    }

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    /**
     * @param connection
     */
    private void sendNewInstance(Conection connection) {

	try {
	    while (connection.out == null) {
		Thread.sleep(500);
	    }
	    onSendMessageData(connection.getConnectionId() + "/Welcome to the jungle");

	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    /**
     * @throws IOException
     * 
     */
    private void openServerSocket() throws IOException {

	if (ssl) {
	    SSLServerSocketFactory ssf = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
	    serverSocket = ssf.createServerSocket(port);

	} else {
	    serverSocket = new ServerSocket(port);
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
	String info[] = message.split("/");
	String conecctionId = info[0];
	String res = info[1];
	conections.get(conecctionId).onSendMessageData(res);

    }

    /*
     * (non-Javadoc)
     * 
     * @see conecctions.IListener#onInputMessageData(java.lang.String,
     * conecctions.IListener)
     */
    @Override
    public void onInputMessageData(String message) {

	String info[] = message.split("/");
	String conecctionId = info[0];
	String query = info[1];
	computeQuery(conecctionId, query);
	System.out.println("recibido en servidor: " + message);

    }

    /**
     * @param conectionId
     * @param query
     */
    private void computeQuery(String conectionId, String query) {
	String[] infoQuery = query.split(":");
	if (infoQuery.length >= 2) {
	    if (infoQuery[0].toLowerCase().equals("download")) {
		if (Transfer.existFile(infoQuery[1])) {
		    onSendMessageData(conectionId + "/200 OK");
		    initializeTransfer(conectionId, infoQuery[1]);
		} else {
		    onSendMessageData(conectionId + "/404 Not Found");
		}

	    }else if(infoQuery[0].toLowerCase().contains("upload")){
		System.out.println("received upload");
		onSendMessageData(conectionId+"/preparefortransfer");
		initializeDownload(conectionId);

	    }
	} else {
	    onSendMessageData(conectionId + "/400 Bad Request");
	}

    }

    /**
     * 
     */
    private void initializeDownload(String conectionId) {
	Conection conection = (Conection) conections.get(conectionId);
	conection.initDownload();
	
    }

    /**
     * @param conectionId
     * @param string
     */
    private void initializeTransfer(String conectionId, String fileName) {

	Conection conection = ((Conection) conections.get(conectionId));
	try {
	    conection.initTransfer(fileName);

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public static void main(String[] args) {
	Server server = new Server();
	server.start();
	File file = new File("./data/img.png");
	System.out.println(file.length());
    }
}
