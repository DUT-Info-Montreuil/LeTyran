package application.modele.personnages.animaux;

import application.modele.Environnement;
import application.modele.objets.Viande;
import application.modele.personnages.PNJ;

public abstract class Animal extends PNJ {

    public Animal(Environnement env, String id, int x, int y, int pv) {
        super(env, id, x, y, pv);
    }

    public abstract int nbViande();

    @Override
    public void detruire() {
        for (int i = 0; i < nbViande(); i++)
            getEnv().getJoueur().getInventaire().ajouterObjet(new Viande());
    }
}
