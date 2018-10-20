package server;

import java.util.List;

import connections.IConnection;
import services.IService;

public class TransferService extends QueryService implements IService{

    
    
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
	
	
    }


    @Override
    public void startService() {
	super.startService();
    }

    @Override
    public boolean isAvailable() {
	// TODO Auto-generated method stub
	return false;
    }

}
