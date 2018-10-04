/**
 * 
 */
package markovChainGame;

import java.text.DecimalFormat;
import java.util.Scanner;

/**
 * @author Miguel
 *
 */
public class Game {

	private static DecimalFormat DECIMAL_FORMATTER = new DecimalFormat(".##");
	private int[] stats = new int[]{0, 0, 0};
	private MarkovChain markovChain;
	private int nbThrows = 0;
	private Item last = null;

	public Game() {
	}

	private void init() {
		int length = Item.values().length;
		markovChain = new MarkovChain(length);
	}

	/**
	 * 
	 */
	public void play() {
		init();
		Scanner sc = new Scanner(System.in);
		System.out.println("Eliga una opcion");
		while (sc.hasNextLine()) {
			String input = sc.nextLine();
			if ("STOP".equals(input)) {
				break;
			}
			
			Item selected = null;
			try {
				selected = Item.valueOf(input.toUpperCase());

			}catch(Exception e) {
				System.out.println("Invalid choice");
		        continue;
			}
			
			Item aiSelected = markovChain.nextMove(last, nbThrows);
			nbThrows++;
			if(last!=null) {
				markovChain.update(last, selected);
			}
			
			last = selected;
			System.out.println("Selección de la IA: "+aiSelected);
			if (aiSelected.equals(selected)) {
		        System.out.println(" ==> Empate! \n");
		        stats[1]++; 
		      } else if(aiSelected.losesTo(selected)) {
		        System.out.println(" ==> Ganaste! \n");
		        stats[0]++;
		      } else {
		        System.out.println(" ==> Perdiste! \n");
		        stats[2]++;
		      }

		      System.out.print("Make your choice : ");
		    

		}
		
	    sc.close();
	    displayStats();
	}
	
	
	/**
	 * 
	 */
	private void displayStats() {
		 System.out.println("\n");
		    System.out.println("Stats de victoria");
		    int total = stats[0] + stats[1] + stats[2];
		    System.out.println("You : " + stats[0] + " - " + 
		          DECIMAL_FORMATTER.format(stats[0] / (float) total * 100) + "%");
		    System.out.println("Empate : " + stats[1] + " - " + 
		          DECIMAL_FORMATTER.format(stats[1] / (float) total * 100) + "%");
		    System.out.println("Computer : " + stats[2] + " - " + 
		          DECIMAL_FORMATTER.format(stats[2] / (float) total * 100) + "%");
		
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}

}

