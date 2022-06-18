package application.modele.objets;

import application.modele.Entite;
import application.modele.Environnement;
import application.modele.personnages.Joueur;

import static application.modele.MapJeu.TUILE_TAILLE;

public abstract class Consommable extends Entite {

    public Consommable() {
    }

    public Consommable(Environnement env, int x, int y) {
        super(env, x *TUILE_TAILLE, y * TUILE_TAILLE);
    }

    public void consommer() {
        if (getEnv().getJoueur().getPv() + getHeal() > Joueur.PV_MAX)
            getEnv().getJoueur().setPv(Joueur.PV_MAX);
        else
            getEnv().getJoueur().setPv(getEnv().getJoueur().getPv() + getHeal());
        detruire();
    }

    public abstract int getHeal();

    @Override
    public void detruire() {
        getEnv().getJoueur().getInventaire().retirerObjet(getEnv().getJoueur().getInventaire().getObjetCorrespondant(this));
    }
}
