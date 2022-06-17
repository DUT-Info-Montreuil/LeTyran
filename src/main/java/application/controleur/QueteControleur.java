package application.controleur;

import application.modele.ModeleQuetes;
import application.modele.ObjetInventaire;
import application.modele.quetes.ObjectifNombreNecessaire;
import application.modele.quetes.QueteType;
import application.vue.VueQuetes;
import application.vue.quetes.QueteObjectif;
import javafx.scene.Scene;
import javafx.scene.text.TextFlow;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class QueteControleur {
    private ModeleQuetes modeleQuetes;
    private VueQuetes vueQuetes;

    public QueteControleur(Scene scene, TextFlow conteneurQuete) {
        this.modeleQuetes = new ModeleQuetes();
        this.vueQuetes = new VueQuetes(conteneurQuete, scene);

        this.vueQuetes.initialiserQuete(this.modeleQuetes.getQueteActuel());

    }

    public void objetAEteRetireeInventaire(ObjetInventaire obj) {
        HashMap<String, ObjectifNombreNecessaire> objectifs = this.modeleQuetes.getQueteActuel().listeObjectifs.get(QueteType.TYPE_QUETE.RAMASSER);
        String nomEntite = obj.getEntite().getClass().getSimpleName();
        if(objectifs.get(nomEntite) != null) {
            objectifs.get(nomEntite).retirerNombreObjectif(obj.getStackActuelProperty().getValue());
        }

    }

    public void objetAEteAjouteeInventaire(ObjetInventaire obj) {
        HashMap<String, ObjectifNombreNecessaire> objectifs = this.modeleQuetes.getQueteActuel().listeObjectifs.get(QueteType.TYPE_QUETE.RAMASSER);
        String nomEntite = obj.getEntite().getClass().getSimpleName();
        if(objectifs.get(nomEntite) != null) {
            int nombreAAJoutee = 0;
            //Avant d'ajouter le nombre qu'on a récupérer, on décomptabilise ce qu'on avait déjà
            /*if(obj.getStackActuelProperty().getValue() ) {
                nombreAAJoutee = obj.getStackActuelProperty().getValue() - obj.getStackActuelProperty().getValue();
            }*/
            objectifs.get(nomEntite).ajouterNombreObjectif(nombreAAJoutee + 1);
        }

        this.verifierQueteCompletee();
    }

    public void verifierQueteCompletee() {
        boolean queteCompletee  = true;

        HashMap<QueteType.TYPE_QUETE, HashMap<String, ObjectifNombreNecessaire>> objectifsActuels = this.modeleQuetes.getQueteActuel().listeObjectifs;

        Iterator iterationQueteTYpe = objectifsActuels.entrySet().iterator();

        while(iterationQueteTYpe.hasNext()) {
            Map.Entry<QueteType.TYPE_QUETE, HashMap<String, ObjectifNombreNecessaire>> objectif = (Map.Entry)iterationQueteTYpe.next();
            Iterator iterationObjectif = objectif.getValue().entrySet().iterator();
            while(iterationObjectif.hasNext()) {
                Map.Entry<String, ObjectifNombreNecessaire> objectifARemplir = (Map.Entry)iterationObjectif.next();

                QueteObjectif objectifAAffiche = new QueteObjectif(objectif.getKey(), objectifARemplir.getKey(), objectifARemplir.getValue());
                ObjectifNombreNecessaire objectifCompteur = objectifARemplir.getValue();
                if(objectifCompteur.getNombreActuel() <  objectifCompteur.getNombreNecessaire()) {
                    queteCompletee = false;
                }
            }

        }


        this.modeleQuetes.getQueteActuel().setCompletee(queteCompletee);

        System.out.println("L'objectif est completee : " + queteCompletee);

    }


}
