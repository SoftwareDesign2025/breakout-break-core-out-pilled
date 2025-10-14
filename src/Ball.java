//Ethan Lowe

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Ball for Breakout
 * Handles position, movement, and bouncing behavior
 */
public class Ball {

	//place holders until we get a better sense of the scope of everything
    public static final int BALL_SIZE = 20;
	public static final int BALL_RAD = (int) (0.5 * BALL_SIZE); 
    private static final double BALL_SPEED = 200;

    public static final String BALL_IMAGE = "resources/ball.gif";
    	private final ImageView myView;
    private Point2D myVelocity;
    private final Random dice = new Random();

    // make a new ball starting at a given position
    public Ball(int startX, int startY) throws FileNotFoundException {
        Image img = new Image(new FileInputStream(BALL_IMAGE));
        myView = new ImageView(img);
        myView.setFitWidth(BALL_SIZE);
        myView.setFitHeight(BALL_SIZE);
        myView.setX(startX);
        myView.setY(startY);

        // give the ball a random diagonal direction
        double angle = Math.toRadians(45 + dice.nextInt(90));
        myVelocity = new Point2D(Math.cos(angle), -Math.sin(angle))
                        .normalize()
                        .multiply(BALL_SPEED);
    }

    // move the ball each frame
    public void move(double elapsedTime) {
        myView.setX(myView.getX() + myVelocity.getX() * elapsedTime);
        myView.setY(myView.getY() + myVelocity.getY() * elapsedTime);
    }

    // bounce depending on what was hit
    public void bounce(int screenWidth, int screenHeight) {
            // collide all bouncers against the walls
            if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()) {
                myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
            }
            if (myView.getY() < 0 || myView.getY() > screenHeight - myView.getBoundsInLocal().getHeight()) {
                myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
            }
//        switch (surface) {
//            case "verticalSurface":
//            	//invert X velocity
//                myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
//                break;
//            case "horizontalSurface":
//            	//invert Y velocity
//                myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
//                break;
//            case "bottom":
//                stop(); // <- placeholder (use to end game)
//                break;
    }

    public void hitPaddle() {
      myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());

    }
    // stop the ball when it falls off screen
    public void stop() {
        myVelocity = Point2D.ZERO;
    }

    // reposition the ball (used when resetting after death)
    public void reset(double x, double y) {
        myView.setX(x);
        myView.setY(y);
        launch();
    }

    // re-randomize the ball’s movement direction
    private void launch() {
        double angle = Math.toRadians(45 + dice.nextInt(90));
        myVelocity = new Point2D(Math.cos(angle), -Math.sin(angle))
                        .normalize()
                        .multiply(BALL_SPEED);
    }

    // allows the game to display the ball
    public ImageView asNode() {
        return myView;
    }

    // helper for detecting overlaps with other objects (paddle, blocks)
    public boolean intersects(ImageView other) {
        return myView.getBoundsInParent().intersects(other.getBoundsInParent());
    }
}
