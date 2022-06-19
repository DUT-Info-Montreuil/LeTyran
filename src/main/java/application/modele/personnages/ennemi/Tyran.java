package application.modele.personnages.ennemi;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;

import application.modele.armes.Epee;

import static application.modele.Direction.Droit;
import static application.modele.Direction.Gauche;
import static application.modele.MapJeu.TUILE_TAILLE;

public class Tyran extends Ennemi {

    private boolean charge;
    private int delaiCharge;

    public Tyran(Environnement env, int x, int y, int distance) {
        super(env, x, y, distance, new Epee(env, 3));
        setPv(100);
        charge = false;
        delaiCharge = 0;
    }

    protected void deplacement() {
        if (charge)
            charger();
        else if (!fuitJoueur() && (Math.abs(getEnv().getJoueur().getX() - (getX())) - 4 >= 6 * TUILE_TAILLE
                || Math.abs(getEnv().getJoueur().getY() - getY()) > TUILE_TAILLE)
                && (Math.abs(getX() - getOrigineX()) > 1)) {
            retourOrigine();
            seDeplacer();
        }
    }

    private void charger() {
        if (delaiCharge > 50) {
            int i = 0;
            Entite ent;
            while (i < getVitesse() * 2) {
                i++;

                ent = super.getCollider().verifierCollisionDirection(getDirection(), 0.45f);
                if (ent != null)
                    ent.detruire();

                if (getDirection() == Direction.Droit) {
                    super.setX(super.getX() + 0.45f);
                } else
                    super.setX(super.getX() - 0.45f);
            }

            if (getDirection() == Droit && getX() > getOrigineX() + getDistance() - TUILE_TAILLE)
                charge = false;
            else if (getDirection() == Gauche && getX() < getOrigineX() - getDistance() + TUILE_TAILLE)
                charge = false;

            if (Math.abs(getEnv().getJoueur().getX() - getX()) < 5 && Math.abs(getEnv().getJoueur().getY() - getY()) < TUILE_TAILLE)
                getEnv().getJoueur().decrementerPv(5);
        } else
            delaiCharge++;
    }

    protected boolean fuitJoueur() {
        if (!charge) {
            if (Math.abs(getEnv().getJoueur().getX() - getX()) < 6 * TUILE_TAILLE
                    && Math.abs(getEnv().getJoueur().getY() - getY()) < TUILE_TAILLE
                    && getX() >= getOrigineX() - getDistance() && getX() <= getOrigineX() + getDistance()) {
                Direction direction;
                if (getEnv().getJoueur().getX() - getX() <= 0)
                    direction = Droit;
                else
                    direction = Gauche;
                int i = 0;
                while (i < 3 && getCollider().verifierCollisionDirection(direction, 0.45f) == null) {
                    i++;
                    if (direction == Droit)
                        super.setX(super.getX() + 0.45f);
                    else
                        super.setX(super.getX() - 0.45f);
                }
                return true;
            } else if (getX() < getOrigineX() - getDistance()) {
                setDirection(Droit);
                charge = true;
                delaiCharge = 0;
            } else if (getX() > getOrigineX() + getDistance()) {
                setDirection(Gauche);
                charge = true;
                delaiCharge = 0;
            }
        }
        return false;
    }

    @Override
    public void update() {
        if (charge) {
            charger();
            if (getDistancePoussee() != 0)
                setDistancePoussee(0);
        } if (getDistancePoussee() != 0) {
            estPoussee();
        } else {
//            if (getAttaque())
//                attaquer();
//            if (!getAttaque())
//                detectionJoueur();
            deplacement();
        }
    }

    @Override
    protected int getHauteurMax() {
        return 5 * TUILE_TAILLE;
    }

    @Override
    protected int getVitesse() {
        return 3;
    }
}
