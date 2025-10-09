import java.awt.Point;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Caelan Duncan
 */
public class Block {
	private static final int POINTMULTIPLIER = 10;
	
	private int myHealth;
	private int points;
	
	private PowerUp myPower;
	private Rectangle myBlock = new Rectangle();
	
	// make a new block at a specific point -> will be called in a loop (with differing coords) in Game class
	public Block(int health, Point location, int width, int height, PowerUp power) {
		myBlock.setX(location.getX());
		myBlock.setY(location.getY());
		myBlock.setWidth(width);
		myBlock.setHeight(height);
		myBlock.setFill(Color.BLUE);
		
		myPower = power;
		myHealth = health;
		points = myHealth * POINTMULTIPLIER;
	}
	
	/**
	 * Reduces health of the block and then breaks it if it's at 0. Only returns non-zero points when breaking.
	 * 
	 * @return points given
	 */
	public int hit() {
		myHealth -= 1;
		
		if (myHealth == 0) {
			breakBlock();
			return points;
		}
		
		return 0;
	}
	
	private void breakBlock() {
		myPower.drop();
	}
}



