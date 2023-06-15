package Lab1.zad3;

import java.util.Arrays;
import java.util.Collections;

class IntegerArray {

    private final int a[];

    public IntegerArray(int[] a) {
        this.a = Arrays.copyOf(a,a.length);
    }
    public int length()
    {
        return a.length;
    }
    public int getElementAt(int i)
    {

        return a[i];
    }
    public int sum()
    {
        int sum=0;
        for(int b:a)
            sum+=b;
        return sum;
    }
    public double average()
    {
        return (double)sum()/length();
    }
    public IntegerArray getSorted()
    {
        int sorted[]= Arrays.copyOf(a,length());
        Arrays.sort(sorted);
        return new IntegerArray(sorted);
    }
    public IntegerArray concat(IntegerArray ia)
    {
        int result[]=new int[a.length+ ia.a.length];
        System.arraycopy(a,0,result,0,a.length);
        System.arraycopy(ia.a,0,result,a.length,ia.a.length);
        return new IntegerArray(result);
    }


    @Override
    public String toString() {
        return Arrays.toString(a);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerArray that = (IntegerArray) o;
        return Arrays.equals(a, that.a);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(a);
    }
}
