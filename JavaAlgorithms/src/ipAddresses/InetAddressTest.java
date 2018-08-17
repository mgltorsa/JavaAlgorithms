/**
 * 
 */
package ipAddresses;

import java.net.*;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

import jdk.nashorn.internal.runtime.arrays.ArrayData;
/**
 * @author Miguel
 *
 */
public class InetAddressTest {
	
	public static void main(String[] args) {
		try {
			InetAddress inet = InetAddress.getLocalHost();
			System.out.println(inet);
			inet = InetAddress.getByName("www.nasa.gov");
			System.out.println(inet);
			ArrayList<InetAddress> addresses = new ArrayList<InetAddress>();			
			InetAddress[] arr1 =InetAddress.getAllByName("www.yahoo.com");
			for(InetAddress inetElement : arr1) {
				addresses.add(inetElement);
			}			
			arr1 =InetAddress.getAllByName("www.google.com");
			for(InetAddress inetElement : arr1) {
				addresses.add(inetElement);
			}
			
			addresses.forEach(item -> 
					System.out.println(item)				
			);

			
			

			
			
			
			
		}catch(Exception e) {
			
		}
	}
}
