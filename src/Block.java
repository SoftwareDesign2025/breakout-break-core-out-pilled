import java.awt.Point;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @author Caelan Duncan
 */
public class Block {
	private static final int POINTMULTIPLIER = 10;
	
	private int myHealth;
	private int points;
	
	private Color[] gradient = {Color.RED, Color.ORANGE, Color.YELLOW, Color.BLUE, Color.GREEN};
	
	private PowerUp myPower;
	private Rectangle myBlock = new Rectangle();
	
	
	// make a new block at a specific point -> will be called in a loop (with differing coords) in Game class
	public Block(int health, Point location, int width, int height, PowerUp power) {
		myBlock.setX(location.getX());
		myBlock.setY(location.getY());
		myBlock.setWidth(width);
		myBlock.setHeight(height);
		myBlock.setFill(gradient[health]);
		
		myPower = power;
		myHealth = health;
		points = myHealth * POINTMULTIPLIER;
	}
	
	public Node getBlock() {
		return myBlock;
	}
	
	/**
	 * Reduces health of the block and then breaks it if it's at 0. Only returns non-zero points when breaking.
	 * When points are returned, the scene needs to remove the block from the tree
	 * 
	 * @return points given
	 */
	public int hit() {
		myHealth -= 1;
		
		if (myHealth == 0) {
			myPower.drop();
			return points;
		}
		
		myBlock.setFill(gradient[myHealth]);
		
		return 0;
	}
}



