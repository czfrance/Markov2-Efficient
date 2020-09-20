import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

public class EfficientWordMarkov extends BaseWordMarkov {
    private Map<WordGram, ArrayList<String>> myMap;

    public EfficientWordMarkov() {
        this(2);
    }

    public EfficientWordMarkov(int order) {
        super(order);
        myMap = new HashMap<>();
    }

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

    @Override
    public ArrayList<String> getFollows(WordGram kGram) {
        if (! myMap.containsKey(kGram)) {
            throw new NoSuchElementException(kGram + " not contained in the map");
        }
        return myMap.get(kGram);
    }
}
