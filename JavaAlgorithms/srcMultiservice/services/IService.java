/**
 * 
 */
package services;

import java.util.List;

import connections.IConnection;

/**
 * @author Miguel
 *
 */
public interface IService {
    
    public List<IConnection> getConnections();
    public int getPort();
    public void setPort(int port);
    public void initConnection(IConnection connection);
    public void startService();
    public boolean isAvailable();
}
