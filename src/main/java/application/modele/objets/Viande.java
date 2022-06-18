package application.modele.objets;

import application.modele.Environnement;

public class Viande extends Consommable {

    public Viande() {}

    public Viande(Environnement env, int x, int y) {
        super(env, x, y);
    }

    @Override
    public int getHeal() {
        return 5;
    }
}
