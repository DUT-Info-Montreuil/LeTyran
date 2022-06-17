package application.controleur;

import application.controleur.listeners.PersonnageListeners;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.armes.arc.Fleche;
import application.modele.objets.Arbre;
import application.modele.objets.Coffre;
import application.modele.objets.Materiau;
import application.modele.personnages.animaux.Animal;
import application.modele.personnages.ennemi.Ennemi;
import application.vue.ArmeVue;
import application.vue.EnvironnementVue;
import application.vue.FlecheVue;
import application.vue.PersonnageVue;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;

import static application.modele.MapJeu.*;

public class EnvironnementControleur {

    public EnvironnementControleur(Pane root, EnvironnementVue envVue, Environnement env) {

        ((Pane) root.lookup("#paneEnnemis")).setPrefSize(WIDTH * TUILE_TAILLE, HEIGHT * TUILE_TAILLE);

        root.lookup("#tileSol").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((TUILE_TAILLE * WIDTH)) / 2));
        root.lookup("#tileSol").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((TUILE_TAILLE * HEIGHT)) / 2));
        root.lookup("#paneDecors").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((TUILE_TAILLE * WIDTH)) /2));
        root.lookup("#paneDecors").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((TUILE_TAILLE * HEIGHT)) / 2));
        root.lookup("#paneEnnemis").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((TUILE_TAILLE * WIDTH)) / 2));
        root.lookup("#paneEnnemis").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((TUILE_TAILLE * HEIGHT)) / 2));

        env.getListeMateriaux().addListener(new ListChangeListener<Materiau>() {
            @Override
            public void onChanged(Change<? extends Materiau> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++) {
                    int id = (int)((change.getRemoved().get(0).getY() / TUILE_TAILLE) * WIDTH + (change.getRemoved().get(0).getX() / TUILE_TAILLE));
                    //System.out.println((int) change.getRemoved().get(i).getY() * WIDTH + (int) change.getRemoved().get(0).getX());
                    envVue.supprimerBloc(id);
                }

                for(int i = 0; i < change.getAddedSize(); i++) {
                    Entite ent = change.getAddedSubList().get(i);
                    int x = (int)ent.getX() / TUILE_TAILLE;
                    int y = (int)ent.getY() / TUILE_TAILLE;

                    env.getMapJeu().getTabMap()[y][x] = 34;
                    envVue.ajouterBloc((int)ent.getY()/TUILE_TAILLE * WIDTH + (int)ent.getX()/TUILE_TAILLE, ent);
                }
            }
        });

        env.getListeArbres().addListener(new ListChangeListener<Arbre>() {
            @Override
            public void onChanged(Change<? extends Arbre> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerArbre((int)change.getRemoved().get(i).getY() * WIDTH + (int)change.getRemoved().get(0).getX());
            }
        });

        env.getListeEnnemis().addListener(new ListChangeListener<Ennemi>() {
            @Override
            public void onChanged(Change<? extends Ennemi> change) {
               change.next();
               for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerPNJ(change.getRemoved().get(i).getId());

                for (int i = 0; i < change.getAddedSize(); i++)
                    new PersonnageListeners(change.getAddedSubList().get(i), new PersonnageVue((Pane) root.lookup("#paneEnnemis"), change.getAddedSubList().get(i)), new ArmeVue((Pane) root.lookup("#paneEnnemis"),  change.getAddedSubList().get(i)));
            }
        });

        env.getListeAnimaux().addListener(new ListChangeListener<Animal>() {
            @Override
            public void onChanged(Change<? extends Animal> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerPNJ(change.getRemoved().get(i).getId());

                for (int i = 0; i < change.getAddedSize(); i++)
                    new PersonnageListeners(change.getAddedSubList().get(i), new PersonnageVue((Pane) root.lookup("#paneEnnemis"), change.getAddedSubList().get(i)));
            }
        });

        env.getListeCoffres().addListener(new ListChangeListener<Coffre>() {
            @Override
            public void onChanged(Change<? extends Coffre> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.changerImgCoffre((int)change.getRemoved().get(i).getY() * WIDTH + (int)change.getRemoved().get(i).getX());
            }
        });

        env.getListeFleches().addListener(new ListChangeListener<Fleche>() {
            @Override
            public void onChanged(Change<? extends Fleche> change) {
                change.next();
                for (int i = 0; i < change.getAddedSize(); i++)
                    new FlecheVue(((Pane) root.lookup("#paneEnnemis")), change.getAddedSubList().get(i));
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerFleche(change.getRemoved().get(i).getId());
            }
        });
    }
}
