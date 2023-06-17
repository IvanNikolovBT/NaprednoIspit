package Aud8.CakePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomPicker {
    private final int n;
    public RandomPicker(int n)
    {
        this.n=n;
    }
    public List<Integer> pick(int x)
    {
        Random random=new Random();
        List<Integer> picked=new ArrayList<>();

       return random.ints(2*n,1,n+1).
                boxed().distinct().limit(x).collect(Collectors.toList());

    }

}
