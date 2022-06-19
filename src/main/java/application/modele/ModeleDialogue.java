package application.modele;

import java.util.ArrayList;

public class ModeleDialogue {
    private int partieActuelle;
    private int nombrePartieTotal;
    private ArrayList<String> texteEntier;


    public ModeleDialogue () {
        texteEntier = new ArrayList<>();
        texteEntier.add("Bonjour, bienvenue au village Village");
        texteEntier.add("Comme vous pouvez le voir, nous ne sommes plus beaucoup...");
        texteEntier.add("En effet depuis l'arrivé du nouveau roi la vie est devenu presque impossible, l'ancien n'était pas un ange" +
                "mais vous pouvions vivre correctement tout de même");
        texteEntier.add("Celui se faisant appeler Momo le Tyran ne nous laisse aucuns répit, entre l'augmentation des taxes, l'enlèvement de nos jeunes");
        texteEntier.add("Regardez moi, ma fille s'est faite kidnappé dans le but de devenir son esclave et de le servir pour le restant de ses jours");
        texteEntier.add("Aventurier, accepteriez vous de me venir en aide et de récupérer ma fille ?");
        texteEntier.add("Je pense qu'avant cela vous devriez vous entraîner en amassant des ressources et des équipements");
        texteEntier.add("Voici une liste de choses à faire avant que vous soyez prêt");

        partieActuelle = 0;
        nombrePartieTotal = texteEntier.size() - 1;
    }

    public void reinitialiserDialogue() {
        partieActuelle = 0;
    }

    public String getTexteDialogue() {
        return this.texteEntier.get(partieActuelle);
    }


    public void avancerPartie() {
        if(partieActuelle < nombrePartieTotal) {
            partieActuelle++;
        }
    }

    public boolean dernierePartie() {
        return this.partieActuelle == this.nombrePartieTotal;
    }


}
