package application.modele.personnages.ennemi;

import application.modele.Environnement;
import application.modele.armes.Arme;
import application.modele.objets.consommable.Potion;
import application.modele.objets.consommable.Viande;
import application.modele.personnages.PNJ;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import static application.modele.Direction.Droit;
import static application.modele.Direction.Gauche;
import static application.modele.MapJeu.TUILE_TAILLE;

public abstract class Ennemi extends PNJ {

    private static int id = 0;

    private Arme arme;
    private BooleanProperty attaqueProperty;
    private int delaiFrappe;
    private boolean retourZone;
    private boolean poursuitJoueur;

    public Ennemi(Environnement env, int x, int y, int distance, Arme arme) {
        super(env, "Ennemi" + id++, x, y, distance);
        this.arme = arme;
        attaqueProperty = new SimpleBooleanProperty(false);
        delaiFrappe = 0;
        retourZone = false;
        poursuitJoueur = false;
    }

    protected void detectionJoueur() {
        if (joueurEnFace()) {
            attaqueProperty.setValue(true);
            delaiFrappe = 0;
        }
    }

    protected void attaquer() {
        if (delaiFrappe++ >= 30) {
            if (joueurEnFace())
                arme.frapper(this, getEnv().getJoueur());
            attaqueProperty.setValue(false);
        }
    }

    protected boolean joueurEnFace() {
        return Math.abs(getEnv().getJoueur().getX() - getX()) < arme.getDistance() * TUILE_TAILLE
                && Math.abs(getEnv().getJoueur().getY() - getY()) < TUILE_TAILLE
                && ((getDirection() == Gauche && getEnv().getJoueur().getX() - getX() < 1)
                || (getDirection() == Droit && getEnv().getJoueur().getX() - getX() > -1));
    }

    protected void retourneDansZone() {
        if (((getX() < getOrigineX() - 10 * TUILE_TAILLE && getDirection() == Gauche)
                || (getX() > getOrigineX() + getDistance() + 10 * TUILE_TAILLE  && getDirection() == Droit)) && !getRetourZone()) {
            setDirection(getDirectionOpposee());
            retourZone = true;
        } else if (getX() >= getOrigineX() && getX() <= getOrigineX() + getDistance() && getY() == getOrigineY() && getRetourZone())
            retourZone = false;
    }

    protected void retourOrigine() {
        if ((getX() - getOrigineX() < -1 && getDirection() == Gauche) || (getX() - getOrigineX() > 1 && getDirection() == Droit))
            setDirection(getDirectionOpposee());
        //met une nouvelle origine s'il est bloqué
        if (estBloque())
            setOrigineX(getX());
        if (getY() != getOrigineY()) setOrigineY(getY());
    }

    protected void poursuiteJoueur() {
        if (!retourZone && !joueurEnFace() && Math.abs(getEnv().getJoueur().getX() - getX()) < 5 * TUILE_TAILLE && Math.abs(getEnv().getJoueur().getY() - getY()) < 2 * TUILE_TAILLE) {
                if (getEnv().getJoueur().getX() - getX() > 0)
                    setDirection(Droit);
                else
                    setDirection(Gauche);
                poursuitJoueur = true;
        } else
            poursuitJoueur = false;
    }

    @Override
    public void update() {
        if (getDistancePoussee() != 0)
            estPoussee();
        else {
            tomber();
            if (getAttaque())
                attaquer();
            if (!getAttaque())
                detectionJoueur();
            if (!getAttaque())
                deplacement();
        }
    }

    @Override
    public void detruire() {
        if (Math.random() <= 0.4)
            getEnv().getJoueur().getInventaire().ajouterObjet(new Potion(getEnv()));
        //this.getEnv().getListeEntites().add(new Potion(getEnv(), (int) getX(), (int) getY()));
        getEnv().getListeEnnemis().remove(this);
    }

    @Override
    protected int getHauteurMax() {
        return 2 * TUILE_TAILLE;
    }

    @Override
    protected int getVitesse() {
        return 4;
    }

    public final boolean getAttaque() {
        return attaqueProperty.getValue();
    }
    
    public final BooleanProperty getAttaqueProperty() {
        return attaqueProperty;
    }

    public boolean getRetourZone() {
        return retourZone;
    }

    public boolean getPoursuitJoueur() {
        return poursuitJoueur;
    }

    @Override
    public Arme getArme() {
        return arme;
    }
}
