/**
 * 
 */
package conecctions;

import java.io.DataOutputStream;

/**
 * @author Miguel
 *
 */
public interface IConnection {

	public int getPort();
	public DataOutputStream getOutputStream();
	public void setOutputStream(DataOutputStream stream);
	public Type getConnectionType();
	public boolean setPort(int port);
    public void start();
	
}
