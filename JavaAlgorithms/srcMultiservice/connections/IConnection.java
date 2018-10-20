/**
 * 
 */
package connections;

import java.net.InetAddress;

/**
 * @author Miguel
 *
 */
public interface IConnection {
    
    public InetAddress getAddressConsumer();
    public boolean isConnected();
    public void sendMessage(byte[] message);
    public void startConnection();
    public void start();
    
}
