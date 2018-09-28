/**
 * 
 */
package conecctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Miguel
 *
 */
public class ReaderThread extends Thread implements IListener {

	private Socket socket;
	private IListener listener;
	private BufferedReader in;
	/**
	 * 
	 */
	public ReaderThread(Socket socket, IListener listener) {
		this.socket = socket;
		this.listener = listener;
		openInputStream();
	}
	/**
	 * 
	 */
	private void openInputStream() {
		try {
			in = new BufferedReader(
					new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			// TODO
			e.printStackTrace();
			listener.onSendMessageData(e.getMessage());
		}

	}

	public void start() {
		while (!socket.isClosed()) {
			try {
				while (!in.ready()) {
					Thread.sleep(500);
				}
				String input = "";
				while (in.ready()) {
					input += in.readLine();
				}
				onInputMessageData(input);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IListener#onInputMessageData(java.lang.String,
	 * conecctions.IListener)
	 */
	@Override
	public void onInputMessageData(String message) {
		
		listener.onInputMessageData(message);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IListener#onSendMessageData(java.lang.String,
	 * conecctions.IListener)
	 */
	@Override
	public void onSendMessageData(String message) {
		if (message.equals("revalidate")) {
			openInputStream();
		}
	}

}
