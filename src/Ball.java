import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Simple Ball class for Breakout.
 * Moves around, can reverse direction, and implements Collidable so
 * the game world can treat it like any other collidable object.
 *
 */
public class Ball implements Collidable {
    private static final String BALL_IMAGE = "resources/ball.gif";
    private static final int BALL_SIZE = 15;

    // default screen bounds in case Game doesn't call a bounds-check method
    // you can override/check with Game calling checkBounds(screenW, screenH)
    private static final double DEFAULT_SCREEN_WIDTH = 480;
    private static final double DEFAULT_SCREEN_HEIGHT = 640;

    private ImageView myView;
    private double dx;
    private double dy;

    // constructor sets start position and starting velocity
    public Ball(double startX, double startY, double startDX, double startDY) {
        try {
            Image img = new Image(new FileInputStream(BALL_IMAGE));
            myView = new ImageView(img);
        } catch (FileNotFoundException e) {
            System.out.println("Ball image not found! using empty ImageView.");
            myView = new ImageView();
        }

        myView.setFitWidth(BALL_SIZE);
        myView.setFitHeight(BALL_SIZE);
        myView.setX(startX);
        myView.setY(startY);

        dx = startDX;
        dy = startDY;
    }

    // simple getter for the view (used by Game and Collidable checks)
    @Override
    public ImageView getView() {
        return myView;
    }

    // move the ball by its velocity (very simple, no elapsed time)
    // Game can call this every frame
    public void move() {
        myView.setX(myView.getX() + dx);
        myView.setY(myView.getY() + dy);

        // keep it from leaving the screen using defaults (Game can do nicer checks)
        checkBounds((int) DEFAULT_SCREEN_WIDTH, (int) DEFAULT_SCREEN_HEIGHT);
    }

    // check and bounce on screen edges (Game can call this with real window size)
    public void checkBounds(int screenWidth, int screenHeight) {
        double x = myView.getX();
        double y = myView.getY();

        // left / right sides
        if (x <= 0 || x + BALL_SIZE >= screenWidth) {
            reverseX();
        }

        // top
        if (y <= 0) {
            reverseY();
        }

        // bottom (fell off) -> for now just reverse Y so it doesn't disappear.
        // In your Game you might want to treat this as "lose life" instead.
        if (y + BALL_SIZE >= screenHeight) {
            reverseY();
        }
    }

    // flip horizontal speed
    public void reverseX() {
        dx = -dx;
    }

    // flip vertical speed
    public void reverseY() {
        dy = -dy;
    }

    // allow game or other objects to set velocity directly (e.g., powerups)
    public void setVelocity(double newDX, double newDY) {
        dx = newDX;
        dy = newDY;
    }

    // expose position-ish methods if needed (keeps them simple)
    public double getX() { return myView.getX(); }
    public double getY() { return myView.getY(); }

    // ----------------------
    // Collidable implementation
    // ----------------------

    // check intersection with another ball (or any Collidable who uses Ball)
    @Override
    public boolean checkCollision(Ball other) {
        if (other == null || other.getView() == null) return false;
        return myView.getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

    // default reaction when something calls onCollision(thisBall)
    // Usually Game will call otherObject.onCollision(ball) (not ball.onCollision(other)).
    // We implement this so Ball itself can be treated as a collidable if needed.
    @Override
    public void onCollision(Ball other) {
        // pretty simple: if another ball/object hits this ball, flip vertical direction
        // you could add more advanced physics later
        reverseY();
    }


}
