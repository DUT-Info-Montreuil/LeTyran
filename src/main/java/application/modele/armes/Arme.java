package application.modele.armes;

import application.modele.Entite;
import application.modele.Environnement;
import application.modele.personnages.Joueur;
import application.modele.personnages.Personnage;

public abstract class Arme extends Entite {

    private int qualite;

    public Arme(Environnement env, int qualite) {
        super(env);
        this.qualite = qualite;
        setPv(qualite * 15);
    }


    public void frapper(Personnage perso,Personnage ennemi) {
        ennemi.decrementerPv(nbDegat());
        if (perso instanceof Joueur)
            decrementerPv();
    }

    //renvoie les dégâts de l'arme selon la qualité
    public int nbDegat() {
        return qualite*3;
    }

    public int getQualite() {
        return qualite;
    }

    public int getDistance() {
        return 1;
    }

    @Override
    public void detruire() {
        getEnv().getJoueur().getInventaire().desequiperArme();
        getEnv().getJoueur().getInventaire().retirerNbRessources(this.getClass().getSimpleName(), 1);
        setPv(qualite * 15);
    }
}
