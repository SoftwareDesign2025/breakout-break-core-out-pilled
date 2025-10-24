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
    public static final int PADDLE_VELOCITY = 5;
    public static final String PADDLE_IMAGE = "resources/Paddle.png";
    public static final int MIN_RANDOMIZING = -5;
    public static final int MAX_RANDOMIZING = 5;
    private ImageView myView;
    private int xCoordinate;
    private int yCoordinate;

    /*
     * method: Paddle (constructor)
     * arguments (startX, startY) represent the starting X and Y coordinate of the paddle
     * assumptions: There is an image in the resources folder titled Paddle.png
     *
     */
    public Paddle(int startX, int startY) {
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

    // getter for Game 
    public Node asNode() {
        return myView;
    }

    // move paddle based on key input
    /*
     * method: move
     * inputs: a KeyCode which represents a key on the keyboard the player presses
     * outputs: moves the paddle object
     * */
    public void move(KeyCode code) {
        if (code == KeyCode.LEFT) {
            xCoordinate -= PADDLE_VELOCITY;
        } else if (code == KeyCode.RIGHT) {
            xCoordinate += PADDLE_VELOCITY;
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
        // just make ball bounce up
    	ball.reverseY();
    	Random random = new Random();
    	int reverseX = random.nextInt(0, 2);
    	if(reverseX > 0) {
    		ball.reverseX();
    	}
    }

    @Override
    public Node getView() {
        return myView;
    }
}