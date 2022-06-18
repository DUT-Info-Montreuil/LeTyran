package application.modele.personnages.animaux;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.ObjetJeu;
import application.modele.objets.Consommable;
import application.modele.objets.materiaux.Materiau;
import application.modele.personnages.Personnage;

import static application.modele.Direction.Droit;
import static application.modele.Direction.Gauche;
import static application.modele.MapJeu.TUILE_TAILLE;

public class Sanglier extends Animal {

    public final static int PV_MAX = 12;

    private static int id = 0;
    private int delaiCharge;

    public Sanglier(Environnement env, int x, int y, int distance) {
        super(env, "Sanglier" + id++, x, y, distance, PV_MAX);
        delaiCharge = 0;
    }

    @Override
    public void deplacement() {
        if (delaiCharge < 50)
            delaiCharge++;
        else {
            deplacementAllerRetour();
            seDeplacer();
        }
    }

    @Override
    protected void deplacementAllerRetour() {
        if ((getX() >= getOrigineX() && getX() <= getOrigineX() + getDistance() && getY() == getOrigineY() && estBloque() || (getX() < getOrigineX() && getDirection() == Gauche) || (getX() > getOrigineX() + getDistance() && getDirection() == Droit))) {
            setDirection(getDirectionOpposee());
            delaiCharge = 0;
        } else if (estBloque()) {
            if (getDirection() == Gauche)
                setOrigineX(getX());
            else
                setOrigineX(getX() - getDistance());
            setOrigineY(getY());
        }
    }

    @Override
    public void update() {
        collide();
        tomber();
        deplacement();
    }

    @Override
    public void quandCollisionDetectee(Entite ent) {
        if (ent instanceof Personnage)
            if (getDirection() == Direction.Droit)
                ((Personnage) ent).setDistancePoussee(TUILE_TAILLE);
            else
                ((Personnage) ent).setDistancePoussee(-TUILE_TAILLE);
    }

    @Override
    protected int getHauteurMax() {
        return 0;
    }

    @Override
    protected int getVitesse() {
        return 7;
    }

    @Override
    public int nbViande() {
        return 4;
    }
}
