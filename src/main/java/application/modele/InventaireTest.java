package application.modele;

import application.modele.objets.materiaux.Pierre;
import application.modele.objets.materiaux.Terre;
import org.junit.Test;

import static org.junit.Assert.*;

public class InventaireTest {
    Environnement env = new Environnement();
    Inventaire inventaire = new Inventaire(env);

    @Test
    public void scrollObjetMain() {
    }

    @Test
    public void recupererPlaceDispo() {
    }

    @Test
    public void ajouterObjetVersionDeux() {
    }

    @Test
    public void recupererNombreRessources() {
        //On test avec l'inventaire vide
        assertEquals(0, inventaire.recupererNombreRessources("Pierre"));
        assertEquals(0, inventaire.recupererNombreRessources("Terre"));
        assertEquals(0, inventaire.recupererNombreRessources("Fer"));
        assertEquals(0, inventaire.recupererNombreRessources("Platine"));
        assertEquals(0, inventaire.recupererNombreRessources("Bois"));

        //On en met 6 sachant qu'on peut posséder qu'une stack de 5, la fonction recherche dans toutes les stacks

        for(int i = 0; i < 6; i++) {
            inventaire.ajouterObjet(new Pierre());
        }


        for(int i = 0; i < 99; i++) {
            inventaire.ajouterObjet(new Terre());
        }

        assertEquals(6, inventaire.recupererNombreRessources("Pierre"));
        assertEquals(99, inventaire.recupererNombreRessources("Terre"));

    }

    @Test
    public void retirerNbRessources() {
        inventaire.getObjets().clear();

        for(int i = 0; i < 6; i++) {
            inventaire.ajouterObjet(new Pierre());
        }


        for(int i = 0; i < 30; i++) {
            inventaire.ajouterObjet(new Terre());
        }

        inventaire.retirerNbRessources("Terre", 1);
        assertEquals(29, inventaire.recupererNombreRessources("Terre"));

        inventaire.retirerNbRessources("Terre", 10);

        assertEquals(19, inventaire.recupererNombreRessources("Terre"));

        //On enlève tout ce qui reste
        inventaire.retirerNbRessources("Terre", 19);
        assertEquals(0, inventaire.recupererNombreRessources("Terre"));

    }
}