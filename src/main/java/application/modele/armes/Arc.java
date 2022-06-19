package application.modele.armes;

import application.modele.Environnement;
import application.modele.projectiles.Fleche;
import application.modele.personnages.Personnage;

import static application.modele.MapJeu.TUILE_TAILLE;

public class Arc extends Arme {

    public Arc(Environnement env, int qualite) {
        super(env, qualite);
    }

    public void frapper(Personnage perso, Personnage ennemi) {
        getEnv().getListeProjectiles().add(new Fleche(getEnv(), perso, getDistance()*TUILE_TAILLE, nbDegat()));
    }

    public void frapper(int x, int y) {
        if (getEnv().getJoueur().getInventaire().recupererNombreRessources("Fleche") > 0) {
            getEnv().getJoueur().getInventaire().retirerNbRessources("Fleche", 1);
                getEnv().getListeProjectiles().add(new Fleche(getEnv(), getEnv().getJoueur(), x, y, getDistance() * TUILE_TAILLE, nbDegat()));
                decrementerPv();
        }
    }

    public int getDistance() {
        if (getQualite() == 1) {
            return 5;
        } else if (getQualite() == 2) {
            return 7;
        } else {
            return 9;
        }
    }
}
