package application.vue;

import application.modele.Ennemie;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

public class EnnemiVue {
    private Pane rootPane;
    private ImageView image;
    private Pane paneDecors;
    private Ennemie ennemi;


    public EnnemiVue(Pane root, Pane tilesol, Ennemie ennemie){
        this.rootPane=root;
        this.paneDecors=tilesol;
        this.ennemi=ennemie;
        afficherCadavres();
        image.setLayoutX(500);
        image.setLayoutY(350);
        image.setFitWidth(50);
        image.setFitHeight(45);

        paneDecors.getChildren().add(image);
    }
    public void afficherCadavres(){
        this.image=new ImageView(new Image("file:src/main/resources/application/perso/mortChevalier.png"));
    }

    public void supprimerCadavre() {
        System.out.println("remove cadavre");
        this.rootPane.getChildren().remove(image);
    }

    public ImageView getImage(){
        return this.image;
    }

}
