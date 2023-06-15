package Lab2.zad1;

import java.util.Arrays;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class DoubleMatrix {
    private final double[][] matrix;

    public DoubleMatrix(double[] a, int m,int n) throws InsufficientElementsException {
       if(a.length < m*n)
           throw new InsufficientElementsException();
        matrix=new double[m][n];
        int k=a.length-(m*n);
        for( int i=0;i<m;i++)
        {
            for(int j=0;j<n;j++)
                matrix[i][j]=a[k++];
        }
    }
    public String getDimensions()
    {
        return String.format("[%d x %d]",matrix.length,matrix[0].length);
    }
    public int rows()
    {
        return matrix.length;
    }
    public int columns()
    {
        return matrix[0].length;
    }
    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if(row <1 || row >rows())
            throw new InvalidRowNumberException();
        return Arrays.stream(matrix[row-1]).max().orElse(0);
    }
    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        if(column<1 || column>columns())
            throw  new InvalidColumnNumberException();
        return IntStream.range(0,matrix.length).mapToDouble(i->matrix[i][column-1]).max().orElse(0);
    }

    public double sum()
    {
        return IntStream.range(0, matrix.length).mapToDouble(i->IntStream.range(0,matrix[i].length).mapToDouble(j->matrix[i][j]).sum()).sum();
    }
    public double[] toSortedArray()
    {
        double[] sorted=Arrays.stream(matrix).flatMapToDouble(Arrays::stream).sorted().toArray();
    }

}
