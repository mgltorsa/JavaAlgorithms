package parcialRedes;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class Server {

	public static int group = 100;

	// MINUTO
	public static final long threeminutes = 1000 * 60 * 3;

	ServerSocket serverSocket;
	int port;
	ArrayList<Connection> connections;
	Hashtable<Integer, Horse> horses;
	Thread timer;

	public Server() throws IOException {
		this.port = 1234;
		connections = new ArrayList<>();
		serverSocket = new ServerSocket(port);
		horses = new Hashtable<>();
		for (int i = 1; i < 7; i++) {
			horses.put(i, new Horse(i));
		}

	}

	public void setPort(int port) {
		this.port = port;
	}

	public void start() {
		// Inicializa el cronometro
		initTimer();

		while (true) {
			try {
				Socket socket = serverSocket.accept();
				Connection conn = new Connection(this, socket, 0);
				connections.add(conn);
				conn.start();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	private void initTimer() {
		timer = new Thread(new Runnable() {

			@Override
			public void run() {
				// Milliseconds * seconds in a minute * 3 minutes
				try {
					Thread.sleep(threeminutes);
					closeAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}

		});
		timer.start();

	}

	public synchronized String compute(String input, Connection connection) {

		if (input.equals("newinstance")) {
			return getWelcomeResponse();
		}
		String[] info = input.split(":");

		if (info.length > 1) {

			try {
				int horseCode = parseInt(info[0]);
				double bet = parseDouble(info[1]);

				if (!existHorse((int) horseCode)) {
					return "Ingrese un caballo inscrito en la carrera (codigos del 1 al 6)";
				}
				if (bet <= 0) {
					return "Por favor ingrese una cantidad de apuesta mayor a 0";
				} else {
					horses.get(horseCode).bet += bet;
//					System.out.println("Se aposto al caballo "+horseCode+" una cantidad de "+bet+" el total para el es de"+horses.get(horseCode).bet);
					return "Apuesta exitosa al caballo " + horseCode + " por una cantidad de " + bet;
				}
			} catch (Exception e) {
				return "Ingrese un formato de solicitud valido";
			}
		} else {
			return "No ingreso los 2 datos necesarios";
		}
	}

	private int parseInt(String data) {
		int bet = -1;
		try {
			bet = Integer.parseInt(data);
		} catch (NumberFormatException e) {
			return -1;
		}
		return bet;
	}

	private boolean existHorse(int horseCode) {
		if (horseCode < 1 || horseCode > 6) {
			return false;
		}
		return true;
	}

	private double parseDouble(String data) {
		double bet = -1;
		try {
			bet = Double.parseDouble(data);
		} catch (NumberFormatException e) {
			return -1;
		}
		return bet;
	}

	public synchronized void closeAll() {
		System.out.println("Se acabo el tiempo de las apuestas");
		for (Connection conn : connections) {
			conn.close();
		}

		connections.clear();
		Enumeration<Integer> a = horses.keys();
		while (a.hasMoreElements()) {
			int code = a.nextElement();
			Horse horse = horses.get(code);
			System.out.println("Para el caballo " + horse.code + " la apuesta fue de: " + horse.bet);
		}
	}

	private String getWelcomeResponse() {
		String message = "Bienvenido a la sala de apuestas.\n"
				+ "Para crear una apuesta digite el codigo del caballo : valor a apostar\n"
				+ "Nota: los codigos de los caballos van de 1 a 6, no se permiten apuestas de $0";
		return message;
	}

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
	}

}
