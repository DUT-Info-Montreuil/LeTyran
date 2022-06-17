package application.modele.personnages.animaux;

import application.modele.Environnement;
import application.modele.personnages.PNJ;

public abstract class Animal extends PNJ {
    public Animal(Environnement env, String id, int x, int y, int pv) {
        super(env, id, x, y, pv);
    }
}
