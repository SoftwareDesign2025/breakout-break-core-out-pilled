import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

public class Bullet extends Ball {
    private static final String BULLET_IMAGE = "resources/ball.gif";
    private static final double BULLET_SPEED = 8; // pixels per frame
	public static final double BULLET_SIZE = 1;

    public Bullet(double startX, double startY) {
        super(startX, startY, 0, -BULLET_SPEED); // vertical only, upwards

        try {
            Image img = new Image(new FileInputStream(BULLET_IMAGE));
            getView().setImage(img);
        } catch (FileNotFoundException e) {
            System.out.println("Bullet image not found!");
        }

        getView().setFitWidth(6);  // smaller than ball
        getView().setFitHeight(12);
    }

    // override move so it only moves upward
    @Override
    public void move() {
        getView().setY(getView().getY() + getDy());
    }

    // bullets don't bounce, so override collision to do nothing
    @Override
    public void onCollision(Ball other) {
        // optional: could set to remove bullet
    }
}