import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

/**
 * A block that can be hit by the ball.
 * Loses health and gives points when broken.
 */
public class Block implements Collidable {
	
	// constants/getters for general block properties:
	public static final int SIZE_X = 40;
	public static final int SIZE_Y = 12;
	
	public static int getX() {
		return SIZE_X;
	}
	
	public static int getY() {
		return SIZE_Y;
	}

	// local constants
    private static final int POINT_MULTIPLIER = 10;
    private static final String IMAGE_RESOURCES = "resources/brick%s.gif";

    private int myHealth;
    private int myPoints;
    private boolean destroyed = false;

    private PowerUp myPower; // can be null
    private ImageView myView;
    
    private ArrayList<Rectangle> horizontalBounds = new ArrayList<>();
    private ArrayList<Rectangle> verticalBounds = new ArrayList<>();

    public Block(int xCoord, int yCoord, int health, PowerUp power) {
        try {
            Image img = new Image(new FileInputStream(String.format(IMAGE_RESOURCES, health)));
            myView = new ImageView(img);
        } catch (FileNotFoundException e) {
            myView = new ImageView();
            System.out.println("Block image not found for health: " + health);
        }

        // set size and position
        myView.setFitWidth(SIZE_X);
        myView.setFitHeight(SIZE_Y);
        myView.setX(xCoord);
        myView.setY(yCoord);

        myHealth = health;
        myPoints = myHealth * POINT_MULTIPLIER;
        myPower = power;
    }

    // show block in scene
    public Node asNode() {
        return myView;
    }

    // called when the ball hits this block
    private int hit() {
        if (destroyed) return 0; // already gone

        myHealth -= 1;

        if (myHealth <= 0) {
            destroyed = true;
            myView.setVisible(false); // hide block

            if (myPower != null) {
                myPower.drop(); // drop powerup if it exists
            }

            return myPoints; // give points only when destroyed
        }

        return 0;
    }

    // tells game if block is gone
    public boolean isDestroyed() {
        return destroyed;
    }

    // ----------------------------
    // Collidable interface methods
    // ----------------------------

    @Override
    public boolean checkCollision(Ball ball) {
        // simple bounds check
        return !destroyed && myView.getBoundsInParent().intersects(ball.getView().getBoundsInParent());
    }

    @Override
    public void onCollision(Ball ball) {
        hit();         // reduce health / mark destroyed
        
        Bounds bounds1 = myView.getBoundsInParent();
        Bounds bounds2 = ball.getView().getBoundsInParent();
        
        // calculating size of collision box
        double intersectX = Math.max(bounds1.getMinX(), bounds2.getMinX());
        double intersectY = Math.max(bounds1.getMinY(), bounds2.getMinY());
        double intersectWidth = Math.min(bounds1.getMaxX(), bounds2.getMaxX()) - intersectX;
        double intersectHeight = Math.min(bounds1.getMaxY(), bounds2.getMaxY()) - intersectY;
        
        if (intersectHeight >= intersectWidth) ball.reverseX();
        else ball.reverseY();
    }

    @Override
    public Node getView() {
        return myView;
    }
}
