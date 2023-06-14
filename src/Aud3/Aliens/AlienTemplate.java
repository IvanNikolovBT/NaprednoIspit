package Aud3.Aliens;

public class AlienTemplate {
    int health;      // 0=dead, 100=full strength
    int damage;
    String name;

    public AlienTemplate(int health, int damage, String name) {
        this.health = health;
        this.damage = damage;
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getDamage() {
        return damage;
    }
    public String getName()
    {
        return  name;
    }




}
