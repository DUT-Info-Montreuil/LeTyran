package application.modele.personnages.ennemi;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;

import application.modele.armes.Epee;
import application.modele.objets.Cle;
import application.modele.objets.consommable.Potion;
import application.modele.projectiles.BouleDeFeu;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import static application.modele.Direction.Droit;
import static application.modele.Direction.Gauche;
import static application.modele.MapJeu.TUILE_TAILLE;

public class Tyran extends Ennemi {

    private BooleanProperty chargeProperty;
    private int delaiCharge;
    private int delaiLancer;

    public Tyran(Environnement env, int x, int y, int distance) {
        super(env, x, y, distance, new Epee(env, 3));
        setPv(100);
        chargeProperty = new SimpleBooleanProperty(false);
        delaiCharge = 0;
        delaiLancer = 0;
    }

    protected void deplacement() {
        if (chargeProperty.getValue())
            charger();
        else if (!fuitJoueur() && (Math.abs(getEnv().getJoueur().getX() - (getX())) - 4 >= 6 * TUILE_TAILLE
                || Math.abs(getEnv().getJoueur().getY() - getY()) > TUILE_TAILLE)
                && (Math.abs(getX() - getOrigineX()) > 1)) {
            if ((getX() - getOrigineX() < -1 && getDirection() == Gauche) || (getX() - getOrigineX() > 1 && getDirection() == Droit))
                setDirection(getDirectionOpposee());
            seDeplacer();
        }
    }

    private void charger() {
        if (delaiCharge++ > 50) {
            int i = 0;
            Entite ent;
            while (i < getVitesse() * 3) {
                i++;

                ent = super.getCollider().verifierCollisionDirection(getDirection(), 0.45f);
                if (ent != null)
                    ent.detruire();

                if (getDirection() == Direction.Droit) {
                    super.setX(super.getX() + 0.45f);
                } else
                    super.setX(super.getX() - 0.45f);
            }

            if (getDirection() == Droit && getX() > getOrigineX())
                chargeProperty.setValue(false);
            else if (getDirection() == Gauche && getX() < getOrigineX())
                chargeProperty.setValue(false);

            if (Math.abs(getEnv().getJoueur().getX() - getX()) < 5 && Math.abs(getEnv().getJoueur().getY() - getY()) < TUILE_TAILLE)
                getArme().frapper(this, getEnv().getJoueur());
        }
    }

    protected boolean fuitJoueur() {
        if (!chargeProperty.getValue()) {
            if (Math.abs(getEnv().getJoueur().getX() - getX()) < 5 * TUILE_TAILLE
                    && Math.abs(getEnv().getJoueur().getY() - getY()) < TUILE_TAILLE
                    && getX() >= getOrigineX() - getDistance() && getX() <= getOrigineX() + getDistance()) {
                Direction direction;
                if (getEnv().getJoueur().getX() - getX() <= 0) {
                    direction = Droit;
                    setDirection(Gauche);
                } else {
                    direction = Gauche;
                    setDirection(Droit);
                }
                int i = 0;
                while (i < 3 && getCollider().verifierCollisionDirection(direction, 0.45f) == null) {
                    i++;
                    if (direction == Droit)
                        super.setX(super.getX() + 0.45f);
                    else
                        super.setX(super.getX() - 0.45f);
                }
                return true;
            } else if (getX() < getOrigineX() - getDistance() || (getX() > getOrigineX() + getDistance()))  {
                chargeProperty.setValue(true);
                delaiCharge = 0;
            }
        }
        return false;
    }

    private void lancer() {
        if (delaiLancer++ >= 90) {
            if (!joueurEnFace() && getEnv().getJoueur().getX() > getOrigineX() - getDistance() &&  getEnv().getJoueur().getX() < getOrigineX() + getDistance()) {
                if (Math.abs(getEnv().getJoueur().getX() - getX()) >= 7 * TUILE_TAILLE)
                    getEnv().getListeProjectiles().add(new BouleDeFeu(getEnv(), this, 1));
                else if (Math.abs(getEnv().getJoueur().getX() - getX()) >= 3 * TUILE_TAILLE)
                    getEnv().getListeProjectiles().add(new BouleDeFeu(getEnv(), this, 2));
            }
            delaiLancer = 0;
        }
    }

    @Override
    public void update() {
        tomber();
        if (chargeProperty.getValue()) {
            charger();
        } else {
            lancer();
            if (getAttaque())
                attaquer();
            if (!getAttaque())
                detectionJoueur();
            deplacement();
        }
    }

    @Override
    public void detruire() {
        getEnv().getListeEntites().add(new Cle(getEnv(), (int) getX(), (int) getY()));
        getEnv().getListeEnnemis().remove(this);
    }

    @Override
    protected int getHauteurMax() {
        return 5 * TUILE_TAILLE;
    }

    @Override
    protected int getVitesse() {
        return 3;
    }

    public BooleanProperty getChargeProperty() {
        return chargeProperty;
    }
}
