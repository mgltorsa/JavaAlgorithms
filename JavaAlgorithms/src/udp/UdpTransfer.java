/**
 * 
 */
package udp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Miguel
 *
 */
public final class UdpTransfer {

    private static final String ROOT = "./data";

    public static void Transfer(DatagramSocket socket, long fileSize) {
	transfer(socket, fileSize, ROOT);
    }

    private static void transfer(DatagramSocket socket, long fileSize, String path) {
	byte[] info = new byte[256];
	ByteArrayOutputStream bytes = new ByteArrayOutputStream();
	long readed = 0;
	InetAddress address = null;
	try {
	    while (readed < fileSize) {
		try {
		    DatagramPacket input = new DatagramPacket(info, info.length);
		    if (address == null) {
			address = input.getAddress();
		    }
		    socket.receive(input);
		    bytes.write(input.getData());
		    readed += input.getLength();
		} catch (Exception e) {
		    break;
		}
	    }
	    if (readed >= fileSize) {
		File file = new File(path + "from " + address.getHostAddress().replaceAll(".", "-"));
		if (!file.exists()) {
		    file.createNewFile();
		}
		
		

	    }
	} catch (Exception e) {

	}

    }
}
