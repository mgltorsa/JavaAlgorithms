package parcialRedes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class Client {

	public static Random random= new Random();
	Socket socket;
	int port;
	String host;

	public Client(String host, int port) {
		setHost(host);
		setPort(port);
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void start() {
		try {
			socket = new Socket(host, port);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			BufferedReader inClient = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

			InitReadThread(in, socket);
			InitWriteThread(out, inClient, socket);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void InitWriteThread(PrintWriter out, BufferedReader inClient, Socket socket) throws IOException {
		while (!socket.isClosed()) {

			String toServer = "";
			String inputClient = inClient.readLine();

			toServer += inputClient;

			out.println(toServer);

		}
	}

	private void InitReadThread(BufferedReader in, Socket socket) {
		Thread readT = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!socket.isClosed()) {
					try {
						while (!in.ready()) {
							Thread.sleep(500);
						}
//						String input = "";
						while (in.ready()) {
							System.out.println(in.readLine());
						}

//						System.out.println(input);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
		});
		readT.start();

	}

	public static void main(String[] args) {

//		Client client = new Client("172.30.175.248",1234);
//		Client client = new Client("localhost",1234);
//		Client client = new Client("172.30.81.95", 1234);
//		client.start();
		
		//CREO UN HILO QUE CREA VARIOS CLIENTES
//		runClients();
		Client client = new Client("localhost",1234);
		client.start();


	}
	
	public static void runClients() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
							Client client = new Client("localhost", 1234);
							//METODO ESPECIAL CONFIGURADO PARA QUE CADA CLIENTE HAGA QUERIES RANDOM A EL SERVIDOR
							client.test();
							
						}
					}).start();					
				}

			}
		}).start();
	}

	public void test() {
		try {
			socket = new Socket(host, port);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			//HILO DE LECTURA, EL CLIENTE SIEMPRE ESTA ESCUCHANDO
			Thread readT = new Thread(new Runnable() {
				@Override
				public void run() {
					while (true) {
						try {
							while (!in.ready()) {
								Thread.sleep(500);
							}
//							String input = "";
							while (in.ready()) {
//								System.out.println(in.readLine());
							}

//							System.out.println(input);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

				}
			});
			readT.start();
			int i=0;
			//ESCRITURA: PETICIONES AL SERVIDOR
			while (i<15) {

				String toServer = "";
				//GENERO CODIGO ALEATORIO DE CABALLO
				int horseCode = random.nextInt(2)+1;
				//GENERO VALOR ALEATORIO PARA APOSTAR
				int bet = random.nextInt(1000)+10;

				toServer = horseCode+":"+bet;

				out.println(toServer);
				i++;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
