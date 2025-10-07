//Ethan Lowe

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Ball for Breakout
 * Handles position, movement, and bouncing behavior
 */
public class Ball {

	//place holders until we get a better sense of the scope of everything
    public static final int BALL_SIZE = 15; 
    private static final double BALL_SPEED = 200;

    private final ImageView myView;
    private Point2D myVelocity;
    private final Random dice = new Random();

    // make a new ball starting at a given position
    public Ball(Image image, int startX, int startY) {
        myView = new ImageView(image);
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
    public void bounce(String surface) {
        switch (surface) {
            case "leftWall":
            case "rightWall":
            	//invert X velocity
                myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
                break;
            case "topWall":
            case "paddle":
            case "brick":
            	//invert Y velocity
                myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
                break;
            case "bottom":
                stop();
                break;
        }
    }

    // stop the ball when it falls off screen
    public void stop() {
        myVelocity = Point2D.ZERO;
    }

    // reposition the ball (used when resetting)
    public void reset(double x, double y) {
        myView.setX(x);
        myView.setY(y);
        startMoving();
    }

    // re-randomize the ball’s movement direction
    private void startMoving() {
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
