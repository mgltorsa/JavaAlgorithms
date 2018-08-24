/**
 * 
 */
package conecctions;

/**
 * @author Miguel
 *
 */
public interface IServer extends IConnection{
	public void setResponseMessage(String message);
	public String getResponseMessage();
}
