import javafx.scene.image.ImageView;

public class Enemy extends Block {

    private static final double SPEED_X = 100; // horizontal movement speed
    private static final double SPEED_Y = 100; // vertical drop per row
    private boolean movingRight = true;

    public Enemy(int xCoord, int yCoord, int health) {
        super(xCoord, yCoord, health);
    }

    public void move(double elapsedTime) {
        ImageView view = (ImageView) getView();
        double newX = view.getX() + (movingRight ? SPEED_X : -SPEED_X) * elapsedTime / 1000;

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
