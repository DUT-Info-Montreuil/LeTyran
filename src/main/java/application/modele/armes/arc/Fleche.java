package application.modele.armes.arc;

import application.modele.Direction;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.objets.Arbre;
import application.modele.objets.materiaux.Materiau;
import application.modele.personnages.Personnage;

public class Fleche extends Entite {
    
    private static int idMax = 2;
    
    private String id;
    private Personnage perso;
    private Direction direction;
    private int distanceMax;
    private float distanceParcourue;
    private int degat;
    private boolean touche;

    public Fleche() {
    }

    public Fleche(Environnement env, Personnage perso, int distanceMax, int degat) {
        super(env, (int) perso.getX(), (int) (perso.getY()));
        this.perso = perso;
        this.direction = perso.getDirection();
        this.distanceMax = distanceMax;
        this.degat = degat;
        id = "Fleche" + idMax++;
        distanceParcourue = 0;
        touche = false;
        this.getCollider().getHitBox().setHeight(15);
    }

    private void seDeplace() {
        int i = 0;

        while (i < 7 && distanceParcourue < distanceMax) {
            i++;
            Entite touchee = this.getCollider().verifierCollisionDirection(this.direction, 0.5f);
            if(touchee != null)
                quandCollisionDetectee(touchee);

            switch (direction) {
                case Droit: setX(getX() + 0.5f); break;
                case Gauche: setX(getX() - 0.5f); break;
                case Bas: setY(getY() + 0.5f); break;
                case Haut: setY(getY() - 0.5f); break;
            }
            distanceParcourue+=0.5f;
            //collide();
        }

        if (i < 3)
            getEnv().getListeFleches().remove(this);
    }

    public void update() {
        super.collide();
        seDeplace();
    }

    @Override
    public void quandCollisionDetectee(Entite ent) {
        if (!touche && ent != perso && !(ent instanceof Fleche) && !(ent instanceof Arbre)) {
            System.out.println(ent);
            touche = true;
            if (!(ent instanceof Materiau))
                ent.decrementerPv(degat);
            getEnv().getListeFleches().remove(this);
        }
    }

    public String getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }
}
