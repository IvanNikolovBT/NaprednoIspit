package Aud9.CountOccurence;

import java.util.Collection;

public class CountOccurencesTest {
    public static int count1(Collection<Collection<String>> c, String str) {
        int count = 0;
        for (Collection<String> collection : c) {
            for(String element:collection)
                if(element.equalsIgnoreCase(str))
                {
                    count++;
                }
        }
        return count;
    }
    public static int count2(Collection<Collection<String>> c,String str)
    {
        return Math.toIntExact(c.stream().flatMap(Collection::stream).filter(a -> a.equalsIgnoreCase(str)).count());
    }


    public static void main(String[] args) {

    }
}
