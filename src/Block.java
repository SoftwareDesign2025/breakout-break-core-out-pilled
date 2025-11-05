import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * A block that can be hit by the ball.
 * Loses health and gives points when broken.
 */
public class Block implements Collidable {
	
	// constants/getters for general block properties:
	public static final int SIZE_X = 40;
	public static final int SIZE_Y = 12;

	// local constants
    private static final int POINT_MULTIPLIER = 10;
    private static final String IMAGE_RESOURCES = "resources/brick%s.gif";

    private int myHealth;
    private int myPoints;
    private boolean destroyed = false;

    private PowerUp myPower; // can be null
    protected ImageView myView;

    public Block(int xCoord, int yCoord, int health) {
        setImage(health);

        // set size and position
        myView.setFitWidth(SIZE_X);
        myView.setFitHeight(SIZE_Y);
        myView.setX(xCoord);
        myView.setY(yCoord);

        myHealth = health;
        myPoints = myHealth * POINT_MULTIPLIER;
    }
    
    protected void setImage(int health) {
    	try {
            Image img = new Image(new FileInputStream(String.format(IMAGE_RESOURCES, health)));
            myView = new ImageView(img);
        } catch (FileNotFoundException e) {
            myView = new ImageView();
            System.out.println("Block image not found for health: " + health);
        }
    }
    
    public void setPowerup(PowerUp powerup) {
    	myPower = powerup;
    }

    // show block in scene
    public Node asNode() {
        return myView;
    }

    // called when the ball hits this block
    protected int hit() {
        if (destroyed) return 0; // already gone

        myHealth -= 1;

        if (myHealth <= 0) {
            destroyed = true;
            myView.setVisible(false); // hide block

            if (myPower != null) {
                myPower.drop(); // drop powerup if it exists
            }

            return myPoints; // give points only when destroyed
        } else {
        	try {
                Image img = new Image(new FileInputStream(String.format(IMAGE_RESOURCES, myHealth)));
                myView.setImage(img);
            } catch (FileNotFoundException e) {
                System.out.println("Block image not found for health: " + myHealth);
            }
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
//        hit();         // reduce health / mark destroyed - commented, called from Level
        
        Bounds bounds1 = myView.getBoundsInParent();
        Bounds bounds2 = ball.getView().getBoundsInParent();
        
        double intersectX = Math.max(bounds1.getMinX(), bounds2.getMinX());
        double intersectY = Math.max(bounds1.getMinY(), bounds2.getMinY());
        double intersectWidth = Math.min(bounds1.getMaxX(), bounds2.getMaxX()) - intersectX;
        double intersectHeight = Math.min(bounds1.getMaxY(), bounds2.getMaxY()) - intersectY;
        
        if (intersectHeight >= intersectWidth) {
        	ball.reverseX();

        	double moveAmount = determineMoveDirection(intersectWidth, ball.getDx());
        	
        	ball.setX(ball.getX() + moveAmount);
        }
        else {
        	ball.reverseY();
        	
        	double moveAmount = determineMoveDirection(intersectHeight, ball.getDy());
        	
        	ball.setY(ball.getY() + moveAmount);
        }
        
    }
    
    private double determineMoveDirection(double moveAmount, double velocity) {
    	if (velocity < 0) {
    		return -moveAmount;
    	}
    	
    	return moveAmount;
    }

    @Override
    public Node getView() {
        return myView;
    }
}