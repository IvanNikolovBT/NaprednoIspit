package Aud4Pak.WordCount;

public class Line {
    int lines;
    int words;
    int chars;

    public Line(int lines, int words, int chars) {
        this.lines = lines;
        this.words = words;
        this.chars = chars;
    }
    public Line(String l)
    {
        lines=1;
        words = l.split("\\s+").length;
        chars = l.length();
    }
    public Line sum(Line o)
    {
        return new Line(lines+o.lines,words+o.words,chars+o.chars);
    }

    @Override
    public String toString() {
        return String.format("linii %d  zbora %d karakteri %d%n", lines, words, chars);
    }
}
