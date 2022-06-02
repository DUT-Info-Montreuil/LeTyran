package application.controleur;

import application.modele.Environnement;
import application.vue.EtabliVue;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Iterator;

public class EtabliControleur {

    private Pane root;
    private Environnement env;
    private EtabliVue etabliVue;
    private VBox vBoxArmes;
    Button boutonFabriquer;

    public EtabliControleur(Pane root, Environnement env, EtabliVue etabliVue) {
        this.root = root;
        this.env = env;
        this.etabliVue = etabliVue;
        vBoxArmes = (VBox) ((ScrollPane) etabliVue.getbPaneEtabli().lookup("#sPArmes")).getContent();
        boutonFabriquer = (Button) etabliVue.getbPaneEtabli().lookup("#VboxFabriquer").lookup("#boutonFabriquer");

        root.lookup("#spriteEtabli").setOnMouseClicked(mouseEvent -> {
            fabricable();
            etabliVue.affichageEtabli();
            env.getPersonnage().freezer();
        });

        //pour afficher les infos d'une arme lorsque cliquée
        for (int i = 1; i < vBoxArmes.getChildren().size(); i++) {
            vBoxArmes.getChildren().get(i).setOnMouseClicked(mouseEvent -> {
                etabliVue.affichageArmeSelected(Color.BLACK);
                env.getEtabli().setArmeSelected(((HBox)mouseEvent.getSource()).getId());
                etabliVue.affichageArmeSelected(Color.WHITE);
                etabliVue.affichageInfosArmeSelected();
                fabricable();
            });
        }

        //pour lancer la fabrication
        boutonFabriquer.setOnAction(actionEvent -> {
            env.getEtabli().fabriquer();
            fabricable();
            root.requestFocus();
        });

        ((Button) etabliVue.getbPaneEtabli().lookup("#boutonFermer")).setOnAction(actionEvent -> {
            etabliVue.affichageEtabli();
            env.getPersonnage().freezer();
            root.requestFocus();
        });


        Iterator iterator = env.getEtabli().getListeMateriauxArmesID().iterator();
        String idArme;
        do {
            idArme = String.valueOf(iterator.hasNext());
            if (idArme.charAt(idArme.length()-1) < (char) env.getEtabli().getNiveau()) {
                etabliVue.affichageArmeDispo(0.5);
            }
        } while (iterator.hasNext());



        //simule un clique pour l'initialisation
        vBoxArmes.lookup("#" + env.getEtabli().getArmeSelected()).fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                true, true, true, true, true, true, null));
    }

    private void fabricable() {
        if (env.getEtabli().peutFabriquer()) {
            boutonFabriquer.setDisable(false);
            etabliVue.affichageBouton(1);
        } else {
            boutonFabriquer.setDisable(true);
            etabliVue.affichageBouton(0.5);
        }
    }

    public void amelioration() {
        Iterator iterator = env.getEtabli().getListeMateriauxArmesID().iterator();
        String idArme;
        do {
            idArme = String.valueOf(iterator.hasNext());
            if (idArme.charAt(idArme.length()-1) == (char) env.getEtabli().getNiveau()) {
                etabliVue.affichageArmeDispo(1);
            }
        } while (iterator.hasNext());

    }
}