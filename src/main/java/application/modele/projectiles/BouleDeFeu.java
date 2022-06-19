//package application.modele.projectiles;
//
//import application.modele.Direction;
//import application.modele.Entite;
//import application.modele.Environnement;
//import application.modele.personnages.Joueur;
//import application.modele.personnages.Personnage;
//import javafx.beans.property.BooleanProperty;
//import javafx.beans.property.SimpleBooleanProperty;
//
//import static application.modele.MapJeu.TUILE_TAILLE;
//
//public class BouleDeFeu extends Projectile {
//
//    private final static int HAUTEUR_MAX = 5 * TUILE_TAILLE;
//    private final static int DEGATS = 10;
//    private static int idMax = 0;
//
//    private BooleanProperty chuteProperty;
//
//    public BouleDeFeu(Environnement env, Personnage perso) {
//        super(env, (int) perso.getX(), (int) perso.getY());
//        this.perso = perso;
//        this.direction = perso.getDirection();
//        id = "Fleche" + idMax++;
//        distanceParcourue = 0;
//        touche = false;
//        chuteProperty = new SimpleBooleanProperty(false);
//    }
//
//    @Override
//    public void seDeplacer() {
//        if (!chuteProperty.getValue()) {
//            int i = 0;
//            while (i < 7 && distanceParcourue < HAUTEUR_MAX) {
//                i++;
//                Entite touchee = this.getCollider().verifierCollisionDirection(this.direction, 0.5f);
//                if (touchee != null)
//                    quandCollisionDetectee(touchee);
//                setY(getY() - 0.5f);
//                distanceParcourue += 0.5f;
//            }
//        }
//    }
//
//    @Override
//    public void update() {
//        super.collide();
//        seDeplacer();
//    }
//
//    @Override
//    public void quandCollisionDetectee(Entite ent) {
//        if (!touche && ent != perso) {
//            touche = true;
//            if (ent instanceof Joueur)
//                ent.decrementerPv(DEGATS);
//            else
//                detruire();
//            getEnv().getListeProjectiles().remove(this);
//        }
//    }
//}
