package application.controleur;

import application.modele.Environnement;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import static application.modele.MapJeu.*;

public class MousePressed implements EventHandler<MouseEvent> {
    private Controleur controleur;
    private Environnement env;

    public MousePressed(Controleur controleur, Environnement env) {
        this.controleur = controleur;
        this.env = env;
    }

    //calcule les coordonnées selon la position de la souris et du personnage
    @Override
    public void handle(MouseEvent mouseEvent) {
        System.out.println("Joueur " + (int) env.getJoueur().getX()/TUILE_TAILLE + " " + (int) env.getJoueur().getY()/TUILE_TAILLE);
        if (mouseEvent.getX() < (WIDTH * TUILE_TAILLE) / 2 + 5*TUILE_TAILLE && mouseEvent.getX() > (WIDTH * TUILE_TAILLE) / 2 - 5*TUILE_TAILLE
                && mouseEvent.getY() < (HEIGHT * TUILE_TAILLE) / 2 + 5*TUILE_TAILLE && mouseEvent.getY() > (HEIGHT * TUILE_TAILLE) / 2 - 5*TUILE_TAILLE) {
            int mouseX, mouseY;

            if (mouseEvent.getY() < (HEIGHT * TUILE_TAILLE) / 2)
                mouseY = (int) (env.getJoueur().getY() / TUILE_TAILLE) - 1;
            else if (mouseEvent.getY() > (HEIGHT * TUILE_TAILLE) / 2 + TUILE_TAILLE)
                mouseY = (int) (env.getJoueur().getY() / TUILE_TAILLE) + 1;
            else
                mouseY = (int) (env.getJoueur().getY() / TUILE_TAILLE);

            if (mouseEvent.getX() > (WIDTH * TUILE_TAILLE) / 2 + TUILE_TAILLE)
                if ((env.getJoueur().getX() / TUILE_TAILLE) % 1 > 0.8)
                    mouseX = (int) (env.getJoueur().getX() / TUILE_TAILLE) + 2;
                else
                    mouseX = (int) (env.getJoueur().getX() / TUILE_TAILLE) + 1;
            else if (mouseEvent.getX() < (WIDTH * TUILE_TAILLE) / 2)
                if ((env.getJoueur().getX() / TUILE_TAILLE) % 1 > 0.8 && mouseY != (int) (env.getJoueur().getY() / TUILE_TAILLE))
                    mouseX = (int) (env.getJoueur().getX() / TUILE_TAILLE);
                else
                    mouseX = (int) (env.getJoueur().getX() / TUILE_TAILLE) - 1;
            else
            if ((env.getJoueur().getX() / TUILE_TAILLE) % 1 > 0.5)
                mouseX = (int) (env.getJoueur().getX() / TUILE_TAILLE) + 1;
            else
                mouseX = (int) (env.getJoueur().getX() / TUILE_TAILLE);


            if(mouseX >= 0 && mouseX < env.getMapJeu().getWidth() && mouseY < env.getMapJeu().getHeight()) {
                if (env.getJoueur().interagit(mouseX, mouseY)) {
                    controleur.getArmeVue().animationFrappe();
                } else {
                    env.getJoueur().poserBloc(mouseX, mouseY);
                }
            }
        }
    }
}
