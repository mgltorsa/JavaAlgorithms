/**
 * 
 */
package conections;

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
    private boolean download;

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
	    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	} catch (IOException e) {
	    // TODO
	    e.printStackTrace();
	    listener.onSendMessageData(e.getMessage());
	}

    }

    public void start() {
	while (!socket.isClosed()) {
	    try {
		String input = null;
		if (!download) {
		    while (((input = in.readLine()) != null)) {
			onInputMessageData(input);
		    }
		}

	    } catch (Exception e) {
		try {
		    socket.close();
		} catch (IOException e1) {
		    e1.printStackTrace();
		}
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

    /**
     * @param b
     * @return 
     */
    public synchronized void setTransferMode(boolean b) {
	clearInputStream();
	
	this.download = b;

    }

    /**
     * 
     */
    private void clearInputStream() {
	try {
	    if(!in.ready()) {
		return;
	    }
	    while (in.ready()) {
	        in.readLine();
	    }
	} catch (IOException e) {
	}
	
    }

}
