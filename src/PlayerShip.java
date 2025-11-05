import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class PlayerShip extends Paddle {

    private static final String SHIP_IMAGE = "resources/paddle.png";
    private static final double BULLET_SPEED = 6; // can be pixels per frame
    private ArrayList<Bullet> bullets;

    public PlayerShip(double startX, double startY) {
        super(startX, startY); // reuse Paddle positioning
        bullets = new ArrayList<>();

        // Swap Paddle image for ship image
        try {
            Image img = new Image(new FileInputStream(SHIP_IMAGE));
            getViewAsImageView().setImage(img);
        } catch (FileNotFoundException e) {
            System.out.println("PlayerShip image not found!");
        }
    }

    public PlayerShip() {
        this((480 / 2) - (PADDLE_WIDTH / 2), 600); // default bottom-center position
    }

    /** Shoot a bullet from the center-top of the ship */
    public void shoot() {
        double bulletX = getViewAsImageView().getX() + getViewAsImageView().getFitWidth() / 2 - Bullet.BULLET_SIZE / 2;
        double bulletY = getViewAsImageView().getY() - Bullet.BULLET_SIZE;
        Bullet b = new Bullet(bulletX, bulletY); // upward bullet
        bullets.add(b);
        // The level/game should add the bullet to the scene
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    /** Convenience method to get ImageView from Paddle */
    private ImageView getViewAsImageView() {
        return (ImageView) super.asNode();
    }

    /** Horizontal movement stays the same as Paddle */
    @Override
    public void move(KeyCode code, double elapsedTime) {
        super.move(code, elapsedTime); // just reuse Paddle movement
    }
    
    
}
