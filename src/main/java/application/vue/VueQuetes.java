package application.vue;

import application.modele.quetes.BaseQuete;
import application.modele.quetes.ObjectifNombreNecessaire;
import application.modele.quetes.QueteType;
import application.vue.quetes.QueteObjectif;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class VueQuetes {


    private TextFlow paneQuete;
    private VBox vboxObjectifs;
    private Text queteNomText;

    public VueQuetes(TextFlow paneQuete, Scene scene) {
        this.paneQuete = paneQuete;
        this.vboxObjectifs = (VBox) paneQuete.getChildren().get(0);
        this.queteNomText = (Text) this.paneQuete.lookup("#nomQuete");


    }

    public void initialiserQuete(BaseQuete quete) {
        this.ajouterObjectifs(quete.recupererListeObjectifs());
        queteNomText.setText(quete.getNom());
    }

    public void ajouterObjectifs(HashMap<QueteType.TYPE_QUETE, HashMap<String, ObjectifNombreNecessaire>> objectifs) {

        Iterator iterationQueteTYpe = objectifs.entrySet().iterator();

        while(iterationQueteTYpe.hasNext()) {
            Map.Entry<QueteType.TYPE_QUETE, HashMap<String, ObjectifNombreNecessaire>> objectif = (Map.Entry)iterationQueteTYpe.next();
            Iterator iterationObjectif = objectif.getValue().entrySet().iterator();
            while(iterationObjectif.hasNext()) {
                Map.Entry<String, ObjectifNombreNecessaire> objectifARemplir = (Map.Entry)iterationObjectif.next();

                QueteObjectif objectifAAffiche = new QueteObjectif(objectif.getKey(), objectifARemplir.getKey(), objectifARemplir.getValue());
                vboxObjectifs.getChildren().add(objectifAAffiche);
            }

        }
        //this.vboxObjectifs.getChildren().add(new QueteObjectif(objectifs))
    }




}
