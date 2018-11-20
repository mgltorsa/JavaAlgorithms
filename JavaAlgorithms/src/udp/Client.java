/**
 * 
 */
package udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Miguel
 *
 */
public class Client implements UdpListener {

    private UdpConection conection;
    private InetAddress address;
    private int port;

    public Client() {
	this("localhost", 1234);
    }

    public Client(String host, int port) {
	try {
	    address = InetAddress.getLocalHost();
	    this.port = port;
	} catch (UnknownHostException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    public void start() {
	try {
	    conection = new UdpConection(new DatagramSocket(1235), this);
	    conection.start();
	    initInputClient();
	} catch (SocketException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    /**
     * 
     */
    private void initInputClient() {
	Scanner sc = new Scanner(System.in);
	while (true) {
	    String inputClient = sc.nextLine();

	    if (inputClient.toUpperCase().equals("STOP")) {
		break;
	    }
	    DatagramPacket output = new DatagramPacket(inputClient.getBytes(), inputClient.length(), address, port);

	    conection.sendDatagram(output);
	}
	sc.close();

    }

    @Override
    public void inputDatagram(DatagramPacket input) {
	System.out.println("recibido en cliente: "+new String(input.getData()));

    }

    public static void main(String[] args) {
	Client client = new Client();
	client.start();

//	try {
//	    InetAddress address = InetAddress.getByName("localhost");
//	    int port = 1234;
//	    DatagramPacket output = new DatagramPacket(Server.GET_TIME.getBytes(), Server.GET_TIME.length(), address,
//		    port);
//	    DatagramSocket socket = new DatagramSocket(1235);
//	    socket.send(output);
//	    DatagramPacket input = new DatagramPacket(new byte[Server.BUFF_LIMIT], Server.BUFF_LIMIT);
//	    socket.receive(input);
//	    System.out.println("recibido en cliente: " + new String(input.getData()));
//	    socket.close();
//	} catch (Exception e) {
//	    e.printStackTrace();
//	}
    }

}
