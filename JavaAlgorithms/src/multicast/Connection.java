package multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Connection extends Thread {

    private DatagramSocket socket;
    private IConListener listener;

    public Connection(DatagramSocket socket, IConListener listener) {
	this.socket = socket;
	this.listener = listener;
    }

    @Override
    public void run() {
	while (!socket.isClosed()) {
	    byte[] buf = new byte[1024];
	    DatagramPacket packet = new DatagramPacket(buf, buf.length);
	    try {
		socket.receive(packet);
		listener.onInputDatagram(packet);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    public void sendMessage(DatagramPacket packet) {
	try {
	    socket.send(packet);
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }
}
