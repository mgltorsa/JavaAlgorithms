/**
 * 
 */
package connections;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Miguel
 *
 */
public class UDPConnection extends Thread implements IUDPConnection {

    
    private DatagramSocket socket;
    private IConnectionListener listener;
    
    
    public UDPConnection(DatagramSocket socket, IConnectionListener listener) {
	this.socket=socket;
	this.listener = listener;
    }
    
    
    @Override
    public InetAddress getAddressConsumer() {
	// TODO Auto-generated method stub
	return null;
    }

   
    @Override
    public boolean isConnected() {
	// TODO Auto-generated method stub
	return false;
    }

   
    @Override
    public void sendMessage(byte[] message) {
	// TODO Auto-generated method stub
	
    }

    
    @Override
    public void run() {
	while(true) {
	    DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
	    try {
		socket.receive(packet);
		onInputDatagram(packet);
	    } catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}
    }

    
    private void onInputDatagram(DatagramPacket packet) {
	
    }


    @Override
    public DatagramSocket getSocket() {
	return socket;
    }


    @Override
    public void startConnection() {
	run();	
    }

    
    
}
