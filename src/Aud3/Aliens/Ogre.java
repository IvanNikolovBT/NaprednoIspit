package Aud3.Aliens;

public class Ogre extends  AlienTemplate {
    public Ogre(String name) {
        super(100,6 , name);
    }

    @Override
    public String toString() {
        return "Ogre{" +
                "health=" + health +
                ", damage=" + damage +
                ", name='" + name + '\'' +
                '}';
    }
}
