package Aud4Pak.WordCount;

import java.util.function.Consumer;

public class LineConsumer implements Consumer<String> {

    int lines;
    int words;
    int chars;


    @Override
    public void accept(String line) {
        ++lines;
        words=line.split("\\s+").length;
        chars=line.length();
    }

    @Override
    public String toString() {
        return "LineConsumer{" +
                "lines=" + lines +
                ", words=" + words +
                ", chars=" + chars +
                '}';
    }
}
