package application.modele;

import application.modele.quetes.BaseQuete;
import application.modele.quetes.QueteType;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class ModeleQuetes {

    private ArrayList<BaseQuete> quetesDisponibles;
    private IntegerProperty queteActuel;

    public ModeleQuetes() {
        quetesDisponibles = new ArrayList<BaseQuete>();
        this.queteActuel = new SimpleIntegerProperty(0);
        chargerQuetes();
    }

    public void chargerQuete(int indexQuete) {
        this.queteActuel.setValue(indexQuete);
    }


    public BaseQuete getQueteActuel() {
        return this.quetesDisponibles.get(this.queteActuel.getValue());
    }

    public void chargerQuetes() {
        BaseQuete bq = new BaseQuete("Renforce toi");
        bq.ajouterObjectif(QueteType.TYPE_QUETE.RAMASSER, "Bois", 3);
        bq.ajouterObjectif(QueteType.TYPE_QUETE.RAMASSER, "Terre", 10);
        bq.ajouterObjectif(QueteType.TYPE_QUETE.RAMASSER, "Hache en pierre", 10);

        this.quetesDisponibles.add(bq);

        /*File repertoire = new File("src/main/java/application/modele/quetes/listes");
        File[] quetesTrouvee = repertoire.listFiles((dir, name) -> {
            try {
                String nom = name.substring(0, name.indexOf(".java"));
                Class cl = ClassLoader.getSystemClassLoader().loadClass("application.modele.quetes.listes." + nom);

                System.out.println(cl instanceof BaseQuete);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return false;
        });*/

    }
}
