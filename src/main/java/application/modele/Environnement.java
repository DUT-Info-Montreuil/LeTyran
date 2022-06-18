package application.modele;

import application.modele.armes.arc.Fleche;
import application.modele.objets.*;
import application.modele.personnages.*;
import application.modele.personnages.allies.Allie;
import application.modele.personnages.allies.ChefVillage;
import application.modele.personnages.animaux.Animal;
import application.modele.personnages.animaux.Lapin;
import application.modele.personnages.ennemi.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;

import static application.modele.MapJeu.TUILE_TAILLE;
import static application.modele.MapJeu.WIDTH;

public class Environnement {

    private BooleanProperty pauseProperty;
    private Joueur joueur;
    private MapJeu mapJeu;
    private Etabli etabli;
    private FeuDeCamp feuDeCamp;
    private HashMap<String, ObservableList> hashMapListes;

    public Environnement() {
        pauseProperty = new SimpleBooleanProperty(false);
        hashMapListes = new HashMap<>() {{
            put("listeEntites", FXCollections.observableArrayList());
            put("listeMateriaux", FXCollections.observableArrayList());
            put("listeArbres", FXCollections.observableArrayList());
            put("listeCoffres", FXCollections.observableArrayList());
            put("listeEnnemis", FXCollections.observableArrayList());
            put("listeAnimaux", FXCollections.observableArrayList());
            put("listeFleches", FXCollections.observableArrayList());
            put("listeAllies", FXCollections.observableArrayList());
        }};
        joueur = new Joueur(this);

        mapJeu = new MapJeu();
        etabli = new Etabli(this);
        feuDeCamp = new FeuDeCamp(this, 13, 8);

        ObjetJeu nouvObj = new ObjetJeu(this, "Epee", 1);
        ObjetJeu nouvObj2 = new ObjetJeu(this,  "Bois", 1);
        nouvObj.setX(2 * 32);
        nouvObj.setY(4 * 32);
        nouvObj2.setX(2 * 32);
        nouvObj2.setY(2 * 32);
        /*this.getListeEntites().add(nouvObj);
        this.getListeEntites().add(nouvObj2);*/
        getListeEntites().add(joueur);

        initListeMinerais();
        initListeArbres();
        initListeCoffres();
        initListeEnnemis();
        initListeAnimaux();
        initListeAllies();
    }

    //region init
    private void initListeArbres() {
        for (int i = 0; i < mapJeu.getHeight(); i++) {
            for (int j = 0; j < mapJeu.getWidth(); j++) {
                if (mapJeu.getTabMap()[i][j] == 55) {
                    getListeArbres().add(new Arbre(this, j, i));
                }
            }
        }
    }

    private void initListeMinerais() {
        for (int i = 0; i < mapJeu.getHeight(); i++) {
            for (int j = 0; j < mapJeu.getWidth(); j++) {
                int id = mapJeu.getTabMap()[i][j];
                //switch (mapJeu.getTabMap()[i][j]) {
                    if(id != 0) {
                        getListeMateriaux().add(new Terre(this, j, i));
                    }
                    /*case 183: getListeMateriaux().add(new Terre(this, j, i)); break;
                    case 167: getListeMateriaux().add(new Terre(this, j, i)); break;
                    case 88: getListeMateriaux().add(new Terre(this, j, i)); break;
                    case 52: getListeMateriaux().add(new Pierre(this, j, i)); break;
                    case 53: getListeMateriaux().add(new Fer(this, j, i)); break;
                    case 54: getListeMateriaux().add(new Platine(this, j, i)); break;*/
                //}
            }
        }
    }

    public void initListeEnnemis() {
        getListeEnnemis().clear();
        getListeEnnemis().add(new Archer(this, 1, 20, 11, 0));
        getListeEnnemis().add(new Lancier(this, 1, 15, 11, 0));
        getListeEnnemis().add(new Epeiste(this,1, 20, 0, 5));
        getListeEnnemis().add(new Epeiste(this,1, 18, 18, 10));
    }

