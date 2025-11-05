import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

/**
 * Simple paddle that moves left/right and can be hit by the ball.
 * Implements Collidable so the game can check collisions easily.
 */
public class Paddle implements Collidable {
    public static int PADDLE_WIDTH = 92;
    public static int PADDLE_HEIGHT = 12;
    public static final int PADDLE_VELOCITY = 500;
    public static final String PADDLE_IMAGE = "resources/Paddle.png";
    public static final int MIN_RANDOMIZING = -5;
    public static final int MAX_RANDOMIZING = 5;
    private ImageView myView;
    private double xCoordinate;
    private double yCoordinate;
    
    // default screen bounds in case Game doesn't call a bounds-check method
    private static final double DEFAULT_SCREEN_WIDTH = 480;
    private static final double DEFAULT_SCREEN_HEIGHT = 640;
    
    // default paddle dimensions for default ball position
    private static final double PADDLE_PADDING = 30;
    private static final double DEFAULT_POS_X = (DEFAULT_SCREEN_WIDTH / 2) - (PADDLE_WIDTH / 2);
	private static final double DEFAULT_POS_Y = (DEFAULT_SCREEN_HEIGHT - (PADDLE_HEIGHT + PADDLE_PADDING));

    /*
     * method: Paddle (constructor)
     * arguments (startX, startY) represent the starting X and Y coordinate of the paddle
     * assumptions: There is an image in the resources folder titled Paddle.png
     *
     */
    public Paddle(double startX, double startY) {
        try {
            Image img = new Image(new FileInputStream(PADDLE_IMAGE));
            myView = new ImageView(img);
        } catch (FileNotFoundException e) {
            System.out.println("Paddle image not found!");
            myView = new ImageView();
        }

        myView.setFitWidth(PADDLE_WIDTH);
        myView.setFitHeight(PADDLE_HEIGHT);
        myView.setX(startX);
        myView.setY(startY);

        xCoordinate = startX;
    }
    
    // paddle constructor with default position
    public Paddle() {
    	this(DEFAULT_POS_X, DEFAULT_POS_Y);
    }

    // getter for Game / collision checks
    public Node asNode() {
        return myView;
    }

    // move paddle based on key input
    /*
     * method: move
     * inputs: a KeyCode which represents a key on the keyboard the player presses
     * outputs: moves the paddle object
     * */
    public void move(KeyCode code, double elapsedTime) {
        if (code == KeyCode.LEFT) {
            xCoordinate -= PADDLE_VELOCITY * elapsedTime / 1000;
        } else if (code == KeyCode.RIGHT) {
            xCoordinate += PADDLE_VELOCITY * elapsedTime / 1000;
        }
        // make sure paddle doesn't go off screen
        if (xCoordinate < 0) xCoordinate = 0;
        if (xCoordinate + PADDLE_WIDTH > 480) xCoordinate = 480 - PADDLE_WIDTH; // assuming screen width 480
        myView.setX(xCoordinate);
    }
    
    @Override
    public boolean checkCollision(Ball ball) {
        return myView.getBoundsInParent().intersects(ball.getView().getBoundsInParent());
    }

    @Override
    public void onCollision(Ball ball) {
        // Move the ball just above the paddle to prevent sticking
        ball.setY(myView.getY() - ball.getView().getFitHeight() - 1);

        // Pick a random upward angle: 45° to 135°
        double minAngle = Math.toRadians(45);
        double maxAngle = Math.toRadians(135);
        double angle = minAngle + Math.random() * (maxAngle - minAngle);

        // Keep speed magnitude, apply new random upward angle
        ball.setVelocityFromAngle(angle);
    }





    @Override
    public Node getView() {
        return myView;
    }
}