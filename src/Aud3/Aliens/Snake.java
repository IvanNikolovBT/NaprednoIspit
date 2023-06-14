package Aud3.Aliens;

public class Snake extends  AlienTemplate{
    public Snake(String name) {
        super(100,10 , name);
    }

    @Override
    public String toString() {
        return "Snake{" +
                "health=" + health +
                ", damage=" + damage +
                ", name='" + name + '\'' +
                '}';
    }
}
