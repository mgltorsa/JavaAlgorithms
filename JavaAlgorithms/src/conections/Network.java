/**
 * 
 */
package conections;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * @author Miguel
 *
 */
public class Network {

	
	public static void main(String[] args) {
		SimpleNetwork();
//		MultiNetwork();
//		NetworkByName();
		
	}

	/**
	 * 
	 */
	private static void NetworkByName() {
		NetworkInterface eth0;
		try {
			eth0 = NetworkInterface.getByName("eth0");
			Enumeration<?> addresses = eth0.getInetAddresses();
			while (addresses.hasMoreElements()) {
			 System.out.println(addresses.nextElement());
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 
	 */
	private static void MultiNetwork() {
		Enumeration<NetworkInterface> interfaces;
		try {
			interfaces = NetworkInterface.getNetworkInterfaces();
			while (interfaces.hasMoreElements()) {
				NetworkInterface ni = interfaces.nextElement();
				System.out.println(ni);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static void SimpleNetwork() {
		try {
			InetAddress local = InetAddress.getByName("127.0.0.1");
			NetworkInterface ni = NetworkInterface.getByInetAddress(local);
			System.out.println(ni);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
