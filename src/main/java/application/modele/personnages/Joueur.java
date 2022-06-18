package application.modele.personnages;

import application.modele.*;
import application.modele.armes.Arme;
import application.modele.armes.Hache;
import application.modele.armes.Pioche;
import application.modele.armes.arc.Arc;
import application.modele.armes.arc.Fleche;
import application.modele.objets.Arbre;
import application.modele.objets.Coffre;
import application.modele.objets.Materiau;
import application.modele.personnages.ennemi.Ennemi;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.media.AudioClip;

import static application.modele.MapJeu.TUILE_TAILLE;

public class Joueur extends Personnage {

    public final static int PV_MAX = 30;

    private Inventaire inventaire;
    private boolean freeze;
    private BooleanProperty mortProperty;
    private BooleanProperty seReposeProperty;
    private long delai;
    private BooleanProperty avanceProperty;

    public Joueur(Environnement env) {
        super(env);
        this.inventaire = new Inventaire(super.getEnv());
        this.inventaire.ajouterObjet(new Pioche(getEnv(), 1));
        this.inventaire.ajouterObjet(new Hache(getEnv(), 1));
        this.inventaire.ajouterObjet(new Arc(getEnv(), 1));
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        this.inventaire.ajouterObjet(new Fleche());
        mortProperty = new SimpleBooleanProperty(false);
        seReposeProperty = new SimpleBooleanProperty(false);
        avanceProperty = new SimpleBooleanProperty(false);
        freeze = false; delai = 0;
    }

    public boolean interagit(int x, int y) {
        if(interactionFeuDeCamp(x,y) || interactionEtabli(x, y) || (this.inventaire.getArme() != null && (frapper(x, y) || miner(x, y) || couper(x, y))) || ouvrirCoffre(x, y))
                return true;
        return false;
    }

    private boolean interactionFeuDeCamp(int x, int y) {
        if (x == getEnv().getFeuDeCamp().getX() && y == getEnv().getFeuDeCamp().getY()) {
            if (!seReposeProperty.getValue()) {
                seReposeProperty.setValue(true);
                delai = System.currentTimeMillis();
            }
            return true;
        }
        return false;
    }

    private boolean interactionEtabli(int x, int y) {
        if (x == getEnv().getEtabli().getX() && y == getEnv().getEtabli().getY()) {
            getEnv().getEtabli().interagir();
            return true;
        }
        return false;
    }

    public boolean poserBlock(int x, int y) {
        ObjetInventaire objetEquipe = this.getInventaire().getObjetInventaireSelectionnee();
        if(objetEquipe != null && !(objetEquipe.getEntite() instanceof Arme)) {
            try {

                Materiau nouvBloc = (Materiau) objetEquipe.getEntite().getClass().getDeclaredConstructor().newInstance();
                nouvBloc.setX(x * TUILE_TAILLE);
                nouvBloc.setY(y * TUILE_TAILLE);
                nouvBloc.setEnv(this.getEnv());

                objetEquipe.retirerDansStack();

                this.getEnv().getListeMateriaux().add(nouvBloc);
                System.out.println("Bloc ajouté");
            } catch(Exception exception) {
                System.out.println("Impossible d'ajouter un bloc");
                exception.printStackTrace();
            }
        }

        return false;
    }

    private boolean ouvrirCoffre(int x, int y){
        Coffre coffre = getEnv().getCoffre(x, y);
        if (coffre != null) {
            coffre.ouvrir();
            return  true;
        }
        return false;
    }

    private boolean frapper(int x, int y) {
        if (getArme() instanceof Arc) {
            getArme().frapper(this, null);
            return true;
        } else {
            Ennemi ennemi = getEnv().getEnnemi(x, y);
            if (ennemi != null) {
                getArme().frapper(this, ennemi);
                return true;
            }
            return false;
        }
    }

    private boolean couper(int x, int y) {
        Arbre arbre = getEnv().getArbre(x, y);
        if (arbre != null) {
            arbre.estFrappe();
            return true;
        }
        return false;
    }

