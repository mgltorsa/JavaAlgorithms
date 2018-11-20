/**
 * 
 */
package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.MulticastSocket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Miguel
 *
 */
public class Server implements UdpListener {

    public final static int BUFF_LIMIT = 256;
    public final static String GET_TIME = "date";

    private DatagramSocket socket;
    private UdpConection conection;

    public Server() {
	this(8888, false);
    }

    public Server(int port) {
	this(port, false);
    }

    public Server(boolean availableForGroups) {

	this(1234, availableForGroups);
    }

    public Server(int port, boolean availableForGroups) {
	try {
	    if (availableForGroups) {
		// TODO
		throw new IllegalArgumentException("Aun no esta implementado el modo MulticastServer");
//		socket = new MulticastSocket(port);
	    } else {
		socket = new DatagramSocket(port);
	    }

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    public void start() {
	conection = new UdpConection(this.socket, this);
	conection.start();
    }

    @Override
    public synchronized void inputDatagram(DatagramPacket input) {
	try {
	    if (socket instanceof MulticastSocket) {
		((MulticastSocket) socket).joinGroup(input.getAddress());
	    }

	    computeQuery(input);

	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    public DatagramPacket computeQuery(DatagramPacket input) {
	String info = new String(input.getData());
	DatagramPacket output = null;
	System.out.println("recibido en servidor: " + info);

	String message = "puto";

	this.conection.sendDatagram(new DatagramPacket(message.getBytes(), message.length(), input.getAddress(), input.getPort()));

	if (info.contains(GET_TIME)) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY/MM/dd:hh:mm:ss");
	    Date date = new Date();
	    message = "La fecha del servidor es: " + dateFormat.format(date);
	} else if (info.contains("transfer")) {
	    message = "envie esa monda";
	    String fileSize = info.split(":")[1];
	    conection.initDownload(Long.parseLong(fileSize));
	}

	output = new DatagramPacket(message.getBytes(), message.length(), input.getAddress(), input.getPort());

	return output;

    }

    public static void main(String[] args) {
	Server server = new Server(1234);
	server.start();
//	try {
//	    DatagramPacket input = new DatagramPacket(new byte[Server.BUFF_LIMIT], Server.BUFF_LIMIT);
//	    DatagramSocket socket = new DatagramSocket(1234);
//	    socket.receive(input);
//	    System.out.println("received in server: "+new String(input.getData()));
//	    DatagramPacket output = computeQuery(input);
//	    socket.send(output);
//	    socket.close();
//	    socket.close();
//	} catch (IOException e) {
//	    // TODO
//	    e.printStackTrace();
//	}

    }

}
