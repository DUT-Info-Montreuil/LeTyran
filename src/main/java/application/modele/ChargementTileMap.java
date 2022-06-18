package application.modele;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;

public class ChargementTileMap {

    private static String recupererString(FileInputStream file) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(file));

            String ligne;
            while((ligne = br.readLine()) != null) {
                sb.append(ligne);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static int[][] recupererTileMap(int index) {

        int[][] tileMap;

        File file = new File("src/main/resources/application/tilemap/donne_tilemap.json");
        if (file.exists()) {
            try {
                FileInputStream is = new FileInputStream("src/main/resources/application/tilemap/donne_tilemap.json");
                //String donne
                String map = recupererString(is);
                JSONObject json = new JSONObject(map);
                int mapWidth = json.getInt("width");
                int mapHeight = json.getInt("height");

                tileMap = new int[mapHeight][mapWidth];


                JSONArray infoCarte = json.getJSONArray("layers");
                //JSONObject ensembleBloc = infoCarte.getString("data");

                JSONObject mapDetails = (JSONObject) infoCarte.get(index);
                JSONArray mapdsdqs = (JSONArray) mapDetails.get("data");

                for (int i = 0; i < mapdsdqs.length(); i++) {
                    int y = i / mapWidth;
                    int x = i - (y) * mapWidth;
                    tileMap[y][x] = mapdsdqs.getInt(i);
                }

                return tileMap;

                //System.out.println(mapDetails.get("data"));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }

        return new int[0][0];
    }




}
