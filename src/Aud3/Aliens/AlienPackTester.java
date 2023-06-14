package Aud3.Aliens;

public class AlienPackTester {
    public static void main(String[] args) {
        AlienPack alienPack=new AlienPack();
        alienPack.addAlien(new Snake("Korle"));
        //alienPack.addAlien(new Snake("Ivan"));
        alienPack.addAlien(new Ogre("Antonio"));
        //alienPack.addAlien(new Ogre("Marko"));
        //alienPack.addAlien(new MARSHMALLOW_MAN_ALIEN("Pavel"));
        alienPack.addAlien(new MARSHMALLOW_MAN_ALIEN("Kalina"));
        System.out.println(alienPack.getAliens());
        System.out.println(alienPack.calculateDamage());

    }
}
