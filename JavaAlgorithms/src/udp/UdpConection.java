/**
 * 
 */
package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author Miguel
 *
 */
public class UdpConection extends Thread {

    private DatagramSocket socket;
    private UdpListener listener;
    private boolean downloadMode;

    public UdpConection(DatagramSocket socket, UdpListener listener) {
	this.socket = socket;
	this.listener = listener;
    }

    @Override
    public void run() {
//	initReceiveThread();

	while (true) {
	    if (!downloadMode) {
		DatagramPacket input = new DatagramPacket(new byte[Server.BUFF_LIMIT], Server.BUFF_LIMIT);
		try {
		    socket.receive(input);
		    inputDatagram(input);
		} catch (IOException e) { // TODO
		    e.printStackTrace();
		}
	    }
	}
    }

//    private void initReceiveThread() {
//	Runnable run = new Runnable() {
//
//	    @Override
//	    public void run() {
//
//		DatagramPacket input = new DatagramPacket(new byte[Server.BUFF_LIMIT], Server.BUFF_LIMIT);
//		try {
//		    socket.receive(input);
//		    inputDatagram(input);
//		} catch (IOException e) {
//		    // TODO
//		    e.printStackTrace();
//		}
//
//	    }
//	};
//	Thread thread = new Thread(run);
//
//    }

    public void inputDatagram(DatagramPacket input) {
	listener.inputDatagram(input);
    }

    public void sendDatagram(DatagramPacket output) {
	try {
	    socket.send(output);

	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    /**
     * 
     */
    public void initDownload(long fileSize) {
	downloadMode = true;
	UdpTransfer.Transfer(socket,fileSize);

    }

}
