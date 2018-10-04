/**
 * 
 */
package MarkovChain;

import java.text.DecimalFormat;
import java.util.Random;

/**
 * @author Miguel
 *
 */
public class MarkovChain {

	public static final Random RANDOM = new Random();
	private int[][] markovChain;
	private int states;
	private Item currentState;


	/**
	 * @param length
	 * @param length2
	 */
	public MarkovChain(int length) {
		this.states = length;
		initialize();
	}

	/**
	 * 
	 */
	private void initialize() {
		markovChain = new int[states][states];
		for (int i = 0; i < states; i++) {
			for (int j = 0; j < states; j++) {
				markovChain[i][j] = 0;
			}
		}
	}

	public void update(Item prev, Item next) {
		markovChain[prev.ordinal()][next.ordinal()]++;
	}

	public Item next(Item prev) {
		if (prev==null) {
			// FIRST MOVE, RANDOM ITEM USED.
			return Item.values()[RANDOM.nextInt(Item.values().length)];
		}
		Item predictedNext = getPredictedItem(prev);
	    return predictedNext;
	}

	/**
	 * @return
	 */
	private Item getPredictedItem(Item prev) {
		int nextIndex = 0;
	
		for (int i = 0; i < Item.values().length; i++) {
			int prevIndex = prev.ordinal();

			if (markovChain[prevIndex][i] > markovChain[prevIndex][nextIndex]) {
				nextIndex = i;
			}
		}
		Item predictedNext = Item.values()[nextIndex];
		return predictedNext;
	}

	public double getStateProbability(int state) {
		double probability = getStateProbability(currentState.ordinal(),state);		
		return probability;		
	}
	
	public void printMarkov() {
		DecimalFormat dm = new DecimalFormat("#0.00#");
		System.out.println("Printing Markov ocurrences");
		System.out.print("      ");
		for (int i = 0; i < markovChain.length; i++) {
			System.out.print(Item.values()[i]+"         ");
		}
		System.out.println();
		for (int i = 0; i < markovChain.length; i++) {
			System.out.print(Item.values()[i]+" ");
			for (int j = 0; j < markovChain.length; j++) {
				double pro = getStateProbability(i, j);
				System.out.print("("+dm.format(pro)+" ");
				System.out.print(markovChain[i][j]+")");
			}
			System.out.println();
		}
		System.out.println("Current state: "+currentState);
	}
	
	/**
	 * @param state
	 */
	private int getTotal(int state) {
		int total=0;
		for (int i = 0; i < markovChain.length; i++) {
			total += markovChain[state][i];
		}
		return total;
	}

	public double getStateProbability(int fromState, int state) {
		double probability = 0.0;
		int total=getTotal(fromState);
		probability = (double)markovChain[fromState][state]/(double)total;
		return probability;
		
	}

	/**
	 * @param item
	 */
	public void update(Item item) {
		if(currentState!=null) {
			update(currentState, item);
		}
		currentState = item;

		
	}

	/**
	 * 
	 */
	public Item getPredictedElement(Item selected) {
		Item predicted = next(currentState);
		return predicted;
		
	}

	/**
	 * @return
	 */
	public Item getCurrentState() {
		return currentState;
	}
}
