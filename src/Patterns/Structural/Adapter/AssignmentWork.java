package Patterns.Structural.Adapter;

public class AssignmentWork {

    private Pen p;
    public  void writeAssignment(String s)
    {
    p.write(s);
    }

    public Pen getP() {
        return p;
    }

    public void setP(Pen p) {
        this.p = p;
    }
}
