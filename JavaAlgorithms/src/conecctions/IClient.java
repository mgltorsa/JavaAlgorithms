/**
 * 
 */
package conecctions;

/**
 * @author Miguel
 *
 */
public interface IClient extends IConnection {

	public void setRequestMessage(String request);
	public String getRequestMessage();
}
