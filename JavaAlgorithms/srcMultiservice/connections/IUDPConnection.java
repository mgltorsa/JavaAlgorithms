/**
 * 
 */
package connections;

import java.net.DatagramSocket;

/**
 * @author Miguel
 *
 */
public interface IUDPConnection extends IConnection{

    public DatagramSocket getSocket();
    
    
}
