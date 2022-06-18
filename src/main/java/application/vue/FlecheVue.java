package application.vue;

import application.modele.Direction;
import application.modele.armes.arc.Fleche;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import static application.modele.MapJeu.TUILE_TAILLE;

public class FlecheVue {

    public FlecheVue(Pane root, Fleche fleche) {
        ImageView spriteFleche = new ImageView(ChargeurRessources.iconObjets.get("Fleche1"));
        spriteFleche.setId(fleche.getId());
        if (fleche.getDirection() == Direction.Gauche) spriteFleche.setScaleX(-1);
        spriteFleche.setFitWidth(TUILE_TAILLE);
        spriteFleche.setFitHeight(TUILE_TAILLE);
        spriteFleche.translateXProperty().bind(fleche.getXProperty());
        spriteFleche.translateYProperty().bind(fleche.getYProperty());
        root.getChildren().add(spriteFleche);
    }
}