    private boolean miner(int x, int y) {
        Materiau minerai = getEnv().getMinerai(x, y);
        if (minerai != null) {
            minerai.estFrappe();
            return true;
        }
        return false;
    }

    @Override
    public void quandCollisionDetectee(Entite ent) {
        if (ent instanceof ObjetJeu || ent instanceof Materiau) {
            this.inventaire.ajouterObjet(ent);
        }
    }

    @Override
    public void update() {
        super.collide();
        if (mortProperty.getValue())
            mourir();
        else if (seReposeProperty.getValue())
            seReposer();
        else if (getDistancePoussee() != 0)
            estPoussee();
        else {
            if (!freeze) {
                if (getSaute()) sauter();
                if (getAvance()) seDeplacer();
            }
            if (!getSaute()) tomber();
        }
    }

    public void decrementerPv(int degat) {
        int degatSubit;
        if (inventaire.getArmure() == null)
            degatSubit = degat;
        else if (degat <= inventaire.getArmure().defendre()) {
            degatSubit = 0;
            inventaire.getArmure().decrementerPv(degat);
        } else {
            degatSubit = degat - inventaire.getArmure().defendre();
            inventaire.getArmure().decrementerPv(inventaire.getArmure().defendre());
        }
        setPv(getPv() - degatSubit);
        if (getPv() <= 0)
            detruire();
    }

    private void seReposer() {
        if (System.currentTimeMillis() - delai >= 5_000) {
            seReposeProperty.setValue(false);
        } else if (System.currentTimeMillis() - delai >= 2_000 && getX() + TUILE_TAILLE != getEnv().getFeuDeCamp().getX() * TUILE_TAILLE) {
            setSaute(false); setAvance(false); setDistancePoussee(0);
            getEnv().getFeuDeCamp().seReposer();
        }
    }

    private void mourir() {
        if (System.currentTimeMillis() - delai >= 5_000) {
            mortProperty.setValue(false);
        } else if (System.currentTimeMillis() - delai >= 2_000 && getX() + TUILE_TAILLE != getEnv().getFeuDeCamp().getX() * TUILE_TAILLE) {
            setSaute(false); setAvance(false); setDistancePoussee(0);
//            if (getArme() != null) getArme().detruire();
//            if (inventaire.getArmure() != null) inventaire.getArmure().detruire();

            for (int i = inventaire.getObjets().size() - 1; i >= 0; i--)
                if (inventaire.getObjets().get(i).getEntite() instanceof Materiau)
                    inventaire.retirerObjet(inventaire.getObjets().get(i));

            int i = 0;
            while (i < inventaire.getObjets().size() && !(inventaire.getObjets().get(i).getEntite() instanceof Arme)) i++;

            if (i == inventaire.getObjets().size()) {
                this.inventaire.ajouterObjet(new Pioche(getEnv(), 1));
                this.inventaire.ajouterObjet(new Hache(getEnv(), 1));
            }
            getEnv().getFeuDeCamp().seReposer();
        }
    }

    @Override
    public void detruire() {
        mortProperty.setValue(true);
        delai = System.currentTimeMillis();
    }

    public void freezer() {
        freeze = !freeze;
    }

    @Override
    protected int getHauteurMax() {
        return 2 * TUILE_TAILLE;
    }

    @Override
    protected int getVitesse() {
        return 3;
    }

    public Inventaire getInventaire() {
        return this.inventaire;
    }

    public final boolean getMort() {
        return mortProperty.getValue();
    }

    public final BooleanProperty getMortProperty() {
        return mortProperty;
    }

    public final boolean getAvance() {
        return avanceProperty.getValue();
    }

    public final BooleanProperty getAvanceProperty() {
        return avanceProperty;
    }

    public void setAvance(boolean avance) {
        this.avanceProperty.setValue(avance);
    }

    @Override
    public Arme getArme() {
        return inventaire.getArme();
    }

    public final boolean getSeRepose() {
        return seReposeProperty.getValue();
    }

    public final BooleanProperty getSeReposeProperty() {
        return seReposeProperty;
    }
}
