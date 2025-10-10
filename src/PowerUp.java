import java.awt.Point;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * @author Caelan Duncan
 */
public abstract class PowerUp {
	public static final int MOVING_VELOCITY = 80;
	
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
	
	public abstract void activate();
}