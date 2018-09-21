package conecctions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionThread implements Runnable {

	private volatile Server server;
	private volatile Socket connection;
	private volatile int cash;

	public ConnectionThread(Server server, Socket connection) {
		this.server = server;
		this.connection = connection;
	}

	@Override
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			PrintWriter out = new PrintWriter(connection.getOutputStream(),true);
			writeFormat(out);
			AtomicBoolean canBet= new AtomicBoolean(true);
			while (!connection.isClosed()) {

				betGame(in, out,canBet);
				
			}

		} catch (Exception e) {

		}

	}

	private synchronized void betGame(BufferedReader in, PrintWriter out, AtomicBoolean canBet) {
		try {
			Thread.sleep(500);
			System.out.println("Entra bet");
			char[] buff =new char[10]; 
			
			in.read(buff,0,10);
			System.out.println("read buff");
			System.out.println(new String(buff).toString());
			String[] info = new String(buff).toString().split(":");
			System.out.println("lee info");
			
			computeQuery(info, out, canBet);
		} catch (Exception e) {
			out.print("Please type in a valid format or type valid value to your cash or bet");
		}

	}

	private void computeQuery(String[] info,PrintWriter out, AtomicBoolean canBet) throws Exception {
		server.canBet(cash, canBet);
		
		if(!canBet.get()) {
			return;
		}		
		if(info[0].equalsIgnoreCase("bet")) {
			String[] data = info[1].split(",");
			
			int q = Integer.parseInt(data[0]);
			int value  = Integer.parseInt(data[1]);
			
			if(value<cash) {
				throw new Exception();
			}
			if(q!=0 && q!=1) {
				throw new Exception();
			}
			int bet= server.bet(q,value);
			cash+=bet;
			if(bet<0) {
				out.println("You lost $"+bet);
			}
			else {
				out.println("You earn $"+bet);
			}
		}else if(info[0].equalsIgnoreCase("con")){
			out.println("Your cash is: "+cash);
		}else {
			throw new Exception();
		}
		
	}

	private synchronized void writeFormat(PrintWriter out) {		
		out.println("Welcome to the binary bet\n"+"In this game you have to make a bet for the most bet number.\n"
				+ "You can earn a lot of money if you are the first on bet for a number or bet for the most bet number in the momment. \n"
				+ "Note the following:\n"
				+ "-When you make a bet, if you win the bet you'll double your money.\n"
				+ "-You start with $15, and the first bet for both 1 or 0 is 2\n"
				+ "-When someone wins a bet the number of bets for 1 or 0 is reduced.\n"
				+"Please, bet or consult your cash  as follows:\n"
				+"For make a bet --> bet:1,$Amount$ (if you'll bet for 1) or bet:0,$Amount$ Example: bet:1,10\n"
				+"For make a consult --> con\n");	
	}

}
