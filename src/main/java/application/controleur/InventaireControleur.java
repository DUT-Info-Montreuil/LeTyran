package application.controleur;

import application.controleur.listeners.InventaireListener;
import application.modele.Inventaire;
import application.modele.Environnement;
import application.vue.inventaire.InventaireVue;
import application.vue.inventaire.InvItem;
import javafx.event.Event;
import javafx.event.EventHandler;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;

public class InventaireControleur implements EventHandler<Event> {

    private Pane root;
    private Environnement jeu;
    private Inventaire inv;
    private InventaireVue invVue;

    public InventaireControleur(Pane root, QueteControleur controleurQuete, Environnement jeu, Pane inventaireMain, Pane inventaireSac, Pane inventaireEquip) {
        this.root = root;
        this.jeu = jeu;

        this.inv = this.jeu.getJoueur().getInventaire();

        this.invVue = new InventaireVue(inv, this, inventaireMain, inventaireSac, inventaireEquip);

        //this.inv.getObjets().addListener(new ObjetInventaireChanger(this, controleurQuete));
        this.inv.getObjets().addListener(new InventaireListener(invVue, controleurQuete));

        inventaireEquip.getChildren().get(0).setPickOnBounds(true);
        inventaireEquip.getChildren().get(0).setOnMouseClicked(mouseEvent -> {
            inv.desequiperArmure();
        });

        inventaireEquip.getChildren().get(1).setOnMouseClicked(mouseEvent -> {
            inv.desequiperArme();
        });

    }

    public void gererEntreeClavier(KeyEvent keyvent) {
        if(keyvent.getCode() == KeyCode.I) {
            this.invVue.afficherInventaire();
        }
    }


    public void gererEntreeSouris(ScrollEvent scrollEvent) {
        if(scrollEvent.getDeltaY() > 0) {
            System.out.println("-1");
            this.inv.scrollObjetMain(-1);
        } else if(scrollEvent.getDeltaY() < 0) {
            System.out.println("+1");
            this.inv.scrollObjetMain(+1);

        }
    }

    @Override
    public void handle(Event event) {
        //System.out.println(event.);
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            gererEntreeClavier((KeyEvent) event);
        } else if(event.getEventType() == ScrollEvent.SCROLL) {
            gererEntreeSouris((ScrollEvent) event);
        }
    }


    public void objetPlaceInventaireChanger(InvItem objetInventaire, int anciennePlace, int nouvellePlace) {
        this.inv.libererPlacePrise(anciennePlace);
        this.inv.definirPlacePrise(nouvellePlace);

        objetInventaire.getObjetInventaire().setPlaceInventaire(nouvellePlace);
    }

    public void jeterObjet(InvItem vueObjet) {
        this.inv.lacherObjet(vueObjet.getObjetInventaire());

    }

    public void echangerObjet(InvItem premier, InvItem second, int nouvPlacePrem, int nouvPlaceSec) {
        int placeEchange = second.getObjetInventaire().getPlaceInventaire();


        //System.out.println(second.getParent());

        second.getObjetInventaire().setPlaceInventaire(premier.getObjetInventaire().getPlaceInventaire());
        premier.getObjetInventaire().setPlaceInventaire(placeEchange);


    }

    public InventaireVue getInvVue() {
        return invVue;
    }
}
