package application.modele;

import application.modele.armes.arc.Fleche;
import application.modele.objets.*;
import application.modele.objets.materiaux.*;
import application.modele.personnages.*;
import application.modele.personnages.animaux.Animal;
import application.modele.personnages.animaux.Lapin;
import application.modele.personnages.animaux.Sanglier;
import application.modele.personnages.ennemi.*;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
        listeFleches = FXCollections.observableArrayList();
    }

    //region init
    private void initListeArbres() {
        listeArbres = FXCollections.observableArrayList();
        for (int i = 0; i < MapJeu.HEIGHT; i++) {
            for (int j = 0; j < MapJeu.WIDTH; j++) {
                if (mapJeu.getTabMap()[i][j] == 55) {
                    listeArbres.add(new Arbre(this, j, i));
                }
            }
        }
    }

    private void initListeMateriaux() {
        listeMateriaux = FXCollections.observableArrayList();
        for (int i = 0; i < MapJeu.HEIGHT; i++) {
            for (int j = 0; j < MapJeu.WIDTH; j++) {
                switch (mapJeu.getTabMap()[i][j]) {
                    case 34: listeMateriaux.add(new Terre(this, j, i)); break;
                    case 52: listeMateriaux.add(new Pierre(this, j, i)); break;
                    case 53: listeMateriaux.add(new Fer(this, j, i)); break;
                    case 54: listeMateriaux.add(new Platine(this, j, i)); break;
                    default: break;
                }
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
        for (int i = 0; i < MapJeu.HEIGHT; i++) {
            for (int j = 0; j < MapJeu.WIDTH; j++) {
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
    //endregion

    public boolean entreEnCollision(int xPerso, int yPerso, Direction dir) {
        boolean collision = false;
        int x,y;
        switch (dir) {
            case Droit:
                x = (xPerso+TUILE_TAILLE+1)/TUILE_TAILLE;
                y = yPerso/TUILE_TAILLE;
                if ((xPerso+TUILE_TAILLE+1) % TUILE_TAILLE == 0) x--;
                if (x >= WIDTH || estUnObstacle(x, y) || (yPerso % TUILE_TAILLE != 0 && estUnObstacle(x,y+1)))
                    collision = true;
                break;
            case Gauche:
                x = (xPerso-1)/TUILE_TAILLE;
                y = yPerso/TUILE_TAILLE;
                if ((xPerso-1) % TUILE_TAILLE == 0) x++;
                if (xPerso-1 < 0 || estUnObstacle(x, y) || (yPerso % TUILE_TAILLE != 0 && estUnObstacle(x,y+1)))
                    collision = true;
                break;
            case Bas:
                x = xPerso/TUILE_TAILLE;
                y = yPerso/TUILE_TAILLE;
                if (y + 1 >= MapJeu.HEIGHT || estUnObstacle(x,y+1) || (xPerso % TUILE_TAILLE != 0 && estUnObstacle(x+1,y+1)))
                    collision = true;
                break;
            case Haut:
                x = xPerso/TUILE_TAILLE;
                y = (yPerso-1)/TUILE_TAILLE;
                if (yPerso-1 < 0 || estUnObstacle(x,y) || (xPerso % TUILE_TAILLE != 0 && estUnObstacle(x+1,y)))
                    collision = true;
                break;
            default:
                break;
        }
        return collision;
    }

    private boolean estUnObstacle(int x, int y) {
        return mapJeu.getTabMap()[y][x] == 34 || mapJeu.getTabMap()[y][x] == 54 || mapJeu.getTabMap()[y][x] == 52 || mapJeu.getTabMap()[y][x] == 53;
    }

    public boolean pauser() {
        this.pauseProperty.setValue(!pauseProperty.getValue());
        return pauseProperty.getValue();
    }

    public Materiau getMinerai(int x, int y) {
        for (Materiau minerai : listeMateriaux)
            if (minerai.getX() == x && minerai.getY() == y)
                return minerai;
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
        for(int i = 0; i < this.listeEntites.size(); i++) {
            Entite obj = this.listeEntites.get(i);
            obj.update();

            //appliquerGravite();
        }

        for (int i = 0; i < listeEnnemis.size(); i++)
            listeEnnemis.get(i).update();

        for (int i = 0; i < listeAnimaux.size(); i++)
            listeAnimaux.get(i).update();

        for (int i = 0; i < listeFleches.size(); i++)
            listeFleches.get(i).update();
    }

    /*public void appliquerGravite() {
        for(int i = 0; i < this.listeEntites.size(); i++) {
            Entite ent = this.listeEntites.get(i);
            if(!ent.getIgnoreGravite()) {
                Entite collisionEntite = ent.getCollider().tracerLigne(0, 32);
                if(collisionEntite == null) {
                    ent.gravite();
                }
            };
        }
    }*/

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
