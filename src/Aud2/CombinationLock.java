package Aud2;

import java.util.concurrent.locks.Lock;

public class CombinationLock {

    int sequence;
    boolean locked;

    public CombinationLock(int sequence) {
        this.sequence = sequence;
        locked = true;
    }

    public void open(int sequence) {
        if (!locked) {
            System.out.println("The lock is already open!");
            return;
        }
        if (sequence == this.sequence) {
            locked = false;
            System.out.println("The lock is now open!");
            return;
        }
        System.out.println("Wrong sequence!");

    }

    public void changeCombo(int sequence, int newsequence) {
        if (sequence != this.sequence) {
            System.out.println("You have entered the wrong sequence");
        }
        this.sequence=newsequence;
        System.out.println("The lock has been changed from "+sequence+" to "+newsequence);
    }

    public static void main(String[] args) {
        CombinationLock combinationLock=new CombinationLock(123);
        combinationLock.open(321);
        combinationLock.open(123);
        combinationLock.open(123);
        combinationLock.changeCombo(1234,321);
        combinationLock.changeCombo(123,321);
    }
}
