package application.modele;

import static application.modele.MapJeu.TUILE_TAILLE;

public class FeuDeCamp {

    private Environnement env;
    private int x;
    private int y;

    public FeuDeCamp(Environnement env, int x, int y) {
        this.env = env;
        this.x = x;
        this.y = y;

    }

    public void seReposer() {
        env.initListeEnnemis();
        env.initListeAnimaux();
        env.getListeFleches().clear();
        env.getJoueur().setX(x * TUILE_TAILLE - TUILE_TAILLE);
        env.getJoueur().setY(y * TUILE_TAILLE);
        env.getJoueur().setPv(30);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}