package Patterns.Structural.Adapter;

import Patterns.Structural.AdapterExtra.PilotPen;

public class PenAdaptor implements  Pen {

    PilotPen pp=new PilotPen();
    @Override
    public void write(String s) {
        pp.mark(s+"ova e od pen addaptor");
    }
}
