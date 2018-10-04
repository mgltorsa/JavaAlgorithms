/**
 * 
 */
package MarkovChain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * @author Miguel
 *
 */
public class Main {

	private MarkovChain markovChain;
	String chain="";
	/**
	 * 
	 */
	public Main() {
		markovChain = new MarkovChain(Item.values().length);
	}

	public static void main(String[] args) {
		String path = "C:/Users/Asus/Desktop/dolar.csv";
		Main main = new Main();
		main.trainMarkov(path);
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

			} catch (Exception e) {
				System.out.println("Invalid choice");
				continue;
			}

			Item aiSelection = main.getPredictedItem(selected);
			
			System.out.print("Markov prediction: " + aiSelection.toString()+" ");
			System.out.println(main.markovChain.getStateProbability(aiSelection.ordinal()));
			System.out.println("current state "+main.markovChain.getCurrentState());
			main.markovChain.update(selected);
			main.markovChain.printMarkov();

		}
		sc.close();
	}

	/**
	 * @return
	 */
	private Item getPredictedItem(Item selected) {
		Item selection = markovChain.getPredictedElement(selected);
		return selection;
	}

	/**
	 * @param path
	 */
	private void trainMarkov(String path) {
		File file = new File(path);
		FileReader reader;
		try {
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			while (br.ready()) {
				String[] info = br.readLine().split(";");
				trainMarkov(info);

			}
			
//			markovChain.printMarkov();
			System.out.println(chain);
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param info
	 */
	private void trainMarkov(String[] info) {
		for (String state : info) {
			Item item = Item.valueOf(state.toUpperCase());
			chain+=item;
			markovChain.update(item);
		}

	}

}
