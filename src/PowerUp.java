import java.awt.Point;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * Defines the seen object, actual implementation of each powerups affects will need to be done in game and whatever
 * class it is effecting
 * 
 * @author Caelan Duncan
 */
public class PowerUp {
	public static final int MOVING_VELOCITY = 80;
	public static final long DURATION = 15000;
	
	protected ImageView myView;
	protected Point2D myVelocity;
	
	public PowerUp(Point pos) {
		myVelocity = new Point2D(0, 0);
	}
	
	public void drop() {
		myVelocity = new Point2D(0, MOVING_VELOCITY);
	}
	
	public void move(double elapsedTime) {
		myView.setY(myView.getY() + myVelocity.getY() * elapsedTime);
	}
	
	public Node getView() {
		return myView;
	}
	
	public boolean intersects(ImageView other) {
		return myView.getBoundsInParent().intersects(other.getBoundsInParent());
	}
	
	/**
	 * When this returns, the effect that was originally put in place should be reverted.
	 * 
	 * @return	true when the duration has passed
	 */
	public boolean powerUpTimer() {
		try {
			Thread.sleep(DURATION);
		} catch (InterruptedException e) {}
		
		return true;
	}
}