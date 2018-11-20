package conections;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Conection extends Thread implements IListener {

    Socket socket;
    IListener listener;
    PrintWriter out;
    private String id;
    private ReaderThread readThread;

    public Conection(Socket socket, IListener listener) throws IOException {
	this.listener = listener;
	this.socket = socket;
	this.out = new PrintWriter(socket.getOutputStream(), true);

    }

    public void SetId(String id) {
	this.id = id;
    }

    public String getConnectionId() {
	return id;
    }

    @Override
    public void run() {

	readThread = new ReaderThread(socket, this);
	readThread.start();

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
	    System.out.println("cerro");
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
    public synchronized void onSendMessageData(String message) {

	if (readThread != null && readThread.isDownloadMode()) {
	    try {
		this.wait();
	    } catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

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
	String m = message;
	if (this.id != null) {
	    m = this.id + "/" + m;
	}
	listener.onInputMessageData(m);
    }

    /**
     * @return
     */
    public Socket getSocket() {
	return socket;
    }

    public void initDownload() {
	try {
	    readThread.setTransferMode(true);
	    Transfer transfer = new Transfer(socket);
	    transfer.download();
	    readThread.setTransferMode(false);

	} catch (Exception e) {
	    readThread.setTransferMode(false);
	    e.printStackTrace();
	}

    }

    /**
     * @param fileName
     */
    public void initTransfer(String fileName) {
	Transfer transfer;
	try {
	    readThread.setTransferMode(true);
	    transfer = new Transfer(socket);
	    transfer.transfer(fileName);
	    readThread.setTransferMode(false);

	} catch (Exception e) {
	    readThread.setTransferMode(false);
	}

    }

}
