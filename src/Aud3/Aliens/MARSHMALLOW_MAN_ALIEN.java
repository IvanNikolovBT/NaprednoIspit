package Aud3.Aliens;

public class MARSHMALLOW_MAN_ALIEN extends AlienTemplate {

    public MARSHMALLOW_MAN_ALIEN(String name) {
        super(100,1 , name);
    }

    @Override
    public String toString() {
        return "MARSHMALLOW_MAN_ALIEN{" +
                "health=" + health +
                ", damage=" + damage +
                ", name='" + name + '\'' +
                '}';
    }
}
