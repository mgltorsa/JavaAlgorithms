/**
 * 
 */
package udp;

import java.net.DatagramPacket;

/**
 * @author Miguel
 *
 */
public interface UdpListener {

    public void inputDatagram(DatagramPacket input);
}
