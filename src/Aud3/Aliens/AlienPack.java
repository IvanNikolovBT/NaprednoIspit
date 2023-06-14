package Aud3.Aliens;

import java.util.ArrayList;

public class AlienPack {
    private ArrayList<AlienTemplate> aliens;

    public AlienPack() {
        aliens=new ArrayList<AlienTemplate>();
    }

    public void addAlien(AlienTemplate newAlien) {
        aliens.add(newAlien);
    }

    public ArrayList<AlienTemplate> getAliens() {
        return aliens;
    }

    public int calculateDamage() {
        int damage = 0;
        for( AlienTemplate alienTemplate: aliens)
            damage+=alienTemplate.getDamage();

        return damage;
    }
}