package application.modele;

import application.modele.armes.arc.Fleche;
import application.modele.objets.*;
import application.modele.objets.Materiau;
import application.modele.objets.materiaux.Terre;
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
    private ObservableList<Entite> listeEntites;
    private ObservableList<Materiau> listeMateriaux;
    private ObservableList<Arbre> listeArbres;
    private ObservableList<Coffre> listeCoffres;
    private ObservableList<Ennemi> listeEnnemis;
    private ObservableList<Animal> listeAnimaux;
    private ObservableList<Allie> listeAllies;
    private ObservableList<Fleche> listeFleches;

    public Environnement() {
        pauseProperty = new SimpleBooleanProperty(false);
        joueur = new Joueur(this);

        mapJeu = new MapJeu();
        etabli = new Etabli(this);
        feuDeCamp = new FeuDeCamp(this, 13, 8);

        listeEntites = FXCollections.observableArrayList();
        listeEntites.add(joueur);

        initListeMateriaux();
        initListeArbres();
        initListeCoffres();
        listeEnnemis = FXCollections.observableArrayList();
        initListeEnnemis();
        listeAnimaux = FXCollections.observableArrayList();
        initListeAnimaux();
        listeAllies = FXCollections.observableArrayList();
        initListeAllies();
        listeFleches = FXCollections.observableArrayList();
    }

    //region init
    private void initListeArbres() {
        listeArbres = FXCollections.observableArrayList();
        for (int i = 0; i < mapJeu.getHeight(); i++) {
            for (int j = 0; j < mapJeu.getWidth(); j++) {
                if (mapJeu.getTabMap()[i][j] == 55) {
                    listeArbres.add(new Arbre(this, j, i));
                }
            }
        }
    }

    private void initListeMateriaux() {
        listeMateriaux = FXCollections.observableArrayList();
        for (int i = 0; i < mapJeu.getHeight(); i++) {
            for (int j = 0; j < mapJeu.getWidth(); j++) {
                int id = mapJeu.getTabMap()[i][j];
                //switch (mapJeu.getTabMap()[i][j]) {
                    if(id != 0) {
                        listeMateriaux.add(new Terre(this, j, i));
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
        listeEnnemis.clear();
        listeEnnemis.add(new Archer(this, 1, 20, 11, 0));
        listeEnnemis.add(new Lancier(this, 1, 15, 11, 0));
        listeEnnemis.add(new Epeiste(this,1, 20, 0, 5));
        listeEnnemis.add(new Epeiste(this,1, 18, 18, 10));
    }

    private void initListeCoffres() {
        listeCoffres = FXCollections.observableArrayList();
        for (int i = 0; i < mapJeu.getHeight(); i++) {
            for (int j = 0; j < mapJeu.getWidth(); j++) {
                if (mapJeu.getTabMap()[i][j] == 58) {
                    listeCoffres.add(new Coffre(this, j, i));
                }
            }
        }
    }

    public void initListeAnimaux() {
//        listeAnimaux.clear();
//        listeAnimaux.add(new Lapin(this, 15,11, 10));
//        listeAnimaux.add(new Sanglier(this, 15,11, 8));
    }

    public void initListeAllies() {
        listeAllies.clear();
        listeAllies.add(new ChefVillage(this, 5,30, 11));
    }
            //endregion

    public boolean pauser() {
        this.pauseProperty.setValue(!pauseProperty.getValue());
        return pauseProperty.getValue();
    }

    public Materiau getMinerai(int x, int y) {
        for (Materiau minerai : getListeMateriaux()) {
            if (minerai.getX() == x * TUILE_TAILLE && minerai.getY() == y * TUILE_TAILLE)
                return minerai;
        }
        return null;

    }

    public Arbre getArbre(int x, int y) {
        if (mapJeu.getTabMap()[y][x] == 56) y++;
        else if (mapJeu.getTabMap()[y][x] == 57) y+=2;

        for (Arbre arbre : listeArbres)
            if (arbre.getX() == x && arbre.getY() == y)
                return arbre;
        return null;
    }

    public Ennemi getEnnemi(int x, int y) {
        for (Ennemi ennemi : listeEnnemis) {
            if (Math.abs(ennemi.getX() / TUILE_TAILLE - x) < 1 && Math.abs(ennemi.getY() / TUILE_TAILLE - y) < 1)
                return ennemi;
        }
        return null;
    }

    public Animal getAnimal(int x, int y) {
        for (Animal animal : listeAnimaux) {
            if (Math.abs(animal.getX() / TUILE_TAILLE - x) < 1 && Math.abs(animal.getY() / TUILE_TAILLE - y) < 1)
                return animal;
        }
        return null;
    }

    public Coffre getCoffre(int x, int y) {
        for (Coffre coffre : listeCoffres)
            if (coffre.getX() == x && coffre.getY() == y)
                return coffre;
        return null;
    }

    public void update() {
        for(int i = 0; i < this.listeEntites.size(); i++)
            this.listeEntites.get(i).update();

        for (int i = 0; i < listeEnnemis.size(); i++)
            listeEnnemis.get(i).update();

        for (int i = 0; i < listeAnimaux.size(); i++)
            listeAnimaux.get(i).update();

        for (int i = 0; i < listeAllies.size(); i++)
            listeAllies.get(i).update();

        for (int i = 0; i < listeFleches.size(); i++)
            listeFleches.get(i).update();
    }

    //region Getter & Setter


    public ObservableList<Entite> getListeEntites() {
        return listeEntites;
    }

    public ObservableList<Materiau> getListeMateriaux() {
        return listeMateriaux;
    }

    public ObservableList<Arbre> getListeArbres() {
        return listeArbres;
    }

    public ObservableList<Coffre> getListeCoffres() {
        return listeCoffres;
    }

    public ObservableList<Ennemi> getListeEnnemis() {
        return listeEnnemis;
    }

    public ObservableList<Animal> getListeAnimaux() {
        return listeAnimaux;
    }

    public ObservableList<Allie> getListeAllies() {
        return listeAllies;
    }

    public ObservableList<Fleche> getListeFleches() {
        return listeFleches;
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
