package multicast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements IConListener {

    public static String ADDRESS ="224.0.0.3";
    String host;
    int port;
    private DatagramSocket socket;
    int toPort;
    Connection conn;

    public Client(String host) {
	this(host, 1234, 2345);
    }

    public Client() {
	this(ADDRESS, 1234, 2345);
    }

    public Client(String host, int port, int toPort) {
	this.host = host;
	this.port = port;
	this.toPort = toPort;
    }

    public Client(int port, int toPort) {
	this(ADDRESS, port, toPort);
    }

    public void setPort(int port) {
	if (conn == null) {
	    this.port = port;
	}
    }

    public void setToPort(int toPort) {
	if (conn == null) {
	    this.toPort = toPort;
	}
    }

    public void startMulticast() {
	try {
	    openSocket(false);
	    startInput();
	    conn = new Connection(socket, this);
	    conn.start();
	    

	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private void openSocket(boolean multiAvailable) throws Exception {
	if (multiAvailable) {
	    InetAddress net = InetAddress.getByName(this.host);
	    MulticastSocket ms = new MulticastSocket(port);
	    ms.joinGroup(net);
	    socket = ms;
	}else {
	    socket = new DatagramSocket(port);
	    socket.setBroadcast(true);
	}
    }

    private void startInput() {
	Thread t = new Thread(new Runnable() {

	    @Override
	    public void run() {
		System.out.println("Ingrese datos");
		Scanner sc = new Scanner(System.in);
		String in = "";
		while (!in.toUpperCase().equals("STOP")) {
		    in = sc.nextLine();
		    sendMessage(in);

		}

		sc.close();

	    }
	});
	t.start();

    }

    @Override
    public void onInputDatagram(DatagramPacket packet) {
	String m = new String(packet.getData());
	System.out.println("Recibido en cliente : " + m);

    }

    public void sendMessage(String m) {
	InetAddress host = null;
	try {
	    host = InetAddress.getByName(this.host);
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	DatagramPacket packet = new DatagramPacket(m.getBytes(), m.length(), host, toPort);
	conn.sendMessage(packet);
    }

    public static void main(String[] args) {
	Client c = new Client();
	c.startMulticast();
    }

}
