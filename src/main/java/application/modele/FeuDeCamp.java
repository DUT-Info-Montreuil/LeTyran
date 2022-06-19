package application.modele;

import application.controleur.Constantes;
import application.modele.personnages.Joueur;


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
        env.getListeProjectiles().clear();
        env.getJoueur().setX(x * Constantes.TAILLE_TUILE - Constantes.TAILLE_TUILE);
        env.getJoueur().setY(y * Constantes.TAILLE_TUILE);
        env.getJoueur().setPv(Joueur.PV_MAX);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}