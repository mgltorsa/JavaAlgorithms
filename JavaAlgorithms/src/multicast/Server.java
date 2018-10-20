package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

public class Server implements IConListener {

    DatagramSocket socket;
    int port;
    String net;
    int toPort;
    Connection conn;

    public Server(String net) {
	this(net, 2345, 1234);
    }

    public Server() {
	this("224.0.0.3", 2345, 1234);
    }

    public Server(String net, int port, int toPort) {
	this.net = net;
	this.port = port;
	this.toPort = toPort;
    }

    public void startMulticast() {
	try {
	    openSocket(true);
	    conn = new Connection(socket, this);
	    conn.start();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    private void openSocket(boolean multiAvailable) throws IOException {
	if (multiAvailable) {
	    InetAddress group = InetAddress.getByName(net);
	    socket = new MulticastSocket(port);
	    ((MulticastSocket) socket).joinGroup(group);
	}
    }

    @Override
    public void onInputDatagram(DatagramPacket packet) {
	String message = new String(packet.getData());
	String from = " desde: " + packet.getAddress().getHostAddress();
	System.out.println("Recibido en servidor : " + message);
	sendMessage("Confirmado hijo "+from);
	System.out.println("from "+from);

    }

    public void sendMessage(String m) {
	InetAddress host = null;
	try {
	    host = InetAddress.getByName(this.net);
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	DatagramPacket packet = new DatagramPacket(m.getBytes(), m.length(), host, toPort);
	conn.sendMessage(packet);
    }

    public static void main(String[] args) {
	Server s = new Server();
	s.startMulticast();
    }

}
