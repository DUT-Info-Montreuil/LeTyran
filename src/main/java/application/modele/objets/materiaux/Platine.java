package application.modele.objets.materiaux;

import application.modele.Environnement;
import application.modele.objets.Materiau;

public class Platine extends Materiau {

    private final static int PV_MAX = 8;

    public Platine() {
    }

    public Platine(Environnement env, int x, int y) {
        super(env, x, y, PV_MAX);
    }
}
