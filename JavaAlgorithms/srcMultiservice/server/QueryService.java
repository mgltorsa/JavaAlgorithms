package server;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;

import connections.IConnection;
import connections.IConnectionListener;
import connections.UDPConnection;
import services.IService;

public class QueryService implements IService, IConnectionListener {

    public static String MAIN_CONNECTION ="0.0.0.0";
    
    private int port;
    
    private HashMap<String, IConnection> connections;

    public QueryService() {
	this(-1);
    }

    public QueryService(int port) {
	this.port = port;
	connections = new HashMap<>();
    }

    @Override
    public List<IConnection> getConnections() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public int getPort() {
	// TODO Auto-generated method stub
	return 0;
    }

    @Override
    public void setPort(int port) {
	// TODO Auto-generated method stub

    }

    @Override
    public void initConnection(IConnection connection) {
	connections.put(connection.getAddressConsumer().getHostAddress(),connection);
	connection.start();	

    }

    

    @Override
    public void startService() {
	if (!isAvailable()) {
	    throw new IllegalArgumentException("El servicio no esta asignado a un puerto");
	}

	DatagramSocket socket;
	try {
	    socket = new DatagramSocket(port);
	    IConnection con = new UDPConnection(socket, this);
	    this.connections.put(MAIN_CONNECTION, con);	    
	    con.start();
	} catch (SocketException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    @Override
    public boolean isAvailable() {

	return port != -1;
    }
    
    

}
