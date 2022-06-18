package application.controleur;

import application.controleur.listeners.ArmeListener;
import application.controleur.listeners.ArmureListener;
import application.controleur.listeners.PersonnageListeners;
import application.controleur.listeners.VieListener;
import application.modele.Environnement;
import application.vue.ArmeVue;
import application.vue.EtabliVue;
import application.vue.PersonnageVue;
import application.vue.VieVue;
import application.vue.EnvironnementVue;
import application.modele.ModeleDialogue;
import application.vue.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class Controleur implements Initializable {

    private Environnement env;
    private PersonnageVue personnageVue;
    private EnvironnementVue envVue;
    private ArmeVue armeVue;
    private VieVue vievue;
    private ObjetVue objetVue;
    private EtabliVue etabliVue;
    private FeuDeCampVue feuDeCampVue;
    private QueteControleur controleurQuete;
    private InventaireControleur inventaireControleur;
    private VueDialogue vueDialog;
    private ModeleDialogue modeleDialogue;

    private Timeline gameLoop;

    @FXML private Pane root;
    @FXML private TilePane tileSol;
    @FXML private TilePane tileDecors;
    @FXML private TilePane tileFond;
    @FXML private TilePane tileFondDecor;
    @FXML private ImageView spriteJoueur;
    @FXML private ImageView spriteArme;
    @FXML private BorderPane bPaneEtabli;
    @FXML private ImageView spriteEtabli;
    @FXML private Label labelMort;
    @FXML private ImageView spriteFeuDeCamp;

    @FXML private Pane paneDecors;
    @FXML private Pane paneEnnemis;
    @FXML private Pane inventaireMain;
    @FXML private Pane inventaireSac;
    @FXML private Pane inventaireEquipement;

    @FXML private Text texteDialogue;
    @FXML private TextFlow dialogFlow;
    @FXML private TextFlow conteneurQuetes;

    @FXML private VBox vBoxPause;
    @FXML private VBox vBoxSuicide;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.controleurQuete = new QueteControleur(this.root.getScene(), conteneurQuetes);
        env = new Environnement();
        modeleDialogue = new ModeleDialogue();


        personnageVue = new PersonnageVue(env.getJoueur(), spriteJoueur);
        envVue = new EnvironnementVue(env, root, tileSol, tileDecors, tileFond, tileFondDecor);
        objetVue = new ObjetVue(this.env, paneDecors);
        armeVue = new ArmeVue(env.getJoueur(), spriteArme);
        vievue = new VieVue(root);
        etabliVue = new EtabliVue(env.getEtabli(), spriteEtabli, bPaneEtabli);
        feuDeCampVue = new FeuDeCampVue(env.getFeuDeCamp(), spriteFeuDeCamp, labelMort);
        vueDialog = new VueDialogue(modeleDialogue, dialogFlow,  texteDialogue);

//        this.ennemie= new Ennemie(env, 500, 350);
//        this.ennemieVue= new EnnemieVue(root,tileSol,ennemie);
//        this.ennemiControleur= new EnnemiControleur(root,env, tileSol,ennemie,this.ennemieVue);
        this.inventaireControleur = new InventaireControleur(root, controleurQuete, env, inventaireMain, inventaireSac, inventaireEquipement);

        root.addEventHandler(KeyEvent.KEY_PRESSED, new KeyPressed(env));
        root.addEventHandler(KeyEvent.KEY_RELEASED, new KeyReleased(this, env));
        root.addEventHandler(Event.ANY, inventaireControleur);
        root.addEventHandler(MouseEvent.MOUSE_PRESSED, new MousePressed(this, env));
        root.addEventHandler(Event.ANY, new DialogueControleur(vueDialog, modeleDialogue));
        root.addEventHandler(KeyEvent.KEY_PRESSED, new PauseControleur(env, root, vBoxPause, vBoxSuicide));

        env.getJoueur().getInventaire().getArmeProperty().addListener(new ArmeListener(armeVue, inventaireControleur.getInvVue()));
        env.getJoueur().getInventaire().getArmureProperty().addListener(new ArmureListener(inventaireControleur.getInvVue()));
        this.env.getJoueur().getPVProperty().addListener(new VieListener(vievue, this.env.getJoueur()));

        new EtabliControleur(root,env, etabliVue);
        new PersonnageListeners(env.getJoueur(), personnageVue, armeVue, feuDeCampVue);
        new EnvironnementControleur(root, envVue, env);



        initAnimation();
        gameLoop.play();
    }

    private void initAnimation() {
        gameLoop = new Timeline();
        gameLoop.setCycleCount(Timeline.INDEFINITE);

        KeyFrame kf = new KeyFrame(
                // on définit le FPS (nbre de frame par seconde)
                Duration.seconds(0.017),
                (ev ->{
                    if (!env.getPause()) {
                        env.getJoueur().update();
                        if (!env.getJoueur().getMort() && !env.getJoueur().getSeRepose()) {
                            objetVue.update();
                            vueDialog.animer(0.017);
                            this.env.update();
                        }
                    }
                })
        );
        gameLoop.getKeyFrames().add(kf);
    }

    public ArmeVue getArmeVue() {
        return armeVue;
    }

    public InventaireControleur getInventaireControleur() {
        return this.inventaireControleur;
    }
}