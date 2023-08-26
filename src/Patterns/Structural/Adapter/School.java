package Patterns.Structural.Adapter;

import Patterns.AdapterExtra.PilotPen;

public class School {

    public static void main(String[] args) {
        AssignmentWork aw = new AssignmentWork();
        PenAdaptor p=new PenAdaptor();
        aw.setP(p);
        aw.writeAssignment("Im bit tired to write an assignment");
    }
}
