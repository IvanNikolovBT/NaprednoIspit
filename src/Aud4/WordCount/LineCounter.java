package Aud4.WordCount;

public class LineCounter {

    private int linecounter;
    private int wordcounter;
    private int charcounter;

    public LineCounter(int linecounter, int wordcounter, int charcounter) {
        this.linecounter = linecounter;
        this.wordcounter = wordcounter;
        this.charcounter = charcounter;
    }

    public LineCounter(String s)
    {
        linecounter++;
        wordcounter=s.split("\\s+").length;
        charcounter=s.length();
    }
    public LineCounter() {
        this.linecounter = 0;
        this.wordcounter = 0;
        this.charcounter = 0;
    }
    public LineCounter sum(LineCounter other)
    {
        return new LineCounter(this.linecounter+other.linecounter,this.wordcounter+other.wordcounter,this.charcounter+other.charcounter);
    }
    @Override
    public String toString() {
        return String.format("Lines %d words %d chars %d",linecounter,wordcounter,charcounter);
    }
}
