package application.controleur;

import application.modele.MapJeu;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControleurAccueil implements Initializable {
    @FXML
    private Pane root;

    @FXML
    private VBox btnQuitNouv;

    @FXML
    private Label nomJeu;

    private Timeline gameLoop;

    @FXML
    private Button nouvellePartie;

    @FXML
    private Button quitterJeu;

    @FXML
    private Pane paneNuages;

    private double textRedColor = 1;
    private int nbFrame = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Font font = Font.loadFont("file:src/main/resources/application/fonts/IMMORTAL.ttf", 50);

        root.setPrefSize(150 * 32, 34 * 32);
        this.paneNuages.setPrefSize(root.getPrefWidth(), root.getPrefHeight());

        btnQuitNouv.setLayoutX(root.getPrefWidth() / 2 - btnQuitNouv.getPrefWidth() / 2);
        btnQuitNouv.setLayoutY(root.getPrefHeight() / 2 + btnQuitNouv.getPrefHeight() / 3);

        nomJeu.setLayoutX(root.getPrefWidth() / 2 - nomJeu.getBoundsInLocal().getMaxX() );
        nomJeu.setLayoutY(100);

        initAnimation();
        ajouterNuages();


        gameLoop.play();
    }

    private void initAnimation() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                // on définit le FPS (nbre de frame par seconde)
                Duration.seconds(0.017),
                (ev ->{
                    animerText();
                    animerNuages();
                    nbFrame++;
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }

    private void animerText() {
        textRedColor += 0.05;
        Double colorToSet;
        if(textRedColor > 10) {
            colorToSet = Math.cos(textRedColor);

        } else {
            colorToSet = 0d;
        }

        if(colorToSet < 0) {

            colorToSet = 0d;
        }

        nomJeu.opacityProperty().setValue(textRedColor * 0.03);
        nomJeu.setTextFill(Color.color(Math.cos(colorToSet),0, 0));
    }

    public void ajouterNuages() {
        ImageView previous = null;
        int minWidth = 100;
        int maxWidth = 150;
        for(int i = 0; i < 7; i++) {
            ImageView imgView = new ImageView(new Image("file:src/main/resources/application/ciel/nuage_" + (int)(Math.random() * 6 +1) + ".png"));
            imgView.setFitWidth(100);
            imgView.setFitHeight(50);

            if (i > 0) {
                imgView.setTranslateY(previous.getTranslateY());
                if (i % 2 == 0)
                    imgView.setTranslateY(previous.getTranslateY() + 50 + Math.random() * 20);
            }
            else {
                imgView.setTranslateY(0);
            }

            imgView.setTranslateX(((i *3) + i % 2) * imgView.getFitWidth() + Math.random() * imgView.getFitWidth());
            this.paneNuages.getChildren().add(imgView);
            previous = imgView;
        }
    }

    private void animerNuages() {
        for (int i = 0; i < this.paneNuages.getChildren().size(); i++) {
            ImageView img = (ImageView) this.paneNuages.getChildren().get(i);

            img.setTranslateX((int)(img.getTranslateX() - 1));
            if(img.getTranslateX() < 0 - img.getFitWidth()) {
                img.setTranslateX(this.root.getPrefWidth() + img.getFitWidth());
            }
        }
    }

    public void lancerJeu() throws IOException {
        Pane nouveauRoot = FXMLLoader.load(getClass().getResource("/application/vue.fxml"));
        this.root.getScene().setRoot(nouveauRoot);
        nouveauRoot.requestFocus();

    }

}
