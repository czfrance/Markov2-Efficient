import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String, ArrayList<String>> myMap;
	
	public EfficientMarkov(){
		this(3);
	}

	/**
	 * Constructs EfficientMarkov object with the specified order
	 * @param order size of this markov generator
	 */
	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

	/**
	 * Creates a map of all possible grams of the specified order (key) and all the letters that
	 * come after that gram (value)
	 * @param text the training text from which the map is created
	 */
	@Override
	public void setTraining(String text) {
		myText = text;
		myMap.clear();
		int pos = 0;  // location where search for key in text starts
		String key = "";

		while (pos < myText.length() - myOrder){
			key = myText.substring(pos, pos + myOrder);
			if (! myMap.containsKey(key)){
				myMap.put(key, new ArrayList<String>());
			}
			if (pos + myOrder >= myText.length() - 1) {
				myMap.get(key).add(PSEUDO_EOS);
			}
			else {
				myMap.get(key).add(myText.substring(pos + myOrder, pos + myOrder + 1));
			}

			pos++;
		}
	}

	/**
	 * Returns an ArrayList of the letters that follow the specified gram
	 * @param key the gram we are currently on
	 * @return an ArrayList of all the letters that come after the gram in the training text
	 */
	@Override
	public ArrayList<String> getFollows(String key) {
		if (! myMap.containsKey(key)) {
			throw new NoSuchElementException(key + " not contained in the map");
		}
		return myMap.get(key);
	}
}	
