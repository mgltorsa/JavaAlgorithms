package server;

import java.util.HashMap;

import services.IService;

public class Server {

    // HASH BY PORT
    private HashMap<Integer, IService> services;

    private boolean available;

    public Server() {
	this(null);

    }

    public Server(IService... services) {
	this.services = new HashMap<>();

	if (services == null) {
	    initDefaultServices();
	} else {
	    addServices(services);
	}
	

    }
    
    public void startServer() {
	this.available=true;
	startAllServices();
    }
    

    private void startAllServices() {
	IService[] toRunServices = new IService[this.services.values().size()];
	this.services.values().toArray(toRunServices);
	startServices(toRunServices);	
    }

    private void addServices(IService... services) {
	for (int i = 0; i < services.length; i++) {
	    IService service = services[i];
	    int port = service.getPort();
	    if (this.services.containsKey(port)) {
		throw new IllegalArgumentException("Servicio ya existente");
	    }
	    this.services.put(port, service);
	}
	startServices(services);

    }

    private void initDefaultServices() {
	QueryService qs = new QueryService();
	TransferService ts = new TransferService();
	addServices(qs,ts);
    }

    // ADD SERVICE AND RUN IT
    public void addService(IService service) {
	int port = service.getPort();
	if (services.containsKey(port)) {
	    throw new IllegalArgumentException("Servicio ya existente");
	}
	services.put(port, service);
	startServices(service);

    }

    private void startServices(IService... services) {
	for (int i = 0; i < services.length; i++) {
	    IService service = services[i];
	    if (!service.isAvailable() && this.isAvailable()) {
		service.startService();
	    }
	}

    }

    private boolean isAvailable() {
	return available;
    }

}
