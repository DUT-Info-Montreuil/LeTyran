package application.controleur;

import application.modele.Ennemie;
import application.modele.Entite;
import application.modele.Environnement;
import application.modele.objets.Bois;
import application.vue.EnnemiVue;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.media.AudioClip;

import static application.modele.MapJeu.TUILE_TAILLE;

public class EnnemiControleur{

    private Pane root;
    private Environnement env;
    private TilePane tileSol;
    private Ennemie ennemie;


    private EnnemiVue ennemieVue;
    private AudioClip sound = new AudioClip(getClass().getResource("/application/sons/bruitLoorr.mp3").toExternalForm());
    private AudioClip sound1 = new AudioClip(getClass().getResource("/application/sons/zip.mp3").toExternalForm());

    public EnnemiControleur(Pane root, Environnement env, TilePane tilesol, Ennemie ennemie, EnnemiVue ennemieVue) {
        this.root = root;
        this.tileSol=tilesol;
        this.env=env;
        this.ennemie= ennemie;
        this.ennemieVue= ennemieVue;

        this.ennemieVue.getImage().setOnMouseClicked(mouseEvent ->{
            if((this.ennemie.getX() <= this.env.getJoueur().getX()+2*TUILE_TAILLE && this.ennemie.getX() >= this.env.getJoueur().getX()-TUILE_TAILLE && this.ennemie.getY() <= this.env.getJoueur().getY()+2*TUILE_TAILLE && this.ennemie.getY() >= this.env.getJoueur().getY()-TUILE_TAILLE)) {
                this.prendreLoot();
                this.ennemieVue.supprimerCadavre();
            }
        });
         }

        public void prendreLoot(){
            Entite bois= new Bois(this.env,0,0);
            this.env.getJoueur().getInventaire().ajouterObjet(bois);
            //On baisse le son de l'audio
            sound.setVolume(15. / 30.);
            sound.play();
            sound1.setVolume(15. / 30.);
            sound1.play();

        }



}