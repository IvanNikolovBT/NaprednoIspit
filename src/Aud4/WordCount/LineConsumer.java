package Aud4.WordCount;

import java.util.function.Consumer;

public class LineConsumer implements Consumer<String> {
    private int linecounter=0;
    private int wordcounter=0;
    private int charcounter=0;

    @Override
    public void accept(String s) {
        ++linecounter;
        wordcounter=s.split("\\s+").length;
        charcounter=s.length();
    }

    @Override
    public String toString() {
        return String.format("Lines %d words %d chars %d",linecounter,wordcounter,charcounter);
    }
}
