// Author: Ethan Lowe

import javafx.scene.image.ImageView;

/**
 * Represents an enemy in Galaga mode.
 * Moves horizontally or in patterns depending on the level.
 * Can be destroyed by bullets, awarding points to the player.
 */


public class Enemy extends Block {

    private static final double SPEED_X = 2; // horizontal movement speed
    private static final double SPEED_Y = 20; // vertical drop per row
    private boolean movingRight = true;

    public Enemy(int xCoord, int yCoord, int health) {
        super(xCoord, yCoord, health);
    }

    public void move() {
        ImageView view = (ImageView) getView();
        double newX = view.getX() + (movingRight ? SPEED_X : -SPEED_X);

        if (newX <= 0) {
            movingRight = true;
            view.setY(view.getY() + SPEED_Y); // move down
        } else if (newX + SIZE_X >= Game.SIZE_X) {
            movingRight = false;
            view.setY(view.getY() + SPEED_Y); // move down
        } else {
            view.setX(newX);
        }
    }
    
    public void moveDown(double dy) {
        myView.setY(myView.getY() + dy);
    }


}
