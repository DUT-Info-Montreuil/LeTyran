package application.modele.personnages.animaux;

import application.modele.Environnement;
import application.modele.personnages.animaux.Animal;

public class Lapin extends Animal {

    private static int id = 0;

    public Lapin(Environnement env, int x, int y, int distance) {
        super(env, "Lapin" + id++, x, y, distance);
    }

    @Override
    public void deplacement() {
        deplacementAllerRetour();
        seDeplacer();
    }
    
    @Override
    public void update() {
        tomber();
        deplacement();
    }

    @Override
    protected int getHauteurMax() {
        return 0;
    }

    @Override
    protected int getVitesse() {
        return 3;
    }
}
