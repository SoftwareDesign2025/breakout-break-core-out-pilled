import java.awt.Point;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Caelan Duncan
 */
public class Block {
	private static final int POINT_MULTIPLIER = 10;
	private static final String IMAGE_RESOURCES = "resources/brick%s.gif";
	
	private int myHealth;
	private int points;
	
	private PowerUp myPower;
	private ImageView myView;
	
	
	// make a new block at a specific point -> will be called in a loop (with differing coords) in Game class
	/**
	 * 
	 * @param health	amount of health the block should have
	 * @param location	top left corner of the block
	 * @param width		width of the block
	 * @param height	height of the block
	 * @param power		what power, if any the block has
	 */
	public Block(int health, Point location, int width, int height, PowerUp power) {
		try {
			Image img = new Image(new FileInputStream(String.format(IMAGE_RESOURCES, health)));
			myView = new ImageView(img);
		}
		catch(FileNotFoundException e) {}
		
		// defining size and location
		myView.setFitWidth(width);
		myView.setFitHeight(height);
		myView.setX(location.getX());
		myView.setY(location.getY());
		
		myPower = power;
		myHealth = health;
		points = myHealth * POINT_MULTIPLIER;
	}
	
	// allows the game to display the block
	public Node asNode() {
		return myView;
	}
	
	/**
	 * Reduces health of the block and then breaks it if it's at 0. Only returns non-zero points when breaking.
	 * When non-zero points are returned, the scene needs to remove the block from the root
	 * 
	 * @return points
	 */
	public int hit() {
		myHealth -= 1;
		
		if (myHealth == 0) {
			myPower.drop();
			return points;
		}
				
		return 0;
	}
}



