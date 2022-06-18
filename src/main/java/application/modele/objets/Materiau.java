package application.modele.objets;

import application.modele.Entite;
import application.modele.Environnement;
import application.modele.MapJeu;
import application.modele.armes.Arme;
import application.modele.armes.Pioche;

import static application.modele.MapJeu.TUILE_TAILLE;

import java.util.Map;

public abstract class Materiau extends Entite {

    private int idMateriau;
    public Materiau() {
        super(null, 0, 0, 0);
    }

    public Materiau(Environnement env,  int x, int y, int pv) {

        super(env, x *TUILE_TAILLE, y * TUILE_TAILLE, pv);

        //System.out.println(x + (y/env.getMapJeu().getWidth()) * env.getMapJeu().getWidth());
        this.idMateriau = x + y * env.getMapJeu().getWidth();
    }

    public int getId() {
        return this.idMateriau;
    }

    //appelé quand le bloc idMateriau cliqué décremente selon la qualité si le joueur a la bonne arme sinon de 1
    public void estFrappe() {
        if (getEnv().getJoueur().getArme() instanceof Pioche)
            decrementerPv(getEnv().getJoueur().getArme().nbDegat());
        else
            decrementerPv();
        getEnv().getJoueur().getArme().decrementerPv();
    }

    public void detruire() {
        Materiau materiau;
        int positionX = (int)this.getX()/TUILE_TAILLE;
        int positionY = (int)this.getY()/TUILE_TAILLE;
        switch (this.getClass().getSimpleName()) {
            case "Pierre": materiau = new Pierre(this.getEnv(), positionX, positionY); break;
            case "Fer": materiau = new Fer(this.getEnv(), positionX, positionY); break;
            case "Platine": materiau = new Platine(this.getEnv(), positionX, positionY); break;
            case "Terre" : materiau = new Terre(this.getEnv(), positionX, positionY); break;
            case "Bois" : materiau = new Bois(this.getEnv(), positionX, positionY); break;
            default: materiau = null; break;
        }
        this.getEnv().getListeEntites().add(materiau);

        getEnv().getMapJeu().getTabMap()[(int) (getY() / TUILE_TAILLE)][(int) (getX() / TUILE_TAILLE)] = 0;
        getEnv().getListeMateriaux().remove(this);
    }

    public float getX() {
        return super.getX();
    }

    public float getY() {
        return super.getY();
    }

    public int getPv() {
        return super.getPv();
    }

}


