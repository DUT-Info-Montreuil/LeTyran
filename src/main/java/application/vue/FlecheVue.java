package application.vue;

import application.modele.Direction;
import application.modele.projectiles.Fleche;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static application.modele.MapJeu.TUILE_TAILLE;

public class FlecheVue {
    public FlecheVue(Pane paneEnnemis, Fleche fleche) {
        ImageView spriteFleche = new ImageView(ChargeurRessources.iconObjets.get("Fleche1"));
        spriteFleche.setId(fleche.getId());
        switch (fleche.getDirection()) {
            case Gauche -> spriteFleche.setScaleX(-1);
            case Bas -> spriteFleche.setRotate(90);
            case Haut -> spriteFleche.setRotate(-90);
        }
        spriteFleche.setFitWidth(TUILE_TAILLE);
        spriteFleche.setFitHeight(TUILE_TAILLE);
        spriteFleche.translateXProperty().bind(fleche.getXProperty());
        spriteFleche.translateYProperty().bind(fleche.getYProperty());
        paneEnnemis.getChildren().add(spriteFleche);
    }
}
