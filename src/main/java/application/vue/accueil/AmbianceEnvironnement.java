package application.vue.accueil;

import application.vue.ChargeurRessources;
import javafx.scene.media.AudioClip;

public class AmbianceEnvironnement {

    private AudioClip ambianceSonoreActuel;
    private String sonActuel;


    public AmbianceEnvironnement() {
        ambianceSonoreActuel = ChargeurRessources.ensembleSonJeu.get("introjeu");
        ambianceSonoreActuel.setVolume(0.1);
        ambianceSonoreActuel.play();
    }

    public void changerSon(String son) {
        if(sonActuel != son) {
            sonActuel = son;
            if(ambianceSonoreActuel != null) {
                ambianceSonoreActuel.stop();
            }

            ambianceSonoreActuel = ChargeurRessources.ensembleSonJeu.get(sonActuel);
            ambianceSonoreActuel.setVolume(0.05);

            ambianceSonoreActuel.play();
        }

    }

    public void stopperSon() {
        ambianceSonoreActuel.stop();
    }
}
