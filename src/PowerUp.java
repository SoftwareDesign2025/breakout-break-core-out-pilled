import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Defines the seen object, actual implementation of each powerups affects will need to be done in game and whatever
 * class it is effecting
 * 
 * @author Caelan Duncan
 */
public abstract class PowerUp {
	protected static final int MOVING_VELOCITY = 200;
	protected static final int SIZE = 12;
	protected static final String POWER_IMAGE = "resources/%spower.gif";
	
	protected String powerName;
	protected ImageView myView;
	protected int myVelocity;
	
	public PowerUp(int xCoord, int yCoord) {
		try {
			Image img = new Image(new FileInputStream(String.format(POWER_IMAGE, powerName)));
			myView = new ImageView(img);
		} 
		catch (FileNotFoundException e) {}
		
		myView.setFitWidth(SIZE);
        myView.setFitHeight(SIZE);
        myView.setX(xCoord);
        myView.setY(yCoord);
		
		myVelocity = 0;
	}
	
	public void drop() {
		myVelocity = MOVING_VELOCITY;
	}
	
	public void move(double elapsedTime) {
		myView.setY(myView.getY() + myVelocity * elapsedTime / 1000);
	}
	
	public Node getView() {
		return myView;
	}
	
	public boolean intersects(Node other) {
		return myView.getBoundsInParent().intersects(other.getBoundsInParent());
	}
	
	public void activatePower() {
		myView.setX(0);
		myView.setY(0);
		myView.setVisible(false);
	}
}