/**
 * 
 */
package MarkovChain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author Miguel
 *
 */
public class Automaton {

	String chain = "";
	/**
	 * 
	 */
	public Automaton() {

	}
	private void trainAutomaton(String path) {
		File file = new File(path);
		FileReader reader;
		try {
			reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			while (br.ready()) {
				String[] info = br.readLine().split(";");
				trainAutomaton(info);

			}

			// markovChain.printMarkov();
			printChain(chain);
			br.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * 
	 */
	private void printChain(String chain) {
		int maxLength = 50;
		int count = 0;
		for (int i = 0; i < chain.length(); i++) {
			System.out.print(chain.charAt(i));
			count++;
			if (count >= maxLength) {
				System.out.println();
				count = 0;
			}
		}

	}
	/**
	 * @param info
	 */
	private void trainAutomaton(String[] info) {
		for (String state : info) {
			Item item = Item.valueOf(state.toUpperCase());
			chain += item;
		}

	}

	public static void main(String[] args) {
		String path = "C:/Users/Asus/Desktop/dolar.csv";
		Automaton a = new Automaton();
		a.trainAutomaton(path);
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("Ingrese un dato");
		while (sc.hasNextLine()) {

			String line = sc.nextLine();
			if(line.toUpperCase().equals("STOP")) {
				break;
			}
			a.chain+=line;			
			System.out.println("Ingrese un dato");
		}
		
		
		a.printChain(a.chain);
		
		sc.close();
	}
	/**
	 * @return
	 */
	private String getNext() {
		char k = chain.charAt(chain.length() - 1);
		char j = chain.charAt(chain.length() - 2);
		char i = chain.charAt(chain.length() - 3);
		String next = next(i, j, k) + "";
		chain += next;
		return next;
	}
	/**
	 * @param i
	 * @param j
	 * @param k
	 * @return
	 */
	private String next(char i, char j, char k) {
		HashMap<Character, Integer> map = new HashMap<>();
		map.put(i, 1);
		Integer aux = map.put(j, 1);
		if (aux != null) {
			map.put(j, 1 + aux);
		}
		aux = map.put(k, 1);
		if (aux != null) {
			map.put(k, 1 + aux);
		}

		Iterator<Character> keys = map.keySet().iterator();
		char max = i;
		while (keys.hasNext()) {
			char key = keys.next();
			if (map.get(key) > map.get(max)) {
				max = key;
			}
		}

		return max + "";
	}
}
