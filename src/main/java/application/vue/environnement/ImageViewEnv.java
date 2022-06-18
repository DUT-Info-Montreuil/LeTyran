package application.vue.environnement;

import application.controleur.Constantes;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageViewEnv extends ImageView {

    private int width;

    public ImageViewEnv(int bType) {

        bType -= 1;


        this.width = (int)Constantes.tileSetSol.getWidth()/32;
        int y = bType / width;
        int x = bType - y * width;


        if(bType <= 0) {
            x = 13;
            y = 5;
        }




        this.setImage(Constantes.tileSetSol);
        this.setViewport(new Rectangle2D(32 * x, 32 * y, 32,32));


    }

    public void setCustomView(String nom) {
        /*int blocID =0;
        switch (nom) {
            case "Terre":
                blocID = 183;
                break;
            case "Fer":
                break;

        }
        int y = blocID / width;
        int x = blocID - y * width;
        this.setViewport(new Rectangle2D(32 * x, 32 * y, 32,32));*/
    }
}
