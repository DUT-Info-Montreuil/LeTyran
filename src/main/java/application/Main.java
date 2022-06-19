package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import static application.modele.MapJeu.TUILE_TAILLE;
import static application.modele.MapJeu.HEIGHT;
import static application.modele.MapJeu.WIDTH;

public class Main extends Application {

    public final static int WIDTH_FENETRE = WIDTH*TUILE_TAILLE;
    public final static int HEIGHT_FENETRE = HEIGHT*TUILE_TAILLE;

    @Override
    public void start(Stage primaryStage) {
        try {
            Pane root = FXMLLoader.load(getClass().getResource("vue_accueil.fxml"));
            Scene scene = new Scene(root, WIDTH*TUILE_TAILLE,HEIGHT*TUILE_TAILLE);
            root.requestFocus();

            //controleurScene.montrerEcranJeu();

            primaryStage.setTitle("Le Tyran");
            primaryStage.setResizable(true);
            //primaryStage.setMaximized(true);
            primaryStage.setScene(scene);

            //Jeu jeu = new Jeu(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
