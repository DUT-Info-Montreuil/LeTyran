package application.controleur;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ControleurApplication implements Initializable {
    private Controleur controleurJeu;
    private ControleurAccueil controleurAccueil;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        controleurJeu.initialize(url, resourceBundle);
    }
}
