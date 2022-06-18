package application.modele.objets;

import application.modele.Entite;
import application.modele.Environnement;
import application.modele.armes.Epee;
import application.modele.objets.materiaux.Pierre;

import java.util.ArrayList;


public class Coffre extends Entite {

    private ArrayList<Entite> loot;

    public Coffre(Environnement env, int x, int y) {
        super(env, x, y);
        loot= new ArrayList<>();
        this.remplirLoot();
    }

    public Coffre(Environnement env, int x, int y, ArrayList loot) {
        super(env, x, y);
        this.loot = loot;
    }

    private ArrayList<Entite> remplirLoot(){
        int x=(int) (Math.random() * 3 + 1);
        this.loot.add(new Epee(getEnv(), x));
        for (int j=0 ; j<5;j++){
            this.loot.add(new Pierre(getEnv(),0,0 ));
        }
        return this.loot;
    }

    public void ouvrir() {
        for (int i = 0 ; i < loot.size();i++)
            getEnv().getJoueur().getInventaire().ajouterObjet(loot.get(i));
        detruire();
    }

    @Override
    public  void detruire() {
        getEnv().getMapJeu().getTabMap()[(int) getY()][(int) getX()] = 59;
        getEnv().getListeCoffres().remove(this);
    }

    public float getX() {
        return super.getX();
    }

    public float getY() {
        return super.getY();
    }

    public ArrayList<Entite> getLoot() {
        return this.loot;
    }
}
