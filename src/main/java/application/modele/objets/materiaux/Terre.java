package application.modele.objets.materiaux;

import application.modele.Environnement;
import application.modele.objets.Materiau;

public class Terre extends Materiau {

    private final static int PV_MAX = 0;

    public Terre() {
    }

    public Terre(Environnement env, int x, int y) {
        super(env,x, y, PV_MAX);
    }

    @Override
    public void estFrappe() {
        getEnv().getJoueur().getArme().decrementerPv();
        detruire();
    }

}
