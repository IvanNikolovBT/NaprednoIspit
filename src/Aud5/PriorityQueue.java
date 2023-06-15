package Aud5;

import java.util.ArrayList;

public class PriorityQueue <T> {
    private ArrayList<T> arrayList;
    private ArrayList<Integer> priorities;

    public PriorityQueue() {
        this.arrayList = new ArrayList<>();
        this.priorities = new ArrayList<>();
    }
    public void add(T item,int priority)
    {
        int i;
        for(i=0;i<arrayList.size();i++)
        {
            if(priority>priorities.get(i))
                break;
        }
        arrayList.add(i,item);
        priorities.add(i,priority);
    }
    public T remove()
    {
        if(arrayList.size()==0)
            return null;
        T item=arrayList.get(0);
        arrayList.remove(0);
        priorities.remove(0);
        return item;
    }
}
