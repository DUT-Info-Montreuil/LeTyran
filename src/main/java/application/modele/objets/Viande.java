package application.modele.objets;

import application.modele.Entite;
import application.modele.Environnement;

import static application.modele.MapJeu.TUILE_TAILLE;

public class Viande extends Consommable {

    public Viande() {}

    public Viande(Environnement env, int x, int y) {
        super(env, x *TUILE_TAILLE, y * TUILE_TAILLE);
    }

    @Override
    public int getHeal() {
        return 5;
    }
}
