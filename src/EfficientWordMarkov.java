import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EfficientWordMarkov extends BaseWordMarkov {
    private Map<WordGram, ArrayList<String>> myMap;

    public EfficientWordMarkov() {
        this(2);
    }

    /**
     * Constructs EfficientWordMarkov object with the specified order
     * @param order size of this markov generator
     */
    public EfficientWordMarkov(int order) {
        super(order);
        myMap = new HashMap<>();
    }

    /**
     * Creates a map of all possible word grams of the specified order (key) and all the letters that
     * come after that word gram (value)
     * @param text the training text from which the map is created
     */
    @Override
    public void setTraining(String text) {
        myWords = text.split("\\s+");
        myMap.clear();

        int pos = 0;  // location where search for key in text starts

        while (pos < myWords.length - myOrder){
            WordGram key = new WordGram(myWords, pos, myOrder);
            if (! myMap.containsKey(key)){
                myMap.put(key, new ArrayList<String>());
            }
            if (pos + myOrder >= myWords.length - 1) {
                myMap.get(key).add(PSEUDO_EOS);
            }
            else {
                myMap.get(key).add(myWords[pos + myOrder]);
            }

            pos++;
        }
    }


    /**
     * Returns an ArrayList of the letters that follow the specified gram
     * @param kGram the gram we are currently on
     * @return an ArrayList of all the letters that come after the gram in the training text
     */
    @Override
    public ArrayList<String> getFollows(WordGram kGram) {
        if (! myMap.containsKey(kGram)) {
            throw new NoSuchElementException(kGram + " not contained in the map");
        }
        return myMap.get(kGram);
    }
}
