package application.modele.collisions;

import application.modele.Direction;
import application.modele.Entite;

public class Collider {
    private RectangleCol hitbox;
    private Entite ent;
    private boolean ignoreCollision;
    private boolean activeVerifCollision;

    public Collider(Entite ent) {
        this.ent = ent;
        hitbox = new RectangleCol(32, 32);
        this.ignoreCollision = false;
        this.activeVerifCollision = false;
    }

    public boolean getIgnoreCollision() {
        return this.ignoreCollision;
    }

    public void setIgnoreCollision(boolean ignore) {
        this.ignoreCollision = ignore;
    }

    public RectangleCol getHitBox() {
        return this.hitbox;
    }

    public Entite getEnt() {
        return this.ent;
    }

    public boolean intersect(Entite ent) {
        if (ent.getCollider() != null) {

            double entRecX = ent.getX();
            double entRecY = ent.getY();
            double entColWidth = ent.getCollider().getHitBox().getWidth();
            double entColHeight = ent.getCollider().getHitBox().getHeight();

            double colRecX = this.getEnt().getX();
            double colRecY = this.getEnt().getY();
            double colWidth = this.getHitBox().getWidth();
            double colRecHeight = this.getHitBox().getHeight();

            if (colRecX + colWidth > entRecX
                    && colRecX < entRecX + entColWidth
                    && colRecY + colRecHeight > entRecY && colRecY < entRecY + entColHeight) {
                return true;
            }


        }
        return false;
    }

    public Entite verificationCollisionHorizontal(double valeur) {
        if (!this.getIgnoreCollision()) {
            for (int i = 0; i < this.getEnt().getEnv().getListeMateriaux().size(); i++) {
                Entite ent = this.getEnt().getEnv().getListeMateriaux().get(i);
                if (ent != this.getEnt()) {
                    double entRecX = ent.getX();
                    double entRecY = ent.getY();
                    double entColWidth = ent.getCollider().getHitBox().getWidth();
                    double entColHeight = ent.getCollider().getHitBox().getHeight();

                    double colRecX = this.getEnt().getX();
                    double colRecY = this.getEnt().getY();
                    double colWidth = this.getHitBox().getWidth();
                    double colRecHeight = this.getHitBox().getHeight();

                    if (colRecX + colWidth + valeur > entRecX
                            && colRecX + valeur < entRecX + entColWidth
                            && colRecY + colRecHeight > entRecY && colRecY < entRecY + entColHeight) {
                        return ent;
                    }
                }
            }
        }

        return null;
    }

    public Entite verificationCollisionVertical(double valeur) {
        if (!this.getIgnoreCollision()) {
            for (int i = 0; i < this.getEnt().getEnv().getListeMateriaux().size(); i++) {
                Entite ent = this.getEnt().getEnv().getListeMateriaux().get(i);
                if (ent != this.getEnt()) {
                    double entRecX = ent.getX();
                    double entRecY = ent.getY();
                    double entColWidth = ent.getCollider().getHitBox().getWidth();
                    double entColHeight = ent.getCollider().getHitBox().getHeight();

                    double colRecX = this.getEnt().getX();
                    double colRecY = this.getEnt().getY();
                    double colWidth = this.getHitBox().getWidth();
                    double colRecHeight = this.getHitBox().getHeight();

                    if (colRecX + colWidth > entRecX
                            && colRecX < entRecX + entColWidth
                            && colRecY + colRecHeight + valeur > entRecY &&
                            colRecY + valeur < entRecY + entColHeight) {
                        return ent;
                    }
                }
            }
        }
        return null;
    }

    public Entite verifierCollisionDirection(Direction direction, double valeur) {
        switch (direction) {
            case Droit:
                return verificationCollisionHorizontal(Math.abs(valeur));
            case Gauche:
                return verificationCollisionHorizontal(-Math.abs(valeur));
            case Haut:
                return verificationCollisionVertical(-Math.abs(valeur));
            case Bas:
                return verificationCollisionVertical(Math.abs(valeur));
        }

        return null;
    }
}
