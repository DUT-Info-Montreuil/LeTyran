package application.controleur;

import application.modele.ModeleDialogue;
import application.vue.VueDialogue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class DialogueControleur implements EventHandler<Event> {

    private VueDialogue vueDialogue;
    private ModeleDialogue modDialog;
    private int nombreClick = 0;

    public DialogueControleur(VueDialogue vueDialogue, ModeleDialogue modeleDialogue) {
        this.vueDialogue = vueDialogue;
        this.modDialog = modeleDialogue;
    }

    @Override
    public void handle(Event event) {

        if(event instanceof MouseEvent && event.getEventType() == MouseEvent.MOUSE_CLICKED) {
            if ( ((MouseEvent) event).getButton() == MouseButton.PRIMARY) {
                nombreClick++;


                if (nombreClick == 1) {
                    this.vueDialogue.afficherFin();
                } else if (nombreClick == 2 && !this.modDialog.dernierePartie()) {
                    this.vueDialogue.prochainePartie();
                    this.modDialog.avancerPartie();
                    nombreClick = 0;
                } else if (nombreClick == 3 || nombreClick == 2 && this.modDialog.dernierePartie()) {
                    this.vueDialogue.fermer();
                }

            }
        } else if( event instanceof KeyEvent && event.getEventType() == KeyEvent.KEY_RELEASED) {
            if(((KeyEvent) event).getCode() == KeyCode.H ) {

                this.vueDialogue.afficher();
                if(this.vueDialogue.estAffiche()) {
                    lancerDialogue();
                }
            }
        }

    }

    public void debutDialogue()  {
        this.vueDialogue.afficher();
        if(this.vueDialogue.estAffiche()) {
            lancerDialogue();
        }
    }

    public void lancerDialogue() {
        this.modDialog.reinitialiserDialogue();
        nombreClick = 0;

    }
}
