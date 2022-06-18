package application.modele.personnages.animaux;

import application.modele.Direction;
import application.modele.Environnement;

import static application.modele.MapJeu.TUILE_TAILLE;

public class Lapin extends Animal {

    public final static int PV_MAX = 6;

    private static int id = 0;
    private int delaiSaut;

    public Lapin(Environnement env, int x, int y, int distance) {
        super(env, "Lapin" + id++, x, y, distance, PV_MAX);
        delaiSaut = 0;
    }

    @Override
    public void deplacement() {
        deplacementAllerRetour();
        seDeplacer();
    }

    public void saut() {
        if (!getSaute() && !getTombe())
            if (delaiSaut < 50)
                delaiSaut++;
            else {
                setSaute(true);
                delaiSaut = 0;
            }
    }

    protected void seDeplacer() {
        int distance;
        if (getTombe() || getSaute())
            distance = getVitesse() + 1;
        else
            distance = getVitesse();
        int i = 0;
        while (i < distance && super.getCollider().verifierCollisionDirection(getDirection(), 0.45f) == null) {
            i++;
            if (getDirection() == Direction.Droit)
                super.setX(super.getX() + 0.45f);
            else
                super.setX(super.getX() - 0.45f);
        }
    }

    @Override
    protected void sauter() {
        int i = 0;
        while (i < getVitesse() && getHauteurSaut() < getHauteurMax() && super.getCollider().verifierCollisionDirection(Direction.Haut, 0.10f) == null) {
            i++;
            super.setY(super.getY() - 0.10f);
            setHauteurSaut(getHauteurSaut()+0.10f);
        }
        if (i < getVitesse())
            setSaute(false);
    }

    @Override
    protected void tomber() {
        int i = 0;
        while (i < getVitesse() && super.getCollider().verifierCollisionDirection(Direction.Bas, 0.10f) == null) {
            i++;
            setTombe(true);
            super.setY(super.getY() + 0.10f);
        }

        if (i < getVitesse()) {
            setTombe(false);
            setHauteurSaut(0);
        }
    }
    
    @Override
    public void update() {
        saut();
        if (getSaute()) sauter();
        else tomber();
        deplacement();
    }

    @Override
    protected int getHauteurMax() {
        return TUILE_TAILLE/2;
    }

    @Override
    protected int getVitesse() {
        return 4;
    }

    @Override
    public int nbViande() {
        return 1;
    }
}
