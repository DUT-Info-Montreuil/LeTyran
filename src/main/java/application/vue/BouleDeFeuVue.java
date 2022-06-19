package application.vue;

import application.modele.Direction;
import application.modele.projectiles.BouleDeFeu;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static application.modele.MapJeu.TUILE_TAILLE;

public class BouleDeFeuVue {

    private BouleDeFeu bouleDeFeu;
    private ImageView spriteBouleDeFeu;

    public BouleDeFeuVue(Pane paneEnnemis, BouleDeFeu bouleDeFeu) {
        this.bouleDeFeu = bouleDeFeu;
        spriteBouleDeFeu = new ImageView(ChargeurRessources.iconObjets.get("BouleDeFeu"));
        spriteBouleDeFeu.setId(bouleDeFeu.getId());
        spriteBouleDeFeu.setRotate(-90);
        spriteBouleDeFeu.setFitWidth(TUILE_TAILLE);
        spriteBouleDeFeu.setFitHeight(TUILE_TAILLE);
        spriteBouleDeFeu.translateXProperty().bind(bouleDeFeu.getXProperty());
        spriteBouleDeFeu.translateYProperty().bind(bouleDeFeu.getYProperty());
        paneEnnemis.getChildren().add(spriteBouleDeFeu);
    }

    public void chute() {
        if (bouleDeFeu.getDirection() == Direction.Droit)
            spriteBouleDeFeu.setRotate(30);
        else
            spriteBouleDeFeu.setRotate(150);
    }
}
