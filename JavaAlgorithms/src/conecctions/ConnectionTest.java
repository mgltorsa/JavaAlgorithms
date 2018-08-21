/**
 * 
 */
package conecctions;

/**
 * @author Miguel
 *
 */
public class ConnectionTest {

	private static Server server;
	
	public static void main(String[] args) throws InterruptedException {
		
		
		Thread serverT = new Thread(getRunnableServer());
//		Thread client = new Thread (getRunnableClient());
		serverT.start();
//		client.start();
		
		Client client = new Client();
		while(server==null || !server.isOnline()) {
			Thread.sleep(500);
		}
		
		client.startClient();
		
	}
	
	/**
	 * @return
	 */
	private static Runnable getRunnableClient() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				Client client = new Client();
				String message = ConnectionTest.getClientMessages();
				client.startClient(message);
			}

			
		};
		return runnable;
	}

	
	/**
	 * @return
	 */
	private static Runnable getRunnableServer() {
		Runnable runnable = new Runnable() {

			

			@Override
			public void run() {
				server = new Server();
				server.startServer();
			}
		};

		return runnable;
	}
	
	/**
	 * @return
	 */
	protected static String getClientMessages() {
		
		
		return "Mensaje de miguel";
	}

}
