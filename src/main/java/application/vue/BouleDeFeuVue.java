package application.vue;

import application.modele.Direction;
import application.modele.projectiles.BouleDeFeu;
import javafx.animation.RotateTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import static application.modele.MapJeu.TUILE_TAILLE;

public class BouleDeFeuVue {

    private BouleDeFeu bouleDeFeu;
    private ImageView spriteBouleDeFeu;

    public BouleDeFeuVue(Pane panePNJ, BouleDeFeu bouleDeFeu) {
        this.bouleDeFeu = bouleDeFeu;
        spriteBouleDeFeu = new ImageView(ChargeurRessources.iconObjets.get("BouleDeFeu"));
        spriteBouleDeFeu.setId(bouleDeFeu.getId());
        //orientation de la boule de feu
        if (bouleDeFeu.getType() == 1)
            if (bouleDeFeu.getDirection() == Direction.Droit)
                spriteBouleDeFeu.setRotate(-30);
            else
                spriteBouleDeFeu.setRotate(-150);
        else if (bouleDeFeu.getDirection() == Direction.Gauche)
            spriteBouleDeFeu.setScaleX(-1);


        spriteBouleDeFeu.setFitWidth(TUILE_TAILLE);
        spriteBouleDeFeu.setFitHeight(TUILE_TAILLE);
        spriteBouleDeFeu.translateXProperty().bind(bouleDeFeu.getXProperty());
        spriteBouleDeFeu.translateYProperty().bind(bouleDeFeu.getYProperty());

        panePNJ.getChildren().add(spriteBouleDeFeu);
    }

    // avoir un arc de cercle fluide
    public void chute() {
        RotateTransition rt = new RotateTransition(Duration.millis(300), spriteBouleDeFeu);
        if (bouleDeFeu.getDirection() == Direction.Droit)
            rt.setByAngle(50);
        else
            rt.setByAngle(-50);
        rt.play();
    }
}
