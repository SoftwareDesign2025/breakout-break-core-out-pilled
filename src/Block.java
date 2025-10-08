import java.awt.Rectangle;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block {
	public static final int BLOCK_HEIGHT = 50; //placeholder
	public static final int BLOCK_WIDTH = 100; //placeholder 
	public static final int POINT_VALUE = 1; //could be moved to Game class
	
	private Rectangle myBlock;
	
	// make a new block at a specific point -> will be called in a loop (with differing coords) in Game class
	public Block(Image image, int startX, int startY) {
		myBlock.setSize(BLOCK_WIDTH, BLOCK_HEIGHT);
		
	}
	
	// remove block method to be called when collision is detected between ball & block
	public void removeBlock() {
		
	}
	
	
	
}
