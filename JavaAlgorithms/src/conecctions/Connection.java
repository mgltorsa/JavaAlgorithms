package conecctions;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection extends Thread implements IListener {

	Socket socket;
	IListener listener;
	volatile PrintWriter out;

	public Connection(Socket socket, IListener listener) throws IOException {
		this.listener = listener;
		this.socket = socket;
		this.out = new PrintWriter(
				new OutputStreamWriter(socket.getOutputStream()), true);
	}

	@Override
	public void run() {

		listener.onSendMessageData("newinstance");
		ReaderThread rt = new ReaderThread(socket, this);
		rt.start();

	}

	public synchronized void write(String response) {
		if (!(response.isEmpty() && response.equals(""))) {
			out.println(response);
		}
	}	

	public void closeConnection() {
		try {
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see conecctions.IListener#onMessageData(java.lang.String,
	 * conecctions.IListener)
	 */
	@Override
	public void onSendMessageData(String message) {
		out.println(message);
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
}
