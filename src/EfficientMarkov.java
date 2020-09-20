import java.util.*;

public class EfficientMarkov extends BaseMarkov {
	private Map<String, ArrayList<String>> myMap;
	
	public EfficientMarkov(){
		this(3);
	}

	public EfficientMarkov(int order) {
		super(order);
		myMap = new HashMap<>();
	}

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

	@Override
	public ArrayList<String> getFollows(String key) {
		if (! myMap.containsKey(key)) {
			throw new NoSuchElementException(key + " not contained in the map");
		}
		return myMap.get(key);
	}
}	
