package application.controleur;

import application.controleur.listeners.PersonnageListeners;
import application.modele.Environnement;
import application.modele.projectiles.BouleDeFeu;
import application.modele.projectiles.Fleche;
import application.modele.objets.Arbre;
import application.modele.objets.Coffre;
import application.modele.objets.Materiau;
import application.modele.personnages.Personnage;
import application.modele.personnages.animaux.Animal;
import application.modele.projectiles.Projectile;
import application.vue.*;
import javafx.collections.ListChangeListener;
import javafx.scene.layout.Pane;

import static application.modele.MapJeu.*;

public class EnvironnementControleur {

    private Controleur controleur;
    public EnvironnementControleur(Pane root, EnvironnementVue envVue, Environnement env, Controleur controleur) {
        this.controleur = controleur;
        ((Pane) root.lookup("#paneEnnemis")).setPrefSize(WIDTH * TUILE_TAILLE, HEIGHT * TUILE_TAILLE);

        root.lookup("#tileSol").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((TUILE_TAILLE * WIDTH)) / 2));
        root.lookup("#tileSol").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((TUILE_TAILLE * HEIGHT)) / 2));


        root.lookup("#tileFondDecor").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((TUILE_TAILLE * WIDTH)) / 2));
        root.lookup("#tileFondDecor").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((TUILE_TAILLE * HEIGHT)) / 2));


        root.lookup("#paneDecors").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((TUILE_TAILLE * WIDTH)) /2));
        root.lookup("#paneDecors").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((TUILE_TAILLE * HEIGHT)) / 2));
        root.lookup("#paneEnnemis").translateXProperty().bind(env.getJoueur().getXProperty().multiply(-1).add(((TUILE_TAILLE * WIDTH)) / 2));
        root.lookup("#paneEnnemis").translateYProperty().bind(env.getJoueur().getYProperty().multiply(-1).add(((TUILE_TAILLE * HEIGHT)) / 2));

        env.getListeMateriaux().addListener(new ListChangeListener<Materiau>() {
            @Override
            public void onChanged(Change<? extends Materiau> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++) {
                    //int id = (int)((change.getRemoved().get(0).getY() / env.getMapJeu().getWidth()) *  + (change.getRemoved().get(0).getX() / TUILE_TAILLE));
                    //System.out.println((int) change.getRemoved().get(i).getY() * WIDTH + (int) change.getRemoved().get(0).getX());
                    envVue.supprimerBloc(change.getRemoved().get(0));
                }

                for(int i = 0; i < change.getAddedSize(); i++) {
                    Materiau ent = (Materiau) change.getAddedSubList().get(i);
                    System.out.println(ent.getX());
                    int x = (int)ent.getX() / TUILE_TAILLE;
                    int y = (int)ent.getY() / TUILE_TAILLE;

                    env.getMapJeu().getTabMap()[y][x] = 183;
                    envVue.ajouterBloc(ent.getId(), ent);
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

        env.getListeEnnemis().addListener(new ListChangeListener<Personnage>() {
            @Override
            public void onChanged(Change<? extends Personnage> change) {
                change.next();
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerPNJ(change.getRemoved().get(i).getId());

                for (int i = 0; i < change.getAddedSize(); i++) {
                    Personnage perso = change.getAddedSubList().get(i);
                    new PersonnageListeners(perso, new PersonnageVue((Pane) root.lookup("#paneEnnemis"), perso), new ArmeVue((Pane) root.lookup("#paneEnnemis"), perso));
                    //System.out.println(perso);
                }
            }

        });

            //On a qu'un seul villageois
            env.getListeAllies().get(0).getInteractionProperty().addListener(e -> {

                System.out.println(env.getListeAllies().get(0).getInteractionAvancement());
                if (env.getListeAllies().get(0).getInteractionAvancement() == 1) {
                    controleur.getDialogueControleur().debutDialogue();
                }
             });
        /*env.getListeAllies().addListener(new ListChangeListener<Personnage>() {
            @Override
            public void onChanged(Change<? extends Personnage> change) {
                System.out.println(change);
                for (int i = 0; i < change.getAddedSize(); i++) {
                    Allie allie = (Allie) change.getAddedSubList().get(i);
                    System.out.println(allie);
                    allie.getInteractionProperty().addListener(e -> {
                        System.out.println("ok");
                        if (allie.getInteractionAvancement() == 1) {
                            controleur.getDialogueControleur().lancerDialogue();
                        }
                    });
                }
            }
        });*/

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

        env.getListeProjectiles().addListener(new ListChangeListener<Projectile>() {
            @Override
            public void onChanged(Change<? extends Projectile> change) {
                change.next();
                for (int i = 0; i < change.getAddedSize(); i++)
                    if (change.getAddedSubList().get(i) instanceof Fleche)
                        new FlecheVue((Pane) root.lookup("#paneEnnemis"), (Fleche) change.getAddedSubList().get(i));
                    else {
                        BouleDeFeuVue bouleDeFeuVue = new BouleDeFeuVue((Pane) root.lookup("#paneEnnemis"), (BouleDeFeu) change.getAddedSubList().get(i));
                        ((BouleDeFeu) change.getAddedSubList().get(i)).getChuteProperty().addListener(observable -> bouleDeFeuVue.chute());
                    }
                for (int i = 0; i < change.getRemovedSize(); i++)
                    envVue.supprimerProjectile(change.getRemoved().get(i).getId());
            }
        });
    }
}
