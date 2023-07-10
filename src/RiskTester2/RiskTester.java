package RiskTester2;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Risk
{


    public void processAttacksData(InputStream is)
    {
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
        bufferedReader.lines().forEach(this::process);
    }
    public void process(String s)
    {
        String[] splitter =s.split(";");
        String[] val1 =splitter[0].split("\\s+");
        String[] val2 =splitter[1].split("\\s+");
        List<Integer> values1=new ArrayList<>();
        List<Integer> values2=new ArrayList<>();

        values1.add(Integer.parseInt(val1[0]));
        values1.add(Integer.parseInt(val1[1]));
        values1.add(Integer.parseInt(val1[2]));

        values2.add(Integer.parseInt(val2[0]));
        values2.add(Integer.parseInt(val2[1]));
        values2.add(Integer.parseInt(val2[2]));
        int count=0;
        for(int i=0;i<3;i++)
        {
            if(Collections.max(values1)>Collections.max(values2))
                count++;
            values1.remove(Collections.max(values1));
            values2.remove(Collections.max(values2));
        }
        System.out.println(count+" "+(3-count));
    }
}


public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        risk.processAttacksData(System.in);

    }
}