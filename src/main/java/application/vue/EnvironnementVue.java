package application.vue;
import application.controleur.listeners.PersonnageListeners;
import application.modele.ChargementTileMap;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.personnages.allies.Allie;
import application.modele.personnages.animaux.Animal;
import application.modele.personnages.ennemi.Ennemi;
import application.vue.environnement.ImageViewEnv;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import static application.modele.MapJeu.*;

public class EnvironnementVue {

    private Environnement env;
    private Pane root;
    private TilePane tileSol;
    private TilePane tileDecors;
    private TilePane tileFond;
    private TilePane tileFondDecor;

    private AudioClip bruitCoffre = new AudioClip(getClass().getResource("/application/sons/coffreBruit.mp3").toExternalForm());

    public EnvironnementVue(Environnement env, Pane root, TilePane tileSol, TilePane tileDecors, TilePane tileFond, TilePane tileFondDecor) {
        this.env = env;
        this.root = root;
        this.tileSol = tileSol;
        this.tileDecors = tileDecors;
        this.tileFond = tileFond;
        this.tileFondDecor = tileFondDecor;

        construireMap();
        construireDecor();
        construireFond();

        for (Ennemi ennemi : env.getListeEnnemis())
            new PersonnageListeners(ennemi, new PersonnageVue(((Pane) root.lookup("#panePNJ")), ennemi), new ArmeVue(((Pane) root.lookup("#panePNJ")), ennemi));
        for (Animal animal : env.getListeAnimaux())
            new PersonnageListeners(animal, new PersonnageVue((Pane) root.lookup("#panePNJ"), animal));
        for (Allie allie : env.getListeAllies())
            new PersonnageListeners(allie, new PersonnageVue((Pane) root.lookup("#panePNJ"), allie));

    }

    private void construireMap() {

        int width = env.getMapJeu().getTabMap()[0].length;
        int heigth = env.getMapJeu().getTabMap().length;

        this.tileSol.setPrefSize(env.getMapJeu().getTabMap()[0].length * TUILE_TAILLE, env.getMapJeu().getTabMap().length * TUILE_TAILLE);
        ChargeurRessources.charger();
        ImageView img;
        for (int i = 0; i < heigth; i++) {
            for (int j = 0; j < width; j++) {
                int indexImg = env.getMapJeu().getTabMap()[i][j];
                //int id = i * 150 + j;
                ImageViewEnv imgtest = new ImageViewEnv(indexImg, i, j);

                //img = new ImageView(new Image("file:src/main/resources/application/pack1/Pierre.png"));
                tileSol.getChildren().add(imgtest);
            }
        }
    }

    private void construireDecor() {

        this.tileFondDecor.setPrefSize(env.getMapJeu().getTabMap()[0].length * TUILE_TAILLE, env.getMapJeu().getTabMap().length * TUILE_TAILLE);

        int [][]tile = ChargementTileMap.recupererTileMap(0);

        for(int i = 0; i < tile.length; i++) {
            for(int j = 0; j < tile[0].length; j++) {
                tileFondDecor.getChildren().add(new ImageViewEnv(tile[i][j], i, j));
            }
        }

    }

    private void construireFond() {
        this.tileFond.setPrefSize(env.getMapJeu().getTabMap()[0].length * TUILE_TAILLE, env.getMapJeu().getTabMap().length * TUILE_TAILLE);
        tileFond.setBackground(Background.fill(Color.LIGHTBLUE));
    }

    public void supprimerBloc(Entite ent) {
        boolean trouvee = false;
        int i = 0;

        while(i < tileSol.getChildren().size() && !trouvee) {
            ImageViewEnv entView = (ImageViewEnv) tileSol.getChildren().get(i);
            //entView.setCustomView(ent.getClass().getSimpleName());
            if(entView.getBlocX() == (int)(ent.getX() / 32) && entView.getBlocY() == (int)(ent.getY()/32)) {

                entView.setCustomView("Vide");
                trouvee = true;
            }
            i++;
        }
    }

    public void ajouterBloc(int id, Entite ent) {

        boolean trouvee = false;
        int i = 0;

        while(i < tileSol.getChildren().size() && !trouvee) {
            ImageViewEnv entView = (ImageViewEnv) tileSol.getChildren().get(i);
            //entView.setCustomView(ent.getClass().getSimpleName());
            if(entView.getBlocX() == (int)(ent.getX() / 32) && entView.getBlocY() == (int)(ent.getY()/32)) {
                System.out.println(id);

                entView.setCustomView(ent.getClass().getSimpleName());
                trouvee = true;
            }
            i++;
        }

        //Temporaire

    }

    public void supprimerArbre(int id) {
        ImageView img;
        for (int i = 0; i < 3; i++) {
            img = (ImageView) tileSol.getChildren().get(id - i * WIDTH);
            img.setImage(new Image("file:src/main/resources/application/pack1/tile_transparent.png"));
        }
    }

    public void supprimerPNJ(String id) {
        ((Pane) root.lookup("#panePNJ")).getChildren().remove(root.lookup("#" + id));
        ((Pane) root.lookup("#panePNJ")).getChildren().remove(root.lookup("#" + id + "Arme"));
    }

    public void changerImgCoffre(int id) {
        ((ImageView) tileSol.getChildren().get(id)).setImage(new Image("file:src/main/resources/application/Coffre/CoffreOrOuv.png"));
        bruitCoffre.play();
    }

    public void supprimerProjectile(String id) {
        ((Pane) root.lookup("#panePNJ")).getChildren().remove(root.lookup("#" + id));
    }

    public Pane getRoot() {
        return root;
    }
}
