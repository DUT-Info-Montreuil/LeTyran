package application.vue;

import application.modele.armes.Arme;
import application.vue.ArmeVue;
import application.vue.ArmeVue;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashMap;

public class ChargeurRessources {

    public static ArrayList<Image> tileMapAssets = new ArrayList<Image>();
    public static HashMap<String, Image> iconObjets = new HashMap<>();

    private Image tilesetSol = new Image("file:src/main/resources/application/tilemap/tileset_sol.png");

    public static void charger() {
        for (int i = 1; i < 57; i++) {
            tileMapAssets.add(null);
        }

        tileMapAssets.add(0, new Image("file:src/main/resources/application/pack1/tile_transparent.png"));
        for (int i = 2; i <= 51; i++) {
            String fileName = "0" + (i-1);
            if(i < 10) {
                fileName = "00" + (i-1);
            }
            String path = "file:src/main/resources/application/pack1/tile"+ fileName + ".png";
            tileMapAssets.add(i, new Image(path));
        }
        tileMapAssets.add(34, new Image("file:src/main/resources/application/pack1/Terre.png"));
        tileMapAssets.add(52, new Image("file:src/main/resources/application/pack1/Pierre.png"));
        tileMapAssets.add(53, new Image("file:src/main/resources/application/pack1/Fer.png"));
        tileMapAssets.add(54, new Image("file:src/main/resources/application/pack1/Platine.png"));
        tileMapAssets.add(55, new Image("file:src/main/resources/application/pack1/arbre_bas.png"));
        tileMapAssets.add(56, new Image("file:src/main/resources/application/pack1/arbre_milieu.png"));
        tileMapAssets.add(57, new Image("file:src/main/resources/application/pack1/arbre_haut.png"));
        tileMapAssets.add(58, new Image("file:src/main/resources/application/Coffre/CoffreOrFerm.png"));




        //Icon objets
//        iconObjets.put("Terre", new Image("file:src/main/resources/application/ressources/Terre.png"));
//        iconObjets.put("Bois", new Image("file:src/main/resources/application/ressources/Bois.png"));
//        iconObjets.put("Pierre", new Image("file:src/main/resources/application/ressources/Pierre.png"));
//        iconObjets.put("Fer", new Image("file:src/main/resources/application/ressources/Fer.png"));
//        iconObjets.put("Platine", new Image("file:src/main/resources/application/ressources/Platine.png"));
        iconObjets.put("Sol", new Image("file:src/main/resources/application/pack1/Sol.png"));
        iconObjets.put("Terre", new Image("file:src/main/resources/application/pack1/Terre.png"));
        iconObjets.put("Bois", new Image("file:src/main/resources/application/pack1/Bois.png"));
        iconObjets.put("Pierre", new Image("file:src/main/resources/application/pack1/Pierre.png"));
        iconObjets.put("Fer", new Image("file:src/main/resources/application/pack1/Fer.png"));
        iconObjets.put("Platine", new Image("file:src/main/resources/application/pack1/Platine.png"));


        iconObjets.put("inventaireMain", new Image("file:src/main/resources/application/inventaire/icon_main.png"));
        iconObjets.put("inventaireSac", new Image("file:src/main/resources/application/inventaire/inventaire_sac.png"));

        iconObjets.put("Etabli", new Image("file:src/main/resources/application/sprite_etabli.png"));
        iconObjets.put("Fleche1", new Image("file:src/main/resources/application/arme/sprite_fleche.png"));
        iconObjets.put("Hache1", new Image("file:src/main/resources/application/arme/sprite_hache1.png"));
        iconObjets.put("Pioche1", new Image("file:src/main/resources/application/arme/sprite_pioche1.png"));
        iconObjets.put("Epee1", new Image("file:src/main/resources/application/arme/sprite_epee1.png"));
        iconObjets.put("Arc1", new Image("file:src/main/resources/application/arme/sprite_arc1.png"));
        iconObjets.put("Lance1", new Image("file:src/main/resources/application/arme/sprite_lance1.png"));
        iconObjets.put("Hache2", new Image("file:src/main/resources/application/arme/sprite_hache2.png"));
        iconObjets.put("Pioche2", new Image("file:src/main/resources/application/arme/sprite_pioche2.png"));
        iconObjets.put("Epee2", new Image("file:src/main/resources/application/arme/sprite_epee2.png"));
        iconObjets.put("Arc2", new Image("file:src/main/resources/application/arme/sprite_arc2.png"));
        iconObjets.put("Lance2", new Image("file:src/main/resources/application/arme/sprite_lance2.png"));
        iconObjets.put("Hache3", new Image("file:src/main/resources/application/arme/sprite_hache3.png"));
        iconObjets.put("Pioche3", new Image("file:src/main/resources/application/arme/sprite_pioche3.png"));
        iconObjets.put("Epee3", new Image("file:src/main/resources/application/arme/sprite_epee3.png"));
        iconObjets.put("Arc3", new Image("file:src/main/resources/application/arme/sprite_arc3.png"));
        iconObjets.put("Lance3", new Image("file:src/main/resources/application/arme/sprite_lance3.png"));
        iconObjets.put("Armure1", new Image("file:src/main/resources/application/arme/sprite_armure1.png"));
        iconObjets.put("Armure2", new Image("file:src/main/resources/application/arme/sprite_armure2.png"));
        iconObjets.put("Armure3", new Image("file:src/main/resources/application/arme/sprite_armure3.png"));

        iconObjets.put("Viande", new Image("file:src/main/resources/application/ressources/viande.png"));
        iconObjets.put("Potion", new Image("file:src/main/resources/application/ressources/potion.png"));
        iconObjets.put("BouleDeFeu", new Image("file:src/main/resources/application/boule_de_feu.gif"));
        iconObjets.put("Cle", new Image("file:src/main/resources/application/sprite_cle.png"));
    }
}