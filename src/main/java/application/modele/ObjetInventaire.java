package application.modele;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ObjetInventaire {

    private Inventaire inventaire;
    private Entite entite;
    private int placeInventaire;
    private IntegerProperty stackActuel;

    public ObjetInventaire(Inventaire inventaire, Entite ent) {
        this.inventaire = inventaire;
        this.entite = ent;
        this.stackActuel = new SimpleIntegerProperty(1);

    }

    public int getPlaceInventaire() {
        return this.placeInventaire;
    }

    public void setPlaceInventaire(int nouvellePlace) {
        System.out.println("nouvelle place " + nouvellePlace);
        this.placeInventaire = nouvellePlace;
    }

    public IntegerProperty getStackActuelProperty() {
        return this.stackActuel;
    }

    public int getNombre() {
        return this.stackActuel.getValue();
    }

    public void ajouterDansStack() {
        this.stackActuel.setValue(this.stackActuel.getValue() + 1);
    }

    public void retirerDansStack() {
        this.stackActuel.setValue(this.stackActuel.getValue() - 1);
        if (stackActuel.getValue() == 0) {
            if (this == inventaire.getObjetMain())
                inventaire.setObjetMain(null);
            inventaire.retirerObjet(this);
        }
    }

    public Entite getEntite() {
        return this.entite;
    }
}
