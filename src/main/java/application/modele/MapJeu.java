package application.modele;

import java.io.*;

import org.json.*;
import org.json.JSONObject;

public class MapJeu {
    public final static int WIDTH = 30;
    public final static int HEIGHT = 20;
    public final static int TUILE_TAILLE = 32;
    public final static int TAILLE_CHUNK = 25;
    public final static int DISTANCE_RENDU = 4;

    private int mapWidth, mapHeight;
    /*public MapJeu() {
        int joueurX = this.
        for(int x = ) {

        }
    }*/
    private int[][] tabMap;

    public MapJeu() {
        //tabMap = new int[HEIGHT][WIDTH];
        construireMap();
    }


    public int getHeight()  {
        return tabMap.length;
    }

    public int getWidth()  {
        return tabMap[0].length;
    }

    private void construireMap() {
        tabMap = ChargementTileMap.recupererTileMap(1);

    }

    private void afficherMap() {
        for(int i =0; i < tabMap.length; i++) {
            for(int j = 0; j < tabMap[i].length; j++) {
                System.out.print(tabMap[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int[][] getTabMap() {
        return tabMap;
    }
}
