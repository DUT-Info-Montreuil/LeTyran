package application.modele.personnages.allies;

import application.modele.Environnement;
import application.modele.personnages.PNJ;

public abstract class Allie extends PNJ {
    public Allie(Environnement env, String id, int x, int y, int distance) {
        super(env, id, x, y, distance);
    }
}
