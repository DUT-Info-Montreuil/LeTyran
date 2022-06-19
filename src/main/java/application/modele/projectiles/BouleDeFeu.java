package application.modele.projectiles;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.personnages.Joueur;
import application.modele.personnages.Personnage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import static application.modele.Direction.*;
import static application.modele.MapJeu.TUILE_TAILLE;

public class BouleDeFeu extends Projectile {

    private final static int HAUTEUR_MAX = 5 * TUILE_TAILLE;
    private final static int DEGATS = 10;

    private static int idMax = 0;

    private BooleanProperty chuteProperty;
    private int delaiChute;

    public BouleDeFeu(Environnement env, Personnage perso) {
        super(env, (int) perso.getX(), (int) perso.getY(), "Fleche" + idMax++, perso, Direction.Haut);
        chuteProperty = new SimpleBooleanProperty(false);
        delaiChute = 0;
    }

    @Override
    public void seDeplacer() {
        if (!chuteProperty.getValue()) {
            int i = 0;
            while (i < getVitesse() && getDistanceParcourue() < HAUTEUR_MAX) {
                i++;
                Entite touchee = this.getCollider().verifierCollisionDirection(getDirection(), 0.5f);
                if (touchee != null)
                    quandCollisionDetectee(touchee);
                setY(getY() - 0.5f);
                incrementerDistanceParcourue();
            }

            if (i < getVitesse() || Math.abs((int) (getEnv().getJoueur().getX()/TUILE_TAILLE) - (int) (getX()/TUILE_TAILLE))
                    == Math.abs((int) (getEnv().getJoueur().getY()/TUILE_TAILLE) - (int) (getX()/TUILE_TAILLE))) {
                chuteProperty.setValue(true);
                if (getEnv().getJoueur().getX() < getX())
                    setDirection(Gauche);
                else
                    setDirection(Droit);
            }
        } else {
            if (delaiChute < 50)
                delaiChute++;
            else {
                int i = 0;
                while (i < getVitesse() && getDistanceParcourue() < HAUTEUR_MAX) {
                    i++;
                    Entite touchee = this.getCollider().verifierCollisionDirection(getDirection(), 0.5f);
                    if (touchee != null)
                        quandCollisionDetectee(touchee);
                    touchee = this.getCollider().verifierCollisionDirection(Bas, 0.5f);
                    if (touchee != null)
                        quandCollisionDetectee(touchee);

                    setY(getY() + 0.5f);
                    if (getDirection() == Droit)
                        setX(getX() + 0.5f);
                    else
                        setX(getX() - 0.5f);
                    incrementerDistanceParcourue();
                }

                if (i < getVitesse())
                    getEnv().getListeProjectiles().remove(this);
            }
        }
    }

    @Override
    public void update() {
        super.collide();
        seDeplacer();
    }

    @Override
    public void quandCollisionDetectee(Entite ent) {
        if (!getTouche() && ent != getPerso()) {
            if (ent instanceof Joueur) {
                ent.decrementerPv(DEGATS);
                setTouche(true);
                getEnv().getListeProjectiles().remove(this);
            }
            else {
                ent.detruire();
            }
        }
    }

    public BooleanProperty getChuteProperty() {
        return chuteProperty;
    }
}
