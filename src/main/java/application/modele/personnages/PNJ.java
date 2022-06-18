package application.modele.personnages;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.objets.Materiau;

import static application.modele.Direction.Droit;
import static application.modele.Direction.Gauche;
import static application.modele.MapJeu.TUILE_TAILLE;

public abstract class PNJ extends Personnage {

    private float origineX;
    private float origineY;
    private int distance;

    public PNJ(Environnement env, String id, int x, int y, int distance) {
        super(env, id, x * TUILE_TAILLE, y * TUILE_TAILLE);
        origineX = x * TUILE_TAILLE;
        origineY = y * TUILE_TAILLE;
        this.distance = distance * TUILE_TAILLE;
    }

    public PNJ(Environnement env, String id, int x, int y, int distance, int pv) {
        super(env, id, x * TUILE_TAILLE, y * TUILE_TAILLE, pv);
        origineX = x * TUILE_TAILLE;
        origineY = y * TUILE_TAILLE;
        this.distance = distance * TUILE_TAILLE;
    }

    protected void deplacement() {
    }

    protected void deplacementAllerRetour() {
        if ((getX() >= origineX && getX() <= origineX + distance && getY() == origineY && estBloque() || (getX() < origineX && getDirection() == Gauche) || (getX() > origineX + distance && getDirection() == Droit)))
            setDirection(getDirectionOpposee());
        else if (estBloque()) {
            if (getDirection() == Gauche)
                origineX = getX();
            else
                origineX = getX() - getDistance();
            origineY = getY();
        }
    }

    protected boolean estBloque() {
        Entite collide1 = super.getCollider().verifierCollisionDirection(getDirection(), 0.45);
        Entite collide2 = super.getCollider().verifierCollisionDirection(getDirectionOpposee(), 0.45);
        return (collide1 != null && collide1 instanceof Materiau) && (collide2 == null || !(collide2 instanceof Materiau));
    }

    protected Direction getDirectionOpposee() {
        if (getDirection() == Droit)
            return Gauche;
        else
            return Droit;
    }

    public float getOrigineX() {
        return origineX;
    }

    public float getOrigineY() {
        return origineY;
    }

    public void setOrigineX(float origineX) {
        this.origineX = origineX;
    }

    public void setOrigineY(float origineY) {
        this.origineY = origineY;
    }

    public int getDistance() {
        return distance;
    }
}
