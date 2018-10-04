/**
 * 
 */
package markovChainGame;

import java.util.List;
import java.util.Random;

/**
 * @author Miguel
 *
 */
public class MarkovChain {

	public static final Random RANDOM = new Random();
	private int[][] markovChain;
	private int states;	

	/**
	 * @param states
	 */
	public MarkovChain(int states) {
		this.states = states;
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

	public Item nextMove(Item prev, int nbThrows) {
		if (nbThrows < 1) {
			// FIRST MOVE, RANDOM ITEM USED.
			return Item.values()[RANDOM.nextInt(Item.values().length)];
		}
		Item predictedNext = getPredictedItem(prev);
	    List<Item> losesTo = predictedNext.losesTo;
	    return losesTo.get(RANDOM.nextInt(losesTo.size()));
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
	
	

}