    private void initListeCoffres() {
        for (int i = 0; i < mapJeu.getHeight(); i++) {
            for (int j = 0; j < mapJeu.getWidth(); j++) {
                if (mapJeu.getTabMap()[i][j] == 58) {
                    getListeCoffres().add(new Coffre(this, j, i));
                }
            }
        }
    }

    public void initListeAnimaux() {
        getListeAnimaux().clear();
        getListeAnimaux().add(new Lapin(this, 5,39, 15));
    }

    public void initListeAllies() {
        getListeAllies().clear();

        getListeAllies().add(new ChefVillage(this, 0,11, 11 * 32));
    }

    public boolean pauser() {
        this.pauseProperty.setValue(!pauseProperty.getValue());
        return pauseProperty.getValue();
    }

    public Materiau getMinerai(int x, int y) {
        x *= TUILE_TAILLE;
        y *= TUILE_TAILLE;
        for (Materiau minerai : getListeMateriaux()) {
            if (minerai.getX() == x && minerai.getY() == y)
                return minerai;
        }
        return null;

    }

    public Arbre getArbre(int x, int y) {
        if (mapJeu.getTabMap()[y][x] == 56) y++;
        else if (mapJeu.getTabMap()[y][x] == 57) y+=2;

        for (Arbre arbre : getListeArbres())
            if (arbre.getX() == x && arbre.getY() == y)
                return arbre;
        return null;
    }

    public Ennemi getEnnemi(int x, int y) {
        for (Ennemi ennemi : getListeEnnemis()) {
            if (Math.abs(ennemi.getX() / TUILE_TAILLE - x) < 1 && (int) ennemi.getY() / TUILE_TAILLE == y)
                return ennemi;
        }
        return null;
    }

    public Coffre getCoffre(int x, int y) {
        for (Coffre coffre : getListeCoffres())
            if (coffre.getX() == x && coffre.getY() == y)
                return coffre;
        return null;
    }

    public void update() {
        for(int i = 0; i < this.getListeEntites().size(); i++) {
            Entite obj = this.getListeEntites().get(i);
            obj.update();

            //appliquerGravite();
        }

        for (int i = 0; i < getListeEnnemis().size(); i++)
            getListeEnnemis().get(i).update();

        for (int i = 0; i < getListeAnimaux().size(); i++)
            getListeAnimaux().get(i).update();

        for (int i = 0; i < getListeFleches().size(); i++)
            getListeFleches().get(i).update();
    }

    /*public void appliquerGravite() {
        for(int i = 0; i < this.getListeEntites().size(); i++) {
            Entite ent = this.getListeEntites().get(i);
            if(!ent.getIgnoreGravite()) {
                Entite collisionEntite = ent.getCollider().tracerLigne(0, 32);
                if(collisionEntite == null) {
                    ent.gravite();
                }
            };
        }
    }*/

    //region Getter & Setter

    public HashMap<String, ObservableList> getHashMapListes() {
        return hashMapListes;
    }

    public ObservableList<Entite> getListeEntites() {
        return hashMapListes.get("listeEntites");
    }

    public ObservableList<Materiau> getListeMateriaux() {
        return hashMapListes.get("listeMateriaux");
    }

    public ObservableList<Arbre> getListeArbres() {
        return hashMapListes.get("listeArbres");
    }

    public ObservableList<Ennemi> getListeEnnemis() {
        return hashMapListes.get("listeEnnemis");
    }

    public ObservableList<Animal> getListeAnimaux() {
        return hashMapListes.get("listeAnimaux");
    }

    public ObservableList<Allie> getListeAllies() {
        return hashMapListes.get("listeAllies");
    }

    public ObservableList<Coffre> getListeCoffres() {
        return hashMapListes.get("listeCoffres");
    }

    public ObservableList<Fleche> getListeFleches() {
        return hashMapListes.get("listeFleches");
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public MapJeu getMapJeu() {
        return mapJeu;
    }

    public Etabli getEtabli() {
        return etabli;
    }

    public FeuDeCamp getFeuDeCamp() {
        return feuDeCamp;
    }

    public final boolean getPause() {
        return pauseProperty.getValue();
    }

    //endregion

}
