/**
 * 
 */
package conections;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Miguel
 *
 */
public class SpamCheck {

	public static final String BLACKHOLE = "sbl.spamhaus.org";

	public static void main(String[] args) throws UnknownHostException {
		System.out.println("Vaia");
		testSpamCheck(args);

	}

	/**
	 * @param string
	 * @param string2
	 * @param string3
	 */
	private static void testSpamCheck(String... args) {
		if (args.length==0) {
			testSpamCheck("207.34.56.23", "125.12.32.4", "130.130.130.130");
		} else {
			for (String arg : args) {
				if (isSpammer(arg)) {
					System.out.println(arg + " is a known spammer.");
				} else {
					System.out.println(arg + " appears legitimate.");
				}
			}
		}
	}

	private static boolean isSpammer(String arg) {
		try {
			InetAddress address = InetAddress.getByName(arg);
			byte[] quad = address.getAddress();
			String query = BLACKHOLE;
			for (byte octet : quad) {
				int unsignedByte = octet < 0 ? octet + 256 : octet;
				query = unsignedByte + "." + query;
			}
			InetAddress.getByName(query);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
