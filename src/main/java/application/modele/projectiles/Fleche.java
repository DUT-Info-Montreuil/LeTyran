package application.modele.projectiles;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.objets.Arbre;
import application.modele.objets.Materiau;
import application.modele.objets.consommable.Consommable;
import application.modele.personnages.Joueur;
import application.modele.personnages.Personnage;

import static application.modele.MapJeu.TUILE_TAILLE;

public class Fleche extends Projectile {
    
    private static int idMax = 2;

    private int degat;
    private boolean touche;
    private int distanceMax;

    public Fleche() {}

    public Fleche(Environnement env, Joueur joueur, int x, int y, int distanceMax, int degat) {
        super(env, (int) joueur.getX(), (int) joueur.getY(), "Fleche" + idMax++, joueur);

        if (x == (int) (joueur.getX() / TUILE_TAILLE))
            if (y > joueur.getY()/TUILE_TAILLE)
                setDirection(Direction.Bas);
            else
                setDirection(Direction.Haut);
        else
            setDirection(joueur.getDirection());

        this.degat = degat;
        touche = false;
        this.distanceMax = distanceMax;

        this.getCollider().getHitBox().setHeight(15);
        this.getCollider().getHitBox().setWidth(25);
    }

    public Fleche(Environnement env, Personnage perso, int distanceMax, int degat) {
        super(env, (int) perso.getX(), (int) perso.getY(), "Fleche" + idMax++, perso, perso.getDirection());

        this.degat = degat;
        touche = false;
        this.distanceMax = distanceMax;

        this.getCollider().getHitBox().setHeight(15);
        this.getCollider().getHitBox().setWidth(25);
    }

    @Override
    public void seDeplacer() {
        int i = 0;

        while (i < 7 && getDistanceParcourue() < distanceMax) {
            i++;
            Entite touchee = this.getCollider().verifierCollisionDirection(getDirection(), 0.5f);
            if(touchee != null)
                quandCollisionDetectee(touchee);

            switch (getDirection()) {
                case Droit: setX(getX() + 0.5f); break;
                case Gauche: setX(getX() - 0.5f); break;
                case Bas: setY(getY() + 0.5f); break;
                case Haut: setY(getY() - 0.5f); break;
            }
            incrementerDistanceParcourue();
            //collide();
        }

        if (i < 3)
            getEnv().getListeProjectiles().remove(this);
    }

    @Override
    public void update() {
        super.collide();
        seDeplacer();
    }

    @Override
    public void quandCollisionDetectee(Entite ent) {
        if (!touche && ent != getPerso() && !(ent instanceof Fleche) && !(ent instanceof Arbre) && !(ent instanceof Consommable)) {
            System.out.println(ent);
            touche = true;
            if (!(ent instanceof Materiau))
                ent.decrementerPv(degat);
            getEnv().getListeProjectiles().remove(this);
        }
    }
}
